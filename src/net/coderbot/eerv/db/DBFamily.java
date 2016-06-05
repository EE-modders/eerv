package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import java.nio.charset.StandardCharsets;

public class DBFamily 
{
	public static FamilyEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		FamilyEntry[] sr = new FamilyEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new FamilyEntry(data);
		}
		
		return sr;
	}
	
	public static class FamilyEntry
	{
		byte[] name;
		int gameid;
		int index;
		int[] damagePercent;//percents in order of family ID
		
		FamilyEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			
			damagePercent = new int[68];
			IntBuffer ints = data.asIntBuffer();
			ints.get(damagePercent);
			data.position(data.position()+272);//IntBuffer has independent position
		}
		
		public String toString()
		{
			StringBuilder sb = new StringBuilder("[");
			for(int i = 0;i<damagePercent.length;i++)
			{
				String s = Integer.toString(damagePercent[i]);
				int pad = 5-s.length();
				sb.append(s);
				for(int p = 0;p<pad;p++)
				{
					sb.append(' ');
				}
				
				if(i+1<damagePercent.length)
				{
					sb.append(", ");
				}
			}
			sb.append(']');
			
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			if(sname.length()>=20)
			{
				return "FamilyEntry {gameid: "+gameid+"   \tname: "+sname+", \tdamagePercent: "+sb+"}";
			}
			
			if(sname.length()<=10)
			{
				return "FamilyEntry {gameid: "+gameid+"   \tname: "+sname+", \t\t\tdamagePercent: "+sb+"}";
			}
			
			return "FamilyEntry {gameid: "+gameid+"   \tname: "+sname+", \t\tdamagePercent: "+sb+"}";
		}
	}
}
