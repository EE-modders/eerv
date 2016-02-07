package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DBWeaponToHit 
{
	public static WeaponEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		WeaponEntry[] sr = new WeaponEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new WeaponEntry(data);
		}
		
		return sr;
	}
	
	public static class WeaponEntry
	{
		byte[] name;
		int gameid;
		int index;
		
		/**
		 * Percent damage taken from attackers
		 */
		int[] percents;
		
		WeaponEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			
			gameid = data.getInt();
			index = data.getInt();
			percents = new int[6];
			for(int i = 0;i<6;i++)
			{
				percents[i] = data.getInt();
			}
		}
	}
}
