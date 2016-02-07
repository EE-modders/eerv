package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.coderbot.util.Charsets;

public class DBColorTable 
{
	public static ColorEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		ColorEntry[] sr = new ColorEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new ColorEntry(data);
		}
		
		return sr;
	}
	
	public static class ColorEntry
	{
		byte[] name;
		int gameid;
		int index;
		float r1,g1,b1;//Player
		float r2,g2,b2;//Model
		float r3,g3,b3;//Lighting
		float r4,g4,b4;//Unused
		float r5,g5,b5;//Unused
		
		ColorEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			r1 = data.getFloat();
			g1 = data.getFloat();
			b1 = data.getFloat();
			r2 = data.getFloat();
			g2 = data.getFloat();
			b2 = data.getFloat();
			r3 = data.getFloat();
			g3 = data.getFloat();
			b3 = data.getFloat();
			r4 = data.getFloat();
			g4 = data.getFloat();
			b4 = data.getFloat();
			r5 = data.getFloat();
			g5 = data.getFloat();
			b5 = data.getFloat();
			
			String sname = new String(name, Charsets.ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
		}
	}
}
