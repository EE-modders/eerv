package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBTerrainGrayTextures 
{
	public static TerrainGrayTextureEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		TerrainGrayTextureEntry[] sr = new TerrainGrayTextureEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new TerrainGrayTextureEntry(data);
		}
		
		return sr;
	}
	
	public static class TerrainGrayTextureEntry
	{
		String texture;
		byte[] name;
		int gameid;
		int index;
		int r,g,b;
		
		TerrainGrayTextureEntry(ByteBuffer data)
		{
			byte[] asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			
			texture = new String(asciiZ, StandardCharsets.ISO_8859_1).replace('\\', '/').toLowerCase();
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			r = data.getInt();
			g = data.getInt();
			b = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return gameid+" \t"+sname;
		}
	}
}
