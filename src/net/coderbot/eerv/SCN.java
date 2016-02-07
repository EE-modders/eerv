package net.coderbot.eerv;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import net.coderbot.log.Log;
import net.coderbot.util.Charsets;
import net.coderbot.util.DecoderException;

public class SCN 
{
	String name;
	String desc;
	String playerList;
	long date;
	Player[] players;
	byte[] wtf0;
	String[] attrs;
	int[] wtf;
	
	File[] files;
	
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
		TERRAIN(0x03000000), TRIGGERS(0x0A000000), FILE2_MED(0x0C000000), FILE3_TINY(0x05000000), FILE4_MEDVAR(0x07000000), CHATLOG(0x08000000), 
		FILE6_TINY(0x0B000000),
		
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
			
			Log.log("UNKNOWN FILE TYPE: "+Integer.toHexString(nid));
			
			return UNKNOWN;
		}
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
		
		Path rt = Paths.get(out);
		if(Files.exists(rt))
		{
			for(Path infile: Files.newDirectoryStream(rt))
			{
				Files.delete(infile);
			}
			Files.delete(rt);
		}
		Files.createDirectories(rt);
		
		scn.order(ByteOrder.LITTLE_ENDIAN);
		int size = scn.getInt();
		Log.log("EERV","File says it's size is "+size+" actual is "+scn.capacity());
		byte[] asciiZ = new byte[scn.getInt()];
		scn.get(asciiZ);
		String filename = new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII);
		
		asciiZ = new byte[scn.getInt()];
		scn.get(asciiZ);
		String desc = new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII);
		Log.log("EERV",filename+": "+desc);
		
		scn.mark();
		if(scn.getInt()!=0||scn.getInt()!=231)
		{
			scn.reset();
			Log.log("EERV","WTF: "+scn.getInt()+" "+scn.getInt());//TODO: wtf
		}
		else
		{
			Log.log("EERV","Sync");
		}
		
		
		asciiZ = new byte[scn.getInt()];
		scn.get(asciiZ);
		String list = new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII);
		int players = list.split(" ").length;
		Log.log("EERV","("+players+") List: "+list);
		
		asciiZ = new byte[scn.getInt()];
		scn.get(asciiZ);
		String date = new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII);
		Log.log("EERV","Date: "+date);
		
		Log.log("EERV","SpByte "+Integer.toHexString(scn.get()&0xFF));
		
		for(int i = 0;i<16;i++)
		{
			scn.get();
			scn.get();
			scn.get();
			int i0 = scn.getInt();
			boolean human = (i0&1)==1;
			boolean inactive = (i0&4)==4;
			boolean defeated = (i0&2)==2;
			if((i0&0xFFFFFFF8)!=0)
			{
				Log.log("EERV","UNKNOWN FLAG: "+Integer.toHexString(i0&0xFFFFFFF8));
			}
			
			asciiZ = new byte[scn.getInt()];
			//Log.log("EERV","len: "+asciiZ.length);
			scn.get(asciiZ);
			String name = (asciiZ.length>0)?new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII):"";
			
			int i1 = scn.getInt();
			int color = scn.getInt();
			int id = scn.getInt();
			int team = scn.getInt();
			int citizens = scn.getInt();
			
			Log.log("EERV","Player"+i+": \thuman: "+human+" \tinactive: "+inactive+"  \tdefeated: "+defeated+"  \t"+Integer.toHexString(i1)+" \tcolor:"+color+" \tid: "+id+" \tteam: "+team+" \tcitizens: "+citizens+" \t"+name);
		}
		
		byte zero = scn.get();
		if(zero!=0)
		{
			System.err.println("NOTZERO[0]: "+zero);
		}
		
		
		Log.log("EERV","gameType: "+scn.get());
		
		byte one = scn.get();
		if(one!=1)
		{
			System.err.println("NOTONE[0]: "+zero);
		}
		
		int i0 = scn.getInt();
		if(i0!=1)
		{
			System.err.println("NOTONE[1]: "+i0);
		}
		
		int mapSize = scn.getInt();
		Log.log("EERV","mapSize: "+mapSize);
		
		int mapType = scn.getInt();
		Log.log("EERV","mapType: "+mapType);
		
		int resources = scn.getInt();
		Log.log("EERV","resources: "+resources);
		
		int startEpoch = scn.getInt();
		Log.log("EERV","startEpoch: "+startEpoch);
		
		int endEpoch = scn.getInt();
		Log.log("EERV","endEpoch: "+endEpoch);
		
		int u0 = scn.getInt();
		Log.log("EERV","u0: "+u0);
		
		int maxUnits = scn.getInt();
		Log.log("EERV","maxUnits: "+maxUnits);
		
		int variant = scn.getInt();
		Log.log("EERV","variant: "+variant);
		
		int aiDifficulty = scn.getInt();
		Log.log("EERV","aiDifficulty: "+aiDifficulty);
		
		int i1 = scn.getInt();
		Log.log("EERV","i1: "+i1);
		
		int i2 = scn.getInt();
		Log.log("EERV","i2: "+i2);
		
		int i3 = scn.getInt();
		Log.log("EERV","i3: "+i3);
		
		int i4 = scn.getInt();
		Log.log("EERV","i4: "+i4);
		
		int i5 = scn.getInt();
		Log.log("EERV","i5: "+i5);
		
		int i6 = scn.getInt();
		Log.log("EERV","i6: "+i6);
		
		int i7 = scn.getInt();
		Log.log("EERV","i7: "+i7);
		
		int i8 = scn.getInt();
		Log.log("EERV","i8: "+i8);
		
		byte[] wtf0 = new byte[52];//TODO: wtf
		scn.get(wtf0);
		Files.write(Paths.get(out+"/wtf0.bin"), wtf0);
		Log.log("EERV","Snipped WTF0");
		
		zero = scn.get();
		if(zero!=0)
		{
			System.err.println("NOTZERO[1]: "+zero);
		}
		
		for(int i = 0;i<6;i++)
		{
			String attr = "";
			asciiZ = new byte[scn.getInt()];
			scn.get(asciiZ);
			attr = new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII);
			Log.log("EERV","Attr: "+attr);
		}
		
		float wtf1f = scn.getFloat(scn.position());
		int wtf1 = scn.getInt();
		Log.log("EERV","WTF1: "+Integer.toHexString(wtf1).toUpperCase()+" \t\t"+wtf1+" "+wtf1f);
		
		scn.getInt();scn.getInt();
		//Log.log("EERV","    ? "+scn.getInt()+" "+scn.getInt());
		
		System.out.println(scn.position());
		
		int wtf2 = scn.getInt();
		Log.log("EERV","WTF2: "+Integer.toHexString(wtf2).toUpperCase()+" \t\t"+wtf2);
		int wtf3 = scn.getInt();
		Log.log("EERV","WTF3: "+Integer.toHexString(wtf3).toUpperCase()+" \t\t"+wtf3);
		int wtf4 = scn.getInt();
		Log.log("EERV","WTF4: "+Integer.toHexString(wtf4).toUpperCase()+" \t\t"+wtf4);
		int wtf5 = scn.getInt();
		Log.log("EERV","WTF5: "+Integer.toHexString(wtf5).toUpperCase()+" \t\t"+wtf5);
		float wtf6f = scn.getFloat(scn.position());
		int wtf6 = scn.getInt();
		Log.log("EERV","WTF6: "+Integer.toHexString(wtf6).toUpperCase()+" \t"+(wtf6&0xFFFFFFFFL)+" "+wtf6f);
		int wtf7 = scn.getShort()&0xFFFF;
		Log.log("EERV","WTF7: "+Integer.toHexString(wtf7).toUpperCase()+" \t\t"+wtf7);
		
		scn.getInt();scn.getInt();
		//Log.log("EERV","    ? "+scn.getInt()+" "+scn.getInt());
		
		int wtf8 = scn.getInt();
		Log.log("EERV","WTF8: "+Integer.toHexString(wtf8).toUpperCase()+" \t\t"+wtf8);
		int wtf9 = scn.getInt();
		Log.log("EERV","WTF9: "+Integer.toHexString(wtf9).toUpperCase()+" \t\t"+wtf9);
		int wtfA = scn.getInt();
		Log.log("EERV","WTFA: "+Integer.toHexString(wtfA).toUpperCase()+" \t\t"+wtfA);
		
		scn.getInt();scn.getInt();
		//Log.log("EERV","    ? "+scn.getInt()+" "+scn.getInt());
		
		int wtfB = scn.getInt();
		Log.log("EERV","WTFB: "+Integer.toHexString(wtfB).toUpperCase()+" \t\t"+wtfB);
		int wtfC = scn.getInt();
		Log.log("EERV","WTFC: "+Integer.toHexString(wtfC).toUpperCase()+" \t\t"+wtfC);
		
		pk.setInput(scn);
		int e = 1;
		for(int i = 0;e!=0;i++)
		{
			scn.mark();
			e = scn.getInt();
			if(e==0)
			{
				break;
			}
			scn.reset();
			
			ByteBuffer bb = pk.decode();
			Files.write(Paths.get(out+"/file"+i), bb.array());
			
			if(!scn.hasRemaining())
			{
				Log.log("EERV","File"+i+" has no attributes! size:"+bb.capacity());
				break;
			}
			
			int sync = scn.getInt();
			if(sync!=0xE7000000)
			{
				Log.log("EERV","Sync code isnt 0xE7000000? "+Integer.toHexString(sync).toUpperCase());
			}
			
			FileID id = FileID.get(scn.getInt());
			int iat = scn.getInt();
			byte b0 = scn.get();
			byte b1 = scn.get();
			byte b2 = scn.get();
			
			boolean xa = false;
			if(iat==0x0C000000)
			{
				xa=true;
				byte[] attr = new byte[28];
				scn.get(attr);
				Files.write(Paths.get(out+"/attr"+i), attr);
			}
			
			/*if(id==FileID.TRIGGERS)
			{
				SCNTrigger trig = new SCNTrigger();
				bb.order(ByteOrder.LITTLE_ENDIAN);
				bb.position(0);
				
				do
				{
					trig.prefix = bb.getInt();
					if(trig.prefix==0)
					{
						trig.zeros++;
					}
				}
				while(trig.prefix==0);
				
				
				asciiZ = new byte[bb.getInt()];
				
				bb.get(asciiZ);
				trig.name = new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII);
				
				asciiZ = new byte[bb.getInt()];
				bb.get(asciiZ);
				trig.desc = new String(asciiZ, 0, asciiZ.length-1, Charsets.ASCII);
				
				Log.log("EERV","triggers[0] "+trig);
			}*/
			
			Log.log("EERV","File"+i+": "+id+" \t"+Integer.toHexString(iat)+" \t"+Integer.toHexString(b0&0xFF)+" \t"+Integer.toHexString(b1&0xFF)+" \t"+Integer.toHexString(b2&0xFF)+" \t"+((xa)?"xattribs":""));
		}
		
		pk.close();
	}
}
