package net.coderbot.eerv;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import net.coderbot.eerv.compression.Exploder;
import net.coderbot.eerv.compression.ExploderConstants;
import net.coderbot.eerv.compression.Imploder;
import net.coderbot.eerv.compression.TCmpStruct;
import net.coderbot.eerv.scn.SCN;
import net.coderbot.util.DecoderException;

public class Main 
{
	public static void main(String[] args) throws DecoderException, IOException
	{
		/*Path path = Paths.get("/home/coderbot/wingames/ee1/Data/Scenarios/een1ve1s_the basics.scn");
		PKDecoder pk = new PKDecoder(path);
		ByteBuffer bb = pk.decode();*/
		/*Path out = Paths.get("/home/coderbot/wingames/ee1/Data/Scenarios/een1ve1s_the basics_decompressed.scn");
		Files.write(out, bb.array());*/
		
		/*for(byte b: ExploderConstants.DistCode)
		{
			System.out.println(b&0xFF);
		}*/
		
		
		
		/*int x = 0;
		
		for(int i = 0; i < 0x10; i++)
		{
			if((1 << ExploderConstants.ExLenBits[i])!=0)
			{
				for(int n = 0; n < (1 << ExploderConstants.ExLenBits[i]); n++)
				{
					int bits  = ExploderConstants.ExLenBits[i] + ExploderConstants.LenBits[i] + 1;
					int code = (n << (ExploderConstants.LenBits[i] + 1)) | ((ExploderConstants.LenCode[i] & 0xFFFF00FF) * 2) | 1;
					
					System.out.println("["+(x++)+"] "+bits+"\t -> "+Integer.toHexString(code));
				}
			}
		}*/
		
		//Imploder.implode(new TCmpStruct(), true, 4096);
		
		/*ByteBuffer boom = ByteBuffer.allocateDirect(8);
		boom.put((byte)0);
		boom.put((byte)6);
		boom.put((byte)0x01);
		boom.put((byte)0xFF);
		boom.flip();
		Exploder exploder = new Exploder(boom);
		exploder.nextBlock();*/
		
		//Path path = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/Campaigns/EELearningCampaign.ssa");
		//Path path = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/Campaigns/EETheBritish.ssa");
		//Path ssapath = Paths.get("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/data.ssa");
		
		/*SSADecoder dec = new SSADecoder(ssapath);
		SSA ssa = dec.decode();
		dec.close();
		PKDecoder pk = new PKDecoder((ByteBuffer)null);*/
		
		/*TCmpStruct st = new TCmpStruct();
		Imploder implode = new Imploder();
		implode.implode(st, true, 4096);*/
		
		//SCN.test(null,"/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/learning_thebasics");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase0");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase0_bottom.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase0_bottom");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase4_trigger.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase4_trigger");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_condition_AllComputerStone_0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_condition_AllComputerStone_0");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_condition_AllHumanStone_0.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_condition_AllHumanStone_0");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_effect_action_none_agressive.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_effect_action_none_agressive");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase5_object.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase5_object");
		//SCN.test("/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/scn/testcase6_rect_topleft.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/testcase6_rect_topleft");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/...- nano 01.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/nano 01/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/...- nano 02.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/nano 02/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/ww1 - ww2 01.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/ww1ww2 01/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/ww1 - ww2 02.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/ww1ww2 01/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/CRASH0.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/crash0/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Dufus Ryan Mom Tough Game.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/tough/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/ElevTR.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/elevtr/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/FlagFilled-Save.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/flagfilled-save/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/AmbientsBL.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/ambientsbl/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/GameSettings.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gamesettings/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/GameSettingsAllAllies.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gamesettingsaa/");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/GS_None.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gs-none/");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/1_WorldObject.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/1wobj/");
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
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_ClassName_NAME6_applied.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/attrs/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_ClassName_NAME_applied.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/attrs/");
		
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Trigger_NotTrue_NothingAAOO.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Condition_P1Stone_4321.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Condition_Trigger.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Trigger_ActiveFor1234_ANANA.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Trigger_Looping.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Trigger_InCinematic.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Trigger_2.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Condition_PAttr.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Condition_PTech.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Condition_TAttr.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Condition_PDipl.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Condition_GAttr.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Condition_CMContains.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Conditions.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Conditions_Trigger.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Conditions_Diplomacy.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Conditions_ChatMessageContains.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Conditions_GameAttribute.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Conditions_Tech.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_Object.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_Player.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_Game.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_SetAttr.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_ScriptCam.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_AI.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_NoneQed.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_None2Qed.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_None2Qed_After_1:13.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_NoneQed2.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_2Qued.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_1Qued.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_0Qued.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Effects_2Qued2.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Dufus Alexander.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Dufus - Pericles.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		
		//SCN.test("extract/-- Eternal Darkness 3 -- Breakthrough -- v1.43.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Objects_Specification.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Objects.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Objects_SelectOnMap.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Objects_1Destroyed.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Objects_ManyDestroyed.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Areas.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Clock_speed1_00:00:00.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Clock_speed1_00:00:01.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Clock_speed1_00:00:02.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Clock_speed1_00:00:10.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Saved Games/Clock_speed1_00:00:30.ees","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Player_CPU_Shared.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Player_CPU_Shared_NoneSet.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		/*SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Player_CitizenPercentage.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Map_-24.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Map_40.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Map_40_4Corners.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Map_25x15.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");*/
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Wolf_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Walrus_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Vermin_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Tiger_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Ostrich_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Horse_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Hippo_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Goat_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Giraffe_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Flies_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		/*SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Elephant_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Deer_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Chicken_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Canine_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Artifact_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Eagle_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Tarpit_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Gaslight_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Flag2_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		*///SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Flag1_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		/*SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Statue_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Streetlamp_BismarkPalm.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		*/
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Eagle_NoWaypoint.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/WObj_Eagle_Waypoint1.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Civ_HumanCarthage_ComputerNONE.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Civ_HumanByzantineRome_ComputerNONE.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Civ_HumanBabylon_ComputerNONE.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Civ_HumanAssyrianEmpire_ComputerNONE.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Civ_HumanAncientGreece_ComputerNONE.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Civ_HumanNONE_ComputerNONE.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/trigger/");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/GameSettingsTech_HumanAllDisabled.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gamesettingstech/");
		//SCN.test("/home/coderbot/.wine/drive_c/Sierra/Empire Earth/Data/Scenarios/My_New_Map.scn","/home/coderbot/eclipse/workspace/EmpireEarthReverse/extract/testcase/anl/gamesettingstech/");
		
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_2AddPopulate_PopulateTotal7.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_1AddPopulate_PopulateTotal6.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_FlagPos_At.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_ProgressDone.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_ProgressHigh.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_ProgressMed.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_ProgressLow.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_6Citizens6Dogs.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_12Dogs.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_12Citizens.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_FlagPos_Left.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_FlagPos_Right.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_FlagPos_Up.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_FlagPos_Down.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_TownCenter_NoTasks.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_Capitol_EpochStone.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_Capitol_EpochStone_Restarted.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Hum_Capitol.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Trigger_UnkVals.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Trigger_30S.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Trigger_60S.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed_Rand_987887.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed_Set_1234.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed_Set_4321.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Cam_TL.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Cam_TR.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Cam_BL.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Cam_BR.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Cam_TLv2.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Cam_TLv2_Zoom.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Cam_Mid_Zoomed.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed_Rand_472474.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed_Set_472474.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/RotatedUnits_ReadyToAttack.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/RotatedUnits_Attacked.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/TowersMorale.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/CapitolVeryDamaged.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/CapitolVeryDamagedAboutToRepair.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/CapitolVeryDamagedRepairing.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Resources_Set_MinStone_MaxDigital.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/SetAttr_PopCap.scn");
		
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_TinyTournamentIslands.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_TinySmallIslands.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_TinyPlains.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_TinyMediterrainian.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_TinyLargeIslands.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_TinyHighlands.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_GiganticContinental.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_HugeContinental.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_LargeContinental.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_MediumContinental.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_SmallContinental.scn");
		//SCN.test("/home/coderbot/wingames/ee1/Data/Scenarios/Seed0_TinyContinental.scn");
		
		//Clock_speed1_00:00:XX
		
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
