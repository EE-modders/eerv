package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBUIControlEvents 
{
	public static ControlEventEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		ControlEventEntry[] sr = new ControlEventEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new ControlEventEntry(data);
		}
		
		return sr;
	}
	
	public static class ControlEventEntry
	{
		byte[] name;
		int gameid;
		int index;
		int control;
		public ControlEventType type;
		
		ControlEventEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			
			gameid = data.getInt();
			index = data.getInt();
			control = data.getInt();
			int ev = data.getInt();
			
			if(ev==0)
			{
				type = ControlEventType.UPDATE_CONTROLS;
			}
			else if(ev<1000)
			{
				type = ControlEventType.INVALID;
			}
			else
			{
				ev = ev-999;
				type = ControlEventType.values()[ev];
			}
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.US_ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "ControlEventEntry {gameid: "+gameid+", index: "+index+", \tc: "+control+", \ttype: "+type+", \tname: "+sname+"}";
		}
	}
	
	public static enum ControlEventType
	{
		 UPDATE_CONTROLS, SLIDER, CHECK, UNCHECK, LIST_SELECT, DOUBLE_CLICK, UNUSED_5, UNUSED_6, PASSWORD, SINGLE_CLICK, DONE_TYPING, HOVERING, NOT_HOVERING, CHAT, INVALID;
	}
}
