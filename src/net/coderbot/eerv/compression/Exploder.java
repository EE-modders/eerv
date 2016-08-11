package net.coderbot.eerv.compression;

import java.io.EOFException;
import java.nio.BufferUnderflowException;
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
	int dictBits;
	int dictMask;
	int buff;
	int extraBits;
	ByteBuffer in;
	ByteBuffer out;
	
	boolean end;
	
	public Exploder(ByteBuffer in)
	{
		if(in.remaining() < 4)
			throw new IllegalArgumentException("Data too small");
		this.in = in;
		out = ByteBuffer.allocate(0x2204);
		out.position(0x1000);
				
		binary = in.get()==0;
		dictBits = in.get()&0xFF;
		buff = in.get()&0xFF;
		extraBits = 0;
		
		if(dictBits < 4 || dictBits > 6) 
			throw new IllegalArgumentException("Invalid dictionary size "+dictBits);

		dictMask = 0xFFFF >>> (16 - dictBits);
	}
	
	void update(int bits)
	{
		//System.out.println("UseBits "+bits);
		if(bits <= extraBits)
		{
			extraBits -= bits;
			buff >>>= bits;
			return;
		}

		// Load input buffer if necessary
		buff >>>= extraBits;

		// Update bit buffer
		buff |= ((in.get()&0xFF) << 8);
		buff >>>= (bits - extraBits);
		extraBits = (extraBits - bits) + 8;
	}
	
	int DecodeLit() throws EOFException
	{
		int isRep = buff&1;
		update(1);
		int nextByte = buff&0xFF;
		
		if(isRep==1)
		{
			int code = LengthCodes[nextByte]&0xFF;
			//System.out.println("code "+code+" base "+(int)LenBase[code]);
			
			update(LenBits[code]&0xFF);
			int extraBits = ExLenBits[code]&0xFF;
			//Compute the bitmask for the bits to take.
			
			int extra = buff&((1<<extraBits)-1);
			try
			{
				update(extraBits);
			}
			catch(BufferUnderflowException e)
			{
				//System.out.println("Exception!");
				if((code + extra) != 0x10E)
				{
					throw e;
				}
			}
			
			//System.out.println("extra "+extra+" extra_bits "+extraBits+" code_bits "+LenBits[code]);
			//System.out.println("length "+((extraBits!=0?LenBase[code]:code) + extra + 2));
			
			return (extraBits!=0?LenBase[code]:code) + 0x100 + extra;
		}
		else
		{
			if(!binary)
			{
				if(nextByte>0)
				{
					int value = ascIlut[nextByte];

					if(value == 0xFF)
					{
						if((buff&0x3F)>0)
						{
							update(4);
							value = asc4lut[nextByte];
						}
						else
						{
							update(6);
							value = asc6lut[nextByte&0x7F];
						}
					}
					
					update(ChBitsAsc[value]);
					return value;
				}
				else
				{
					update(8);
					int value = asc8lut[nextByte];
					update(ChBitsAsc[value]);
					return value;
				}
			}
			
			update(8);
			return nextByte;
		}
	}
	
	int DecodeDist(int length) throws EOFException
	{
		int distance; // Distance position

		// Next 2-8 bits in the input buffer is the distance position code
		int code = DistPosCodes[buff & 0xFF]&0xFF;
		
		//System.out.println("code "+code+" code_bits "+DistBits[code]+" idx "+(buff&0xFF)+" "+in);
		update(DistBits[code]);
		
		if(length == 2)
		{
			// If the repetition is only 2 bytes length,
			// then take 2 bits from the stream in order to get the distance
			distance = (code << 2) | (buff & 0x03);
			update(2);
		}
		else
		{
			// If the repetition is more than 2 bytes length,
			// then take "dictBits" bits in order to get the distance
			distance = (code << dictBits) | (buff & dictMask);
			update(dictBits);
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
		int lits = 0;
		while((literal = DecodeLit())<0x305)
		{
			/*int length = literal-0xFE;
			System.out.print(literal>=0x100?"[Pair] Len: "+(literal-0xFE)+"\n":"[Byte] "+literal+"\n");
			if(literal>=0x100)
			{
				int dist = DecodeDist(length);
				System.out.println(" Dist: "+dist);
			}
			lits++;
			
			if(lits>1000)
			{
				System.out.println("Exit");
				System.exit(0);
			}*/
			
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
				
				return start;
			}
		}
		
		int remaining = out.position() - 0x1000;
		
		out.position(0x1000);
		out = out.slice();
		out.limit(remaining);
		
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