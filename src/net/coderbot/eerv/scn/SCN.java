package net.coderbot.eerv.scn;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.coderbot.eerv.PKDecoder;
import net.coderbot.eerv.SSA;
import net.coderbot.eerv.SSADecoder;
import net.coderbot.util.DecoderException;

public class SCN 
{
	String name;
	String description;
	String playerList;
	String date;
	Player[] players;
	boolean u0;
	boolean u1;
	boolean u2;
	
	int gameSpeed;
	int u3;
	int gameVariant;
	int mapSize;
	int startEpoch;
	int endEpoch;
	int resources;
	int maxUnits;
	int wondersForVictory;
	int aiDifficulty;
	boolean victoryAllowed, lockTeams, lockSpeed, revealMap;
	boolean cheatCodes, u4, customCivs, u5;
	
	String campaignName;
	String scenarioName;
	String forcedName;
	
	int u6;
	byte u7;
	int[] u8;
	String u9;
	
	int creationUptime;
	
	String hints;
	String history;
	String movie;
	String map;
	String instructions;
	String soundover;
	
	static enum LumpID
	{
		ID1_TINY(1),
		SEED(2),
		CAMERA_POSITION(3),
		TERRAIN(4),
		OBJECTS(5),
		TRIGGERS(6),
		DIPLOMACY(7),
		ID8_TINY(8),
		GFX_EFFECTS(10),
		ID11_TINY(11),
		ID12_TINY(12),
		
		UNKNOWN(0);
		
		int nid;
		LumpID(int nid)
		{
			this.nid = nid;
		}
		
		public static LumpID get(int nid)
		{
			LumpID[] values = values();
			for(int i = 0;i<values.length;i++)
			{
				if(values[i].nid==nid)
				{
					return values[i];
				}
			}
			
			System.out.println("UNKNOWN FILE TYPE: "+Integer.toHexString(nid));
			
			return UNKNOWN;
		}
	}
	
	public void print()
	{
		System.out.println(name+": "+description);
		System.out.println(date);
		System.out.println("Players: "+playerList);
	}
	
	public static void test(String in) throws IOException, DecoderException
	{
		test(in, "");
	}
	
	public static void test(String in, String out) throws IOException, DecoderException
	{
		PKDecoder pk = new PKDecoder((ByteBuffer)null);
		ByteBuffer scn;
		if(in!=null)
		{
			Path saved = Paths.get(in);
			FileChannel gamefc = FileChannel.open(saved);
			scn = gamefc.map(MapMode.READ_ONLY, 0, gamefc.size());
		}
		else
		{
			Path path = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/Campaigns/EELearningCampaign.ssa");
			
			SSADecoder dec = new SSADecoder(path);
			SSA ssa = dec.decode();
			dec.close();
			scn = ssa.map("scenarios/een1ve1s_the basics.scn");
			pk.setInput(scn);
			scn = pk.decode();
		}
		pk.close();
		
		/*Path rt = Paths.get(out);
		if(Files.exists(rt))
		{
			for(Path infile: Files.newDirectoryStream(rt))
			{
				Files.delete(infile);
			}
			Files.delete(rt);
		}
		Files.createDirectories(rt);*/
		
		scn.order(ByteOrder.LITTLE_ENDIAN);
		new SCNDecoder(scn).decode();
	}
}
