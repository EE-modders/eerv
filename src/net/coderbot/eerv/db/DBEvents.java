package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBEvents 
{
	public static EventEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		EventEntry[] sr = new EventEntry[1500];
		
		for(int i = 0;i<1500;i++)
		{
			sr[i] = new EventEntry(data);
		}
		
		return sr;
	}
	
	public static class EventEntry
	{
		byte[] name;
		int gameid;
		int[] effects;
		
		EventEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			effects = new int[data.getInt()];
			for(int i = 0;i<effects.length;i++)
			{
				effects[i] = data.getInt();
			}
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.US_ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "{effects: "+effects.length+", name: "+sname+"}";
		}
	}
}
