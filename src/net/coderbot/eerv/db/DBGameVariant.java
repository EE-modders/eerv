package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DBGameVariant 
{
	public static GameVariantEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		GameVariantEntry[] sr = new GameVariantEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new GameVariantEntry(data);
		}
		
		return sr;
	}
	
	public static class GameVariantEntry
	{
		byte[] name;
		int gameid;
		int index;
		
		float[] attrv;
		int[] attrid;
		
		float one0;
		
		float u6;//St>Sc+T
		float gather;
		int u7;//St=2
		int language;
		
		float u8;//S>St>T
		float u9;//Sc>St>T
		float wonderCost;
		int uA;//St+Sc>T
		
		float uB;//St+Sc>T
		int zeroA;
		int zero9;
		int zero8;
		
		int zero0;
		int zero1;
		int zero2;
		int zero3;
		
		int zero4;
		int zero5;
		int zero6;
		boolean randmap;
		
		int zero7;
		
		GameVariantEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			
			attrv = new float[10];
			attrid = new int[10];
			for(int i = 0;i<10;i++)
			{
				attrv[i] = data.getFloat();
				attrid[i] = data.getInt();
			}
			
			one0 = data.getFloat();
			
			u6 = data.getFloat();
			gather = data.getFloat();
			u7 = data.getInt();
			language = data.getInt();
			
			u8 = data.getFloat();
			u9 = data.getFloat();
			wonderCost = data.getFloat();
			uA = data.getInt();
			
			uB = data.getFloat();
			zeroA = data.getInt();
			zero9 = data.getInt();
			zero8 = data.getInt();
			
			zero0 = data.getInt();
			zero1 = data.getInt();
			zero2 = data.getInt();
			zero3 = data.getInt();
			
			zero4 = data.getInt();
			zero5 = data.getInt();
			zero6 = data.getInt();
			randmap = data.getInt()==1;
			
			zero7 = data.getInt();
			
			System.out.println(u6+" \t"+u7+" \t"+u8+" \t"+u9+" \t"+new String(name));
		}
	}
}
