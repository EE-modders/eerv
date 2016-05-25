package net.coderbot.eerv.scn;

public class SCNTriggers 
{
	public static class SCNTrigger
	{
		String name;
		String desc;
		
		/**
		 * Is the trigger active?
		 */
		boolean active;
		/**
		 * Does it loop? [explain]
		 */
		boolean looping;
		/**
		 * Unknown
		 */
		boolean u0;
		/**
		 * Does it trigger when in a cinematic?
		 */
		boolean inCinematic;
		
		/**
		 * How much time this trigger will be active for, relative to the level start.<br>
		 * If it is 0, it is always active.
		 */
		int activeFor;
		
		int u1;
		
		/**
		 * ID of this trigger.
		 */
		int id;
		
		/**
		 * Binds the conditions to this trigger.
		 */
		SCNConditionBinding[] conditions;
		
		/**
		 * Binds the effects to this trigger.
		 */
		SCNEffectBinding[] effects;
		
		public String toString()
		{
			return "SCNTrigger {name: "+name+", desc: "+desc+", active: "+active+", looping: "+looping+", u0: "+u0+", inCinematic: "+inCinematic+", activeFor: "+activeFor+", u1: "+u1+", id: "+id+"}";
		}
	}
	
	public static class SCNCondition
	{
		String name;
		String desc;
		
		public String toString()
		{
			return "SCNCondition {name: "+name+", desc: "+desc+"}";
		}
	}
	
	public static class SCNEffect
	{
		String name;
		String desc;
		int type;
		
		//Unknown/Unused
		String u0;
		String u1;
		boolean u2, u3, u4;
		int u5;
		
		//Trigger
		int triggerState;
		int triggerId;
		
		//Media
		int mediaDuration;
		String mediaMessage;
		String mediaSoundFile;
		String mediaLine;
		int mediaSubtype;
		int mediaPlayer;
		int mediaChangeTextTarget;
		
		//Object
		int objectSubtype;
		int objectTechId;
		String objectClassName;
		int objectPlayer;
		
		//Player
		int playerSubtype;
		int playerAttributeId;
		boolean playerIsEnemy;
		int player_AiPrimaryPlayer;//Also used by AI
		int playerSecondaryPlayer;
		
		//Game
		int gameDatabaseId;
		int gameAreaId;
		int gameObjectId;
		int gameDaylightHour;
		int gameDayNightTime;
		int gameSubtype;
		
		
		public String toString()
		{
			return "SCNEffect {name: "+name+", desc: "+desc+"}";
		}
	}
	
	public static class SCNConditionBinding
	{
		int condition;
		boolean not;
		ChainType chainType;
		
		public String toString()
		{
			return "SCNConditionBinding {condition: "+condition+", not: "+not+" chainType: "+chainType+"}";
		}
	}
	
	public static class SCNEffectBinding
	{
		int effect;
		int delay;
		ChainType chainType;
		
		public String toString()
		{
			return "SCNEffectBinding {effect: "+effect+", delay: "+delay+" chainType: "+chainType+"}";
		}
	}
	
	public static enum EffectType
	{
		OBJECT, PLAYER, TRIGGER_STATE, MEDIA, GAME, AI;
	}
	
	public static enum TriggerState
	{
		ON, OFF, FIRED, NOT_FIRED;
	}
	
	public static enum ConditionType
	{
		UNKNOWN_0, EXISTS, PLAYER_ATTRIBUTE, PLAYER_TECH, TRIGGER_ATTRIBUTE, PLAYER_DIPLOMACY, GAME_ATTRIBUTE, CHAT_MESSAGE_CONTAINS;
	}
	
	public static enum PlayerAttributeType
	{
		ANY_RESOURCES, ALL_RESOURCES, STONE, GOLD, WOOD, FOOD, IRON, CURRENT_POP, POP_CAP, UNIT_COUNT, DIFFICULTY_LEVEL, 
		VARIABLE0, VARIABLE1, VARIABLE2, VARIABLE3, VARIABLE4, VARIABLE5, VARIABLE6, VARIABLE7, VARIABLE8, VARIABLE9,
		FOOD_SENT, FOOD_RECIEVED, WOOD_SENT, WOOD_RECIEVED, STONE_SENT, STONE_RECIEVED, GOLD_SENT, GOLD_RECIEVED, IRON_SENT, IRON_RECIEVED,
		BUILDINGS_LOST, BUILDINGS_RAZED, UNITS_KILLED, UNITS_LOST, CONVERSIONS, CALAMITIES, 
		FOOD_DUP, WOOD_DUP, GOLD_DUP, STONE_DUP, IRON_DUP,
		EPOCH_REACHED, ARMY_SIZE, CITIZEN_COUNT, HERO_COUNT, WONDER_COUNT, TRIBUTE_SENT, TRIBUTE_RECIEVED, RESEARCH_COUNT, HOT_KEYS, MOUSE_CLICKS,
		STAT_UNUSED_0, STAT_UNUSED_1, TILES_EXPLORED, STAT_UNUSED_2, STAT_UNUSED_3, STAT_UNUSED_4, STAT_UNUSED_5, STAT_UNUSED_6, STAT_UNUSED_7;
	}
	
	public static enum CompareType
	{
		LESS, LESS_OR_EQUAL, EQUAL, GREATER_OR_EQUAL, GREATER;
	}
	
	public static enum ChainType
	{
		END, AND, OR;
	}
	
	public static enum TriggerType
	{
		TRIGGER, CONDITION, EFFECT, UNK3, AREA;
	}
}
