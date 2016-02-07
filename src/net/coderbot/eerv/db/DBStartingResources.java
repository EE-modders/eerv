package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.coderbot.util.Charsets;

public class DBStartingResources 
{
	public static StartingResourceEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		StartingResourceEntry[] sr = new StartingResourceEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new StartingResourceEntry(data);
		}
		
		return sr;
	}
	
	public static class StartingResourceEntry
	{
		byte[] name;
		int gameid;
		int index;
		int languageid;
		
		int food;
		int wood;
		int stone;
		int gold;
		int iron;
		
		StartingResourceEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			languageid = data.getInt();
			
			food = data.getInt();
			wood = data.getInt();
			stone = data.getInt();
			gold = data.getInt();
			iron = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, Charsets.ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "StartingResourceEntry {name: "+sname+", gameid: "+gameid+", index: "+index+", languageid: "+languageid+", food: "+food+", wood: "+wood+", stone: "+stone+", gold: "+gold+", iron: "+iron+"}";
		}
	}
}
