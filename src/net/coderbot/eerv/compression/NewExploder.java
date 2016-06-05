package net.coderbot.eerv.compression;

public class NewExploder extends ExploderConstants
{
	int buff;
	
	public void update(int consumed)
	{
		//TODO
	}
	
	//Max consumption is 16 bytes
	int decode()
	{
		int isRep = buff&1;
		update(1);
		int nextByte = buff&0xFF;
		
		if(isRep==1)
		{
			int code = LengthCodes[nextByte]&0xFF;
			
			update(LenBits[code]&0xFF);
			int extraBits = ExLenBits[code]&0xFF;
			//Compute the bitmask for the bits to take.
			int extra = buff&((1<<extraBits)-1);
			update(extraBits);
				
			return code + extra + 0x100;
		}
		else
		{
			update(8);
			return nextByte;
		}
	}
	
	int decode2(int buff)
	{
		int isRep = buff&1;
		int nextByte = (buff&0x1FE)>>1;
		
		if(isRep==1)
		{
			int code = LengthCodes[nextByte]&0xFF;
			int bitsUsed = LenBits[code]+1;
			int extraBits = ExLenBits[code];
			int extra = buff&((1<<extraBits+bitsUsed)-1);
			
			update(extraBits+bitsUsed);
			return code + (extra>>>bitsUsed) + 0x100;
		}
		else
		{
			update(9);
			return nextByte;
		}
	}
}
