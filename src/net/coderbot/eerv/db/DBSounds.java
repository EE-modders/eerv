package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBSounds 
{
	public static SoundEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		SoundEntry[] sr = new SoundEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new SoundEntry(data);
		}
		
		return sr;
	}
	
	public static class SoundEntry
	{
		String path;
		byte[] name;
		int gameid;
		int index;
		SoundType type;
		int u1;
		int u2;
		int u3;
		int u4;
		int zero;
		
		SoundEntry(ByteBuffer data)
		{
			byte[] asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			path = new String(asciiZ, 0, asciiZ.length, StandardCharsets.ISO_8859_1).toLowerCase().replace('\\', '/');
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			type = SoundType.values()[data.getInt()];
			u1 = data.getInt();
			u2 = data.getInt();
			u3 = data.getInt();
			u4 = data.getInt();
			zero = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return gameid+" \t"+type+"\t "+u1+" \t "+u2+" \t "+u3+" \t "+u4+" \t\t"+sname+"";
		}
	}
	
	public static enum SoundType
	{
		NONE, UNPOS, UNIT, ACTION, GUNFIRE;
		
		public String toString()
		{
			return super.toString().replace("u", "");
		}
	}
}
