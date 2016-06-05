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
import net.coderbot.eerv.scn.trigger.Area;
import net.coderbot.eerv.scn.trigger.Condition;
import net.coderbot.eerv.scn.trigger.Effect;
import net.coderbot.eerv.scn.trigger.Selector;
import net.coderbot.eerv.scn.trigger.Trigger;

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
		scn.name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.description = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			//data.position(data.position()-8);
			//throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.playerList = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.date = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		
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
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.campaignName = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("CampaignName: "+scn.campaignName);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.scenarioName = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("ScenarioName: "+scn.scenarioName);
		
		
		scn.u6 = data.getInt();
		scn.u7 = data.get();
		
		System.out.println(scn.u6+" "+scn.u7);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.forcedName = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("ForcedName: "+scn.forcedName);
		
		scn.u8 = new int[13];
		for(int i = 0;i<13;i++)
		{
			scn.u8[i] = data.getInt();
			System.out.println(scn.u8[i]);
		}
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.u9 = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("UnknownString: "+scn.u9);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.hints = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("Hints: "+scn.hints);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.history = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("History: "+scn.history);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.movie = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("Movie: "+scn.movie);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.map = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("Map: "+scn.map);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.instructions = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
		System.out.println("Instructions: "+scn.instructions);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.soundover = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
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
			
			if(data.remaining()<4)
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
			
			System.out.println("File"+(i++)+": "+file.id+" \t<"+file.data.limit()+"> \t"+file.u0+"  \t"+((file.hasExtendedAttribs)?"xattribs":""));
		}
		while(pkMagic==825248592);
		
		return scn;
	}

	
	static void handleFile(SCNFile file)
	{
		byte[] asciiZ;
		ByteBuffer data = file.data;
		
		if(file.id==FileID.TRIGGERS)
		{
			int triggers = data.getInt();
			for(int i = 0;i<triggers;i++)
			{
				Trigger trig = new Trigger();
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				trig.setName(new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1));
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				trig.setDescription(new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1));
				
				trig.setActive(data.get()==1);
				trig.setLooping(data.get()==1);
				trig.setFired(data.get()==1);
				trig.setInCinematic(data.get()==1);
				
				trig.setActiveFor(data.getInt());
				trig.setSecondsActive(data.getInt());
				trig.setId(data.getInt());
				
				System.out.println("triggers["+i+"] "+trig);
				
				int conds = data.getInt();
				Trigger.ConditionBinding[] conditions = new Trigger.ConditionBinding[conds];
				trig.setConditions(conditions);
				
				for(int c = 0;c<conds;c++)
				{
					boolean not = data.get()==1;
					int chainType = data.getInt();
					int conditionId = data.getInt();
					conditions[c] = new Trigger.ConditionBinding(conditionId, not, Trigger.ChainType.values()[chainType]);
					
					System.out.println("["+c+"] "+conditions[c]);
				}
				
				
				int effs = data.getInt();
				Trigger.EffectBinding[] effects = new Trigger.EffectBinding[effs];
				
				for(int e = 0;e<effs;e++)
				{
					int delay = data.getInt();
					int chainType = data.getInt();
					int effectId = data.getInt();
					effects[e] = new Trigger.EffectBinding(effectId, delay, Trigger.ChainType.values()[chainType]);
					
					System.out.println("["+e+"] "+effects[e]);
				}
			}
			
			int conditions = data.getInt();
			for(int i = 0;i<conditions;i++)
			{
				Condition cond = new Condition();
				cond.read(data);
				System.out.println("cond "+cond);
			}
			
			int effects = data.getInt();
			for(int i = 0;i<effects;i++)
			{
				Effect effect = new Effect();
				effect.read(data);
				System.out.println(effect);
			}
			
			int selectors = data.getInt();
			
			for(int i = 0;i<selectors;i++)
			{
				Selector selector = new Selector();
				selector.read(data);
				System.out.println(selector);
			}
			
			int areas = data.getInt();
			for(int i = 0;i<areas;i++)
			{
				Area area = new Area();
				area.read(data);
				System.out.println(area);
			}
			
			int queued = data.getInt();
			System.out.println(queued+" queued effects");
			for(int i = 0;i<queued;i++)
			{
				int effectId = data.getInt();
				int executeAt = data.getInt();
				byte u0 = data.get();
				
				System.out.println("["+i+"] effectId="+effectId+" executeAt="+executeAt+"ms u0="+u0);
			}
			
			System.out.println("pos: "+data.position());
			
			int len = data.getInt();
			System.out.println("len: "+len);
			for(int i = 0;i<len;i++)
			{
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String name = new String(asciiZ, 0, asciiZ.length>0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
				
				System.out.println("["+i+"] "+name);
				
				data.position(data.position()+243);
			}
			
			int verifyTriggers = data.getInt();
			int verifyConditions = data.getInt();
			int verifyEffects = data.getInt();
			int verifyObjects = data.getInt();
			int verifyAreas = data.getInt();
			
			float timeMilliseconds = data.getFloat();//in milliseconds
			float u1 = data.getFloat();
			
			System.out.println("verifyTriggers="+verifyTriggers+" verifyConditions="+verifyConditions+" verifyEffects="+verifyEffects+" verifyObjects="+verifyObjects
					+" verifyAreas="+verifyAreas+" time="+timeMilliseconds/1000+"s "+u1);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String u2 = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[0] "+u2);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String hints = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[Hints] "+hints);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String history = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[History] "+history);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String movie = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[Movie] "+movie);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String map = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[Map] "+map);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String instructions = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[Instructions] "+instructions);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String soundover = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[Soundover] "+soundover);
			
			int u3 = data.getInt();
			int u4 = data.getInt();
			int u5 = data.getInt();
			short u6 = data.getShort();
			
			System.out.println(u3+" "+u4+" "+u5+" "+u6);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String localizationFile = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[LocalizationFile] "+localizationFile);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String u7 = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
			System.out.println("[2] "+u7);
		}
		
		if(file.id==FileID.TERRAIN)
		{
			int x = data.getInt();
			int y = data.getInt();
			
			int players = data.getInt();
			
			int skip = players*2;
			System.out.println(x+"x"+y+": "+players);
			
			int total = 0;
			int big = 0;
			int with = 0;
			int bigwith = 0;
			
			//Format: height, isVisible[playerId], length of extraDat
			
			/*for(int r = 0;r<480;r++)
			{
				float f = data.getFloat();
				short s = data.getShort();
				//System.out.println("["+r+":"+(data.position()-6)+"] "+f+" "+s);
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
			}*/
			
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
				String sender = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
				if(len==0)
				{
					break;
				}
				
				float r = data.getFloat();
				float g = data.getFloat();
				float b = data.getFloat();
				asciiZ = new byte[data.getInt()];
				
				
				data.get(asciiZ);
				String message = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
				int unk = data.getInt();
				System.out.println(" - unk=[dec: "+unk+" hex: "+Integer.toHexString(unk)+"] rgb=["+r+", "+g+", "+b+"] <"+sender+"> "+message);
			}
			
			System.out.println("end: "+data.getShort());
		}
		
		if(/*file.id==FileID.OBJECTS*/false)
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
				
				System.out.println(u0p+" "+zero0+" "+zero1+" "+zero2+" pos="+data.position());
				
				float r = data.getFloat();
				float g = data.getFloat();
				float b = data.getFloat();
				
				System.out.println("Color r="+r+" g="+g+" b="+b);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String name = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
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
					int techId = data.getInt();
					boolean u2 = data.get()==1;
					boolean u3 = data.get()==1;
					boolean disabled = data.get()==1;
					
					System.out.println("techId="+techId+" "+(u2?"+":"-")+(u3?"+":"-")+(disabled?"D":"-"));
				}
				
				data.position(data.position()+72);
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String u4 = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
				System.out.println(u4);
				
				data.position(data.position()+25);
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String civilization = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
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
				
				System.out.println("PosBefore123: "+file.data.position());
				
				data.position(data.position()+102);
				int len10 = data.getInt();
				System.out.println("len10="+len10);
				data.position(data.position()+(len10*4));
				
				data.position(data.position()+17);
				
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
				String name2 = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
				System.out.println("["+playerIdx+"] Name2: "+name2);
				
				System.out.println("PosBefore160: "+file.data.position());
				
				data.position(data.position()+160);
				
				System.out.println("PosBeforeStr: "+file.data.position());
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String name3 = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
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
				System.out.println(data.getInt());
				
				int pendingProductions = data.getInt();
				System.out.println("pendingProductions: "+pendingProductions);
				for(int t=0;t<pendingProductions;t++)
				{
					System.out.println("["+t+"] productionId="+data.getInt());
				}
				
				System.out.println(data.getInt()+" "+data.getInt());
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
					"CurrentProduction", //For creating a citizen, this is 1451 instead of 1074???
					"[42]",
					"[43]",
					"[44]",
					"[45]",
					"[46]",
					"[47]",
					"[48]",
					"[49]",
					"RemainingTime",
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
				
				for(int fl = 47;fl<49;fl++)
				{
					float val = data.getFloat();
					System.out.print(names[fl]+"="+val+" ");
					
					if((fl&7)==0&&fl!=0)
					{
						System.out.println(" pos="+data.position());
					}
				}
				
				for(int fl = 49;fl<51;fl++)
				{
					int val = data.getInt();
					System.out.print(names[fl]+"="+val+" ");
				}
				
				for(int fl = 51;fl<63;fl++)
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
				
				System.out.println("\n"+names[70]+"="+data.getInt()+" pos="+data.position());
				byte u9 = data.get();
				if(u9==1)
				{
					System.out.println("unkFloat="+data.getFloat());
				}
				
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
					int taskType = data.get()&0xFF;
					String[] types = new String[]{
						"None",
						"Waypoint",
						"2",
						"3",
						"4",
						"5",
						"6",
						"7",
						"8",
						"9",
						"10",
						"11",
						"Flag Position",
						"13",
						"Unknown"
					};
					
					System.out.println("	type="+types[Math.min(taskType,14)]+" ("+taskType+")");
					System.out.println("	[1:I]="+data.getInt());
					boolean b = data.get()==1;
					System.out.println("	[2:B]="+b);
					
					System.out.println("	id="+data.getInt());
					
					if(taskType==1)
					{
						System.out.println("  Waypoint");
						
						System.out.println("	[3:F]="+data.getFloat());//Direction?
						System.out.println("	[4:F]="+data.getFloat());//Direction?
						System.out.println("	[5:B]="+data.get());
						System.out.println("	x="+data.getFloat());
						System.out.println("	y="+data.getFloat());
					
					
						System.out.println("	[6:I]="+data.getInt());
						System.out.println("	[7:I]="+data.getInt());
						System.out.println("	[8:I]="+data.getInt());
					}
					else if(taskType==12)
					{
						System.out.println("  Rally Point");
						data.position(data.position()+90);
						System.out.println("	x="+data.getFloat());
						System.out.println("	y="+data.getFloat());
						//TODO: 1 too many bytes gotten
					}
					else if(taskType==2)
					{
						boolean c = data.get()==1;
						System.out.println("	c="+c);
						if(c)
						{
							System.out.println("	[2_1:I]="+Integer.toHexString(data.getInt()));
							System.out.println("	[2_2:S]="+Integer.toHexString(data.getShort()&0xFFFF));
							System.out.println("	[2_3:B]="+Integer.toHexString(data.get()&0xFF));
						}
						else
						{
							System.out.println("	[2_1:S]="+data.get());
						}
						
						System.out.println("	[?0:I]="+data.getInt());
						System.out.println("	[?1:I]="+data.getInt());
						System.out.println("	[?2:B]="+data.get());
						System.out.println("	[?3:B]="+data.get());
						
						System.out.println("	?x="+data.getFloat());
						System.out.println("	?y="+data.getFloat());//Direction?
						
						System.out.println("	[4:F]="+data.getFloat());//Direction?
						System.out.println("	[5:B]="+data.get());
						System.out.println("	x="+data.getFloat());
						System.out.println("	y="+data.getFloat());
						
						
						System.out.println("	[6:I]="+data.getInt());
						System.out.println("	[7:I]="+data.getInt());
						System.out.println("	[8:I]="+data.getInt());
					}
					else if(taskType==6)
					{
						boolean c = data.get()==1;
						System.out.println("	c="+c);
						if(c)
						{
							System.out.println("	[2_1:I]="+Integer.toHexString(data.getInt()));
							System.out.println("	[2_2:S]="+Integer.toHexString(data.getShort()&0xFFFF));
							System.out.println("	[2_3:B]="+Integer.toHexString(data.get()&0xFF));
						}
						else
						{
							System.out.println("	[2_1:S]="+data.get());
						}
						
						System.out.println("	[?0:I]="+data.getInt());
						System.out.println("	[?1:I]="+data.getInt());
						System.out.println("	[?2:B]="+data.get());
						System.out.println("	[?3:B]="+data.get());
						
						System.out.println("	?x="+data.getFloat());
						System.out.println("	?y="+data.getFloat());//Direction?
						
						System.out.println("	[4:F]="+data.getFloat());//Direction?
						System.out.println("	[5:B]="+data.get());
						System.out.println("	x="+data.getFloat());
						System.out.println("	y="+data.getFloat());
						
						
						System.out.println("	[6:I]="+data.getInt());
						System.out.println("	[7:I]="+data.getInt());
						System.out.println("	[8:I]="+data.getInt());
					}
					else
					{
						if(!b)
						{
							boolean c = data.get()==1;
							System.out.println("	c="+c);
							if(c)
							{
								System.out.println("	[2_1:I]="+Integer.toHexString(data.getInt()));
								System.out.println("	[2_2:S]="+Integer.toHexString(data.getShort()&0xFFFF));
								System.out.println("	[2_3:B]="+Integer.toHexString(data.get()&0xFF));
							}
							else
							{
								System.out.println("	[2_1:S]="+data.get());
							}
							
							System.out.println("	[?0:I]="+data.getInt());
							System.out.println("	[?1:I]="+data.getInt());
							System.out.println("	[?2:B]="+data.get());
							System.out.println("	[?3:B]="+data.get());
							
							System.out.println("	?x="+data.getFloat());
							System.out.println("	?y="+data.getFloat());//Direction?
						}
						else
						{
							System.out.println("	[3:F]="+data.getFloat());//Direction?
						}
						
						System.out.println("	[4:F]="+data.getFloat());//Direction?
						System.out.println("	[5:B]="+data.get());
						System.out.println("	x="+data.getFloat());
						System.out.println("	y="+data.getFloat());
						
						
						System.out.println("	[6:I]="+data.getInt());
						System.out.println("	[7:I]="+data.getInt());
						System.out.println("	[8:I]="+data.getInt());
					}
				}
				
				System.out.println("WaypointEnd: "+data.position());
				
				//0->25
				
				if(u==1)
				{
					System.out.println("{ 0:I}="+data.getInt(data.position())+" "+Integer.toHexString(data.getInt()));
					System.out.println("{ 1:I}="+data.getInt());
					data.get();
				}
				else
				{
					//data.position(data.position()+8);
					data.position(data.position()+4);
					if(data.getInt()!=1)
					{
						data.get();
					}
					data.position(data.position()+13);
				}
				
				System.out.println("{ 3:F}="+data.getFloat());
				System.out.println("{ 4:I]="+data.getInt());
				System.out.println("{ 5:I}="+data.getInt());
				System.out.println("{ 6:F}="+data.getFloat());
				
				int len2 = data.getInt();
				System.out.println("len2="+len2);
				
				data.position(data.position()+len2*4);
				
				System.out.println("{ 7:F}="+data.getFloat());
				System.out.println("{ 8:F}="+data.getFloat());
				System.out.println("{ 9:B}="+data.get());
				System.out.println("{10:F}="+data.getFloat());
				System.out.println("{11:F}="+data.getFloat());
				
				file.data.position(file.data.position()+5);
				int len3 = data.getInt();
				System.out.println("len3="+len3);
				data.position(data.position()+len3*8);
				file.data.position(file.data.position()+81);
				
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
