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
		byte[] name;
		int zero0;
		int zero1;
		int unknown;
		int language;
		int rangeLanguage;
		
		PremadeCivEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			zero0 = data.getInt();
			zero1 = data.getInt();
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
			
			return "{"+unknown+" \t"+language+" \t"+rangeLanguage+"}";
		}
	}
}
