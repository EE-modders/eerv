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
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			StringBuilder efs = new StringBuilder("[");
			for(int i = 0;i<effects.length;i++)
			{
				efs.append(effects[i]);
				if(i+1<effects.length)
				{
					efs.append(", ");
				}
			}
			
			return "{effects: "+efs.toString()+"], name: "+sname+"}";
		}
		
		public int[] getEffects()
		{
			return effects;
		}
	}
}
