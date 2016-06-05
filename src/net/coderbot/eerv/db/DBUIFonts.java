package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBUIFonts 
{
	public static FontEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		FontEntry[] sr = new FontEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new FontEntry(data);
		}
		
		return sr;
	}
	
	public static class FontEntry
	{
		byte[] name;
		int gameid;
		int index;
		FontType type;
		int size;
		boolean active;
		
		boolean bold;
		boolean unused;
		boolean underline;
		boolean shadow;
		
		int unused1;
		
		FontEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			type = FontType.values()[Math.min(data.getInt(), 4)];
			size = data.getInt();
			active = data.getInt()==2;
			
			int bitfield = data.getInt();
			bold = (bitfield&1)==1;
			unused = (bitfield&0x00000100)==0x00000100;
			underline = (bitfield&0x00010000)==0x00010000;
			shadow = (bitfield&0x01000000)==0x01000000;
			
			unused1 = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "FontEntry {name: "+sname+", gameid: "+gameid+", index: "+index+", type:"+type+", size: "+size+", active: "+active+", bold: "+bold+", underline: "+underline+", shadow: "+shadow+"}";
		}
	}
	
	public static enum FontType
	{
		NONE, ARIAL, TIMES_NEW_ROMAN, COURIER_NEW, TAHOMA;
	}
}
