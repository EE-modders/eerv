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
	
	int u6;
	int u7;
	int u8;
	int u9;
	int uA;
	int uB;
	
	int[] uC;
	
	int uD;
	int uE;
	int uF;
	int u10;
	int u11;
	short u12;
	float u13;
	int u14;
	int u15;
	int u16;
	int u17;
	int u18;
	
	String hints;
	String history;
	String movie;
	String map;
	String instructions;
	String soundover;
	
	SCNFile[] files;
	
	static class File
	{
		int id;
		int atr1;
		byte atr2;
		byte atr3;
		byte atr4;
		
		byte[] xattribs;
	}
	
	static enum FileID
	{
		TERRAIN(0x03), TRIGGERS(0x0A), FILE2_MED(0x0C), FILE3_TINY(0x05), OBJECTS(0x07), DIPLOMACY(0x08), 
		FILE6_TINY(0x0B),
		
		UNKNOWN(0);
		
		int nid;
		FileID(int nid)
		{
			this.nid = nid;
		}
		
		public static FileID get(int nid)
		{
			FileID[] values = values();
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
