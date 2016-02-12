package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DBCalamity 
{
	public static CalamityEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		CalamityEntry[] sr = new CalamityEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new CalamityEntry(data);
		}
		
		return sr;
	}
	
	public static class CalamityEntry
	{
		byte[] name;
		float u0;
		float u1;
		float earthquake0;//0.05 for earthquake
		float earthquake1;//0.3 for earthquake
		float zero0;
		float range;
		int gameid;
		int index;
		int uid0;//another ID
		Mode mode;
		int u2;//duration?
		int one0;
		int u3;//up to 202
		int u4;//up to 219
		int u5;//up to 197
		int u6;//up to 201
		int zero1;
		int zero2;
		int zero3;
		int u7;//up to 660, common=0
		int u8;//up to 2000, mid=100, low=10, common=1
		int u9;//up to 10000
		int uA;//up to 2000, mid=300, common=0
		int uB;//up to 4000, mid=20, common=0
		int uC;//up to 700, mid=7, common=0
		int uD;
		boolean hur;//Hurricane ShieldBattery IonPulseCannon
		boolean true0;
		boolean prophet;//Unverified
		int uE;//up to 937, common=-1
		int uF;//516 or -1
		int uG;//up to 100
		int uH;//up to 101
		int uI;//9100+ sequential
		boolean uJ;//boolean valid?
		
		CalamityEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			
			u0 = data.getFloat();
			u1 = data.getFloat();
			earthquake0 = data.getFloat();
			earthquake1 = data.getFloat();
			
			zero0 = data.getFloat();
			range = data.getFloat();
			gameid = data.getInt();
			index = data.getInt();
			
			uid0 = data.getInt();
			mode = Mode.values()[data.getInt()];
			u2 = data.getInt();
			one0 = data.getInt();
			
			u3 = data.getInt();
			u4 = data.getInt();
			u5 = data.getInt();
			u6 = data.getInt();
			
			zero1 = data.getInt();
			zero2 = data.getInt();
			zero3 = data.getInt();
			u7 = data.getInt();
			
			u8 = data.getInt();
			u9 = data.getInt();
			uA = data.getInt();
			uB = data.getInt();
			
			uC = data.getInt();
			uD = data.getInt();
			int bf0 = data.getInt();
			hur = (bf0&256)==256;
			true0 = (bf0&1)==1;
			int bf1 = data.getInt();
			prophet = (bf1&256)==256;
			
			uE = data.getInt();
			uF = data.getInt();
			uG = data.getInt();
			uH = data.getInt();
			
			uI = data.getInt();
			uJ = data.getInt()==1;
			
			System.out.println(u0+" \t"+u1+" \t"+new String(name));
		}
	}
	
	public static enum Mode
	{
		NONE, 
		/**
		 * Unverified
		 */
		GLOBAL, 
		NORMAL, 
		SPREAD;
	}
}
