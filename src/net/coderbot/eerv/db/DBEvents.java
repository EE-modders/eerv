package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.coderbot.util.Charsets;

public class DBEvents 
{
	public static EventEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		data.position(data.position()+108);
		EventEntry[] sr = new EventEntry[1000];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new EventEntry(data);
		}
		
		return sr;
	}
	
	public static class EventEntry
	{
		byte[] name;
		
		EventEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			data.position(data.position()+24);
		}
		
		public String toString()
		{
			String sname = new String(name, Charsets.ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return sname;
		}
	}
}
