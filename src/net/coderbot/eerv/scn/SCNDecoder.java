package net.coderbot.eerv.scn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.coderbot.eerv.PKDecoder;
import net.coderbot.eerv.scn.SCN.LumpID;
import net.coderbot.eerv.scn.trigger.Area;
import net.coderbot.eerv.scn.trigger.Condition;
import net.coderbot.eerv.scn.trigger.Effect;
import net.coderbot.eerv.scn.trigger.Selector;
import net.coderbot.eerv.scn.trigger.Trigger;

import java.nio.channels.FileChannel;
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
	
	private String getString()
	{
		byte[] asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		return new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
	}
	
	@Override
	public SCN decode() throws DecoderException
	{
		SCN scn = new SCN();
		
		int size = data.getInt();
		System.out.println("File says it's size is "+size+" actual is "+data.capacity());
		
		scn.name = getString();
		scn.description = getString();
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			//data.position(data.position()-8);
			//throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		scn.playerList = getString();
		scn.date = getString();
		
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
		
		scn.campaignName = getString();
		System.out.println("CampaignName: "+scn.campaignName);
		
		scn.scenarioName = getString();
		System.out.println("ScenarioName: "+scn.scenarioName);
		
		
		scn.u6 = data.getInt();
		scn.u7 = data.get();
		
		System.out.println(scn.u6+" "+scn.u7);
		
		scn.forcedName = getString();
		System.out.println("ForcedName: "+scn.forcedName);
		
		System.out.println("UnknownInt: "+data.getInt());
		int entries = data.getInt();
		for(int i = 0;i<entries;i++)
		{
			int type = data.getInt();
			System.out.println("lump["+i+"] type: \t"+type+" \t"+SCN.LumpID.get(type));
		}
		
		scn.u9 = getString();
		System.out.println("UnknownString: "+scn.u9);
		
		scn.hints = getString();
		System.out.println("Hints: "+scn.hints);
		
		scn.history = getString();
		System.out.println("History: "+scn.history);
		
		scn.movie = getString();
		System.out.println("Movie: "+scn.movie);
		
		scn.map = getString();
		System.out.println("Map: "+scn.map);
		
		scn.instructions = getString();
		System.out.println("Instructions: "+scn.instructions);
		
		scn.soundover = getString();
		System.out.println("Soundover: "+scn.soundover);
		
		scn.creationUptime = data.getInt();
		System.out.println("creationUptime="+(((float)scn.creationUptime)/1000)+"s");
		
		// Start of File Data
		
		pk.setInput(data);
		int pkMagic = 0;
		int i = 0;
		
		do
		{
			data.getInt();data.getInt();//TODO: Version check
			int type = data.getInt();
			int lumpsize = data.getInt();
			
			System.out.println("size: "+lumpsize+" \ttype: "+type+" ("+SCN.LumpID.get(type)+")");
			
			{
				int start = data.position();
				ByteBuffer lump;
				
				data.mark();
				pkMagic = data.getInt();
				data.reset();
				
				if(lumpsize<=12||pkMagic!=825248592)
				{
					data.limit(data.position()+lumpsize);
					lump = data.slice();
					data.position(data.limit());
					data.limit(data.capacity());
				}
				else
				{
					lump = pk.decode();
				}
				
				int end = data.position();
				
				lump.position(0);
				lump.order(ByteOrder.LITTLE_ENDIAN);
				
				try
				{
					//handleFile(lump, LumpID.get(type));
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				String s = "extract/scn/"+scn.name.toLowerCase().split("\\.")[0]+"/file"+i+"_"+LumpID.get(type).toString().toLowerCase();
				Path path = Paths.get(s);
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
					byte[] lumparray;
					if(!lump.hasArray())
					{
						lumparray = new byte[lump.remaining()];
						lump.get(lumparray);
					}
					else
					{
						lumparray = lump.array();
					}
					
					Files.deleteIfExists(path);
					Files.write(path, lumparray, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
					
					ByteBuffer bb = data.duplicate();
					bb.position(start);
					bb.limit(end);
					FileChannel fc = FileChannel.open(Paths.get(s+".pk"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
					fc.truncate(0);
					fc.write(bb);
				}
				catch(IOException e)
				{
					e.printStackTrace();
				}
				
				i++;
			}
		}
		while(data.hasRemaining());
		
		return scn;
	}
	
	static void handleFile(ByteBuffer data, LumpID id)
	{
		byte[] asciiZ;
		
		if(id==LumpID.TRIGGERS)
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
			System.out.println("queuedEffects="+queued);
			for(int i = 0;i<queued;i++)
			{
				int effectId = data.getInt();
				int executeAt = data.getInt();
				byte u0 = data.get();
				
				System.out.println("["+i+"] effectId="+effectId+" executeAt="+executeAt+"ms u0="+u0);
			}
			
			int len = data.getInt();
			System.out.println("loggedEffects="+len);
			for(int i = 0;i<len;i++)
			{
				Effect effect = new Effect();
				effect.read(data);
				System.out.println(effect);
			}
			
			System.out.println("end: "+data.position());
			
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
		
		if(id==LumpID.TERRAIN)
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
				file.data.position(data.position()+skip);
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
		
		if(id==LumpID.DIPLOMACY)
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
			System.out.println("timeOfDay:  "+uf0);
			System.out.println("waterLevel:  "+ub0);
			System.out.println("zoom:  "+uf1);
			System.out.println("maxUnits:  "+maxUnits2);
			System.out.println("gameSpeed:  "+uf2);
			System.out.println("victoryAllowed: "+victoryAllowed+" unitImprovements: "+unitImprovements+" lockTeams: "+lockTeams+" cheatCodes: "+cheatCodes);
			System.out.println("townIdCounter:  "+ui1);
			System.out.println("herdIdCounter:  "+us0);
			
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
		
		if(id==LumpID.OBJECTS)
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
					int entityType = data.getInt();
					asciiZ = new byte[data.getInt()];
					data.get(asciiZ);
					
					String name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1);
					/*System.out.println("entityType: "+entityType+" name: "+name);
					System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
					System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
					System.out.println(data.getInt());*/
					data.position(data.position()+68);
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
					total = readGroup(data, total, groups);
					groups++;
				}
				
				System.out.println("GroupEnd: "+data.position()+" groups: "+groups);
				
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
				
				System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
				System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
				System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
				System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
				System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
				System.out.println(data.getInt());
				
				System.out.println("food: "+data.getInt()+" wood: "+data.getInt()+" stone: "+data.getInt()+" gold: "+data.getInt()+" iron: "+data.getInt()+" ??: "+data.getInt());
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String civilization = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
				System.out.println("["+playerIdx+"] Civilization: "+civilization);
				
				System.out.println("PosBefore: "+data.position());
				
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
				
				System.out.println("PosBefore280: "+data.position());
				
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
				
				System.out.println("PosBefore123: "+data.position());
				
				data.position(data.position()+102);
				int len10 = data.getInt();
				System.out.println("len10="+len10);
				data.position(data.position()+(len10*4));
				
				data.position(data.position()+17);
				//
				int len2 = data.getInt();
				System.out.println("len2="+len2);
				data.position(data.position()+(len2*4));
				
				int u5 = data.getInt();
				int u6 = data.getInt();
				int u7 = data.getShort();
				System.out.println(u5+" "+u6+" "+u7);
				
				System.out.println("PosBeforeStr: "+data.position());
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String name2 = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
				System.out.println("["+playerIdx+"] Name2: "+name2);
				
				System.out.println("PosBefore160: "+data.position());
				
				data.position(data.position()+160);
				
				System.out.println("PosBeforeStr: "+data.position());
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				String name3 = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.ISO_8859_1);
				System.out.println("["+playerIdx+"] Name3: "+name3);
				
				System.out.println("PosBefore88: "+data.position());
				
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
				
				System.out.println("BeforeAIData: "+data.position());
				
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
				
				System.out.println("End: "+data.position());
				playerIdx++;
			}
			
			
			
			//
		}
		
		if(id==LumpID.ID12_TINY)
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
	
	public static int readGroup(ByteBuffer data, int total, int i)
	{
		int size = data.getInt();
		if(size<0)
		{
			System.out.flush();
			throw new IllegalStateException("size "+size+" < 0 for group");
		}
		
		System.out.println("["+i+"] Group: "+(data.position()-4)+" size="+size);
		
		for(int f = 0;f<size;f++)
		{
			total++;
			int type = data.getInt();
			if(type>999 || type<0)
			{
				System.out.flush();
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
			int id = data.getInt();
			
			byte u1 = data.get();
			byte u2 = data.get();
			byte u3 = data.get();
			
			int u0 = data.getInt();
			float health = data.getFloat();
			int resourceLevel = data.getInt();
			
			int z = data.getInt();
			System.out.println(total+" "+(isExtended?"Complex":"Simple ")+" type: <"+type+"> id: ("+(id>>>24)+"<"+(id&0x00FFFFFF)+">"+") at ["+x+" "+y+"] <health: "+(health*100)+"% resources: "+resourceLevel+" zero: "+z+" u0: "+u0+" flags: "+(u1==1?"+":"-")+(u2==1?"+":"-")+(u3==1?"+":"-")+">");
			
			if(isExtended)
			{
				System.out.println("Extended Object offs: "+data.position());
				
				int productions = data.getInt();
				// Productions appear to be an entry in dbtechtree.
				System.out.print("pending productions: [");
				for (int e = 0;e<productions;e++)
				{
					System.out.print(data.getInt());
					if(e+1<productions)
					{
						System.out.print(", ");
					}
				}
				System.out.println("]");
				
				int len4 = data.getInt();
				System.out.print("unknown entity list: [");
				for (int e = 0;e<len4;e++)
				{
					int eid = data.getInt();
					
					System.out.print((eid>>>24)+"<"+(eid&0x00FFFFFF)+">");
					if(e+1<len4)
					{
						System.out.print(", ");
					}
				}
				System.out.println("]");
				
				int len5 = data.getInt();//Array of EntityIds
				System.out.print("stored entities: [");
				for (int e = 0;e<len5;e++)
				{
					int eid = data.getInt();
					
					System.out.print((eid>>>24)+"<"+(eid&0x00FFFFFF)+">");
					if(e+1<len5)
					{
						System.out.print(", ");
					}
				}
				System.out.println("]");
				
				int waypoints = data.getInt();
				System.out.println("waypoints: "+waypoints);
				for(int w = 0;w<waypoints;w++)
				{
					float wx = data.getFloat(), wy = data.getFloat(), wz = data.getFloat();
					float dist = data.getFloat();//Total distance traveled
					float prevDist = data.getFloat();//Previous Total distance traveled
					
					System.out.println("["+w+"] x="+wx+" \ty="+wy+" \tz="+wz+" \tdist="+dist+" \tprevDist="+prevDist);
				}
				
				System.out.println("-> "+data.position());
				System.out.print("[0]="+data.getFloat()+" ");
				System.out.print("[1]="+data.getFloat()+" ");
				System.out.println("[2]="+data.getFloat()+" ");
				
				// Matrix
				System.out.println("["+(data.getFloat()+0)+" "+(data.getFloat()+0)+" "+(data.getFloat()+0)+" "+(data.getFloat()+0)+"]");
				System.out.println("["+(data.getFloat()+0)+" "+(data.getFloat()+0)+" "+(data.getFloat()+0)+" "+(data.getFloat()+0)+"]");
				System.out.println("["+(data.getFloat()+0)+" "+(data.getFloat()+0)+" "+(data.getFloat()+0)+" "+(data.getFloat()+0)+"]");
				System.out.println("["+(data.getFloat()+0)+" "+(data.getFloat()+0)+" "+(data.getFloat()+0)+" "+(data.getFloat()+0)+"]");
				
				byte u8 = data.get();
				System.out.println("u8="+u8);
				System.out.print("[19]="+data.getFloat()+" ");
				System.out.print("[20]="+data.getInt()+" ");
				System.out.print("[21]="+data.getFloat()+" ");
				System.out.print("[22]="+data.getFloat()+" ");
				System.out.print("[23]="+data.getFloat()+" ");
				System.out.print("[24]="+data.getFloat()+" ");
				System.out.print("[25]="+data.getFloat()+" ");
				System.out.println("[26]="+data.getFloat()+" ");
				System.out.print("[27]="+data.getFloat()+" ");
				System.out.print("[28]="+data.getFloat()+" ");
				System.out.print("[29]="+data.getFloat()+" ");
				System.out.println("[30]="+data.getFloat()+" ");
				
				int eid31 = data.getInt();
				System.out.print("[31]="+(eid31>>>24)+"<"+(eid31&0x00FFFFFF)+">"+" ");
				int eid32 = data.getInt();
				System.out.print("[32]="+(eid32>>>24)+"<"+(eid32&0x00FFFFFF)+">"+" ");
				int eid33 = data.getInt();
				System.out.print("[33]="+(eid33>>>24)+"<"+(eid33&0x00FFFFFF)+">"+" ");
				int eid34 = data.getInt();
				System.out.print("[34]="+(eid34>>>24)+"<"+(eid34&0x00FFFFFF)+">"+" ");
				System.out.println("[35]="+data.getInt()+" ");
				
				int len6 = data.getInt();
				System.out.println("len6="+len6);
				for(int l = 0;l<len6;l++)
				{
					System.out.println("<"+l+">="+data.getInt());
				}
				
				
				System.out.print("[37]="+data.getFloat()+" ");
				System.out.print("[38]="+data.getFloat()+" ");
				System.out.print("[39]="+data.getFloat()+" ");
				System.out.println("[40]="+data.getFloat()+" ");
				System.out.print("current_production="+data.getInt()+" ");
				System.out.print("[42]="+data.getInt()+" ");
				System.out.print("[43]="+data.getInt()+" ");
				System.out.print("[44]="+data.getInt()+" ");
				System.out.print("[45]="+data.getInt()+" ");
				System.out.print("[46]="+data.getInt()+" ");
				System.out.print("[47]="+data.getFloat()+" ");
				System.out.print("[48]="+data.getFloat()+" ");
				System.out.print("[49]="+data.getInt()+" ");
				System.out.print("production_time_remaining="+data.getInt()+" ");
				System.out.print("[51]="+data.getFloat()+" ");
				System.out.print("[52]="+data.getInt()+" ");
				System.out.print("[53]="+data.getInt()+" ");
				System.out.print("[54]="+data.getInt()+" ");
				System.out.print("[55]="+data.getInt()+" ");
				System.out.print("[56]="+data.getInt()+" ");
				System.out.println("[57]="+data.getInt()+" ");
				System.out.print("[58]="+data.getFloat()+" ");
				System.out.print("[59]="+data.getFloat()+" ");
				System.out.print("[60]="+data.getInt()+" ");
				System.out.print("[61]="+data.getFloat()+" ");
				System.out.print("[62]="+data.getFloat()+" ");
				System.out.print("[63]="+data.getInt()+" ");
				System.out.print("[64]="+data.getInt()+" ");
				System.out.print("[65]="+data.getInt()+" ");
				System.out.print("[66]="+data.getInt()+" ");
				System.out.print("[67]="+data.getInt()+" ");
				
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
						//throw new IllegalStateException("not boolean: "+b);
					}
				}
				
				System.out.println();
				
				System.out.print("[68]="+data.getInt()+" ");
				System.out.print("[69]="+data.getInt()+" ");
				System.out.print("[70]="+data.getFloat()+" ");//Zero?
				int eid71 = data.getInt();
				System.out.print("[71]="+(eid71>>>24)+"<"+(eid71&0x00FFFFFF)+">"+" ");
				System.out.print("[72]="+data.getFloat()+" ");//?
				System.out.print("population="+(data.getFloat()*100)+"% ");//Float
				
				System.out.println("\n"+"[74]="+data.getInt()+" pos="+data.position());
				System.out.println("[75]="+data.getInt());
				byte u9 = data.get();
				/*if(u9==1)
				{
					System.out.println("unkFloat="+data.getFloat());
				}*/
				
				System.out.println("u9="+u9+" "+"[75]="+data.getFloat()+" "+"[76]="+data.getFloat()+" "+"[77]="+data.getFloat());
				
				byte uA = data.get();
				System.out.println("uA="+uA+" "+"[78]="+data.getFloat()+" "+"[79]="+data.getFloat()+" "+"[80]="+data.getFloat());
				
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
						System.out.print('#');
						//throw new IllegalStateException("not boolean: "+b);
					}
				}
				
				System.out.println("\n<- "+data.position());
				
				int u = data.getInt();
				
				System.out.println("pos="+data.position()+" waypointSets="+u);
				
				for(int w = 0;w<u;w++)
				{
					int goals = data.getInt();
					System.out.println("waypoints"+w+"="+goals);
				
					for(int j = 0;j<goals;j++)
					{
						readWaypoint(data);
					}
				
					System.out.println("waypoint"+w+"end: "+data.position());
					System.out.println("{ 0:I}="+data.getInt(data.position())+" "+Integer.toHexString(data.getInt()));
					System.out.println("{ 1:I}="+data.getInt());
					data.get();
				}
				
				/*if(u!=1)
				{
					int waypoints2 = data.getInt();
					System.out.println("waypoints2="+waypoints2+" pos="+data.position());
					for(int j = 0;j<waypoints2;j++)
					{
						readWaypoint(data);
					}
					
					System.out.println("Waypoint2End: "+data.position());
					
					data.position(data.position()+9);
				}*/
				
				System.out.println("{ 3:F}="+data.getFloat());
				System.out.println("{ 4:I]="+data.getInt());
				System.out.println("{ 5:I}="+data.getInt());
				System.out.println("{ 6:I}="+data.getInt());
				
				int len2 = data.getInt();
				if(len2<0)
				{
					System.out.flush();
					throw new IllegalStateException("len2 "+len2+" < 0 for group");
				}
				System.out.println("len2="+len2);
				
				data.position(data.position()+len2*4);
				
				System.out.println("{ 7:F}="+data.getFloat());
				System.out.println("{ 8:I}="+data.getInt());
				System.out.println("{ 9:B}="+data.get());
				System.out.println("{10:F}="+data.getFloat());
				System.out.println("{11:F}="+data.getFloat());
				
				data.position(data.position()+5);
				int len3 = data.getInt();
				if(len3<0)
				{
					System.out.flush();
					throw new IllegalStateException("len3 "+len3+" < 0 for group");
				}
				
				System.out.println("len3="+len3);
				data.position(data.position()+len3*8);
				data.position(data.position()+81);
				
				System.out.println("End: "+data.position());
			}
		}

		return total;
	}
	
	public static void readWaypoint(ByteBuffer data)
	{
		//1->55
		int taskType = data.get()&0xFF;
		String[] types = new String[]{
			"None",
			"Waypoint",
			"2",
			"3",
			"Repair",
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
		else if(taskType==2)
		{
			data.position(data.position()+25);
		}
		else if(taskType==4)
		{
			System.out.println("	[0:B]="+data.get());
			System.out.println("	[1:I]="+data.getInt());
			System.out.println("	[2:I]="+data.getInt());
			int eid = data.getInt();
			System.out.println("	Repairing="+(eid>>>24)+"<"+(eid&0x00FFFFFF)+">");
			System.out.println("	[3:B]="+data.get());
			System.out.println("	[4:B]="+data.get());
			System.out.println("	[5:I]="+data.getInt());
			System.out.println("	[6:B]="+data.get());
		}
		else if(taskType==6)
		{
			//data.position(data.position()+51);
			data.position(data.position()+13);
			int eid = data.getInt();
			System.out.println("	[1:EId]="+(eid>>>24)+"<"+(eid&0x00FFFFFF)+">");
			boolean hasValidEId = data.get()==1;
			System.out.println("	[hasValidEId:B]="+hasValidEId);
			System.out.println("	[3:F]="+data.getFloat());
			System.out.println("	[4:F]="+data.getFloat());
			data.position(data.position()+5);
			System.out.println("	[5:F]="+data.getFloat());
			System.out.println("	[6:F]="+data.getFloat());
			data.position(data.position()+4);
		}
		else if(taskType==10)
		{
			data.position(data.position()+27);
		}
		else if(taskType==12)
		{
			data.position(data.position()+58);
		}
		else
		{
			throw new IllegalStateException("unknown task type");
		}
	}
}
