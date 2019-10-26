package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBPremadeCivs 
{
	public static PremadeCivEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		PremadeCivEntry[] sr = new PremadeCivEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new PremadeCivEntry(data);
		}
		
		return sr;
	}
	
	public static class PremadeCivEntry
	{
		int gameid;
		int index;
		byte[] name;
		int unknown;
		int language;
		int rangeLanguage;
		
		PremadeCivEntry(ByteBuffer data)
		{
			gameid = data.getInt();
			index = data.getInt();
			name = new byte[100];
			data.get(name);
			unknown = data.getInt();
			language = data.getInt();
			rangeLanguage = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "{"+unknown+" \tlang: "+language+" \trangeLang: "+rangeLanguage+" "+sname+"}";
		}
	}
}
