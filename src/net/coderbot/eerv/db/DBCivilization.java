package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;

import net.coderbot.util.Charsets;

public class DBCivilization 
{
	static ArrayList<Integer> uniq = new ArrayList<Integer>();
	
	public static CivilizationEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		CivilizationEntry[] sr = new CivilizationEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new CivilizationEntry(data);
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(int i: uniq)
		{
			sb.append((int)(i/(((float)Integer.MAX_VALUE+1)*2)*100));
			sb.append(' ');
		}
		
		System.out.println(sb);
		
		return sr;
	}
	
	public static class CivilizationEntry
	{
		int gameid;
		int civid;
		byte[] name;
		int langid;
		int costIncrease;
		int index;
		
		int[] percents;
		
		CivilizationEntry(ByteBuffer data)
		{
			gameid = data.getInt();
			civid = data.getInt();
			name = new byte[100];
			data.get(name);
			langid = data.getInt();
			costIncrease = data.getInt();
			index = data.getInt();
			
			percents = new int[74];
			IntBuffer idata = data.asIntBuffer();
			idata.get(percents);
			
			for(int i = 0;i<percents.length;i++)
			{
				if(!uniq.contains(percents[i]))
				{
					uniq.add(percents[i]);
				}
			}
			
			data.position(data.position()+296);
		}
		
		public String toString()
		{
			String sname = new String(name, Charsets.ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "CivilizationEntry {name: "+sname+", id: "+gameid+"}";
		}
	}
}
