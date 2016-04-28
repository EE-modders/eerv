package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBAreaEffect 
{
	public static AreaEffectEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		AreaEffectEntry[] sr = new AreaEffectEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new AreaEffectEntry(data);
		}
		
		return sr;
	}
	
	public static class AreaEffectEntry
	{
		byte[] name;
		int gameid;
		int index;
		AreaEffectType type;
		UnknownEnum0 ue0;
		int gfxEffect;
		int unitset;
		Mode mode;
		int zero0;
		int graphics;
		int moraleMax;
		int heal;
		int zero1;
		int zero2;
		int zero3;
		float size;
		float f1;				//0.98,1, 0[TCEconBonus, CapitolTowerMorale, CapitolEconBonus, TCTowerMorale]
		float f2;				//0.0,1.0
		float f3;				//0.0,0.53,1.0
		float f4;//0.14 for all except Tower Morale
		int zero4;
		int zero5;
		int zero6;
		
		AreaEffectEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			
			gameid = data.getInt();
			index = data.getInt();
			type = AreaEffectType.values()[data.getInt()];
			
			ue0 = UnknownEnum0.values()[data.getInt()];
			gfxEffect = data.getInt();
			unitset = data.getInt();
			mode = Mode.values()[data.getInt()];
			
			zero0 = data.getInt();
			
			graphics = data.getInt();
			moraleMax = data.getInt();
			heal = data.getInt();
			
			zero1 = data.getInt();
			zero2 = data.getInt();
			zero3 = data.getInt();
			
			size = data.getFloat();
			f1 = data.getFloat();
			f2 = data.getFloat();
			f3 = data.getFloat();
			f4 = data.getFloat();
			
			zero4 = data.getInt();
			zero5 = data.getInt();
			zero6 = data.getInt();
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.US_ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return type+" \t\t"+ue0+" \t\t"+f1+" \t"+f2+" \t"+f3+" \t"+f4+" \t"+sname;
		}
	}
	
	public static enum AreaEffectType
	{
		NONE, HEAL, TOWER, TEMPLE, UNI, MORBLD, MORUNT, ECON, CLOAK;
	}
	
	public static enum UnknownEnum0
	{
		_____, U1___, ___U2;
		
		public String toString()
		{
			return super.toString().replace('_', ' ');
		}
	}
	
	public static enum Mode
	{
		NONE, SELF, AREA;
	}
}
