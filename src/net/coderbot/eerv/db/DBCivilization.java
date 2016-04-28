package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import java.nio.charset.StandardCharsets;

public class DBCivilization 
{
	public static CivilizationEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		CivilizationEntry[] sr = new CivilizationEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new CivilizationEntry(data);
		}
		
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
		
		float[] percents;
		
		CivilizationEntry(ByteBuffer data)
		{
			gameid = data.getInt();
			civid = data.getInt();
			name = new byte[100];
			data.get(name);
			langid = data.getInt();
			costIncrease = data.getInt();
			index = data.getInt();
			
			percents = new float[74];
			FloatBuffer fdata = data.asFloatBuffer();
			fdata.get(percents);
			
			data.position(data.position()+296);
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.US_ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "CivilizationEntry {name: "+sname+", id: "+gameid+", percents[0]: "+percents[0]+"}";
		}
	}
}
