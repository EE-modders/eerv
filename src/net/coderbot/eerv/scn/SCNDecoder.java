package net.coderbot.eerv.scn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.coderbot.eerv.PKDecoder;
import net.coderbot.eerv.scn.SCN.FileID;
import net.coderbot.eerv.scn.SCNTriggers.ChainType;
import net.coderbot.eerv.scn.SCNTriggers.CompareType;
import net.coderbot.eerv.scn.SCNTriggers.ConditionType;
import net.coderbot.eerv.scn.SCNTriggers.PlayerAttributeType;
import net.coderbot.eerv.scn.SCNTriggers.SCNCondition;
import net.coderbot.eerv.scn.SCNTriggers.SCNConditionBinding;
import net.coderbot.eerv.scn.SCNTriggers.SCNEffect;
import net.coderbot.eerv.scn.SCNTriggers.SCNEffectBinding;
import net.coderbot.eerv.scn.SCNTriggers.SCNTrigger;

import java.nio.charset.StandardCharsets;

import net.coderbot.util.Decoder;
import net.coderbot.util.DecoderException;

public class SCNDecoder extends Decoder<SCN>
{
	ByteBuffer data;
	PKDecoder pk = new PKDecoder((ByteBuffer)null);
	
	public SCNDecoder(ByteBuffer in)
	{
		super(in);
	}
	
	public SCNDecoder(Path file) throws DecoderException
	{
		super(file);
	}

	@Override
	public void setInput(ByteBuffer input)
	{
		this.data = input;
		
		if(this.data!=null)
		{
			this.data.order(ByteOrder.LITTLE_ENDIAN);
		}
	}

	public void close()
	{
		super.close();
		pk.close();
	}
	
	public static final int VERSION_MAJOR_EE1 = 0x00;//version 0_231
	public static final int VERSION_MINOR_EE1 = 0xE7;//version 0_231
	
	public static final int VERSION_MAJOR_AOC = 0x00;//version 0_250
	public static final int VERSION_MINOR_AOC = 0xFA;//version 0_250
	
