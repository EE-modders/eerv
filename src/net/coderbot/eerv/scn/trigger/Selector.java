package net.coderbot.eerv.scn.trigger;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Selector
{
	String name;
	int[] onMap;
	boolean selectOnMap;
	int selectionPlayerId;//Owner of object must be this player
	int familySelectionId;//Family must be this, 0 to disable
	int classSelectionId;//Class must be this, 0 to disable
	int objectSelectionId;//Use another object as the source input, instead of all objects.
	int minSelected;
	int maxSelected;
	boolean dynamic;
	Mode areaMode;
	Mode losMode;//Also range and near
	Mode attrMode;
	Mode visibleByMode;
	Mode hasStateMode;
	Mode selectedByMode;
	int areaId;
	DistanceType distanceType;//LOS/RANGE/NEAR
	int objectId;
	int attrId;
	int min;
	int max;
	int visibleByPlayer;
	int state;
	int player;
	boolean attrPercent;
	int id;
	
	public void read(ByteBuffer data)
	{
		byte[] asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		onMap = new int[data.getInt()];
		for(int i = 0;i<onMap.length;i++)
		{
			onMap[i] = data.getInt();
		}
		
		selectOnMap = data.get()==1;
		selectionPlayerId = data.getInt();//Owner of object must be this player
		familySelectionId = data.getInt();//Family must be this
		classSelectionId = data.getInt();//Class must be this
		objectSelectionId = data.getInt();//Constrain to single object id
		minSelected = data.getInt();
		maxSelected = data.getInt();
		dynamic = data.get()==1;
		areaMode = Mode.values()[data.getInt()];
		losMode = Mode.values()[data.getInt()];//Also range and near
		attrMode = Mode.values()[data.getInt()];
		visibleByMode = Mode.values()[data.getInt()];//DISABLED/NORMAL/NOT
		hasStateMode = Mode.values()[data.getInt()];
		selectedByMode = Mode.values()[data.getInt()];
		areaId = data.getInt();
		distanceType = DistanceType.values()[data.getInt()];//LOS/RANGE/NEAR
		objectId = data.getInt();
		attrId = data.getInt();
		min = data.getInt();
		max = data.getInt();
		visibleByPlayer = data.getInt();
		state = data.getInt();
		player = data.getInt();
		attrPercent = data.get()==1;
		id = data.getInt();
	}
	
	public String toString()
	{
		StringBuilder s = new StringBuilder();
		
		
		
		boolean and = false;
		
		if(areaMode!=Mode.OFF)
		{
			if(areaMode==Mode.NOT)
			{
				s.append("not ");
			}
			s.append("in area ");
			s.append(areaId);
			and = true;
		}
		
		if(losMode!=Mode.OFF)
		{
			if(and)
			{
				s.append(" && ");
			}
			and = true;
			
			if(losMode==Mode.NOT)
			{
				s.append("not ");
			}
			s.append("in ");
			s.append(distanceType.toString());
			s.append(" of ");
			s.append(objectId);
		}
		
		if(attrMode!=Mode.OFF)
		{
			if(and)
			{
				s.append(" && ");
			}
			and = true;
			
			if(attrMode==Mode.NOT)
			{
				s.append("does not have attribute ");
			}
			else
			{
				s.append("has attribute ");
			}
			
			s.append(attrId);
			s.append(" in range [");
			s.append(min);
			if(attrPercent)
			{
				s.append("%");
			}
			s.append(",");
			s.append(max);
			if(attrPercent)
			{
				s.append("%");
			}
			s.append("]");
		}
		
		if(visibleByMode!=Mode.OFF)
		{
			if(and)
			{
				s.append(" && ");
			}
			and = true;
			
			if(visibleByMode==Mode.NOT)
			{
				s.append("not ");
			}
			
			s.append("visible by ");
			s.append(visibleByPlayer);
		}
		
		if(hasStateMode!=Mode.OFF)
		{
			if(and)
			{
				s.append(" && ");
			}
			and = true;
			
			if(hasStateMode==Mode.NOT)
			{
				s.append("not ");
			}
			
			s.append("in state ");
			s.append(state);
		}
		
		if(selectedByMode!=Mode.OFF)
		{
			if(and)
			{
				s.append(" && ");
			}
			and = true;
			
			if(selectedByMode==Mode.NOT)
			{
				s.append("not ");
			}
			
			s.append("selected by ");
			s.append(player);
		}
		
		return s.toString();
	}
	
	public static enum Mode
	{
		OFF, ON, NOT;
	}
	
	public static enum DistanceType
	{
		LOS, RANGE, NEAR;
	}
}
