package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBMusic 
{
	public static MusicEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		MusicEntry[] sr = new MusicEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new MusicEntry(data);
		}
		
		return sr;
	}
	
	public static class MusicEntry
	{
		int gameid;
		int index;
		byte[] name;
		
		int mida;
		int midb;
		float u0;//chance?
		float u1;
		float time;
		
		MusicEntry(ByteBuffer data)
		{
			gameid = data.getInt();
			index = data.getInt();
			name = new byte[100];
			data.get(name);
			data.position(data.position()+37);
			mida = data.getInt();
			midb = data.getInt();
			data.position(data.position()+11);
			u0 = data.getFloat();
			u1 = data.getFloat();
			time = data.getFloat();
			data.position(data.position()+20);
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return midb+" \t"+u0+" \t"+u1+" \t"+time+" \t"+sname+" \t"+mida;
		}
	}
}
