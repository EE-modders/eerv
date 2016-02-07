package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.coderbot.util.Charsets;

public class DBMusic 
{
	public static MusicEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		MusicEntry[] sr = new MusicEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new MusicEntry(data);
		}
		
		return sr;
	}
	
	public static class MusicEntry
	{
		int gameid;
		int index;
		byte[] name;
		
		int mida;
		int midb;
		int a;
		int b;
		int c;
		
		MusicEntry(ByteBuffer data)
		{
			gameid = data.getInt();
			index = data.getInt();
			name = new byte[100];
			data.get(name);
			data.position(data.position()+37);
			mida = data.getInt();
			midb = data.getInt();
			data.position(data.position()+11);
			a = data.getInt();
			b = data.getInt();
			c = data.getInt();
			data.position(data.position()+20);
		}
		
		public String toString()
		{
			String sname = new String(name, Charsets.ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "MusicEntry {gameid: "+gameid+", index: "+index+", name: "+sname+", mida: "+mida+", midb: "+midb+", a: "+a+", b: "+(b&0xFFFFFFFFL)+", c: "+c+"}";
		}
	}
}
