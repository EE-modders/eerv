package net.coderbot.eerv;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import net.coderbot.util.Decoder;
import net.coderbot.util.DecoderException;

public class SSADecoder extends Decoder<SSA>
{
	ByteBuffer in;
	
	public SSADecoder(ByteBuffer in)
	{
		super(in);
	}
	
	
	public SSADecoder(Path p) throws DecoderException
	{
		super(p);
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
	public SSA decode() throws DecoderException 
	{
		SSA ssa = new SSA(in);
		
		int mag = in.getInt();
		if(mag!=1936941426)
		{
			throw new DecoderException("ssa","Bad magic "+mag);
		}
		
		int verh = in.getInt();
		int verl = in.getInt();
		if(verh!=1||verl!=0)
		{
			throw new DecoderException("ssa","Bad version "+verh+"."+verl+", only 1.0 is supported");
		}
		
		int kv = in.getInt()+16;
		
		byte[] asciiZ = null;
		SSA.Entry e;
		
		while(in.position()!=kv)
		{
			e = new SSA.Entry();
			
			asciiZ = new byte[in.getInt()];
			in.get(asciiZ);
			e.offs = in.getInt()&0xFFFFFFFFL;
			e.end = in.getInt();
			e.size = in.getInt()&0xFFFFFFFFL;
			
			//Translate Windows path separator to Unix path separator
			ssa.files.put(new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII).replace('\\', '/'), e);
		}
		
		int ts = in.getInt();
		
		String k,v;
		
		for(int i = 0;i<ts;i++)
		{
			asciiZ = new byte[in.getInt()];
			in.get(asciiZ);
			k = new String(asciiZ, 0, asciiZ.length, StandardCharsets.US_ASCII);
			asciiZ = new byte[in.getInt()];
			in.get(asciiZ);
			v = new String(asciiZ, 0, asciiZ.length, StandardCharsets.US_ASCII);
			ssa.props.put(k, v);
		}
		
		return ssa;
	}
}
