package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DBWorld 
{
	public static WorldEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		WorldEntry[] sr = new WorldEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new WorldEntry(data);
		}
		
		return sr;
	}
	
	public static class WorldEntry
	{
		float f;
		float fmax;
		int gameid;
		Option option;
		int i;
		int imax;
		int zero;
		Type type;
		
		WorldEntry(ByteBuffer data)
		{
			f = data.getFloat();
			fmax = data.getFloat();
			gameid = data.getInt();
			option = Option.values()[data.getInt()+1];
			i = data.getInt();
			imax = data.getInt();
			zero = data.getInt();
			
			type = Type.NONE;
			if(f!=0&&fmax==0)
			{
				type = Type.FLOAT;
			}
			else if(f!=0&&fmax!=0)
			{
				type = Type.FLOAT_RANGE;
			}
			else if(i!=0&&imax==0)
			{
				type = Type.INT;
			}
			else if(i!=0&i!=0)
			{
				type = Type.INT_RANGE;
			}
		}
		
		public String toString()
		{
			if(type==Type.FLOAT)
			{
				return option+".F \t"+f;
			}
			if(type==Type.FLOAT_RANGE)
			{
				return option+".F \t"+f+" ~ "+fmax;
			}
			if(type==Type.INT)
			{
				return option+".I \t"+i;
			}
			if(type==Type.INT_RANGE)
			{
				return option+".I \t"+i+" ~ "+imax;
			}
			
			return option+".N";
		}
	}
	
	public static enum Type
	{
		FLOAT, FLOAT_RANGE, INT, INT_RANGE, NONE;
	}
	
	public static enum Option
	{
		UNDEFINED, NULL, U1, U2, U3, U4, U5, U6, U7, N8, U9, 
		U10, U11, U12, U13, U14, U15, U16, U17, U18, U19, U20, U21, 
		MAX_GATHERERS, U23, N24, U25, POPULATION, U27, N28, N29, N30, N31, 
		U32, U33, WONDER_COUNTDOWN, U35, U36, U37, N38, N39, N40, N41, U42, U43, 
		U44, U45, U46, MAX_UPGRADES, U48, U49, U50, U51, U52, U53, U54, U55, 
		U56, U57, U58, U59, U60, U61, U62, U63, U64, U65, U66, U67, 
		U68, U69, U70, U71, U72, U73, U74, U75, CIV_POINTS, U77, U78, U79, 
		U80, U81, U82, U83;
	}
}