	@Override
	public SCN decode() throws DecoderException
	{
		SCN scn = new SCN();
		byte[] asciiZ;
		int size = data.getInt();
		System.out.println("File says it's size is "+size+" actual is "+data.capacity());
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.description = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			//data.position(data.position()-8);
			//throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.playerList = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.date = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		System.out.println("Date: "+scn.date);
		
		byte split = data.get();
		if(split!=0)
		{
			throw new DecoderException("scn", "split byte was not 0");
		}
		
		scn.players = new Player[16];
		for(int i = 0;i<16;i++)
		{
			scn.players[i] = new Player(data);
			System.out.println(scn.players[i]);
		}
		
		scn.u0 = data.get()==1;
		scn.u1 = data.get()==1;
		scn.u2 = data.get()==1;
		
		scn.gameSpeed = data.getInt();
		scn.u3 = data.getInt();
		scn.gameVariant = data.getInt();
		scn.mapSize = data.getInt();
		scn.startEpoch = data.getInt();
		scn.endEpoch = data.getInt();
		scn.resources = data.getInt();
		scn.maxUnits = data.getInt();
		scn.wondersForVictory = data.getInt();
		scn.aiDifficulty = data.getInt();
		
		scn.victoryAllowed = data.get()==1;
		scn.lockTeams = data.get()==1;
		scn.lockSpeed = data.get()==1;
		scn.revealMap = data.get()==1;
		
		scn.cheatCodes = data.get()==1;
		scn.u4 = data.get()==1;
		scn.customCivs = data.get()==1;
		scn.u5 = data.get()==1;
		
		scn.u6 = data.getInt();
		scn.u7 = data.getInt();
		scn.u8 = data.getInt();
		scn.u9 = data.getInt();
		scn.uA = data.getInt();
		scn.uB = data.getInt();
		
		scn.uC = new int[13];
		for(int i = 0;i<13;i++)
		{
			scn.uC[i] = data.getInt();
		}
		
		split = data.get();
		if(split!=0)
		{
			throw new DecoderException("scn", "split byte was not 0");
		}
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.hints = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		System.out.println("Hints: "+scn.hints);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.history = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		System.out.println("History: "+scn.history);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.movie = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		System.out.println("Movie: "+scn.movie);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.map = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		System.out.println("Map: "+scn.map);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.instructions = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		System.out.println("Instructions: "+scn.instructions);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.soundover = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		System.out.println("Soundover: "+scn.soundover);
		
		scn.uD = data.getInt();
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			//data.position(data.position()-8);
			//throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		scn.uE = data.getInt();
		scn.uF = data.getInt();
		scn.u10 = data.getInt();
		scn.u11 = data.getInt();
		scn.u12 = data.getShort();
		scn.u13 = data.getFloat();
		
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			//data.position(data.position()-8);
			//throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		scn.u14 = data.getInt();
		scn.u15 = data.getInt();
		scn.u16 = data.getInt();
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			//data.position(data.position()-8);
			//throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		scn.u17 = data.getInt();
		scn.u18 = data.getInt();
		System.out.println(scn.uD+" "+scn.uE+" "+scn.uF+" "+scn.u10);
		System.out.println(scn.u11+" "+scn.u12+" "+scn.u13+" "+scn.u14);
		System.out.println(scn.u15+" "+scn.u16+" "+scn.u17+" "+scn.u18);
		
		pk.setInput(data);
		int pkMagic = 0;
		int i = 0;
		
		do
		{
			data.mark();
			pkMagic = data.getInt();
			if(pkMagic!=825248592)
			{
				break;
			}
			data.reset();
			
			SCNFile file = new SCNFile();
			
			file.data = pk.decode();
			
			if(!data.hasRemaining())
			{
				break;
			}
			file.hasAttribs = true;
			
			if(data.getInt()!=0||data.getInt()!=231)
			{
				//data.position(data.position()-8);
				//throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
			}
			
			file.id = FileID.get(data.getInt());
			file.u0 = data.getInt();
			
			
			if(file.u0==0x0C)
			{
				file.hasExtendedAttribs = true;
				file.u4 = data.getFloat();
				file.u5 = data.getFloat();
				file.u6 = data.getFloat();
				
				if(data.getInt()!=0||data.getInt()!=231)
				{
					//data.position(data.position()-8);
					//throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
				}
				
				file.u7 = data.getInt();
				file.u8 = data.getInt();
			}
			
			file.data.position(0);
			file.data.order(ByteOrder.LITTLE_ENDIAN);
			
			try
			{
				handleFile(file);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			Path path = Paths.get("extract/scn", scn.name.toLowerCase().split("\\.")[0],"file"+i);
			System.out.println("Saving file "+i+" to "+path);
			try
			{
				Files.createDirectories(Paths.get("extract/scn", scn.name.toLowerCase().split("\\.")[0]));
			}
			catch(IOException e)
			{
				//e.printStackTrace();
			}
			
			try
			{
				Files.deleteIfExists(path);
				Files.write(path, file.data.array(), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			System.out.println("File"+(i++)+": "+file.id+" \t<"+file.data.limit()+"> \t"+file.u0+" \t"+Integer.toHexString(file.u1&0xFF)+" \t"+Integer.toHexString(file.u2&0xFF)+" \t"+Integer.toHexString(file.u3&0xFF)+" \t"+((file.hasExtendedAttribs)?"xattribs":""));
		}
		while(pkMagic==825248592);
		
		return scn;
	}

	
	static void handleFile(SCNFile file)
	{
		/*if(file.id!=FileID.TRIGGERS&&file.id!=FileID.DIPLOMACY)
		{
			return;
		}*/
		
		byte[] asciiZ;
		ByteBuffer data = file.data;
		
		if(file.id==FileID.TRIGGERS)
		{
			int triggers = data.getInt();
			for(int i = 0;i<triggers;i++)
			{
				SCNTrigger trig = new SCNTrigger();
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				trig.name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				trig.desc = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				trig.active = data.get()==1;
				trig.looping = data.get()==1;
				trig.u0 = data.get()==1;
				trig.inCinematic = data.get()==1;
				
				trig.activeFor = data.getInt();
				trig.u1 = data.getInt();
				trig.id = data.getInt();
				
				System.out.println("triggers["+i+"] "+trig);
				
				int conditions = data.getInt();
				trig.conditions = new SCNConditionBinding[conditions];
				
				for(int c = 0;c<conditions;c++)
				{
					SCNConditionBinding binding = new SCNConditionBinding();
					binding.not = data.get()==1;
					int chainType = data.getInt();
					binding.chainType = ChainType.values()[chainType];
					binding.condition = data.getInt();
					trig.conditions[c] = binding;
					
					System.out.println("["+c+"] "+binding);
				}
				
				
				int effects = data.getInt();
				trig.effects = new SCNEffectBinding[effects];
				
				for(int e = 0;e<effects;e++)
				{
					SCNEffectBinding binding = new SCNEffectBinding();
					binding.delay = data.getInt();
					int chainType = data.getInt();
					binding.chainType = ChainType.values()[chainType];
					binding.effect = data.getInt();
					
					System.out.println("["+e+"] "+binding);
				}
			}
			
			SCNCondition cond = new SCNCondition();
			int conditions = data.getInt();
			for(int i = 0;i<conditions;i++)
			{
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				cond.name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				cond.desc = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				//CHAT MESSAGE CONTAINS
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String chatMessageContains = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				//PLAYER ATTRIBUTES
				
				int iConditionType = data.getInt();
				ConditionType type = ConditionType.values()[iConditionType];
				
				System.out.println("["+i+"] "+cond+" "+type+" contains: "+chatMessageContains);
				
				int existsId = data.getInt();
				int player = data.getInt();
				int iAttribute = data.getInt();
				PlayerAttributeType attribute = PlayerAttributeType.values()[iAttribute];
					
				System.out.println("[PAttr] existsId: "+existsId+" player: "+player+" attribute: "+attribute);
					
				int iCompareType = data.getInt();
				CompareType compareType = CompareType.values()[iCompareType];
				int amount = data.getInt();
					
				int otherPlayer = data.getInt();
				int otheriAttribute = data.getInt();
				PlayerAttributeType otherAttribute = PlayerAttributeType.values()[otheriAttribute];
				
				System.out.println(compareType+" "+amount+" ["+otherPlayer+":"+otherAttribute+"]");
					
				//TECH
				int tech = data.getInt();
				
				//TRIGGER ATTRIBUTES
				int trigger = data.getInt();
				boolean triggerOn = data.get()==1;
				boolean triggerFired = data.get()==1;
				boolean compareOtherPlayer = data.get()==1;
					
				System.out.println("tech: "+tech+" trigger: "+trigger+" on: "+triggerOn+" fired: "+triggerFired+" [PAttr]compareOtherPlayer: "+compareOtherPlayer);
					
				//GAME ATTRIBUTES
				
				int elapsedTimeMax = data.getInt();
				int elapsedTimeMin = data.getInt();
				int hourLeast = data.getInt();
				
				boolean dlevel = data.get()==1;
				boolean etime = data.get()==1;
				boolean hleast = data.get()==1;
				
				int difficultyLevel = data.getInt();
				
				System.out.println("gameAttributes: "+(dlevel?"D":"-")+(etime?"E":"-")+(hleast?"H":"-")+" elapsedTime: "+elapsedTimeMin+"->"+elapsedTimeMax+" hourLeast: "+hourLeast+" difficultyLevel: "+difficultyLevel);
					
				int bf0 = data.getInt();
				if((bf0&0xFFFFFF00)!=0)
				{
					System.out.println("bytefield unknown flags: "+Integer.toHexString(bf0&0xFFFFFF00));
				}
				
				//DIPLOMACY: 
				
				boolean enemies = (bf0&1)==1;
				System.out.println("enemies: "+enemies);
				
				int id = data.getInt();
				System.out.println("id: "+id);
			}
			
			/*
*: name
*: desc
*: conditionId
*: conditionType

PLAYER_*: player
PLAYER_*: otherPlayer
PLAYER_ATTRIBUTE: attribute
PLAYER_ATTRIBUTE: compareType
PLAYER_ATTRIBUTE: amount
PLAYER_ATTRIBUTE: otherAttribute
PLAYER_ATTRIBIUTE: compareOtherPlayer
PLAYER_TECH: techId
PLAYER_DIPLOMACY: isEnemies
TRIGGER_ATTRIBUTE: triggerId
TRIGGER_ATTRIBUTE: triggerOn
TRIGGER_ATTRIBUTE: triggerFired
GAME_ATTRIBUTE: elapsedTimeMax
GAME_ATTRIBUTE: elapsedTimeMin
GAME_ATTRIBUTE: hourLeast
GAME_ATTRIBUTE: useDifficultyLevel
GAME_ATTRIBUTE: useElapsedTime
GAME_ATTRIBUTE: useHourLeast
GAME_ATTRIBUTE: difficultyLevel
CHAT:containsString
EXISTS:existsId
			 */
			
			SCNEffect effect = new SCNEffect();
			int effects = data.getInt();
			for(int i = 0;i<effects;i++)
			{
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				effect.name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				effect.desc = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				int duration = data.getInt();//Media
				System.out.println("-------------------------------- "+effect+" "+duration);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String message = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				System.out.println("[Message] "+message);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String u0 = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				System.out.println("[0] "+u0);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String u1 = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				System.out.println("[1] "+u1);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String setClassName = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				System.out.println("[ClassName] "+setClassName);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String soundFile = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				System.out.println("[SoundFile] "+soundFile);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String line = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				System.out.println("[Line] "+line);
				
				int playerAttributeId = data.getInt();//Player
				int bf3 = data.getInt();//Player: [0x1=IsEnemy][0x100=?][0x10000=?][0x1000000=?]
				int mediaPlayer = data.getInt();//Media: player
				int objectPlayer = data.getInt();//Object: player
				int player0 = data.getInt();//Player+AI: first player
				int player1 = data.getInt();//Player: second player
				int mediaSubtype = data.getInt();//Media
				int u2 = data.getInt();
				int playerSubtype = data.getInt();//Player
				
				System.out.println("[0] playerAttributeId="+playerAttributeId+" bf3="+Integer.toHexString(bf3)+" media:player="+mediaPlayer+" object:player="+objectPlayer+" player0="+player0+" player1="+player1+" mediaSubtype="+mediaSubtype+" "+u2+" playerSubtype="+playerSubtype);
				
				int triggerState = data.getInt();//Trigger
				int type = data.getInt();//*
				int objectSubtype = data.getInt();//Object
				int techId = data.getInt();//Object
				int gameId = data.getInt();//Game: Gfx/Calamity Id
				int areaId = data.getInt();//Game
				int objectId = data.getInt();//Game
				int daylightHour = data.getInt();//Game daylightHour/cinematicGameSpeed: 0.1(x+1)
				int dayNightTime = data.getInt();//Game
				
				System.out.println("[1] triggerState="+triggerState+" type="+type+" objectSubtype="+objectSubtype+" techId="+techId+" gameId="+gameId+" areaId="+areaId+" objectId="+objectId+" daylightHourAndCGameSpeed="+daylightHour+" dayNightTime="+dayNightTime);
				
				int gameSubtype = data.getInt();//Game
				int changeTextTarget = data.getInt();//Media
				int attributeId = data.getInt();//Object
				int u3 = data.getInt();
				int u4 = data.getInt();
				int u5 = data.getInt();
				int attrModifier = data.getInt();//Object: 0=Inc/1=Dec/2=Set
				
				System.out.println("[2] gameSubtype="+gameSubtype+" changeTextTarget="+changeTextTarget+" attributeId="+attributeId+" "+u3+" "+u4+" "+u5+" attrModifier="+attrModifier);
				
				int attrValue = data.getInt();//Object
				int objectId2 = data.getInt();//Media/Object or AI:FavoriteAir
				int stance = data.getInt();//Object
				int task = data.getInt();//Object
				int areaId2 = data.getInt();//Object
				int u6 = data.getInt();
				int awardedCivPoints = data.getInt();//Player
				int playerAttrModifier = data.getInt();//Player
				int playerAttrValue = data.getInt();//Player
				
				System.out.println("[3] attrValue="+attrValue+" objectId2|ai:favoriteAir="+objectId2+" stance="+stance+" task="+task+" areaId2="+areaId2+" "+u6+" awardedCivPoints="+awardedCivPoints+" playerAttrModifier="+playerAttrModifier+" playerAttrValue="+playerAttrValue);
				
				int playerAreaId = data.getInt();//Player
				int playerObjectId = data.getInt();//Player
				int triggerId = data.getInt();//Trigger
				int bf0 = data.getInt();//[0x1=?][0x100=Media:IsAddLine][0x10000=Player:UseArea][0x1000000=Object:UsePercent]
				int bf1 = data.getInt()&0x00FFFFFF;//[0x1=Player:IsAddingTech][0x100=Game:UseArea][0x10000=?]
				data.position(data.position()-1);//Bytefield is only 3 bytes
				
				int aiSubtype = data.getInt();//AI
				int camFaceAreaId = data.getInt();//Player+AI
				byte varId = data.get();//AI
				
				System.out.println("[4] playerAreaId="+playerAreaId+" playerObjectId="+playerObjectId+" triggerId="+triggerId+" bf0="+Integer.toHexString(bf0)+" bf1="+Integer.toHexString(bf1)+" aiSubtype="+aiSubtype+" camFaceAreaId="+camFaceAreaId+" varId="+varId);
				
				int bf2 = data.getInt();//[0x1=?][0x100=?][0x10000=?][0x1000000=Game:AmbientWeather_Active or AI:ToggleOn]
				float floatVar = data.getFloat();//AI
				int camScrollSpeed = data.getInt();//Media+Player or AI:FavoriteLand or AI:IntValue0
				int camZoomLevel = data.getInt();//Player Current=0,HighPitch=1,x=(x+51) for x(>=-49) or AI:FavoriteSea or AI:IntValue1
				
				int listSize = data.getInt();
				System.out.print("[6] objectId list: [");
				for(int l = 0;l<listSize;l++)
				{
					System.out.print(data.getInt());
					if(l+1<listSize)
					{
						System.out.print(", ");
					}
				}
				System.out.println(']');
				
				int u7 = data.getInt();
				int camFaceObjectId = data.getInt();//Player+AI
				int toggleId = data.getInt();//AI
				short bf4 = data.getShort();//[0x1=Player:CamFaceUseArea or AI:UseArea][0x100=AI:AddingX]
				int id = data.getInt();//*
				short bf5 = data.getShort();//[0x1=Player:CamFollow or AI:IsAttack][0x100=Player:CamTrack or AI:IsFavorite]
				
				System.out.println("[6] bf2="+bf2+" floatVar="+floatVar+" camScrollSpeed|ai:favoriteLand="+camScrollSpeed+" camZoomLevel|ai:favoriteSea="+camZoomLevel+" "+u7+" camFaceObjectId="+camFaceObjectId+" toggleId="+toggleId+" bf4="+bf4+" id="+id+" bf5="+bf5);
			}
			
			int objects = data.getInt();
			
			SCNObject object = new SCNObject();
			for(int i = 0;i<objects;i++)
			{
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				object.name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				//91
				
				System.out.println(object);
				
				System.out.print("ObjectsOnMap: [");
				int onMap = data.getInt();
				for(int o = 0;o<onMap;o++)
				{
					System.out.print(data.getInt());
					if(o+1<onMap)
					{
						System.out.print(", ");
					}
				}
				
				System.out.println("]");
				
				boolean selectOnMap = data.get()==1;
				int selectionPlayerId = data.getInt();//Owner of object must be this player
				int familySelectionId = data.getInt();//Family must be this
				int classSelectionId = data.getInt();//Class must be this
				int objectSelectionId = data.getInt();//Constrain to single object id
				int minSelected = data.getInt();
				int maxSelected = data.getInt();
				boolean dynamic = data.get()==1;
				int areaMode = data.getInt();
				int losMode = data.getInt();//Also range and near
				
				System.out.println("selectOnMap="+selectOnMap+" selectionPlayerId="+selectionPlayerId+" familySelectionId="+familySelectionId+" classSelectionId="+classSelectionId+" objectSelectionId="+objectSelectionId+" minSelected="+minSelected+" maxSelected="+maxSelected+" dynamic="+dynamic+" areaMode="+areaMode+" losMode="+losMode);
				
				int attrMode = data.getInt();
				int visibleByMode = data.getInt();//DISABLED/NORMAL/NOT
				int hasStateMode = data.getInt();
				int selectedByMode = data.getInt();
				int areaId = data.getInt();
				int rangeType = data.getInt();//LOS/RANGE/NEAR
				int objectId = data.getInt();
				int attrId = data.getInt();
				
				System.out.println("attrMode="+attrMode+" visibleByMode="+visibleByMode+" hasStateMode="+hasStateMode+" selectedByMode="+selectedByMode+" areaId="+areaId+" rangeType="+rangeType+" objectId="+objectId+" attrId="+attrId);
				
				int min = data.getInt();
				int max = data.getInt();
				int u1 = data.getInt();
				int u2 = data.getInt();
				int player = data.getInt();
				boolean u3 = data.get()==1;
				int id = data.getInt();
				
				System.out.println("min="+min+" max="+max+" "+u1+" "+u2+" "+u3+" player="+player+" id="+id);
			}
			
			int areas = data.getInt();
			SCNArea area = new SCNArea();
			for(int i = 0;i<areas;i++)
			{
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				area.name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				area.desc = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				
				System.out.println(area);
				
				float x = data.getFloat();
				float y = data.getFloat();
				float xMin = data.getFloat();
				float yMin = data.getFloat();
				float xMax = data.getFloat();
				float yMax = data.getFloat();
				
				byte u8 = data.get();
				byte u9 = data.get();
				int type = data.getInt();
				int u7 = data.getInt();
				int id = data.getInt();
				
				System.out.println("x="+x+" y="+y+" xMin="+xMin+" yMin="+yMin+" xMax="+xMax+" yMax="+yMax);
				System.out.println("type="+type+" "+u7+" "+u8+" "+u9+" id="+id);
			}
			
			int u0 = data.getInt();
			int u1 = data.getInt();
			int u2 = data.getInt();
			int u3 = data.getInt();
			
			int u4 = data.getInt();
			int u5 = data.getInt();
			int u6 = data.getInt();
			
			float timeMilliseconds = data.getFloat();//in milliseconds
			float u8 = data.getFloat();
			
			System.out.println(u0+" "+u1+" "+u2+" "+u3+" "+u4+" "+u5+" "+u6+" time="+timeMilliseconds/1000+"s "+u8);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String u9 = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[0] "+u9);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String hints = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[Hints] "+hints);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String history = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[History] "+history);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String movie = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[Movie] "+movie);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String map = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[Map] "+map);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String instructions = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[Instructions] "+instructions);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String soundover = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[Soundover] "+soundover);
			
			int uA = data.getInt();
			int uB = data.getInt();
			int uC = data.getInt();
			short uD = data.getShort();
			
			System.out.println(uA+" "+uB+" "+uC+" "+uD);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String localizationFile = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[LocalizationFile] "+localizationFile);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String uE = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			System.out.println("[2] "+uE);
		}
		
		if(file.id==FileID.TERRAIN)
		{
			int x = data.getInt();
			int y = data.getInt();
			
			int uProp = data.getInt();
			
			int skip;
			System.out.println(x+"x"+y+": "+uProp);
			
			if(uProp==9)
			{
				skip = 20;
			}
			else if(uProp==8)
			{
				skip = 16;
			}
			else if(uProp==17)
			{
				skip = 36;
			}
			else
			{
				System.out.println("Unknown value for uProp: "+uProp);
				return;
			}
			
			int total = 0;
			int big = 0;
			int with = 0;
			int bigwith = 0;
			
			//Format: height, isVisible[playerId], length of extraDat
			
			for(int r = 0;r<480;r++)
			{
				float f = data.getFloat();
				//System.out.println("["+r+":"+(bb.position()-4)+"] "+f);
				file.data.position(file.data.position()+skip);
				int sect = data.getInt();
				
				if(sect!=0)
				{
					with++;
				}
				
				total+=sect;
				int ba = SCNTerrain.handleEntries(file.data, sect);
				big+=ba;
				if(ba>0)
				{
					bigwith++;
				}
			}
			
			System.out.println("Entries: "+total+" total, "+big+" big among "+bigwith+" points, "+(total-big)+" small among "+with+" points ");
		}
		
		if(file.id==FileID.DIPLOMACY)
		{
			//TODO: gameAttributes
			int nPairs = data.getInt();
			System.out.println("Teams ("+nPairs+"):");
			for(int p = 0;p<nPairs;p++)
			{
				int count = data.getInt();
				System.out.print(" "+count+": [");
				for(int pi = 0;pi<count;pi++)
				{
					int c0 = data.getInt();
					System.out.print(c0+(pi+1<count?", ":"]\n"));
				}
			}
			
			int ui0 = data.getInt();
			float uf0 = data.getFloat();
			byte ub0 = data.get();
			float uf1 = data.getFloat();
			int maxUnits2 = data.getInt();
			float uf2 = data.getFloat();
			
			boolean victoryAllowed, lockTeams, unitImprovements, cheatCodes;
			//0?0?0?01
			int bf0 = data.getInt();
			victoryAllowed = (bf0&1)==1;
			unitImprovements = (bf0&256)==256;
			lockTeams = (bf0&65536)==65536;
			cheatCodes = (bf0&0x01000000)==0x01000000;
			
			
			int ui1 = data.getInt();
			short us0 = data.getShort();
			
			System.out.println("wondersForVictory:  "+ui0);
			System.out.println("uf0:  "+uf0);
			System.out.println("ub0:  "+ub0);
			System.out.println("uf1:  "+uf1);
			System.out.println("maxUnits:  "+maxUnits2);
			System.out.println("tickrate:  "+uf2);
			System.out.println("victoryAllowed: "+victoryAllowed+" unitImprovements: "+unitImprovements+" lockTeams: "+lockTeams+" cheatCodes: "+cheatCodes);
			System.out.println("ui1:  "+ui1);
			System.out.println("us0:  "+us0);
			
			int count = data.getInt();
			System.out.println("Messages: "+count);
			
			int len = -1;
			for(int m = 0;m<count;m++)
			{
				int zero0 = data.getInt();//Always 0
				len = data.getInt();
				asciiZ = new byte[len];
				data.get(asciiZ);
				String sender = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				if(len==0)
				{
					break;
				}
				
				float r = data.getFloat();
				float g = data.getFloat();
				float b = data.getFloat();
				asciiZ = new byte[data.getInt()];
				
				
				data.get(asciiZ);
				String message = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
				int unk = data.getInt();
				System.out.println(" - unk=[dec: "+unk+" hex: "+Integer.toHexString(unk)+"] rgb=["+r+", "+g+", "+b+"] <"+sender+"> "+message);
			}
			
			System.out.println("end: "+data.getShort());
		}
		
		if(file.id==FileID.OBJECTS)
		{
			int players = data.getInt();
			int u0 = data.getInt();
			int u1 = data.getInt();
			System.out.println("u0="+u0+" u1="+u1);
			
			int playerIdx = 0;
			for(int p = 0;p<players;p++)
			{
				int isAI = data.getInt();
				data.position(data.position()+40);
				int entries = data.getInt();
				int u0p = data.getInt();
				
				for(int e = 0;e<entries;e++)
				{
					data.position(data.position()+77);
					/*int sequence = data.getInt();
					System.out.println(sequence+" "+data.getInt()+" "+data.get());
					System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
					System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
					System.out.println(data.getInt());*/
				}
				
				int zero0 = data.getInt();
				int zero1 = data.getInt();
				short zero2 = data.getShort();
				
				System.out.println(u0p+" "+zero0+" "+zero1+" "+zero2);
				
				float r = data.getFloat();
				float g = data.getFloat();
				float b = data.getFloat();
				
				System.out.println("Color r="+r+" g="+g+" b="+b);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String name = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.US_ASCII);
				System.out.println("Player name: "+name);
				int objects = data.getInt();
				System.out.println("Objects: "+objects);
				
				int groups = 0;
				
				for(int total = 0;groups<128;)
				{
					total = readGroup(file, data, total, groups);
					groups++;
				}
				
				System.out.println("GroupEnd: "+file.data.position()+" groups: "+groups);
				
				System.out.println("Between (format=ISII) "+data.getInt()+" "+data.getShort()+" "+data.getInt()+" "+data.getInt());
				
				int techEntries = data.getInt();
				System.out.println("Tech Tree ("+techEntries+" entries): ");
				
				for(int i = 0;i<techEntries;i++)
				{
					System.out.println(data.getInt()+" "+(data.get()==1?"+":"-")+(data.get()==1?"+":"-")+(data.get()==1?"+":"-"));
				}
				
				data.position(data.position()+72);
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String u4 = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.US_ASCII);
				System.out.println(u4);
				
				data.position(data.position()+25);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String civilization = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.US_ASCII);
				System.out.println("["+playerIdx+"] Civilization: "+civilization);
				
