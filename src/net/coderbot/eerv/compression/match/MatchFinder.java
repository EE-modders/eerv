package net.coderbot.eerv.compression.match;

import java.util.Arrays;

import net.coderbot.eerv.compression.TCmpStruct;

public class MatchFinder
{
	// Macro for calculating hash of the current byte pair.
	// Note that most exact byte pair hash would be buffer[0] + buffer[1] << 0x08,
	// but even this way gives nice indication of equal byte pairs, with significantly
	// smaller size of the array that holds numbers of those hashes
	
	public static int BYTE_PAIR_HASH(byte[] buffer, int index)
	{
		return ((buffer[index]&0xFF) * 4) + ((buffer[index+1]&0xFF) * 5);
	}
	
	/* 
	 * Local functions
	 * Builds the "hash_to_index" table and "pair_hash_offsets" table.
	 * Every element of "hash_to_index" will contain lowest index to the
	 * "pair_hash_offsets" table, effectively giving offset of the first
	 * occurence of the given PAIR_HASH in the input data.
	 */
	static void SortBuffer(TCmpStruct pWork, byte[] buffer, int begin, int end)
	{
		System.out.println("Sort buff "+begin+"->"+end+" "+(buffer==pWork.work_buff));
		
		// Zero the entire "phash_to_index" table
		Arrays.fill(pWork.phash_to_index, '\0');
		
		// Step 1: Count amount of each PAIR_HASH in the input buffer
		// The table will look like this:
		//  offs 0x000: Number of occurences of PAIR_HASH 0
		//  offs 0x001: Number of occurences of PAIR_HASH 1
		//  ...
		//  offs 0x8F7: Number of occurences of PAIR_HASH 0x8F7 (the highest hash value)
		
		for(int buffer_idx = begin; buffer_idx < end; buffer_idx++)
		{
			int hash = BYTE_PAIR_HASH(buffer, buffer_idx);
			
			System.out.println(hash+" "+((int)pWork.phash_to_index[hash]+1));
			pWork.phash_to_index[hash]++;
		}

		// Step 2: Convert the table to the array of PAIR_HASH amounts. 
		// Each element contains count of PAIR_HASHes that is less or equal
		// to element index
		// The table will look like this:
		//  offs 0x000: Number of occurences of PAIR_HASH 0 or lower
		//  offs 0x001: Number of occurences of PAIR_HASH 1 or lower
		//  ...
		//  offs 0x8F7: Number of occurences of PAIR_HASH 0x8F7 or lower
		
		char total_sum = 0;
		for(int i = 0; i < pWork.phash_to_index.length; i++)
		{
			total_sum += pWork.phash_to_index[i];
			pWork.phash_to_index[i] = total_sum;
		}

		// Step 3: Convert the table to the array of indexes.
		// Now, each element contains index to the first occurence of given PAIR_HASH
		
		int byte_pair_hash;		   // Hash value of the byte pair
		for(end--; end >= begin; end--)
		{
			byte_pair_hash = BYTE_PAIR_HASH(buffer, end);

			System.out.println(byte_pair_hash+" "+(pWork.phash_to_index[byte_pair_hash]-1));
			
			pWork.phash_to_index[byte_pair_hash]--;
			pWork.phash_offs[pWork.phash_to_index[byte_pair_hash]] = (char)end;
		}
	}
	
	public static final int MAX_REP_LENGTH = 0x204;			// The longest allowed repetition
	
