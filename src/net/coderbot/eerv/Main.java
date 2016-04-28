package net.coderbot.eerv;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.coderbot.eerv.scn.SCN;
import net.coderbot.util.DecoderException;

public class Main 
{
	public static void main(String[] args) throws DecoderException, IOException
	{
		//Path path = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/Campaigns/EELearningCampaign.ssa");
		//Path path = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/Campaigns/EETheBritish.ssa");
		//Path ssapath = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/data.ssa");
		
		/*SSADecoder dec = new SSADecoder(ssapath);
		SSA ssa = dec.decode();
		dec.close();
		PKDecoder pk = new PKDecoder((ByteBuffer)null);*/
		
		//SCN.test(null,"/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/learning_thebasics");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase0");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase0_bottom.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase0_bottom");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase4_trigger.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase4_trigger");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_condition_AllComputerStone_0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_condition_AllComputerStone_0");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_condition_AllHumanStone_0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_condition_AllHumanStone_0");
		SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_effect_action_none_agressive.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_effect_action_none_agressive");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_object.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_object");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase6_rect_topleft.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase6_rect_topleft");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/...- nano 01.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/nano 01/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/...- nano 02.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/nano 02/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/ww1 - ww2 01.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/ww1ww2 01/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/CRASH0.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/crash0/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Dufus Ryan Mom Tough Game.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/tough/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/ElevTR.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/elevtr/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/FlagFilled-Save.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/flagfilled-save/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/AmbientsBL.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/ambientsbl/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/GameSettings.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gamesettings/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/GameSettingsAllAllies.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gamesettingsaa/");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/GS_None.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gs-none/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/GameSettingsTech_HumanAllDisabled.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gamesettingstech/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/1_WorldObject.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/1wobj/")
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/2_WorldObject.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/2wobj/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectAloeTL.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjaloeTL/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectAloeBL.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjaloeBL/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectAloeTR.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjaloeTR/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectAloeBR.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjaloeBR/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectAloeDeleted.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjaloeDEL/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectEagle1.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjeagle/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectEagle2.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjeagle2/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectFish.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjfish/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectChicken1.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjchicken/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WorldObjectIronOre1.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/wobjiron/");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Attrs.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/attrs/");
		
		
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
		
		/*DirectoryStream<Path> ds = Files.newDirectoryStream(Paths.get("extract/data/models"));
		for(Path path: ds)
		{
			try
			{
				CEMDecoder cem = new CEMDecoder(path);
				cem.decode();
				cem.close();
			}
			catch(Throwable e)
			{
				System.out.println("FAIL "+path+" "+e.getMessage());
			}
		}*/
		/*String name = "bld_capital_10";
		
		Path p = Paths.get("extract/data/obj/"+name+".lod0.obj");
		if(Files.exists(p))
		{
			Files.delete(p);
		}
		FileChannel obj = FileChannel.open(p, StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		PrintStream ps = new PrintStream(Channels.newOutputStream(obj));
		ps.println("# OBJ file exported from CEM format");
		
		Path cemp = Paths.get("extract/data/models/"+name+".cem");
		CEMDecoder cemd = new CEMDecoder(cemp);
		CEM cem = cemd.decode();
		cemd.close();
		
		int frame = 0;
		ps.println("# CEM Metadata");
		ps.println("# Center point: "+cem.center);
		ps.println("# Misc: "+cem.misc);
		ps.println("# Total Polygons: "+cem.totalPolygons);
		ps.println("# Sets: "+cem.indices0.length);
		ps.println("# Tag Point Names: ("+cem.tagPointNames.length+")");
		for(int i = 0;i<cem.tagPointNames.length;i++)
		{
			ps.println("# Tag Point Name ["+i+"]: "+cem.tagPointNames[i]);
		}
		ps.println("# Frames: "+cem.frames.length);
		ps.println("# Embedded Models: "+cem.subModels);
		ps.println("# Materials: "+cem.materials.length);
		for(int i = 0;i<cem.materials.length;i++)
		{
			ps.println("# Material "+i+": "+cem.materials[i].name);
			ps.println("# Material "+i+": "+cem.materials[i].vertexStart+"->"+(cem.materials[i].vertexStart+cem.materials[i].vertexLength));
			for(int j = 0;j<cem.indices0.length;j++)
			{
				ps.println("# Material "+i+":"+j+": "+cem.materials[i].polygonStart[j]+"->"+(cem.materials[i].polygonStart[j]+cem.materials[i].polygonLength[j]));
			}
		}
		ps.println("# Using frame: "+frame+"\n");
		ps.println("# Section 'Vertex Positions'");
		CEMFrame current = cem.frames[frame];
		
		int a = 0;
		for(int i = 0;i<current.vertices;i++)
		{
			ps.format("v %.9f %.9f %.9f\n", current.pos[a++], current.pos[a++], current.pos[a++]);
		}
		
		ps.println();
		ps.println("# Section 'Vertex Normals'");
		
		a = 0;
		for(int i = 0;i<current.vertices;i++)
		{
			//ps.format("vn %.9f %.9f %.9f\n", current.normal[a++], current.normal[a++], current.normal[a++]);
		}
		
		ps.println();
		ps.println("# Section 'Vertex UVs'");
		
		a = 0;
		for(int i = 0;i<current.vertices;i++)
		{
			//ps.format("vt %.9f %.9f\n", current.uv[a++], current.uv[a++]);
		}
		
		ps.println();
		ps.println("# Section 'Polygon Indices'");
		
		a = 0;
		for(int i = 0;i<1;i++)
		{
			int[] set = cem.indices0[i];
			int[] set1 = cem.indices1[i];
			int[] set2 = cem.indices2[i];
			
			for(int s = 0;s<set.length;s++)
			{
				//ps.println("f "+(set[s]+1)+"/"+(set[s]+1)+"/"+(set[s]+1)+" "+(set1[s]+1)+"/"+(set1[s]+1)+"/"+(set1[s]+1)+" "+(set2[s]+1)+"/"+(set2[s]+1)+"/"+(set2[s]+1));
				ps.println("f "+(set[s]+1)+" "+(set1[s]+1)+" "+(set2[s]+1));
			}
		}*/
		
		/*String name = "world";
		
		FileChannel dbf = FileChannel.open(Paths.get("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/data/db/prog", "db"+name+".dat"));
		ByteBuffer db = dbf.map(MapMode.READ_ONLY, 0, dbf.size());
		db.order(ByteOrder.LITTLE_ENDIAN);
		
		WorldEntry[] entries = DBWorld.load(db);
		for(int i = 0;i<entries.length;i++)
		{
			if(entries[i].type()!=DBWorld.Type.INT&&entries[i].type()!=DBWorld.Type.INT_RANGE)
			{
				continue;
			}
			System.out.println("["+i+"]:    \t"+entries[i]);
		}*/
		
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
