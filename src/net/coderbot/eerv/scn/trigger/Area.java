package net.coderbot.eerv.scn.trigger;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Area
{
	String name;
	String desc;
	
	float x;
	float y;
	float xMin;
	float yMin;
	float xMax;
	float yMax;
	Type type;
	int id;
	boolean isValidMultiTile;
	boolean isValidSingleTile;
	
	int u;
	
	public void read(ByteBuffer data)
	{
		byte[] asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		name = new String(asciiZ, 0, asciiZ.length>0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		desc = new String(asciiZ, 0, asciiZ.length>0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
		
		x = data.getFloat();
		y = data.getFloat();
		xMin = data.getFloat();
		yMin = data.getFloat();
		xMax = data.getFloat();
		yMax = data.getFloat();
		
		isValidMultiTile = data.get()==1;
		isValidSingleTile = data.get()==1;
		type = Area.Type.values()[data.getInt()];
		u = data.getInt();
		id = data.getInt();
	}
	
	public String toString()
	{
		switch(type)
		{
		case AREA:
			return (isValidMultiTile?"":"invalid ")+"area from ["+xMin+","+yMin+"] to ["+xMax+","+yMax+"] id: "+id+" u: "+u+" name: "+name+" desc:"+desc;
		case CONTINENT:
			return (isValidSingleTile?"":"invalid ")+"continent tile at ["+x+","+y+"] id: "+id+" u: "+u+" name: "+name+" desc:"+desc;
		case TOWN:
			return (isValidSingleTile?"":"invalid ")+"town tile at ["+x+","+y+"] id: "+id+" u: "+u+" name: "+name+" desc:"+desc;
		default:
			return "";
		}
	}
	
	public static enum Type
	{
		AREA, CONTINENT, TOWN;
	}
}
