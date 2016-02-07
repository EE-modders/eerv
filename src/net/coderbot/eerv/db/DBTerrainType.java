package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DBTerrainType 
{
	public static TerrainTypeEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		TerrainTypeEntry[] sr = new TerrainTypeEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new TerrainTypeEntry(data);
		}
		
		return sr;
	}
	
	public static class TerrainTypeEntry
	{
		byte[] name;
		int gameid;
		int index;
		int wtf0;
		int wtf1;
		int language;
		int wtf3;
		int wtf4;
		
		TerrainTypeEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			wtf0 = data.getInt();
			wtf1 = data.getInt();
			language = data.getInt();
			wtf3 = data.getInt();
			wtf4 = data.getInt();
			
			System.out.println(gameid+" = \t"+wtf0+" \t"+wtf1+" \t"+Integer.toHexString(wtf3)+" \t"+wtf4+" \t"+new String(name).replace("\0", "").replace("\u0002\u0008\u0004\u0010", ""));
		}
	}
}
