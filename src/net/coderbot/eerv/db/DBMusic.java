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
		byte[] name2;
		float chance;
		float u1;
		float time;
		
		MusicEntry(ByteBuffer data)
		{
			gameid = data.getInt();
			index = data.getInt();
			name = new byte[100];
			data.get(name);
			name2 = new byte[56];
			data.get(name2);
			chance = data.getFloat();
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
			
			return "chance="+chance+" \t"+u1+" \ttime="+time+" \t"+sname;
		}
	}
}
