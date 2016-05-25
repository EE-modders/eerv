package net.coderbot.eerv;

import java.io.EOFException;
import java.nio.ByteBuffer;

/**
 * PKWARE DCL Decompressor, ported to Java from StormLib.<br>
 * You can view StormLib here: http://zezula.net/en/mpq/stormlib.html<br>
 * native version (C) Ladislav Zezula, 2003.<br>
 * <h1>The library [StormLib] is free, no license is needed to use it in your projects.</h1>
 * 
 * @author coderbot
 */
public class Exploder extends ExploderConstants
{
	boolean binary;
	int dict_bits;
	int dict_mask;
	int bit_buff;
	int extra_bits;
	ByteBuffer in;
	ByteBuffer out;
	boolean end;
	
	public Exploder(ByteBuffer in)
	{
		if(in.remaining() <= 4)
			throw new IllegalArgumentException("Data too small");
		this.in = in;
		out = ByteBuffer.allocate(0x2204);
		out.position(0x1000);
				
		binary = in.get()==0;
		dict_bits = in.get()&0xFF;
		bit_buff = in.get()&0xFF;
		extra_bits = 0;
		
		if(4 > dict_bits || dict_bits > 6) 
			throw new IllegalArgumentException("Invalid dictionary size "+dict_bits);

		dict_mask = 0xFFFF >>> (0x10 - dict_bits);
	}
	
	void waste(int bits) throws EOFException
	{
		if(bits <= extra_bits)
		{
			extra_bits -= bits;
			
			bit_buff >>>= bits;
			return;
		}

		// Load input buffer if necessary
		bit_buff >>>= extra_bits;
		if(!in.hasRemaining())
		{
			throw new EOFException();
		}

		// Update bit buffer
		bit_buff |= ((in.get()&0xFF) << 8);
		bit_buff >>>= (bits - extra_bits);
		extra_bits = (extra_bits - bits) + 8;
	}

	// 0x000 to 0xFF: ret
	//>0x100: [rep length] = ret-254
	int DecodeLit() throws EOFException
	{
		int extra_length_bits;	// Number of bits of extra literal length
		int length_code;		 // Length code
		int value;

		// Test the current bit in byte buffer. If is not set, simply return the next 8 bits.
		if((bit_buff&1)==1)
		{
			// Remove one bit from the input data
			waste(1);
						 
			// The next 8 bits hold the index to the length code table
			length_code = LengthCodes[bit_buff & 0xFF]&0xFF;
			
			// Remove the apropriate number of bits
			waste(LenBits[length_code]&0xFF);

			// Are there some extra bits for the obtained length code ?
			if((extra_length_bits = ExLenBits[length_code]&0xFF) != 0)
			{
				int extra_length = bit_buff & ((1 << extra_length_bits) - 1);
				try
				{
					waste(extra_length_bits);
				}
				catch(EOFException e)
				{
					if((length_code + extra_length) != 0x10E)
					{
						throw e;
					}
				}
				length_code = LenBase[length_code] + extra_length;
			}

			// In order to distinguish uncompressed byte from repetition length,
			// we have to add 0x100 to the length.
			return length_code + 0x100;
		}

		// Remove one bit from the input data
		waste(1);

		// If the binary compression type, read 8 bits and return them as one byte.
		if(binary)
		{
			int uncompressed_byte = bit_buff & 0xFF;
			waste(8);
			return uncompressed_byte;
		}
		else if((bit_buff & 0xFF)>0)
		{
			value = ascIlut[bit_buff & 0xFF];

			if(value == 0xFF)
			{
				if((bit_buff & 0x3F)>0)
				{
					waste(4);
					value = asc4lut[bit_buff & 0xFF];
				}
				else
				{
					waste(6);
					value = asc6lut[bit_buff & 0x7F];
				}
			}
		}
		else
		{
			waste(8);
			value = asc8lut[bit_buff & 0xFF];
		}

		waste(ChBitsAsc[value]);
		return value;
	}

	
	int DecodeDist(int length) throws EOFException
	{
		int dist_pos_code;			// Distance position code
		int dist_pos_bits;			// Number of bits of distance position
		int distance;				 // Distance position

		// Next 2-8 bits in the input buffer is the distance position code
		dist_pos_code = DistPosCodes[bit_buff & 0xFF]&0xFF;
		dist_pos_bits = DistBits[dist_pos_code]&0xFF;
		waste(dist_pos_bits);

		if(length == 2)
		{
			// If the repetition is only 2 bytes length,
			// then take 2 bits from the stream in order to get the distance
			distance = (dist_pos_code << 2) | (bit_buff & 0x03);
			waste(2);
		}
		else
		{
			// If the repetition is more than 2 bytes length,
			// then take "dsize_bits" bits in order to get the distance
			distance = (dist_pos_code << dict_bits) | (bit_buff & dict_mask);
			waste(dict_bits);
		}
		
		return distance + 1;
	}
	
	/**
	 * Decodes a block. If it is the end, decrements in.position by 1.
	 * @return
	 * @throws EOFException
	 */
	public ByteBuffer nextBlock() throws EOFException
	{
		if(end)
		{
			return null;
		}
		
		int literal = 0;
		while((literal = DecodeLit())<0x305)
		{
			if(literal>=0x100)
			{
				int length = literal - 0xFE;
				
				ByteBuffer src = out.duplicate();
				int dist = DecodeDist(length);
				src.position(src.position()-dist);
				
				
				//copy repeating
				while(length-- > 0)
					out.put(src.get());
			}
			else
			{
				out.put((byte)literal);
			}
			
			if(out.position() >= 0x2000)
			{
				ByteBuffer start = out.duplicate();
				int outPos = out.position();
				out.position(0x1000);
				start.position(0);
				start.put(out);
				out.position(outPos-0x1000);
				start.position(0);
				start.limit(0x1000);
				try {
					SSA.cap.set(start, 0x1000);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				
				return start;
			}
		}
		
		int remaining = out.position() - 0x1000;
		
		out.position(0x1000);
		out = out.slice();
		out.limit(remaining);
		try {
			SSA.cap.set(out, remaining);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		end = true;
		if(in.position()>0)
		{
			//We grabbed an extra byte.
			in.position(in.position()-1);
		}
		
		return out;
	}
	
	public boolean remaining()
	{
		return !end;
	}
}