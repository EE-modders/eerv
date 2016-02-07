package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DBWorld 
{
	public static WorldEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		WorldEntry[] sr = new WorldEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new WorldEntry(data);
		}
		
		return sr;
	}
	
	public static class WorldEntry
	{
		float u0;
		float u1;
		int gameid;
		int index;
		int i0;
		int i1;
		int zero;
		
		WorldEntry(ByteBuffer data)
		{
			u0 = data.getFloat();
			u1 = data.getFloat();
			gameid = data.getInt();
			index = data.getInt();
			i0 = data.getInt();
			i1 = data.getInt();
			zero = data.getInt();
		}
		
		public String toString()
		{
			return "{"+gameid+" \t"+u0+" \t"+u1+" \t"+i0+" \t"+i1+"}";
		}
	}
}
