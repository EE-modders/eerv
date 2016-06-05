package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBUnitBehavior 
{
	public static UnitBehaviorEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		UnitBehaviorEntry[] sr = new UnitBehaviorEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new UnitBehaviorEntry(data);
		}
		
		return sr;
	}
	
	public static class UnitBehaviorEntry
	{
		byte[] name;
		int gameid;
		int index;
		boolean run;			//Run away from enemy
		boolean ignoreOutside;	//ignore if outside LOS
		boolean attack;			//Attack enemy
		boolean follow;			//Follow enemy if in LOS
		boolean retreat;		//Retreat if attacked
		boolean ret;			//Return to initial location
		int zero0;
		int zero1;
		
		UnitBehaviorEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			
			int bf0 = data.getInt();
			run = (bf0&1)==1?true:false;
			ignoreOutside = (bf0&0x100)==0x100?true:false;
			attack = (bf0&0x10000)==0x10000?true:false;
			follow = (bf0&0x1000000)==0x1000000?true:false;
			int bf1 = data.getInt();
			retreat = (bf1&1)==1?true:false;
			ret = (bf1&0x100)==0x100?true:false;
			
			zero0 = data.getInt();
			zero1 = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return sname;
		}
	}
}
