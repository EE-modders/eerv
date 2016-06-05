package net.coderbot.eerv.compression;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;

public class ImploderStream extends ExploderConstants
{
	WritableByteChannel outChannel;
	ByteBuffer out;
	int flushThresh;
	int dsize;
	int dmask;
	
	int nbitsOut = 0;
	byte current = 0;
	
	byte[] bitLengths;
	char[] codes;
	
	public void writeHeader(int mode, int dsize)
	{
		writeBits(8, mode);
		writeBits(8, dsize);
	}
	
	public void writeLiteral(byte b)
	{
		int i = b&0xFF;
		writeBits(bitLengths[i], codes[b]);
	}
	
	public void writeRepetition(int dist, int length)
	{
		writeBits(bitLengths[length + 0xFE], codes[length + 0xFE]);
		
		if(length == 2)
		{
			writeBits(DistBits[dist >>> 2], DistCode[dist >>> 2]);
			writeBits(2, dist & 3);
		}
		else
		{
			writeBits(DistBits[dist >>> dsize], DistCode[dist >>> dsize]);
			writeBits(dsize, dist&dmask);
		}
	}
	
	public void writeTermination()
	{
		writeBits(bitLengths[0x305], codes[0x305]);
	}
	
	public void writeBits(int nbits, int buf)
	{
		if(nbits>24)
		{
			throw new IllegalArgumentException("too many bits");
		}
		
		buf = (buf<<nbitsOut)|(current&0xFF);
		
		nbits+=nbitsOut;
		nbitsOut = 0;
		current = 0;
		
		nbitsOut = nbits&7;
		
		while(nbits>7)
		{
			out.put((byte)(buf&0xFF));
			buf>>=8;
			nbits-=8;
		}
		
		if((nbits&7)!=0)
		{
			current = (byte)buf;
		}
		
		if(out.position()>=flushThresh)
		{
			flush(false);
		}
	}
	
	public void flush(boolean writeCurrentBits)
	{
		if(writeCurrentBits&&out.hasRemaining())
		{
			out.put(current);
			nbitsOut = 0;
			current = 0;
			writeCurrentBits = false;
		}
		
		out.flip();
		try
		{
			outChannel.write(out);
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.clear();
		
		if(writeCurrentBits)
		{
			//Try again, with an empty buffer.
			flush(true);
		}
	}
}
