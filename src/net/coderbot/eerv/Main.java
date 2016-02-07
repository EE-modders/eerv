package net.coderbot.eerv;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharsetDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

import net.coderbot.config.json.JSONConfig;
import net.coderbot.config.json.JSONStringer;
import net.coderbot.eerv.db.DBAmbientSounds;
import net.coderbot.eerv.db.DBAmbientSounds.AmbientSoundEntry;
import net.coderbot.eerv.db.DBAnimals;
import net.coderbot.eerv.db.DBAnimals.AnimalEntry;
import net.coderbot.eerv.db.DBAreaEffect;
import net.coderbot.eerv.db.DBAreaEffect.AreaEffectEntry;
import net.coderbot.eerv.db.DBButtons;
import net.coderbot.eerv.db.DBButtons.ButtonEntry;
import net.coderbot.eerv.db.DBCivilization;
import net.coderbot.eerv.db.DBCivilization.CivilizationEntry;
import net.coderbot.eerv.db.DBCliffTerrain;
import net.coderbot.eerv.db.DBCliffTerrain.CliffEntry;
import net.coderbot.eerv.db.DBColorTable;
import net.coderbot.eerv.db.DBColorTable.ColorEntry;
import net.coderbot.eerv.db.DBEvents;
import net.coderbot.eerv.db.DBEvents.EventEntry;
import net.coderbot.eerv.db.DBFamily;
import net.coderbot.eerv.db.DBFamily.FamilyEntry;
import net.coderbot.eerv.db.DBMusic;
import net.coderbot.eerv.db.DBMusic.MusicEntry;
import net.coderbot.eerv.db.DBPremadeCivs;
import net.coderbot.eerv.db.DBPremadeCivs.PremadeCivEntry;
import net.coderbot.eerv.db.DBSounds;
import net.coderbot.eerv.db.DBSounds.SoundEntry;
import net.coderbot.eerv.db.DBStartingResources;
import net.coderbot.eerv.db.DBStartingResources.StartingResourceEntry;
import net.coderbot.eerv.db.DBTerrain;
import net.coderbot.eerv.db.DBTerrain.TerrainEntry;
import net.coderbot.eerv.db.DBTerrainType;
import net.coderbot.eerv.db.DBTerrainType.TerrainTypeEntry;
import net.coderbot.eerv.db.DBUIControlEvents;
import net.coderbot.eerv.db.DBUIControlEvents.ControlEventEntry;
import net.coderbot.eerv.db.DBUIControlEvents.ControlEventType;
import net.coderbot.eerv.db.DBUIControls;
import net.coderbot.eerv.db.DBUIControls.ControlEntry;
import net.coderbot.eerv.db.DBUIFonts;
import net.coderbot.eerv.db.DBUIFonts.FontEntry;
import net.coderbot.eerv.db.DBUnitSet;
import net.coderbot.eerv.db.DBUnitSet.UnitSetEntry;
import net.coderbot.eerv.db.DBUpgrade;
import net.coderbot.eerv.db.DBUpgrade.UpgradeEntry;
import net.coderbot.eerv.db.DBWeaponToHit;
import net.coderbot.eerv.db.DBWeaponToHit.WeaponEntry;
import net.coderbot.eerv.db.DBWorld;
import net.coderbot.eerv.db.DBWorld.WorldEntry;
import net.coderbot.log.Log;
import net.coderbot.math.Vector3;
import net.coderbot.util.Charsets;
import net.coderbot.util.DecoderException;
import net.coderbot.util.Timer;

