package net.coderbot.eerv.scn;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
			throw new DecoderException("scn","Expected [0, E7] but got ["+data.getInt()+", "+data.getInt()+"]");
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
		
		int wtf1 = data.getInt();
		System.out.println("WTF1: "+Integer.toHexString(wtf1).toUpperCase()+" \t\t"+wtf1);
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			data.position(data.position()-8);
			throw new DecoderException("scn","Expected [0, E7] but got ["+data.getInt()+", "+data.getInt()+"]");
		}
		
		int wtf2 = data.getInt();
		System.out.println("WTF2: "+Integer.toHexString(wtf2).toUpperCase()+" \t\t"+wtf2);
		int wtf3 = data.getInt();
		System.out.println("WTF3: "+Integer.toHexString(wtf3).toUpperCase()+" \t\t"+wtf3);
		int wtf4 = data.getInt();
		System.out.println("WTF4: "+Integer.toHexString(wtf4).toUpperCase()+" \t\t"+wtf4);
		int wtf5 = data.getInt();
		System.out.println("WTF5: "+Integer.toHexString(wtf5).toUpperCase()+" \t\t"+wtf5);
		int wtf6 = data.getInt();
		System.out.println("WTF6: "+Integer.toHexString(wtf6).toUpperCase()+" \t\t"+(wtf6&0xFFFFFFFFL));
		int wtf7 = data.getShort()&0xFFFF;
		System.out.println("WTF7: "+Integer.toHexString(wtf7).toUpperCase()+" \t\t"+wtf7);
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			data.position(data.position()-8);
			throw new DecoderException("scn","Expected [0, E7] but got ["+data.getInt()+", "+data.getInt()+"]");
		}
		
		int wtf8 = data.getInt();
		System.out.println("WTF8: "+Integer.toHexString(wtf8).toUpperCase()+" \t\t"+wtf8);
		int wtf9 = data.getInt();
		System.out.println("WTF9: "+Integer.toHexString(wtf9).toUpperCase()+" \t\t"+wtf9);
		int wtfA = data.getInt();
		System.out.println("WTFA: "+Integer.toHexString(wtfA).toUpperCase()+" \t\t"+wtfA);
		
		if(data.getInt()!=0||data.getInt()!=231)
		{
			data.position(data.position()-8);
			throw new DecoderException("scn","Expected [0, E7] but got ["+data.getInt()+", "+data.getInt()+"]");
		}
		
		int wtfB = data.getInt();
		System.out.println("WTFB: "+Integer.toHexString(wtfB).toUpperCase()+" \t\t"+wtfB);
		int wtfC = data.getInt();
		System.out.println("WTFC: "+Integer.toHexString(wtfC).toUpperCase()+" \t\t"+wtfC);
		
		pk.setInput(data);
		int endVal = 1;
		for(int i = 0;endVal!=0;i++)
		{
			data.mark();
			endVal = data.getInt();
			if(endVal==0)
			{
				break;
			}
			data.reset();
			
			ByteBuffer bb = pk.decode();
			//Files.write(Paths.get(out+"/file"+i), bb.array());
			
			if(!data.hasRemaining())
			{
				System.out.println("File"+i+" has no attributes! size:"+bb.capacity());
				break;
			}
			
			int sync = data.getInt();
			if(sync!=0xE7000000)
			{
				System.out.println("Sync code isnt 0xE7000000? "+Integer.toHexString(sync).toUpperCase());
			}
			
			FileID id = FileID.get(data.getInt());
			int iat = data.getInt();
			byte b0 = data.get();
			byte b1 = data.get();
			byte b2 = data.get();
			
			boolean xa = false;
			if(iat==0x0C000000)
			{
				xa=true;
				byte[] attr = new byte[28];
				data.get(attr);
				//Files.write(Paths.get(out+"/attr"+i), attr);
			}
			
			bb.position(0);
			bb.order(ByteOrder.LITTLE_ENDIAN);
			
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
			
			if(id==FileID.TERRAIN)
			{
				int x = bb.getInt();
				int y = bb.getInt();
				
				int uProp = bb.getInt();
				
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
					continue;
				}
				
				int total = 0;
				int big = 0;
				int with = 0;
				int bigwith = 0;
				
				for(int r = 0;r<480;r++)
				{
					float f = bb.getFloat();
					//System.out.println("["+r+":"+(bb.position()-4)+"] "+f);
					bb.position(bb.position()+skip);
					int sect = bb.getInt();
					
					if(sect!=0)
					{
						with++;
					}
					
					total+=sect;
					int ba = SCNTerrain.handleEntries(bb, sect);
					big+=ba;
					if(ba>0)
					{
						bigwith++;
					}
				}
				
				System.out.println("Entries: "+total+" total, "+big+" big among "+bigwith+" points, "+(total-big)+" small among "+with+" points");
			}
			
			if(id==FileID.DIPLOMACY)
			{
				//TODO: gameAttributes
				int nPairs = bb.getInt();
				System.out.println("Teams ("+nPairs+"):");
				for(int p = 0;p<nPairs;p++)
				{
					int count = bb.getInt();
					System.out.print(" "+count+": [");
					for(int pi = 0;pi<count;pi++)
					{
						int c0 = bb.getInt();
						System.out.print(c0+(pi+1<count?", ":"]\n"));
					}
				}
				
				int ui0 = bb.getInt();
				float uf0 = bb.getFloat();
				byte ub0 = bb.get();
				float uf1 = bb.getFloat();
				int maxUnits2 = bb.getInt();
				float uf2 = bb.getFloat();
				
				boolean dvictoryAllowed, dlockTeams, unitImprovements, dcheatCodes;
				//0?0?0?01
				int bf0 = bb.getInt();
				dvictoryAllowed = (bf0&1)==1;
				unitImprovements = (bf0&256)==256;
				dlockTeams = (bf0&65536)==65536;
				dcheatCodes = (bf0&0x01000000)==0x01000000;
				
				
				int ui1 = bb.getInt();
				short us0 = bb.getShort();
				
				System.out.println("wondersForVictory:  "+ui0);
				System.out.println("uf0:  "+uf0);
				System.out.println("ub0:  "+ub0);
				System.out.println("uf1:  "+uf1);
				System.out.println("maxUnits:  "+maxUnits2);
				System.out.println("tickrate:  "+uf2);
				System.out.println("victoryAllowed: "+dvictoryAllowed+" unitImprovements: "+unitImprovements+" lockTeams: "+dlockTeams+" cheatCodes: "+dcheatCodes);
				System.out.println("ui1:  "+ui1);
				System.out.println("us0:  "+us0);
				
				int count = bb.getInt();
				System.out.println("Messages: "+count);
				
				int len = -1;
				for(int m = 0;m<count;m++)
				{
					int zero0 = bb.getInt();//Always 0
					len = bb.getInt();
					asciiZ = new byte[len];
					bb.get(asciiZ);
					String sender = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
					if(len==0)
					{
						break;
					}
					
					float r = bb.getFloat();
					float g = bb.getFloat();
					float b = bb.getFloat();
					asciiZ = new byte[bb.getInt()];
					
					
					bb.get(asciiZ);
					String message = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
					int unk = bb.getInt();
					System.out.println(" - unk=[dec: "+unk+" hex: "+Integer.toHexString(unk)+"] rgb=["+r+", "+g+", "+b+"] <"+sender+"> "+message);
				}
				
				System.out.println("end: "+bb.getShort());
			}
			
			if(/*id==FileID.FILE4_MEDVAR*/false)
			{
				bb.position(56);
				
				int entries = bb.getInt();
				int u0p = bb.getInt();
				
				for(int e = 0;e<entries;e++)
				{
					int sequence = bb.getInt();
					System.out.println(sequence+" "+bb.getInt()+" "+bb.get());
					System.out.println(bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt());
					System.out.println(bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt()+" "+bb.getInt());
					System.out.println(bb.getInt());
				}
				
				int zero0 = bb.getInt();
				int zero1 = bb.getInt();
				short zero2 = bb.getShort();
				
				float u0 = bb.getFloat();
				float u1 = bb.getFloat();
				float u2 = bb.getFloat();
				
				System.out.println(u0+" "+u1+" "+u2);
				
				asciiZ = new byte[bb.getInt()];
				bb.get(asciiZ);
				String name = new String(asciiZ, 0, asciiZ.length!=0?asciiZ.length-1:0, StandardCharsets.US_ASCII);
				System.out.println("Player name: "+name);
				int objects = bb.getInt();
				System.out.println("Objects: "+objects);
				int total = 0;
				
				for(int a = 0;a<objects;)
				{
					int fi = bb.getInt();
					if(fi>0)
					{
						a++;
						System.out.println("Object start: "+(bb.position()-4));
					}
					else
					{
						System.out.println("Dead Object: "+(bb.position()-4));
						continue;
					}
					
					for(int f = 0;f<fi;f++)
					{
						total++;
						int type = bb.getInt();
						boolean u4 = bb.get()==1;
						boolean isExtended = bb.get()==1;
						
						int x = bb.getInt();
						int y = bb.getInt();
						
						bb.position(bb.position()+11);
						
						float u6 = bb.getFloat();
						
						//Read: 29
						
						if(!isExtended)
						{
							int resourceLevel = bb.getInt();
							int z = bb.getInt();
							
							System.out.println(total+" !Ext fi: "+fi+" type: <"+type+"> u4: "+u4+" at ["+x+" "+y+"] <u6: "+u6+" resources: "+resourceLevel+">");
						}
						else
						{
							System.out.println(total+"  Ext fi: "+fi+" type: <"+type+"> u4: "+u4+" at ["+x+" "+y+"] <u6: "+u6+">");
							bb.position(bb.position()+532);//532 OR 537
						}
					}
					
				}
				
				System.out.println("End: "+bb.position());
			}
			
			if(id==FileID.FILE3_TINY)
			{
				int ui0 = bb.getInt();
				int ui1 = bb.getInt();
				float uf0 = bb.getFloat();
				float uf1 = bb.getFloat();
				float uf2 = bb.getFloat();
				
				int ui2 = bb.getInt();
				int ui3 = bb.getInt();
				
				float uf3 = bb.getFloat();
				float uf4 = bb.getFloat();
				float uf5 = bb.getFloat();
				float uf6 = bb.getFloat();
				float uf7 = bb.getFloat();
				float uf8 = bb.getFloat();
				
				int ui4 = bb.getInt();
				
				System.out.println(ui0+" "+ui1);
				System.out.println(uf0+" "+uf1+" "+uf2);
				System.out.println(ui2+" "+ui3);
				System.out.println(uf3+" "+uf4+" "+uf5);
				System.out.println(uf6+" "+uf7+" "+uf8);
				System.out.println(ui4);
			}
			
			System.out.println("File"+i+": "+id+" \t<"+bb.array().length+"> \t"+Integer.toHexString(iat)+" \t"+Integer.toHexString(b0&0xFF)+" \t"+Integer.toHexString(b1&0xFF)+" \t"+Integer.toHexString(b2&0xFF)+" \t"+((xa)?"xattribs":""));
		}
		
		return scn;
	}

}
