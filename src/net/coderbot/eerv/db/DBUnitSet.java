package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBUnitSet 
{
	public static UnitSetEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		UnitSetEntry[] sr = new UnitSetEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new UnitSetEntry(data);
		}
		
		return sr;
	}
	
	public static class UnitSetEntry
	{
		byte[] name;
		int gameid;
		int index;
		int[] families;
		int[] objects;
		int extend;
		int exclude;
		boolean first;
		/**
		 * Cyber regular
		 */
		boolean u;
		
		UnitSetEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			
			gameid = data.getInt();
			index = data.getInt();
			
			families = new int[8];
			for(int i = 0;i<8;i++)
			{
				families[i] = data.getInt();
			}
			
			objects = new int[8];
			for(int i = 0;i<8;i++)
			{
				objects[i] = data.getInt();
			}
			
			extend = data.getInt();
			exclude = data.getInt();
			int bf = data.getInt();
			first = (bf&1)==1?true:false;
			u = (bf&4096)==4096?true:false;
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