public class Main 
{
	public static void main(String[] args) throws DecoderException, IOException
	{
		Log.init();
		Timer.init();
		
		//Path path = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/Campaigns/EELearningCampaign.ssa");
		//Path path = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/Campaigns/EETheBritish.ssa");
		Path ssapath = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/data.ssa");
		
		SSADecoder dec = new SSADecoder(ssapath);
		SSA ssa = dec.decode();
		dec.close();
		PKDecoder pk = new PKDecoder((ByteBuffer)null);
		
		//SCN.test(null,"/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/learning_thebasics");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase0");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase0_bottom.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase0_bottom");
		/*SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase4_trigger.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase4_trigger");
		SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_condition_AllComputerStone_0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_condition_AllComputerStone_0");
		SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_condition_AllHumanStone_0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_condition_AllHumanStone_0");
		SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_effect_action_none_agressive.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_effect_action_none_agressive");
		SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_object.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_object");
		SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase6_rect_topleft.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase6_rect_topleft");
		SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/...- nano 01.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/nano 01/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/...- nano 02.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/nano 02/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/ww1 - ww2 01.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/ww1ww2 01/");*/
		
		//System.exit(0);
		
		//RMVEnv loader = new RMVEnv(Paths.get("extract/data/random map scripts/"));
		//loader.load("highlands.rmv");
		
		//TAIEnv tailoader = new TAIEnv(Paths.get("extract/data/unit ai scripts/"));
		//TAI t = tailoader.load("land animal no attack.tai");
		
		/*DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get("extract/data/textures"));
		for(Path path: ds)
		{
			if(path.toString().contains("tga"))
			{
				continue;//TGA file
			}
			
			FileChannel fc = FileChannel.open(path, StandardOpenOption.READ);
			ByteBuffer data = fc.map(MapMode.READ_ONLY, 0, fc.size()).order(ByteOrder.LITTLE_ENDIAN);
			boolean tga = data.get()==0;
			int mipmaps = data.get()&0xFF;
			int version = data.getInt();
			int x = data.getInt();
			int y = data.getInt();
			byte subtype = data.get();
			
			Log.log("SST","version "+version+", "+((tga)?"TGA":"DDS")+"/"+subtype+", "+mipmaps+" mipmap"+((mipmaps==1)?"":"s")+" \t"+x+" \t"+y+" \t"+path.getFileName());
			//int embedded = (x*y*data.get(31)/8)+18;
			//Log.log("SST","Embedded TGA Size: "+embedded+" bytes, "+data.remaining()+" remaining");
			
			if(x>10000||y>10000||x<1||y<1)
			{
				Log.log("Errored texture, skipping");
				continue;
			}
			
			/*byte[] emb = new byte[embedded];
			data.get(emb);
			
			Path xt = Paths.get("extract/data/textures_x/"+path.getFileName().toString().replace(".sst", "")+".tga");
			if(Files.exists(xt))
			{
				Files.delete(xt);
			}
			Files.write(xt, emb);
		}*/
		
		
		
		/*for(int i = 0;i<t.elems.length;i++)
		{
			Log.log("TAI",t.elems[i].toString());
		}*/
		
		/*FileChannel fc = FileChannel.open(Paths.get("extract/data/models/fx_frontsplash.cem"));
		ByteBuffer cem = fc.map(MapMode.READ_ONLY, 0, fc.size());
		cem.order(ByteOrder.LITTLE_ENDIAN);
				
		//HeaderChunk{
		int magic = cem.getInt();
		if(magic!=1179472723)
		{
			//bad
		}
		
		boolean current = cem.getInt()==2;
		if(!current)
		{
			Log.log("EERV","Cannot load LEGACY model");
			//continue;
		}
		//}
			
		//this+0x120 == TagPoints List
				
		//148 14C 150 144 140 13C 154
		
		//CountsChunk{
		int c0 = 		 cem.getInt();//this+0x148
		int c1 = 		 cem.getInt();//this+0x14C
		int nTagPoints = cem.getInt();//this+0x150
		int nMaterials = cem.getInt();//this+0x144
		int nFrames =    cem.getInt();//this+0x140
		int nTextures =  cem.getInt();//this+0x13C
		int nPolygons =  cem.getInt();//this+0x154
				
		/*int nPolygons =  cem.getInt();//this+0x154
		int nTextures =  cem.getInt();//this+0x13C
		int nFrames =    cem.getInt();//this+0x140
		int nMaterials = cem.getInt();//this+0x144
		int nTagPoints = cem.getInt();//this+0x150
		int c1 = 		 cem.getInt();//this+0x14C
		int c0 = 		 cem.getInt();//this+0x148*/
				
		/*Log.log("EERV","c0: "+c0+", c1: "+c1+", tagPoints: "+nTagPoints+", materials: "+nMaterials+", frames: "+nFrames+", textures: "+nTextures+", polygons: "+nPolygons);
		//}
				
		//MiscChunk
		{
			byte[] asciiZ = new byte[cem.getInt()];
			cem.get(asciiZ);
			String misc = (asciiZ.length>0)?new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII):"";
		
			Vector3 point = new Vector3();
			point.x = cem.getFloat();
			point.y = cem.getFloat();
			point.z = cem.getFloat();
			Log.log("EERV",point+" "+misc);
		}
		
		Log.log("EERV",cem.limit()+" bytes (offs:"+cem.position()+")");
		
		//TODO Polygons INDICE TABLE (9)
				
		//TODO Materials
				
		
		//TagPoints
		{
			String[] tagPoints = new String[nTagPoints];
			for(int i = 0;i<tagPoints.length;i++)
			{
				byte[] asciiZ = new byte[cem.getInt()];
		
				cem.get(asciiZ);
				tagPoints[i] = (asciiZ.length>0)?new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII):"";
				Log.log("EERV",tagPoints[i]);
			}
		}
				
		//TODO Frame
		/*
		Path p = Paths.get("extract/cemtail");
		try{Files.delete(p);}catch(Exception e){}
		Files.createFile(p);
		fc = FileChannel.open(p, StandardOpenOption.WRITE);
		fc.write(cem);
		fc.close();
		}
		}*/
		
		String name = "unitset";
		
		FileChannel dbf = FileChannel.open(Paths.get("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/data/db/", "db"+name+".dat"));
		ByteBuffer db = dbf.map(MapMode.READ_ONLY, 0, dbf.size());
		db.order(ByteOrder.LITTLE_ENDIAN);
		
		UnitSetEntry[] entries = DBUnitSet.load(db);
		for(int i = 0;i<entries.length;i++)
		{
			System.out.println("["+i+"]:    \t"+entries[i]);
		}
		
		/*FileChannel dbf = FileChannel.open(Paths.get("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/data/db/dbterraintype.dat"));
		ByteBuffer db = dbf.map(MapMode.READ_ONLY, 0, dbf.size());
		db.order(ByteOrder.LITTLE_ENDIAN);
		
		TerrainTypeEntry[] entries = DBTerrainType.load(db);
		for(int i = 0;i<entries.length;i++)
		{
			//Log.log("EERV","["+i+"]:  \t"+entries[i]);
		}*/
		
		/*int entries = db.getInt();
		int size = db.remaining();
		
		int esize = size/entries;
		int isize = esize/4;
		
		Log.log("EERV","Estimated entry size: "+(size/entries)+" bytes, "+isize+" ints");
		
		int[] wo = new int[isize];
		int last = 0;
		boolean chg = false;
		
		for(int w = 0;w<isize;w++)
		{
			for(int i = 0;i<entries;i++)
			{
				db.position((esize*i)+(w*4));
				wo[i] = db.getInt();
			}
			
			chg = false;
			last = 0;
			
			for(int i = 0;i<entries;i++)
			{
				int word = wo[i];
				if(i==0)
				{
					Log.log("EERV","["+w+"/"+i+"]I "+word+" 0x"+Integer.toHexString(word).toUpperCase());
					last = word;
				}
				else if(last!=word)
				{
					Log.log("EERV","["+w+"/"+i+"]  "+word+" 0x"+Integer.toHexString(word).toUpperCase());
					last = word;
					chg = true;
				}
				else
				{
					
				}
			}
			
			//Log.log("EERV","["+w+"/ ] changes: "+chg);
			System.out.println();
		}*/
		
	}
	
	/*for(String file: ent)
	{
		if(!file.contains("scn")&&!file.contains("txt"))
		{
			continue;
		}
		
		ByteBuffer f = ssa.map(file);
		
		Path p = Paths.get("extract/"+file);
		if(!Files.exists(p.getParent()))
		{
			Files.createDirectories(p.getParent());
		}
		
		Log.log("EERV","Saving interestingfile "+file+" to "+p.toAbsolutePath());
		
		Files.createFile(p);
		FileChannel fc = FileChannel.open(p, StandardOpenOption.WRITE);
		fc.write(f);
		fc.close();
	}*/
}
