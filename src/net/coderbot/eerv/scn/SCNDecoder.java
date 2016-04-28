package net.coderbot.eerv.scn;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Path;

import net.coderbot.eerv.PKDecoder;
import net.coderbot.eerv.scn.SCN.FileID;
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
			data.position(data.position()-8);
			throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.playerList = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.date = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		byte split = data.get();
		if(split!=0)
		{
			throw new DecoderException("scn", "split byte was not 0");
		}
		
		scn.players = new Player[16];
		for(int i = 0;i<16;i++)
		{
			scn.players[i] = new Player(data);
		}
		
		int bval = data.get()&0xFF;
		if(bval>1)
		{
			throw new DecoderException("scn", "boolean value was not 0/1: "+bval);
		}
		scn.u0 = bval==1;
		
		bval = data.get()&0xFF;
		if(bval>1)
		{
			throw new DecoderException("scn", "boolean value was not 0/1: "+bval);
		}
		scn.u1 = bval==1;
		
		bval = data.get()&0xFF;
		if(bval>1)
		{
			throw new DecoderException("scn", "boolean value was not 0/1: "+bval);
		}
		scn.u2 = bval==1;
		
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
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.history = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.movie = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.map = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.instructions = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		scn.soundover = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
		
		scn.uD = data.getInt();
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			data.position(data.position()-8);
			throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		scn.uE = data.getInt();
		scn.uF = data.getInt();
		scn.u10 = data.getInt();
		scn.u11 = data.getInt();
		scn.u12 = data.getInt();
		scn.u13 = data.getShort()&0xFFFF;
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			data.position(data.position()-8);
			throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		scn.u14 = data.getInt();
		scn.u15 = data.getInt();
		scn.u16 = data.getInt();
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			data.position(data.position()-8);
			throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
		}
		
		scn.u17 = data.getInt();
		scn.u18 = data.getInt();
		System.out.println(scn.uD+" "+scn.uE+" "+scn.uF+" "+scn.u10);
		System.out.println(scn.u11+" "+scn.u12+" "+scn.u13+" "+scn.u14);
		System.out.println(scn.u15+" "+scn.u16+" "+scn.u17+" "+scn.u18);
		
		pk.setInput(data);
		int endVal = 0;
		int i = 0;
		
		do
		{
			data.mark();
			endVal = data.getInt();
			if(endVal==0)
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
			
			int sync = data.getInt();
			if(sync!=0xE7000000)
			{
				throw new DecoderException("scn", "Expected 0xE7000000 but got 0x"+Integer.toHexString(sync));
			}
			
			file.id = FileID.get(data.getInt());
			file.u0 = data.getInt();
			file.u1 = data.get();
			file.u2 = data.get();
			file.u3 = data.get();
			
			if(file.u0==0x0C000000)
			{
				file.hasExtendedAttribs = true;
				file.u4 = data.getFloat();
				file.u5 = data.getFloat();
				file.u6 = data.getFloat();
				
				if(data.getInt()!=0||data.getInt()!=231)
				{
					data.position(data.position()-8);
					throw new DecoderException("scn","Expected [0, E7] but got ["+Integer.toHexString(data.getInt())+", "+Integer.toHexString(data.getInt())+"]");
				}
				
				file.u7 = data.getInt();
				file.u8 = data.getInt();
			}
			
			file.data.position(0);
			file.data.order(ByteOrder.LITTLE_ENDIAN);
			
			handleFile(file);
			System.out.println("File"+(i++)+": "+file.id+" \t<"+file.data.limit()+"> \t"+Integer.toHexString(file.u0)+" \t"+Integer.toHexString(file.u1&0xFF)+" \t"+Integer.toHexString(file.u2&0xFF)+" \t"+Integer.toHexString(file.u3&0xFF)+" \t"+((file.hasExtendedAttribs)?"xattribs":""));
		}
		while(endVal!=0);
		
		return scn;
	}

	
	static void handleFile(SCNFile file)
	{
		byte[] asciiZ;
		ByteBuffer data = file.data;
		
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
			trig.name = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			
			asciiZ = new byte[bb.getInt()];
			bb.get(asciiZ);
			trig.desc = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			
			System.out.println("triggers[0] "+trig);
		}*/
		
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
			
			System.out.println("Entries: "+total+" total, "+big+" big among "+bigwith+" points, "+(total-big)+" small among "+with+" points");
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
			
			boolean dvictoryAllowed, dlockTeams, unitImprovements, dcheatCodes;
			//0?0?0?01
			int bf0 = data.getInt();
			dvictoryAllowed = (bf0&1)==1;
			unitImprovements = (bf0&256)==256;
			dlockTeams = (bf0&65536)==65536;
			dcheatCodes = (bf0&0x01000000)==0x01000000;
			
			
			int ui1 = data.getInt();
			short us0 = data.getShort();
			
			System.out.println("wondersForVictory:  "+ui0);
			System.out.println("uf0:  "+uf0);
			System.out.println("ub0:  "+ub0);
			System.out.println("uf1:  "+uf1);
			System.out.println("maxUnits:  "+maxUnits2);
			System.out.println("tickrate:  "+uf2);
			System.out.println("victoryAllowed: "+dvictoryAllowed+" unitImprovements: "+unitImprovements+" lockTeams: "+dlockTeams+" cheatCodes: "+dcheatCodes);
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
		
		if(/*id==FileID.FILE4_MEDVAR*/false)
		{
			file.data.position(56);
			
			int entries = data.getInt();
			int u0p = data.getInt();
			
			for(int e = 0;e<entries;e++)
			{
				int sequence = data.getInt();
				System.out.println(sequence+" "+data.getInt()+" "+data.get());
				System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
				System.out.println(data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt()+" "+data.getInt());
				System.out.println(data.getInt());
			}
			
			int zero0 = data.getInt();
			int zero1 = data.getInt();
			short zero2 = data.getShort();
			
			float u0 = data.getFloat();
			float u1 = data.getFloat();
			float u2 = data.getFloat();
			
			System.out.println(u0+" "+u1+" "+u2);
			
			asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			String name = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.US_ASCII);
			System.out.println("Player name: "+name);
			int objects = data.getInt();
			System.out.println("Objects: "+objects);
			int total = 0;
			
			for(int a = 0;a<objects;)
			{
				int fi = data.getInt();
				if(fi>0)
				{
					a++;
					System.out.println("Object start: "+(file.data.position()-4));
				}
				else
				{
					System.out.println("Dead Object: "+(file.data.position()-4));
					continue;
				}
				
				for(int f = 0;f<fi;f++)
				{
					total++;
					int type = data.getInt();
					boolean u4 = data.get()==1;
					boolean isExtended = data.get()==1;
					
					int x = data.getInt();
					int y = data.getInt();
					
					file.data.position(file.data.position()+11);
					
					float u6 = data.getFloat();
					
					//Read: 29
					
					if(!isExtended)
					{
						int resourceLevel = data.getInt();
						int z = data.getInt();
						
						System.out.println(total+" !Ext fi: "+fi+" type: <"+type+"> u4: "+u4+" at ["+x+" "+y+"] <u6: "+u6+" resources: "+resourceLevel+">");
					}
					else
					{
						System.out.println(total+"  Ext fi: "+fi+" type: <"+type+"> u4: "+u4+" at ["+x+" "+y+"] <u6: "+u6+">");
						file.data.position(file.data.position()+532);//532 OR 537
					}
				}
				
			}
			
			System.out.println("End: "+file.data.position());
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
}