				System.out.println("PosBefore: "+file.data.position());
				
				data.position(data.position()+217);
				
				int len = data.getInt();
				data.position(data.position()+(len*8));
				int len8 = data.getInt();
				System.out.println("len8="+len8);
				data.position(data.position()+13);
				data.position(data.position()+len8*8);
				int len7 = data.getInt();
				System.out.println("len7="+len7);
				data.position(data.position()+13);
				data.position(data.position()+len7*8);
				
				System.out.println("PosBefore280: "+file.data.position());
				
				data.position(data.position()+280);
				int notWorld = data.getInt();
				System.out.println("notWorld="+notWorld);
				data.get();
				int hasAI = data.getInt();
				System.out.println("hasAI="+hasAI);
				data.get();
				int inactiveAI = data.getInt();
				System.out.println("inactiveAI="+inactiveAI);
				data.get();
				
				data.position(data.position()+123);
				
				int len2 = data.getInt();
				System.out.println("len2="+len2);
				data.position(data.position()+(len2*4));
				
				int u5 = data.getInt();
				int u6 = data.getInt();
				int u7 = data.getShort();
				System.out.println(u5+" "+u6+" "+u7);
				
				System.out.println("PosBeforeStr: "+file.data.position());
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String name2 = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.US_ASCII);
				System.out.println("["+playerIdx+"] Name2: "+name2);
				
				System.out.println("PosBefore160: "+file.data.position());
				
				data.position(data.position()+160);
				
				System.out.println("PosBeforeStr: "+file.data.position());
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String name3 = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.US_ASCII);
				System.out.println("["+playerIdx+"] Name3: "+name3);
				
				System.out.println("PosBefore88: "+file.data.position());
				
				data.position(data.position()+8);
				int len4 = data.getInt();
				System.out.println("len4="+len4);
				for(int i = 0;i<len4;i++)
				{
					data.position(data.position()+97);
					int len5 = data.getInt();
					System.out.println(" len="+len5);
					data.position(data.position()+len5*4);
					data.position(data.position()+12);
				}
				
				data.position(data.position()+52);
				
				int len3 = data.getInt();
				System.out.println("len3="+len3);
				data.position(data.position()+(len3*29));
				
				data.position(data.position()+20);
				
				System.out.println("BeforeAIData: "+file.data.position());
				
				if(isAI==1)
				{
					int len9 = data.getInt();
					data.position(data.position()+len9*8);
					
					data.position(data.position()+8);
					
					int lenA = data.getInt();
					data.position(data.position()+lenA*4);
					
					data.position(data.position()+96);
					
					int len5 = data.getInt();
					System.out.println("len5="+len5);
					data.position(data.position()+len5*6);
					data.position(data.position()+121);
					
					System.out.println("M "+data.getInt()+" "+data.getInt());
					
					data.position(data.position()+72);
					
					data.get();
					int len6 = data.getInt();
					System.out.println("len6="+len6);
					if(len6==1)
					{
						data.position(data.position()+22);
					}
					
					data.position(data.position()+89);
					
					int lenB = data.getInt();
					data.position(data.position()+lenB*8);
					
					data.position(data.position()+85);
					
					int lenC = data.getInt();
					System.out.println("lenC="+lenC);
					data.position(data.position()+lenC*9);
					
					data.position(data.position()+570);
					
					int lenD = data.getInt();
					data.position(data.position()+lenD*8);
					
					data.position(data.position()+133);
				}
				
				System.out.println("End: "+file.data.position());
				playerIdx++;
			}
			
			
			
			//
		}
		
		if(file.id==FileID.FILE3_TINY)
		{
			int ui0 = data.getInt();
			int ui1 = data.getInt();
			float uf0 = data.getFloat();
			float uf1 = data.getFloat();
			float uf2 = data.getFloat();
			
			int ui2 = data.getInt();
			int ui3 = data.getInt();
			
			float uf3 = data.getFloat();
			float uf4 = data.getFloat();
			float uf5 = data.getFloat();
			float uf6 = data.getFloat();
			float uf7 = data.getFloat();
			float uf8 = data.getFloat();
			
			int ui4 = data.getInt();
			
			System.out.println(ui0+" "+ui1);
			System.out.println(uf0+" "+uf1+" "+uf2);
			System.out.println(ui2+" "+ui3);
			System.out.println(uf3+" "+uf4+" "+uf5);
			System.out.println(uf6+" "+uf7+" "+uf8);
			System.out.println(ui4);
		}
		
	}
	
	public static int readGroup(SCNFile file, ByteBuffer data, int total, int i)
	{
		int size = data.getInt();
		
		System.out.println("["+i+"] Group: "+(file.data.position()-4)+" size="+size);
		
		for(int f = 0;f<size;f++)
		{
			total++;
			int type = data.getInt();
			if(type>999)
			{
				throw new IllegalStateException("u messed up, a type of "+type+" is probably invalid");
			}
			
			boolean hasData = data.get()==1;
			
			if(!hasData)
			{
				System.out.println("Tiny "+type);
				continue;
			}
			
			boolean isExtended = data.get()==1;
			
			int x = data.getInt();
			int y = data.getInt();
			
			file.data.position(file.data.position()+11);
			
			float u6 = data.getFloat();
			int resourceLevel = data.getInt();
			
			//Read: 29
			
			if(!isExtended)
			{
				int z = data.getInt();
				System.out.println(total+" !Ext type: <"+type+"> at ["+x+" "+y+"] <u6: "+u6+" resources: "+resourceLevel+" zero: "+z+">");
			}
			else
			{
				System.out.println("Extended Object offs: "+file.data.position());
				System.out.println(total+"  Ext type: <"+type+"> at ["+x+" "+y+"] <u6: "+u6+" resources: "+resourceLevel+">");
				data.position(data.position()+16);
				int len = data.getInt();
				System.out.println("len="+len);
				data.position(data.position()+len*20);
				
				String[] names = new String[]{
					"[ 0]",
					"[ 1]",
					"[ 2]",
					"[ 3]",
					"[ 4]",
					"[ 5]",
					"X",
					"[ 7]",
					"[ 8]",
					"[ 9]",
					"Y",
					"[11]",
					"[12]",
					"[13]",
					"[14]",
					"[15]",
					"[16]",
					"[17]",
					"[18]",
					"[19]",
					"[20]",
					"[21]",
					"[22]",
					"[23]",
					"[24]",
					"[25]",
					"[26]",
					"[27]",
					"[28]",
					"[29]",
					"[30]",
					"[31]",
					"[32]",
					"[33]",
					"[34]",
					"[35]",
					"[36]",
					"[37]",
					"[38]",
					"[39]",
					"[40]",
					"[41]",
					"[42]",
					"[43]",
					"[44]",
					"[45]",
					"[46]",
					"[47]",
					"[48]",
					"[49]",
					"[50]",
					"[51]",
					"[52]",
					"[53]",
					"[54]",
					"[55]",
					"[56]",
					"[57]",
					"[58]",
					"[59]",
					"[60]",
					"[61]",
					"[62]",
					"[63]",
					"[64]",
					"[65]",
					"[66]",
					"[67]",
					"[68]",
					"[69]",
					"[70]",
					"[71]",
					"[72]",
					"[73]",
					"[74]",
					"[75]",
					"[76]",
					"[77]",
					"[78]",
					"[79]",
					"[80]",
					"[81]",
					"[82]",
					"[83]",
					"[84]",
					"[85]",
					"[86]",
					"[87]",
					"[88]",
				};
				
				System.out.println("-> "+data.position());
				for(int fl = 0;fl<19;fl++)
				{
					float val = data.getFloat();
					System.out.print(names[fl]+"="+val+" ");
					
					if((fl&7)==0&&fl!=0)
					{
						System.out.println(" pos="+data.position());
					}
				}
				
				byte u8 = data.get();
				System.out.println("u8="+u8);
				System.out.print(names[19]+"="+data.getFloat()+" ");
				System.out.print(names[20]+"="+data.getInt()+" ");
				
				for(int fl = 21;fl<41;fl++)
				{
					float val = data.getFloat();
					System.out.print(names[fl]+"="+val+" ");
					
					if((fl&7)==0&&fl!=0)
					{
						System.out.println(" pos="+data.position());
					}
				}
				
				for(int fl = 41;fl<47;fl++)
				{
					int val = data.getInt();
					System.out.print(names[fl]+"="+val+" ");
					
					if((fl&7)==0&&fl!=0)
					{
						System.out.println(" pos="+data.position());
					}
				}
				
				for(int fl = 47;fl<63;fl++)
				{
					float val = data.getFloat();
					System.out.print(names[fl]+"="+val+" ");
					
					if((fl&7)==0&&fl!=0)
					{
						System.out.println(" pos="+data.position());
					}
				}
				
				for(int fl = 63;fl<68;fl++)
				{
					int val = data.getInt();
					System.out.print(names[fl]+"="+val+" ");
				}
				
				System.out.println(" pos="+data.position());
				
				for(int bl = 0;bl<15;bl++)
				{
					byte b = data.get();
					if(b==1)
					{
						System.out.print(Integer.toHexString(bl).toUpperCase());
					}
					else if(b==0)
					{
						System.out.print('_');
					}
					else
					{
						System.out.print('?');
						throw new IllegalStateException("not boolean: "+b);
					}
				}
				
				System.out.println();
				
				for(int fl = 63;fl<70;fl++)
				{
					float val = data.getFloat();
					System.out.print(names[fl]+"="+val+" ");
					
					if((fl&7)==0&&fl!=0)
					{
						System.out.println(" pos="+data.position());
					}
				}
				
				System.out.println("\n"+names[70]+"="+data.getInt());
				byte u9 = data.get();
				System.out.println("u9="+u9+" "+names[71]+"="+data.getFloat()+" "+names[72]+"="+data.getFloat()+" "+names[73]+"="+data.getFloat());
				
				byte uA = data.get();
				System.out.println("uA="+uA+" "+names[74]+"="+data.getFloat()+" "+names[75]+"="+data.getFloat()+" "+names[76]+"="+data.getFloat());
				
				for(int bl = 0;bl<10;bl++)
				{
					byte b = data.get();
					if(b==1)
					{
						System.out.print(Integer.toHexString(bl).toUpperCase());
					}
					else if(b==0)
					{
						System.out.print('_');
					}
					else
					{
						System.out.print('?');
						throw new IllegalStateException("not boolean: "+b);
					}
				}
				
				System.out.println("\n<- "+data.position());
				
				int u = data.getInt();
				
				System.out.println("pos="+data.position()+" ?="+u);
				int waypoints = data.getInt();
				System.out.println("waypoints="+waypoints);
				
				for(int j = 0;j<waypoints;j++)
				{
					//1->55
					System.out.println("	[0:B]="+data.get());
					System.out.println("	[1:I]="+data.getInt());
					System.out.println("	[2:B]="+data.get());
					
					System.out.println("	id="+data.getInt());
					
					System.out.println("	[3:F]="+data.getFloat());//Direction?
					System.out.println("	[4:F]="+data.getFloat());//Direction?
					System.out.println("	[5:B]="+data.get());
					System.out.println("	x="+data.getFloat());
					System.out.println("	y="+data.getFloat());
					System.out.println("	[6:I]="+data.getInt());
					System.out.println("	[7:I]="+data.getInt());
					System.out.println("	[8:I]="+data.getInt());
				}
				
				System.out.println("WaypointEnd: "+data.position());
				
				//0->25
				
				if(u==1)
				{
					System.out.println("{ 0:I}="+data.getInt());
					System.out.println("{ 1:I}="+data.getInt());
					System.out.println("{ 2:B}="+data.get());
				}
				else
				{
					data.position(data.position()+22);
				}
				
				System.out.println("{ 3:I}="+data.getInt());
				System.out.println("{ 4:I]="+data.getInt());
				System.out.println("{ 5:I}="+data.getInt());
				System.out.println("{ 6:I}="+data.getInt());
				
				int len2 = data.getInt();
				System.out.println("len2="+len2);
				
				data.position(data.position()+len2*4);
				
				System.out.println("{ 7:F}="+data.getFloat());
				System.out.println("{ 8:F}="+data.getFloat());
				System.out.println("{ 9:B}="+data.get());
				System.out.println("{10:F}="+data.getFloat());
				System.out.println("{11:F}="+data.getFloat());
				
				file.data.position(file.data.position()+98);
				
				if(waypoints==2)
				{
					//System.exit(0);
				}
				
				System.out.println("End: "+data.position());
			}
		}

		return total;
	}
}
