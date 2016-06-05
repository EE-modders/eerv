package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBAmbientSounds 
{
	public static AmbientSoundEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		AmbientSoundEntry[] sr = new AmbientSoundEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new AmbientSoundEntry(data);
		}
		
		return sr;
	}
	
	public static class AmbientSoundEntry
	{
		byte[] name;
		
		int index;
		int gameid;
		int wtf0;
		float wtf1;
		float wtf2;
		float wtf3;
		float wtf4;
		float wtf5;
		int zero0;
		int zero1;
		int zero2;
		int wtf6;
		int zero3;
		
		AmbientSoundEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			
			index = data.getInt();
			gameid = data.getInt();
			wtf0 = data.getInt();
			wtf1 = data.getFloat();
			wtf2 = data.getFloat();
			wtf3 = data.getFloat();
			wtf4 = data.getFloat();
			wtf5 = data.getFloat();
			zero0 = data.getInt();
			zero1 = data.getInt();
			zero2 = data.getInt();
			wtf6 = data.getInt();
			zero3 = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "AmbientSoundEntry {gameid: "+gameid+", \twtf0: "+wtf0+", \twtf1: "+wtf1+", \twtf2: "+wtf2+", \twtf3: "+wtf3+", \twtf4: "+wtf4+", \twtf5: "+wtf5+", \twtf6: "+wtf6+" \t"+sname+"}";
		}
	}
}
