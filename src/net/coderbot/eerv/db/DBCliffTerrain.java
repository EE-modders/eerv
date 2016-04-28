package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBCliffTerrain 
{
	public static CliffEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		CliffEntry[] sr = new CliffEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new CliffEntry(data);
		}
		
		return sr;
	}
	
	public static class CliffEntry
	{
		byte[] name;
		int gameid;
		int index;
		int terrain0;
		int terrain1;
		
		CliffEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			terrain0 = data.getInt();
			terrain1 = data.getInt();
			data.position(data.position()+8);
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.US_ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "CliffEntry {name: "+sname+", gameid: "+gameid+", index: "+index+", terrain0: "+terrain0+", terrain1: "+terrain1+"}";
		}
	}
}
