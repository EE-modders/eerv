package net.coderbot.eerv.scn.trigger;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Effect
{
	String name;
	String desc;
	Type type;
	
	int id;
	
	//Unknown/Unused
	String u0;
	String u1;
	boolean u2, u3, u4;
	int u5;
	int u6, u7, u8;
	boolean u9;
	boolean uA;
	int uB;
	
	//Trigger
	TriggerState triggerState;
	int triggerId;
	
	//Media
	int mediaDuration;
	String mediaMessage;
	String mediaSoundFile;
	String mediaLine;
	MediaViewType mediaSubtype;
	int mediaPlayer;
	int mediaChangeTextTarget;
	boolean mediaIsClear;
	
	//Selector
	SelectorViewType selectorSubtype;
	int selectorTechId;
	String selectorClassName;
	int selectorPlayer;
	
	int selectorAttrId;
	int selectorAttrModifier;
	int selectorAttrValue;
	
	int selectorSelectorId;
	int selectorSecondSelectorId;
	
	int selectorStance;
	int selectorTask;
	int selectorAreaId;
	boolean selectorUsePercent;
	
	//Player
	PlayerViewType playerSubtype;
	int playerAttributeId;
	boolean playerIsEnemy;
	int player_AiPrimaryPlayer;//Also used by AI
	int playerSecondPlayer;
	int playerAwardedCivPoints;
	int playerAttrModifier;
	int playerAttrValue;
	int playerAreaId;
	int playerSelectorId;
	boolean playerUseArea;
	boolean playerIsAddingTech;
	
	//Game
	int gameDatabaseId;
	int gameAreaId;
	int gameSelectorId;
	int gameDaylightHour;
	int gameDayNightTime;
	GameViewType gameSubtype;
	boolean gameUseArea;
	
	//AI
	AiViewType aiSubtype;
	int aiToggleId;
	int aiVariableId;
	float aiFloatVar;
	int aiIntVar;//Media+Player:ScrollSpeed or AI:FavoriteLand or AI:IntValue0
	int aiSecondIntVar;//Player Current=0,HighPitch=1,x=(x+51) for x(>=-49) or AI:FavoriteSea or AI:IntValue1
	int[] aiSelectorList;
	boolean aiUseArea;//Player:SecondUseArea
	boolean aiAdding;
	boolean aiIsAttack;//Player:Follow
	boolean aiIsFavorite;//Player:Track
	int aiAreaId;//Player:SecondArea
	int aiSelectorId;//Player:SecondSelector
	boolean aiToggleOn;//Game:AmbientWeather_Active
	
	public void read(ByteBuffer data)
	{
		byte[] asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		desc = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		mediaDuration = data.getInt();//Media
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		mediaMessage = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		u0 = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		u1 = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		selectorClassName = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		mediaSoundFile = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		mediaLine = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		playerAttributeId = data.getInt();//Player
		playerIsEnemy = data.get()==1;
		u2 = data.get()==1;
		u3 = data.get()==1;
		u4 = data.get()==1;
		
		mediaPlayer = data.getInt();//Media: player
		selectorPlayer = data.getInt();//Selector: player
		player_AiPrimaryPlayer = data.getInt();//Player+AI: first player
		playerSecondPlayer = data.getInt();//Player: second player
		mediaSubtype = MediaViewType.values()[data.getInt()];//Media
		u5 = data.getInt();
		
		playerSubtype = PlayerViewType.values()[data.getInt()];//Player
		triggerState = TriggerState.values()[data.getInt()];//Trigger
		type = Type.values()[data.getInt()];//*
		selectorSubtype = SelectorViewType.values()[data.getInt()];//Selector
		selectorTechId = data.getInt();//Selector
		gameDatabaseId = data.getInt();//Game: Gfx/Calamity Id
		gameAreaId = data.getInt();//Game
		gameSelectorId = data.getInt();//Game
		gameDaylightHour = data.getInt();//Game daylightHour/cinematicGameSpeed: 0.1(x+1)
		gameDayNightTime = data.getInt();//Game
		
		gameSubtype = GameViewType.values()[data.getInt()];//Game
		mediaChangeTextTarget = data.getInt();//Media
		selectorAttrId = data.getInt();//Selector
		u6 = data.getInt();
		u7 = data.getInt();
		u8 = data.getInt();
		selectorAttrModifier = data.getInt();//Selector: 0=Inc/1=Dec/2=Set
		
		
		selectorAttrValue = data.getInt();//Selector
		selectorSelectorId = data.getInt();//Media/Selector or AI:FavoriteAir
		selectorStance = data.getInt();//Selector
		selectorTask = data.getInt();//Selector
		selectorAreaId = data.getInt();//Selector
		
		selectorSecondSelectorId = data.getInt();
		playerAwardedCivPoints = data.getInt();//Player
		playerAttrModifier = data.getInt();//Player
		playerAttrValue = data.getInt();//Player
		
		playerAreaId = data.getInt();//Player
		playerSelectorId = data.getInt();//Player
		triggerId = data.getInt();//Trigger
		u9 = data.get()==1;
		mediaIsClear= data.get()==1;
		playerUseArea = data.get()==1;
		selectorUsePercent = data.get()==1;
		playerIsAddingTech = data.get()==1;
		gameUseArea = data.get()==1;
		uA = data.get()==1;
		
		aiSubtype = AiViewType.values()[data.getInt()];//AI
		aiAreaId = data.getInt();//Player+AI
		aiVariableId = data.getInt();//AI
		
		aiToggleOn = data.get()==1;//Game:AmbientWeather_Active or AI:ToggleOn
		aiFloatVar = data.getFloat();//AI
		aiIntVar = data.getInt();
		aiSecondIntVar = data.getInt();
		
		aiSelectorList = new int[data.getInt()];
		for(int i = 0;i<aiSelectorList.length;i++)
		{
			aiSelectorList[i] = data.getInt();
		}
		
		uB = data.getInt();
		aiSelectorId = data.getInt();//Player+AI
		aiToggleId = data.getInt();//AI
		aiUseArea = data.get()==1;//Also Player:CamFaceUseArea
		aiAdding = data.get()==1;
		id = data.getInt();//*
		aiIsAttack = data.get()==1;
		aiIsFavorite = data.get()==1;
	}
	
	public String toString()
	{
		return "Effect {("+type.toString(this)+"), name: "+name+", desc: "+desc+", id: "+id+"}";
	}
	
	public static enum Type
	{
		NONE, SELECTOR, PLAYER, TRIGGER_STATE, MEDIA, GAME, AI;
		
		public String toString(Effect effect)
		{
			switch(this)
			{
			case AI:
				return effect.new AiView().toString();
			case SELECTOR:
				return effect.new SelectorView().toString();
			case GAME:
				return effect.new GameView().toString();
			case MEDIA:
				return effect.new MediaView().toString();
			case NONE:
				return "";
			case PLAYER:
				return effect.new PlayerView().toString();
			case TRIGGER_STATE:
				return effect.new TriggerView().toString();
			default:
				return "Unknown";
			}
		}
	}
	
	public class SelectorView
	{
		public SelectorViewType getSubtype()
		{
			return selectorSubtype;
		}

		public int getTechId()
		{
			return selectorTechId;
		}

		public String getClassName()
		{
			return selectorClassName;
		}
		
		public int getPlayer()
		{
			return selectorPlayer;
		}
		
		public int getAttributeId()
		{
			return selectorAttrId;
		}

		public int getAttributeModifier()
		{
			return selectorAttrModifier;
		}

		public int getAttributeValue()
		{
			return selectorAttrValue;
		}
		
		public int getSelectorId()
		{
			return selectorSelectorId;
		}

		public int getSecondSelectorId()
		{
			return selectorSecondSelectorId;
		}
		
		public int getStance()
		{
			return selectorStance;
		}

		public int getTask()
		{
			return selectorTask;
		}

		public int getAreaId()
		{
			return selectorAreaId;
		}
		
		public boolean usePercent()
		{
			return selectorUsePercent;
		}
		
		public boolean useSnap()
		{
			return selectorUsePercent;
		}
		
		public void setSubtype(SelectorViewType subtype)
		{
			selectorSubtype = subtype;
		}

		public void setTechId(int techId)
		{
			selectorTechId = techId;
		}

		public void setClassName(String name)
		{
			selectorClassName = name;
		}

		public void setPlayer(int player)
		{
			selectorPlayer = player;
		}
		
		public void setAttributeId(int id)
		{
			selectorAttrId = id;
		}

		public void setAttributeModifier(int modifier)
		{
			selectorAttrModifier = modifier;
		}

		public void setAttributeValue(int value)
		{
			selectorAttrValue = value;
		}
		
		public void setSelectorId(int id)
		{
			selectorSelectorId = id;
		}

		public void setSecondSelectorId(int id)
		{
			selectorSecondSelectorId = id;
		}
		
		public void setStance(int stance)
		{
			selectorStance = stance;
		}

		public void setTask(int task)
		{
			selectorTask = task;
		}

		public void setAreaId(int areaId)
		{
			selectorAreaId = areaId;
		}
		
		public void setUsePercent(boolean percent)
		{
			selectorUsePercent = percent;
		}
		
		public void setUseSnap(boolean snap)
		{
			selectorUsePercent = snap;
		}
		
		public String toString()
		{
			switch(getSubtype())
			{
			case CLASS_ATTRIBUTE:
				return "ClassAttribute "+getAttributeId()+" of "+getSelectorId()+": "+(getAttributeModifier()==1?"-":"+")+getAttributeValue()+(usePercent()?"%":"");
			case CLASS_NAME:
				return "set "+getSelectorId()+"'s ClassName = "+getClassName();
			case CREATE:
				return "create "+getSelectorId();
			case FACE:
				return "face "+getSelectorId()+" in direction "+getAttributeValue()+(useSnap()?" <snap>":"");
			case FREEZE:
				return "freeze "+getSelectorId();
			case KILL:
				return "kill "+getSelectorId();
			case OWNER:
				return "set owner of "+getSelectorId()+" to "+getPlayer();
			case REMOVE:
				return "remove "+getSelectorId();
			case STANCE:
				return "set stance of "+getSelectorId()+" to "+getStance();
			case TASK_AREA:
				return "task "+getSelectorId()+" to area "+getAreaId()+" with task "+getTask();
			case TASK_SELECTOR:
				return "task "+getSelectorId()+" to selector "+getSecondSelectorId()+" with task "+getTask();
			case UNFREEZZE:
				return "unfreeze "+getSelectorId();
			case UNIT_ATTRIBUTE:
				
				String symbol = "+";
				if(getAttributeModifier()==1)
				{
					symbol = "-";
				}
				else if(getAttributeModifier()==2)
				{
					symbol = "=";
				}
				
				return "UnitAttribute "+getAttributeId()+" of "+getSelectorId()+": "+(symbol)+getAttributeValue()+(usePercent()?"%":"");
			default:
				return "";
			}
		}
	}
	
	public static enum SelectorViewType
	{
		CREATE, FREEZE, UNFREEZZE, KILL, REMOVE, TASK_SELECTOR, TASK_AREA, STANCE, UNIT_ATTRIBUTE, OWNER, CLASS_NAME, FACE, CLASS_ATTRIBUTE;
	}
	
	public class PlayerView
	{
		public PlayerViewType getSubtype()
		{
			return playerSubtype;
		}
		
		public int getAttributeId()
		{
			return playerAttributeId;
		}
		
		public boolean isEnemy()
		{
			return playerIsEnemy;
		}
		
		public int getPlayer()
		{
			return player_AiPrimaryPlayer;
		}
		
		public int getSecondPlayer()
		{
			return playerSecondPlayer;
		}
		
		public int getAwardedCivPoints()
		{
			return playerAwardedCivPoints;
		}
		
		public int getAttributeModifier()
		{
			return playerAttrModifier;
		}
		
		public int getAttributeValue()
		{
			return playerAttrValue;
		}
		
		public int getAreaId()
		{
			return playerAreaId;
		}
		
		public int getSelectorId()
		{
			return playerSelectorId;
		}
		
		public boolean useArea()
		{
			return playerUseArea;
		}
		
		public boolean useSecondArea()
		{
			return aiUseArea;
		}
		
		public boolean isAddingTech()
		{
			return playerIsAddingTech;
		}
		
		public int getTechId()
		{
			return selectorTechId;
		}
		
		public boolean isFollow()
		{
			return aiIsAttack;
		}
		
		public boolean isTrack()
		{
			return aiIsFavorite;
		}
		
		public int getSecondAreaId()
		{
			return aiAreaId;
		}
		
		public int getSecondSelectorId()
		{
			return aiSelectorId;
		}
		
		public int getScrollSpeed()
		{
			return aiIntVar;
		}
		
		public int getZoomLevel()
		{
			return aiSecondIntVar;
		}
		
		public String toString()
		{
			switch(getSubtype())
			{
			case AWARD_CIV_POINTS:
				return "award "+getAwardedCivPoints()+" civ points to player "+getPlayer();
			case DEFEAT:
				return "defeat player "+getPlayer();
			case DIPLOMACY:
				return "set diplomacy of "+getPlayer()+" and "+getSecondPlayer()+" to "+(isEnemy()?"enemies":"allies");
			case FADE_IN:
				return "fade in player "+getPlayer();
			case FADE_OUT:
				return "fade out player "+getPlayer();
			case MINIMAP_FLARE:
				if(useArea())
				{
					return "minimap flare to player "+getPlayer()+" in area "+getAreaId();
				}
				else
				{
					return "minimap flare to player "+getPlayer()+" at selector "+getSelectorId();
				}
			case MODIFY_TECH:
				return (isAddingTech()?"add":"remove")+" tech "+getTechId()+" "+(isAddingTech()?"to":"from")+" player "+getPlayer();
			case SCRIPT_CAMERA:
				if(useArea())
				{
					return (isFollow()?"follow":"move cam to")+" area "+getAreaId()+", "+(isTrack()?"track":"face")+" "+(useSecondArea()?"area "+
							getSecondAreaId():"selector "+getSecondSelectorId()+" with scroll speed "+getScrollSpeed()+" to zoom level "+getZoomLevel());
				}
				else
				{
					return (isFollow()?"follow":"move cam to")+" selector "+getSelectorId()+", "+(isTrack()?"track":"face")+" "+(useSecondArea()?"area "+
							getSecondAreaId():"selector "+getSecondSelectorId()+" with scroll speed "+getScrollSpeed()+" to zoom level "+getZoomLevel());
				}
			case SET_ATTRIBUTE:
				String symbol = "=";
				if(getAttributeModifier()==1)
				{
					symbol = "+";
				}
				else if(getAttributeModifier()==2)
				{
					symbol = "-";
				}
				
				return "SetAttribute "+getAttributeId()+" of "+getPlayer()+": "+(symbol)+getAttributeValue();
			case VICTORY:
				return "give victory to player "+getPlayer();
			case VISIBILITY_OFF:
				return "set visibility off for player "+getPlayer();
			case VISIBILITY_ON:
				return "set visibility on for player "+getPlayer();
			default:
				return "";
			}
		}
	}
	
	public static enum PlayerViewType
	{
		VICTORY, SCRIPT_CAMERA, DEFEAT, DIPLOMACY, SET_ATTRIBUTE, MODIFY_TECH, AWARD_CIV_POINTS, FADE_IN, FADE_OUT, VISIBILITY_ON, VISIBILITY_OFF, MINIMAP_FLARE;
	}
	
	public class TriggerView
	{
		public int getTriggerId()
		{
			return triggerId;
		}
		
		public TriggerState getState()
		{
			return triggerState;
		}
		
		public String toString()
		{
			return "set "+getTriggerId()+"'s state to "+getState();
		}
	}
	
	public static enum TriggerState
	{
		ON, OFF, FIRED, NOT_FIRED;
	}
	
	public class MediaView
	{
		public MediaViewType getSubtype()
		{
			return mediaSubtype;
		}
		
		public int getDuration()
		{
			return mediaDuration;
		}
		
		public String getMessage()
		{
			return mediaMessage;
		}
		
		public String getSoundFile()
		{
			return mediaSoundFile;
		}
		
		public String getLine()
		{
			return mediaLine;
		}
		
		public int getPlayer()
		{
			return mediaPlayer;
		}
		
		public int getTextTarget()
		{
			return mediaChangeTextTarget;
		}
		
		public boolean isClear()
		{
			return mediaIsClear;
		}
		
		public int getSelectorId()
		{
			return selectorSelectorId;
		}
		
		public int getScrollSpeed()
		{
			return aiIntVar;
		}
		
		public String toString()
		{
			switch(getSubtype())
			{
			case CHANGE_TEXT:
				if(isClear())
				{
					return "clear "+getTextTarget();
				}
				else
				{
					return "addline \""+getLine()+"\" to text "+getTextTarget();
				}
			case PLAY_SOUND:
				return "play sound \""+getSoundFile()+"\"";
			case SEND_CHAT:
				return "send message \""+getMessage()+"\" to player "+getPlayer()+" for "+getDuration()+"s";
			case SEND_DIALOG:
				return "send dialog \""+getMessage()+"\" to player "+getPlayer()+" for "+getDuration()+"s from "+getSelectorId()+" while playing "+getSoundFile()+" scroll speed "+getScrollSpeed();
			default:
				return "";
			}
		}
	}
	
	public static enum MediaViewType
	{
		PLAY_SOUND, CHANGE_TEXT, SEND_DIALOG, SEND_CHAT;
	}
	
	public class GameView
	{
		public GameViewType getSubtype()
		{
			return gameSubtype;
		}
		
		public int getDatabaseId()
		{
			return gameDatabaseId;
		}
		
		public int getAreaId()
		{
			return gameAreaId;
		}
		
		public int getSelectorId()
		{
			return gameSelectorId;
		}
		
		public boolean useArea()
		{
			return gameUseArea;
		}
		
		public int getDaylightHour()
		{
			return gameDaylightHour;
		}
		
		public int getDayNightTime()
		{
			return gameDayNightTime;
		}
		
		public boolean isAmbientWeatherOn()
		{
			return aiToggleOn;
		}
		
		public int getGameSpeed()
		{
			return gameDaylightHour;
		}
		
		public String toString()
		{
			switch(getSubtype())
			{
			case AMBIENT_WEATHER:
				return "turn ambient weather "+(isAmbientWeatherOn()?"on":"off");
			case CALAMITY:
				return "start calamity "+getDatabaseId()+" at "+(useArea()?"area "+getAreaId():"selector "+getSelectorId());
			case DAYLIGHT_HOUR:
				return "set daylight hour to "+getDaylightHour();
			case DAY_NIGHT_TIME:
				return "set day night time to "+getDayNightTime();
			case END_CINEMATIC:
				return "end cinematic";
			case GRAPHIC_EFFECT:
				return "play graphic effect "+getDatabaseId()+" at "+(useArea()?"area "+getAreaId():"selector "+getSelectorId());
			case START_CINEMATIC:
				return "start cinematic at game speed "+getGameSpeed()+" ["+((getGameSpeed()+1)*0.1F)+"x]";
			default:
				return "";
			}
		}
	}
	
	public static enum GameViewType
	{
		DAYLIGHT_HOUR, DAY_NIGHT_TIME, CALAMITY, GRAPHIC_EFFECT, START_CINEMATIC, END_CINEMATIC, AMBIENT_WEATHER;
	}
	
	public class AiView
	{
		public AiViewType getSubtype()
		{
			return aiSubtype;
		}
		
		public int getPlayer()
		{
			return player_AiPrimaryPlayer;
		}
		
		public int getSelectorId()
		{
			return aiSelectorId;
		}
		
		public int getAreaId()
		{
			return aiAreaId;
		}
		
		public boolean useArea()
		{
			return aiUseArea;
		}
		
		public int getToggleId()
		{
			return aiToggleId;
		}
		
		public boolean isToggleOn()
		{
			return aiToggleOn;
		}
		
		public int getIntVar()
		{
			return aiIntVar;
		}
		
		public int getSecondIntVar()
		{
			return aiSecondIntVar;
		}
		
		public int getVariableId()
		{
			return aiVariableId;
		}
		
		public float getFloatVar()
		{
			return aiFloatVar;
		}
		
		public int getFavoriteAir()
		{
			return selectorSelectorId;
		}
		
		public int getFavoriteLand()
		{
			return aiIntVar;
		}
		
		public int getFavoriteSea()
		{
			return aiSecondIntVar;
		}
		
		public boolean isAdding()
		{
			return aiAdding;
		}
		
		public boolean isAttack()
		{
			return aiIsAttack;
		}
		
		public boolean isFavorite()
		{
			return aiIsFavorite;
		}
		
		public int[] getSelectorList()
		{
			return aiSelectorList;
		}
		
		public String toString()
		{
			switch(getSubtype())
			{
			case BUILD_SELECTOR:
				return "player "+getPlayer()+": build selector "+getSelectorId();
			case CREATE_GROUP:
				
				StringBuilder sb = new StringBuilder("player ");
				sb.append(Integer.toString(getPlayer()));
				sb.append(": create ");
				
				if(isFavorite())
				{
					sb.append("favorite ");
				}
				
				sb.append("group [");
				
				int[] entities = getSelectorList();
				if(entities!=null)
				{
					for(int i = 0;i<entities.length;i++)
					{
						sb.append(Integer.toString(entities[i]));
						if(i+1<entities.length)
						{
							sb.append(", ");
						}
					}
				}
				sb.append("] ");
				
				if(isAttack())
				{
					sb.append("attacking target" );
				}
				else
				{
					sb.append("defending target ");
				}
				
				if(useArea())
				{
					sb.append("area ");
					sb.append(Integer.toString(getAreaId()));
				}
				else
				{
					sb.append("selector ");
					sb.append(Integer.toString(getSelectorId()));
				}
				
				return sb.toString();
			case FAVORITE_UNITS:
				return "player "+getPlayer()+": air "+getFavoriteAir()+", land "+getFavoriteLand()+", sea "+getFavoriteSea();
			case MAINTAIN_SELECTOR:
				return "player "+getPlayer()+": maintain selector "+getSelectorId();
			case MODIFY_TARGET:
				return "player "+getPlayer()+": "+(isAdding()?"add":"remove")+" "+(isAttack()?"attacking":"defending")+" target: "+(useArea()?"area "+getAreaId():"selector "+getSelectorId());
			case SET_TOGGLE:
				return "player "+getPlayer()+": turn "+(isToggleOn()?"on":"off")+" toggle "+getToggleId();
			case SET_VARIABLE:
				return "player "+getPlayer()+": set variable "+getVariableId()+" to "+getIntVar()+","+getSecondIntVar()+","+getFloatVar();
			default:
				return "";
			}
		}
	}
	
	public static enum AiViewType
	{
		MODIFY_TARGET, CREATE_GROUP, BUILD_SELECTOR, MAINTAIN_SELECTOR, SET_TOGGLE, SET_VARIABLE, FAVORITE_UNITS;
	}
}
