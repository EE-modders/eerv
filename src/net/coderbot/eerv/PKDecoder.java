package net.coderbot.eerv;

import java.io.EOFException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;

import net.coderbot.log.Log;
import net.coderbot.util.Decoder;
import net.coderbot.util.DecoderException;

public class PKDecoder extends Decoder<ByteBuffer>
{
	ByteBuffer in;
	
	public PKDecoder(ByteBuffer in) 
	{
		super(in);
	}
	
	public PKDecoder(Path path) throws DecoderException
	{
		super(path);
	}

	@Override
	public void setInput(ByteBuffer input) 
	{
		in = input;
		if(input!=null)
		{
			input.order(ByteOrder.LITTLE_ENDIAN);
		}
	}

	@Override
	public ByteBuffer decode() throws DecoderException 
	{
		int mag = in.getInt();
		if(mag!=825248592)
		{
			throw new DecoderException("pk","bad magic "+mag);
		}
		
		ByteBuffer un = ByteBuffer.allocate(in.getInt());
		int uk = in.getInt();
		
		if(uk!=0)
		{
			Log.log("PKEX","Unknown value isnt 0: "+uk);
		}
		
		Exploder ex = new Exploder(in);
		
		while(ex.remaining())
		{
			try 
			{
				un.put(ex.nextBlock());
			} catch (EOFException e)
			{
				throw new DecoderException("pk","unexpected eof");
			}
		}
		
		un.flip();
		return un;
	}
}
