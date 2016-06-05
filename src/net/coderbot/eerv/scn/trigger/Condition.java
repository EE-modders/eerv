package net.coderbot.eerv.scn.trigger;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Condition
{
	String name;
	String desc;
	
	String contains;	//ChatMessageContains
	Type type;
	int existsId;
	int player;			
	PlayerAttribute attribute;
	CompareType compareType;
	int amount;
	int otherPlayer;
	int otheriAttribute;
	PlayerAttribute otherAttribute;
	int tech;
	int trigger;
	boolean triggerOn;
	boolean triggerFired;
	boolean compareOtherPlayer;
	int elapsedTimeMax;
	int elapsedTimeMin;
	int hourLeast;
	boolean dlevel;
	boolean etime;
	boolean hleast;
	DifficultyLevel difficultyLevel;
	boolean u0;
	boolean u1;
	boolean u2;
	boolean enemies;
	int id;
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getContains()
	{
		return contains;
	}

	public void setContains(String contains)
	{
		this.contains = contains;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public int getExistsId()
	{
		return existsId;
	}

	public void setExistsId(int existsId)
	{
		this.existsId = existsId;
	}

	public int getPlayer()
	{
		return player;
	}

	public void setPlayer(int player)
	{
		this.player = player;
	}

	public PlayerAttribute getAttribute()
	{
		return attribute;
	}

	public void setAttribute(PlayerAttribute attribute)
	{
		this.attribute = attribute;
	}

	public CompareType getCompareType()
	{
		return compareType;
	}

	public void setCompareType(CompareType compareType)
	{
		this.compareType = compareType;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}

	public int getOtherPlayer()
	{
		return otherPlayer;
	}

	public void setOtherPlayer(int otherPlayer)
	{
		this.otherPlayer = otherPlayer;
	}

	public int getOtheriAttribute()
	{
		return otheriAttribute;
	}

	public void setOtheriAttribute(int otheriAttribute)
	{
		this.otheriAttribute = otheriAttribute;
	}

	public PlayerAttribute getOtherAttribute()
	{
		return otherAttribute;
	}

	public void setOtherAttribute(PlayerAttribute otherAttribute)
	{
		this.otherAttribute = otherAttribute;
	}

	public int getTech()
	{
		return tech;
	}

	public void setTech(int tech)
	{
		this.tech = tech;
	}

	public int getTrigger()
	{
		return trigger;
	}

	public void setTrigger(int trigger)
	{
		this.trigger = trigger;
	}

	public boolean isTriggerOn()
	{
		return triggerOn;
	}

	public void setTriggerOn(boolean triggerOn)
	{
		this.triggerOn = triggerOn;
	}

	public boolean isTriggerFired()
	{
		return triggerFired;
	}

	public void setTriggerFired(boolean triggerFired)
	{
		this.triggerFired = triggerFired;
	}

	public boolean isCompareOtherPlayer()
	{
		return compareOtherPlayer;
	}

	public void setCompareOtherPlayer(boolean compareOtherPlayer)
	{
		this.compareOtherPlayer = compareOtherPlayer;
	}

	public int getElapsedTimeMax()
	{
		return elapsedTimeMax;
	}

	public void setElapsedTimeMax(int elapsedTimeMax)
	{
		this.elapsedTimeMax = elapsedTimeMax;
	}

	public int getElapsedTimeMin()
	{
		return elapsedTimeMin;
	}

	public void setElapsedTimeMin(int elapsedTimeMin)
	{
		this.elapsedTimeMin = elapsedTimeMin;
	}

	public int getHourLeast()
	{
		return hourLeast;
	}

	public void setHourLeast(int hourLeast)
	{
		this.hourLeast = hourLeast;
	}

	public boolean isDlevel()
	{
		return dlevel;
	}

	public void setDlevel(boolean dlevel)
	{
		this.dlevel = dlevel;
	}

	public boolean isEtime()
	{
		return etime;
	}

	public void setEtime(boolean etime)
	{
		this.etime = etime;
	}

	public boolean isHleast()
	{
		return hleast;
	}

	public void setHleast(boolean hleast)
	{
		this.hleast = hleast;
	}

	public DifficultyLevel getDifficultyLevel()
	{
		return difficultyLevel;
	}

	public void setDifficultyLevel(DifficultyLevel difficultyLevel)
	{
		this.difficultyLevel = difficultyLevel;
	}

	public boolean isU0()
	{
		return u0;
	}

	public void setU0(boolean u0)
	{
		this.u0 = u0;
	}

	public boolean isU1()
	{
		return u1;
	}

	public void setU1(boolean u1)
	{
		this.u1 = u1;
	}

	public boolean isU2()
	{
		return u2;
	}

	public void setU2(boolean u2)
	{
		this.u2 = u2;
	}

	public boolean isEnemies()
	{
		return enemies;
	}

	public void setEnemies(boolean enemies)
	{
		this.enemies = enemies;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void read(ByteBuffer data)
	{
		byte[] asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		setName(new String(asciiZ, 0, asciiZ.length>0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1));
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		setDesc(new String(asciiZ, 0, asciiZ.length>0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1));
		
		//CHAT MESSAGE CONTAINS
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		setContains(new String(asciiZ, 0, asciiZ.length>0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1));
		
		//PLAYER ATTRIBUTES
		
		int iConditionType = data.getInt();
		setType(Condition.Type.values()[iConditionType]);
		
		setExistsId(data.getInt());
		setPlayer(data.getInt());
		int iAttribute = data.getInt();
		setAttribute(Condition.PlayerAttribute.values()[iAttribute]);
			
		int iCompareType = data.getInt();
		setCompareType(Condition.CompareType.values()[iCompareType]);
		setAmount(data.getInt());
			
		setOtherPlayer(data.getInt());
		int otheriAttribute = data.getInt();
		setOtherAttribute(Condition.PlayerAttribute.values()[otheriAttribute]);
			
		//TECH
		setTech(data.getInt());
		
		//TRIGGER ATTRIBUTES
		setTrigger(data.getInt());
		setTriggerOn(data.get()==1);
		setTriggerFired(data.get()==1);
		setCompareOtherPlayer(data.get()==1);
		
		//GAME ATTRIBUTES
		
		setElapsedTimeMax(data.getInt());
		setElapsedTimeMin(data.getInt());
		setHourLeast(data.getInt());
		
		setDlevel(data.get()==1);
		setEtime(data.get()==1);
		setHleast(data.get()==1);
		
		int iDifficultyLevel = data.getInt();
		setDifficultyLevel(Condition.DifficultyLevel.values()[iDifficultyLevel]);
		
		//DIPLOMACY: 
		setEnemies(data.get()==1);
		setU0(data.get()==1);
		setU1(data.get()==1);
		setU2(data.get()==1);
		
		setId(data.getInt());
		
	}
	
	public String toString()
	{
		return "{("+type.toString(this)+"), name: "+name+", desc: "+desc+"}";
	}
	
	public static enum Type
	{
		UNKNOWN_0, EXISTS, PLAYER_ATTRIBUTE, PLAYER_TECH, TRIGGER_ATTRIBUTE, PLAYER_DIPLOMACY, GAME_ATTRIBUTE, CHAT_MESSAGE_CONTAINS;
		
		public String toString(Condition cond)
		{
			switch(this)
			{
			case CHAT_MESSAGE_CONTAINS:
				return cond.new ChatMessageView().toString();
			case EXISTS:
				return cond.new ExistsView().toString();
			case GAME_ATTRIBUTE:
				return cond.new GameAttributeView().toString();
			case PLAYER_ATTRIBUTE:
				return cond.new PlayerAttributeView().toString();
			case PLAYER_DIPLOMACY:
				return cond.new PlayerDiplomacyView().toString();
			case PLAYER_TECH:
				return cond.new PlayerTechView().toString();
			case TRIGGER_ATTRIBUTE:
				return cond.new TriggerAttributeView().toString();
			default:
				return "Unknown";
			}
		}
	}
	
	public static enum PlayerAttribute
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
		LESS("<"), LESS_OR_EQUAL("<="), EQUAL("=="), GREATER_OR_EQUAL(">="), GREATER(">");
		
		String compare;
		
		CompareType(String compare)
		{
			this.compare = compare;
		}
		
		public String toString()
		{
			return compare;
		}
	}
	
	public static enum DifficultyLevel
	{
		EASY, MEDIUM, HARD;
	}
	
	public class ExistsView
	{
		public int getSelectorId()
		{
			return existsId;
		}
		
		public void setSelectorId(int id)
		{
			existsId = id;
		}
		
		public String toString()
		{
			return "selector "+getSelectorId()+" exists";
		}
	}
	
	public class PlayerAttributeView
	{
		public int getPlayer()
		{
			return player;
		}
		
		public PlayerAttribute getAttribute()
		{
			return attribute;
		}
		
		public CompareType getCompareType()
		{
			return compareType;
		}
		
		public boolean compareOtherPlayer()
		{
			return compareOtherPlayer;
		}
		
		public int getAmount()
		{
			return amount;
		}
		
		public int getOtherPlayer()
		{
			return otherPlayer;
		}
		
		public PlayerAttribute getOtherAttribute()
		{
			return otherAttribute;
		}
		
		public String toString()
		{
			if(compareOtherPlayer())
			{
				return "player "+getPlayer()+"'s "+getAttribute()+" "+getCompareType()+" player "+getOtherPlayer()+"'s "+getOtherAttribute();
			}
			else
			{
				return "player "+getPlayer()+"'s "+getAttribute()+" "+getCompareType()+" "+getAmount();
			}
		}
	}
	
	public class PlayerTechView
	{
		public int getPlayer()
		{
			return player;
		}
		
		public void setPlayer(int player_)
		{
			player = player_;
		}
		
		public int getTech()
		{
			return tech;
		}
		
		public void setTech(int tech_)
		{
			tech = tech_;
		}
		
		public String toString()
		{
			return "player "+getPlayer()+" has tech "+tech;
		}
	}
	
	public class TriggerAttributeView
	{
		//If both are checked, it acts like an AND.
		//If one is checked, then that one determines the state.
		//If none are checked, then it is always true.
		
		public int getTrigger()
		{
			return trigger;
		}
		
		public void setTrigger(int trigger_)
		{
			trigger = trigger_;
		}
		
		public boolean isOn()
		{
			return triggerOn;
		}
		
		public void setOn(boolean on)
		{
			triggerOn = on;
		}
		
		public boolean hasFired()
		{
			return triggerFired;
		}
		
		public void setFired(boolean fired)
		{
			triggerFired = fired;
		}
		
		public String toString()
		{
			if(isOn()&&hasFired())
			{
				return "trigger "+getTrigger()+" IsOn && HasFired";
			}
			else if(isOn())
			{
				return "trigger "+getTrigger()+" IsOn";
			}
			else if(hasFired())
			{
				return "trigger "+getTrigger()+" HasFired";
			}
			
			return "trigger "+getTrigger()+" AlwaysTrue";
		}
	}
	
	public class PlayerDiplomacyView
	{
		public int getPlayer()
		{
			return player;
		}
		
		public int getOtherPlayer()
		{
			return otherPlayer;
		}
		
		public boolean isEnemies()
		{
			return enemies;
		}
		
		public String toString()
		{
			return "player "+getPlayer()+" is "+(isEnemies()?"enemies with":"allied to")+" player "+getOtherPlayer();
		}
	}
	
	public class GameAttributeView
	{
		public int getElapsedTimeMin()
		{
			return elapsedTimeMin;
		}
		
		public int getElapsedTimeMax()
		{
			return elapsedTimeMax;
		}
		
		public int getHourLeast()
		{
			return hourLeast;
		}
		
		public DifficultyLevel getDifficultyLevel()
		{
			return difficultyLevel;
		}
		
		public boolean useDifficultyLevel()
		{
			return dlevel;
		}
		
		public boolean useElapsedTime()
		{
			return etime;
		}
		
		public boolean useHourLeast()
		{
			return hleast;
		}
		
		public String toString()
		{
			String hl = "Hour >= "+getHourLeast();
			String et = getElapsedTimeMin()+" <= ElapsedTime <= "+getElapsedTimeMax();
			String dl = "DifficulyLevel == "+getDifficultyLevel();
			
			String s = "";
			
			boolean prev = false;
			if(useDifficultyLevel())
			{
				s+=dl;
				prev = true;
			}
			
			if(useElapsedTime())
			{
				if(prev)
				{
					s+=" && ";
				}
				s+=et;
			}
			
			if(useHourLeast())
			{
				if(prev)
				{
					s+=" && ";
				}
				s+=hl;
			}
			
			return s;
		}
	}
	
	public class ChatMessageView
	{
		/**
		 * Gets the string that needs to be contained in the message. It is not case sensitive.
		 * @return the contains string
		 */
		public String getContainsString()
		{
			return contains;
		}
		
		/**
		 * Sets the string that needs to be contained in the message. It is not case sensitive.
		 * @param string the contains string
		 */
		public void setContainsString(String string)
		{
			contains = string;
		}
		
		public String toString()
		{
			return "any chat contains \""+contains+"\"";
		}
	}
}
