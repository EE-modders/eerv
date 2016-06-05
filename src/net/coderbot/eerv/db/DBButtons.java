package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBButtons 
{
	public static ButtonEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		ButtonEntry[] sr = new ButtonEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new ButtonEntry(data);
		}
		
		return sr;
	}
	
	public static class ButtonEntry
	{
		byte[] name;
		byte[] texture;
		int gameid;
		int index;
		int unknown;//5 for espionage headquarters, 0 for everything else
		int unknown2;//5 for espionage headquarters, 0 for everything else
		int position;
		int unknown3;//this one is wierd, it seems to be pretty random
		
		ButtonEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			texture = new byte[100];
			data.get(texture);
			
			
			gameid = data.getInt();
			index = data.getInt();
			unknown = data.getInt();
			unknown2 = data.getInt();
			position = data.getInt();
			unknown3 = data.getInt();
		}
		
		public String toString()
		{
			String stexture = new String(texture, StandardCharsets.ISO_8859_1);
			int idx0 = stexture.indexOf(0);
			if(idx0>-1)
			{
				stexture = stexture.substring(0, idx0);
			}
			
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "ButtonEntry {name: "+sname+", texture: "+stexture+"}";
		}
	}
}
