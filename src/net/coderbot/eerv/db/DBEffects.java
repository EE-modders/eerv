package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class DBEffects 
{
	public static EffectEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		EffectEntry[] sr = new EffectEntry[2000];
		
		for(int i = 0;i<2000;i++)
		{
			sr[i] = new EffectEntry(data);
		}
		
		return sr;
	}
	
	public static class EffectEntry
	{
		int gameid;
		int index;
		int zero0;
		float set;//Sets attribute
		
		float alter;//Adds to attribute
		int apply;//Object upgrade applies to
		int target;//Upgrade target
		int unitSet;
		
		Mode mode;
		int graphic;
		int tech;
		Attribute attribute;
		
		int sound;
		int button;
		int areaEffect;
		int zero1;
		
		int zero2;
		
		EffectEntry(ByteBuffer data)
		{
			gameid = data.getInt();
			index = data.getInt();
			zero0 = data.getInt();
			set = data.getFloat();
			
			alter = data.getFloat();
			apply = data.getInt();
			target = data.getInt();
			unitSet = data.getInt();
			
			int effectCode = data.getInt();
			if(effectCode<0)
			{
				mode = Mode.NONE;
			}
			else
			{
				mode = Mode.values()[effectCode+1];
			}
			
			graphic = data.getInt();
			tech = data.getInt();
			int attributeCode = data.getInt();
			if(attributeCode<0)
			{
				attribute = Attribute.NONE;
			}
			else
			{
				attribute = Attribute.values()[attributeCode+1];
			}
			
			sound = data.getInt();
			button = data.getInt();
			areaEffect = data.getInt();
			zero1 = data.getInt();
			zero2 = data.getInt();
		}
	}
	
	public static enum Attribute
	{
		NONE, U0, CITIZENS_DOG_STATS, U2, RANGE, LOS, U5, HEALTH, U7, U8, SPEED, U10, U11, U12, U13, U14, U15, U16, U17, U18, U19, U20, U21, U22,
		POP_LIMIT, U24, U25, WOOD_GATHER, STONE_GATHER, GOLD_GATHER, IRON_GATHER, HUNT_GATHER, FORAGE_GATHER, FARM_GATHER, U34, U35, CONVERT_PRIEST,
		CONVERT_BUILDING, COMMERCIAL_TAXES, U39, REPAIR_SPEED, POWER_RECOVER_RATE, PATH_FINDING, HEALTH_RECOVER_RATE, POWER_AMOUNT, U45;
	}
	
	public static enum Mode
	{
		NONE, U0, SET_BUTTON, ALTER, U3, U4, U5, SET_GRAPHIC, U7, ENABLE_TECH, DISABLE_TECH, START_GAME, U11, BACKGROUND, U13, U14,
		SET_ACTION_SOUND_1, U16, SET_DEATH_SOUND, SET_SELECTION_SOUND_1, REPLACE_ALL, SET_ACTION_SOUND_2, SET_SELECTION_SOUND_2, REPLACE_OBJECT;
	}
}