	static int FindRep(TCmpStruct pWork, byte[] input_data, int offs)
	{
		char phash_to_index_offs;			// Pointer into pWork.phash_to_index table
		char phash_offs_offs;				// Pointer to the table containing offsets of each PAIR_HASH
		int repetition_limit_offs;		   // An eventual repetition must be at position below this pointer
		int prev_repetition_offs;			// Pointer to the previous occurence of the current PAIR_HASH
		int prev_rep_end_offs;			   // End of the previous repetition
		char phash_offs_index;			// Index to the table with PAIR_HASH positions
		char min_phash_offs;			  // The lowest allowed hash offset
		char offs_in_rep;				 // Offset within found repetition
		int equal_byte_count;			  // Number of bytes that are equal to the previous occurence
		int rep_length = 1;				// Length of the found repetition
		int rep_length2;				   // Secondary repetition
		char di_val;

		// Calculate the previous position of the PAIR_HASH
		phash_to_index_offs   =  (char)BYTE_PAIR_HASH(input_data, offs);
		min_phash_offs   = (char)(offs - pWork.dsize_bytes + 1);
		phash_offs_index = pWork.phash_to_index[phash_to_index_offs];

		// If the PAIR_HASH offset is below the limit, find a next one
		phash_offs_offs = phash_offs_index;
		if(pWork.phash_offs[phash_offs_offs] < min_phash_offs)
		{
			while(pWork.phash_offs[phash_offs_offs] < min_phash_offs)
			{
				phash_offs_index++;
				phash_offs_offs++;
			}
			pWork.phash_to_index[phash_to_index_offs] = phash_offs_index;
		}

		// Get the first location of the PAIR_HASH,
		// and thus the first eventual location of byte repetition
		phash_offs_offs = phash_offs_index;
		prev_repetition_offs = pWork.phash_offs[phash_offs_offs];
		repetition_limit_offs = offs - 1;
		
		// If the current PAIR_HASH was not encountered before,
		// we haven't found a repetition.
		if(prev_repetition_offs >= repetition_limit_offs)
			return 0;

		// We have found a match of a PAIR_HASH. Now we have to make sure
		// that it is also a byte match, because PAIR_HASH is not unique.
		// We compare the bytes and count the length of the repetition
		int input_data_offs = offs;
		for(;;)
		{
			// If the first byte of the repetition and the so-far-last byte
			// of the repetition are equal, we will compare the blocks.
			if(input_data[input_data_offs] == input_data[prev_repetition_offs] && input_data[input_data_offs+rep_length-1] == input_data[prev_repetition_offs+rep_length-1])
			{
				// Skip the current byte
				prev_repetition_offs++;
				input_data_offs++;
				equal_byte_count = 2;

				// Now count how many more bytes are equal
				while(equal_byte_count < MAX_REP_LENGTH)
				{
					prev_repetition_offs++;
					input_data_offs++;
					
					// Are the bytes different ?
					if(input_data[prev_repetition_offs] != input_data[input_data_offs])
						break;

					equal_byte_count++;
				}

				// If we found a repetition of at least the same length, take it.
				// If there are multiple repetitions in the input buffer, this will
				// make sure that we find the most recent one, which in turn allows
				// us to store backward length in less amount of bits
				input_data_offs = offs;
				if(equal_byte_count >= rep_length)
				{
					// Calculate the backward distance of the repetition.
					// Note that the distance is stored as decremented by 1
					pWork.distance = offs - prev_repetition_offs + equal_byte_count - 1;

					// Repetitions longer than 10 bytes will be stored in more bits,
					// so they need a bit different handling
					if((rep_length = equal_byte_count) > 10)
						break;
				}
			}

			// Move forward in the table of PAIR_HASH repetitions.
			// There might be a more recent occurence of the same repetition.
			phash_offs_index++;
			phash_offs_offs++;
			prev_repetition_offs = pWork.phash_offs[phash_offs_offs];

			// If the next repetition is beyond the minimum allowed repetition, we are done.
			if(prev_repetition_offs >= repetition_limit_offs)
			{
				// A repetition must have at least 2 bytes, otherwise it's not worth it
				return (rep_length >= 2) ? rep_length : 0;
			}
		}

		// If the repetition has max length of 0x204 bytes, we can't go any fuhrter
		if(equal_byte_count == MAX_REP_LENGTH)
		{
			pWork.distance--;
			return equal_byte_count;
		}

		// Check for possibility of a repetition that occurs at more recent position
		phash_offs_offs = phash_offs_index;
		if(pWork.phash_offs[phash_offs_offs+1] >= repetition_limit_offs)
			return rep_length;

		//
		// The following part checks if there isn't a longer repetition at
		// a latter offset, that would lead to better compression.
		//
		// Example of data that can trigger this optimization:
		//
		//   "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEQQQQQQQQQQQQ"
		//   "XYZ"
		//   "EEEEEEEEEEEEEEEEQQQQQQQQQQQQ";
		//
		// Description of data in this buffer
		//   [0x00] Single byte "E"
		//   [0x01] Single byte "E"
		//   [0x02] Repeat 0x1E bytes from [0x00]
		//   [0x20] Single byte "X"
		//   [0x21] Single byte "Y"
		//   [0x22] Single byte "Z"
		//   [0x23] 17 possible previous repetitions of length at least 0x10 bytes:
		//		  - Repetition of 0x10 bytes from [0x00] "EEEEEEEEEEEEEEEE"
		//		  - Repetition of 0x10 bytes from [0x01] "EEEEEEEEEEEEEEEE"
		//		  - Repetition of 0x10 bytes from [0x02] "EEEEEEEEEEEEEEEE"
		//		  ...
		//		  - Repetition of 0x10 bytes from [0x0F] "EEEEEEEEEEEEEEEE"
		//		  - Repetition of 0x1C bytes from [0x10] "EEEEEEEEEEEEEEEEQQQQQQQQQQQQ"
		//		  The last repetition is the best one.
		//
		
		pWork.offs09BC[0] = 0xFFFF;
		pWork.offs09BC[1] = 0x0000;
		di_val = 0;

		// Note: I failed to figure out what does the table "offs09BC" mean.
		// If anyone has an idea, let me know to zezula_at_volny_dot_cz
		for(offs_in_rep = 1; offs_in_rep < rep_length; )
		{
			if(input_data[offs_in_rep] != input_data[di_val])
			{
				di_val = pWork.offs09BC[di_val];
				if(di_val != 0xFFFF)
					continue;
			}
			pWork.offs09BC[++offs_in_rep] = ++di_val;
		}

		// 
		// Now go through all the repetitions from the first found one
		// to the current input data, and check if any of them migh be
		// a start of a greater sequence match.
		//

		prev_repetition_offs = pWork.phash_offs[phash_offs_offs];
		prev_rep_end_offs = prev_repetition_offs + rep_length;
		rep_length2 = rep_length;
		
		for(;;)
		{
			rep_length2 = pWork.offs09BC[rep_length2];
			if(rep_length2 == 0xFFFF)
				rep_length2 = 0;

			// Get the pointer to the previous repetition
			phash_offs_offs = phash_offs_index;

			// Skip those repetitions that don't reach the end
			// of the first found repetition
			do
			{
				phash_offs_offs++;
				phash_offs_index++;
				prev_repetition_offs = pWork.phash_offs[phash_offs_offs];
				if(prev_repetition_offs >= repetition_limit_offs)
					return rep_length;
			}
			while(prev_repetition_offs + rep_length2 < prev_rep_end_offs);

			// Verify if the last but one byte from the repetition matches
			// the last but one byte from the input data.
			// If not, find a next repetition			
			byte pre_last_byte = input_data[rep_length - 2];// Last but one byte from a repetion
			if(pre_last_byte == input_data[prev_repetition_offs+(rep_length - 2)])
			{
				// If the new repetition reaches beyond the end
				// of previously found repetition, reset the repetition length to zero.
				if(prev_repetition_offs + rep_length2 != prev_rep_end_offs)
				{
					prev_rep_end_offs = prev_repetition_offs;
					rep_length2 = 0;
				}
			}
			else
			{
				phash_offs_offs = phash_offs_index;
				do
				{
					phash_offs_offs++;
					phash_offs_index++;
					prev_repetition_offs = pWork.phash_offs[phash_offs_offs];
					if(prev_repetition_offs >= repetition_limit_offs)
						return rep_length;
				}
				while(input_data[prev_repetition_offs+(rep_length - 2)] != pre_last_byte || input_data[prev_repetition_offs] != input_data[0]);

				// Reset the length of the repetition to 2 bytes only
				prev_rep_end_offs = prev_repetition_offs + 2;
				rep_length2 = 2;
			}

			// Find out how many more byteacters are equal to the first repetition.
			while(input_data[prev_rep_end_offs] == input_data[rep_length2])
			{
				if(++rep_length2 >= 0x204)
					break;
				prev_rep_end_offs++;
			}

			// Is the newly found repetion at least as long as the previous one ?
			if(rep_length2 >= rep_length)
			{
				// Calculate the distance of the new repetition
				pWork.distance = offs - prev_repetition_offs - 1;
				if((rep_length = rep_length2) == 0x204)
					return rep_length;

				// Update the additional elements in the "offs09BC" table
				// to reflect new rep length
				while(offs_in_rep < rep_length2)
				{
					if(input_data[offs_in_rep] != input_data[di_val])
					{
						di_val = pWork.offs09BC[di_val];
						if(di_val != 0xFFFF)
							continue;
					}
					pWork.offs09BC[++offs_in_rep] = ++di_val;
				}
			}
		}
	}
}
