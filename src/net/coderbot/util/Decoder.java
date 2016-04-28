package net.coderbot.util;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Path;

public abstract class Decoder<T> implements java.io.Closeable
{
	protected java.io.Closeable optClose;
	
	public Decoder(ByteBuffer in)
	{
		setInput(in);
	}
	
	public Decoder(Path path) throws DecoderException
	{
		try {
			FileChannel fc = FileChannel.open(path);
			setInput(fc.map(MapMode.READ_ONLY, 0, fc.size()));
			optClose = fc;
		} catch (java.io.IOException e) 
		{
			throw new DecoderException("decoder","Failed to map "+path+": "+e.getClass()+" "+e.getMessage());
		}
	}
	
	public void close()
	{
		if(optClose!=null)
		{
			try {
				optClose.close();
			} catch (java.io.IOException e) {}
		}
	}
	
	public abstract void setInput(ByteBuffer input);
	public abstract T decode() throws DecoderException;
}
