package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DBGfxEffects 
{
	public static GfxEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		GfxEntry[] sr = new GfxEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new GfxEntry(data);
		}
		
		return sr;
	}
	
	public static class GfxEntry
	{
		byte[] name;
		int gameid;
		int index;
		int uid0;//range 66
		int u0;//range -1,5,21
		int graphics;
		float u1;//scale?
		float u2;
		float u3;
		int u4;
		int u5;
		int u6;
		int u7;
		int u8;//Seems to apply to emmiters
		int u9;
		int emitted0;//effect emitted
		int emitted1;//effect emitted
		int uA;//0 or -1
		int language;
		float uB;//tiles...
		float uC;
		float uD;//radians? 1/PI/0.1/...
		float uE;//radians?
		float uF;
		float u10;
		float u11;
		float u12;//0/0.75/1/2
		float u13;//10.0 -> 250.0 (multiple of 5)
		float u14;//debris, battlecry
		float u15;//debris, battlecry, particle
		float u16;//emitter
		int bf0;
		int zero0;
		int u17;
		
		GfxEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			uid0 = data.getInt();
			u0 = data.getInt();
			graphics = data.getInt();
			u1 = data.getFloat();
			u2 = data.getFloat();
			u3 = data.getFloat();
			
			u4 = data.getInt();
			u5 = data.getInt();
			u6 = data.getInt();
			
			u7 = data.getInt();
			u8 = data.getInt();
			u9 = data.getInt();
			emitted0 = data.getInt();
			emitted1 = data.getInt();
			System.out.println(index+" \t"+uid0+" \t"+new String(name));
			
			uA = data.getInt();
			language = data.getInt();
			uB = data.getFloat();
			uC = data.getFloat();
			uD = data.getFloat();
			uE = data.getFloat();
			uF = data.getFloat();
			u10 = data.getFloat();
			u11 = data.getFloat();
			u12 = data.getFloat();
			u13 = data.getFloat();
			u14 = data.getFloat();
			u15 = data.getFloat();
			u16 = data.getFloat();
			bf0 = data.getInt();
			zero0 = data.getInt();
			u17 = data.getInt();
		}
	}
}
