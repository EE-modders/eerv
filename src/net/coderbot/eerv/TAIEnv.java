package net.coderbot.eerv;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

public class TAIEnv 
{
	HashMap<String,TAI> loaded;
	Path folder;
	boolean killuppercase = true;
	
	public TAIEnv(Path path) 
	{
		folder = path;
		loaded = new HashMap<String,TAI>();
	}

	public TAI load(String tai)
	{
		if(killuppercase)
		{
			tai = tai.toLowerCase();
		}
		
		TAI load = loaded.get(tai);
		if(load!=null)
		{
			return load;
		}
		try 
		{
			load = load0(tai);
			loaded.put(tai, load);
			
			return load;
		} 
		catch (IOException e) 
		{
			System.out.println("TAI: failed to load "+tai+": "+e);
			return null;
		}
	}
	
	public TAI load0(String tai) throws IOException
	{
		Path path = folder.resolve(tai);
		System.out.println("TAI: loading "+tai);
		
		FileChannel fc = FileChannel.open(path, StandardOpenOption.READ);
		
		ByteBuffer block = ByteBuffer.allocateDirect(4096);
		CharBuffer chars = CharBuffer.allocate(8192);
		CharsetDecoder dec = StandardCharsets.US_ASCII.newDecoder();
		
		CharBuffer preprocess = CharBuffer.allocate(256);
		CharBuffer statename = CharBuffer.allocate(256);
		CharBuffer conname = CharBuffer.allocate(256);
		CharBuffer conlist = CharBuffer.allocate(256);
		
		TAI.Condition cond = null;
		TAI.Handler curr = null;
		ArrayList<TAI.Condition> conds = new ArrayList<TAI.Condition>(16);
		ArrayList<TAI.Element> elems = new ArrayList<TAI.Element>(16);
		
		//0NextLine 1ReadPreprocessor 2ReadStateName 3ReadStateHandler 4ReadConName 5ConWhitespace 6ReadConList 7StateDone 8Comment 9StateComment
		int state = 0;
		boolean commentS = false;
		
		int read = 4096;
		while(read>0)
		{
			block.position(0);
			block.limit(4096);
			read = fc.read(block);
			block.limit(block.position());
			block.position(0);
			
			chars.limit(8192);
			chars.position(0);
			dec.decode(block, chars, read!=4096);
			chars.limit(chars.position());
			chars.position(0);
			
			while(chars.hasRemaining())
			{
				//Log.log("TAI",state+" "+chars.get(chars.position()));
				
				if(state==0)
				{
					chars.mark();
					char c = chars.get();
					if(c==13)
					{
						commentS = false;
						continue;
					}
					else if(c==10)
					{
						commentS = false;
						state = 0;
					}
					else if(c=='#')
					{
						commentS = false;
						state = 1;
					}
					else if(c=='/')
					{
						if(commentS)
						{
							state = 8;
						}
						else
						{
							commentS = true;
						}
					}
					else
					{
						commentS = false;
						chars.reset();
						state = 2;
					}
				}
				else if(state==1)
				{
					char c = chars.get();
					if(c==10)
					{
						state = 0;
						
						preprocess.limit(preprocess.position());
						preprocess.position(0);
						
						TAI.Include inc = new TAI.Include();
						String ppe = preprocess.toString();
						if(ppe.startsWith("include"))
						{
							inc.file = ppe.substring(9,ppe.length()-2).toLowerCase();
							elems.add(inc);
						}
						else
						{
							System.out.println("TAI: Unknown preprocessor statement: "+ppe);
						}
						
						preprocess.limit(256);
					}
					else if(c==13)
					{
						continue;
					}
					else
					{
						preprocess.put(c);
					}
				}
				else if(state==2)
				{
					char c = chars.get();
					if(c=='{')
					{
						state = 3;
						
						statename.limit(statename.position());
						statename.position(0);
						
						curr = new TAI.Handler();
						curr.name = statename.toString();
						
						statename.limit(256);
					}
					else if(c==13||c==10)
					{
						continue;
					}
					else
					{
						statename.put(c);
					}
				}
				else if(state==3)
				{
					chars.mark();
					char c = chars.get();
					if(c=='}')
					{
						state = 7;
					}
					else if(!Character.isWhitespace(c))
					{
						chars.reset();
						state = 4;
					}
				}
				else if(state==4)
				{
					char c = chars.get();
					if(Character.isWhitespace(c))
					{
						commentS = false;
						state = 5;
						
						conname.limit(conname.position());
						conname.position(0);
						
						cond = new TAI.Condition();
						String cn = conname.toString();
						if(cn.startsWith("allof"))
						{
							cond.and = true;
							cond.list = cn.substring(6,cn.length()-1).split(",");
						}
						else if(cn.startsWith("anyof"))
						{
							cond.list = cn.substring(6,cn.length()-1).split(",");
						}
						else
						{
							cond.list = new String[]{cn};
						}
						
						conname.limit(256);
					}
					else if(c=='/')
					{
						if(commentS)
						{
							commentS = false;
							state = 9;
						}
						else
						{
							commentS = true;
						}
					}
					else
					{
						commentS = false;
						conname.put(c);
					}
				}
				else if(state==5)
				{
					chars.mark();
					char c = chars.get();
					if(c==10)
					{
						conds.add(cond);
						
						state = 3;
					}
					else if(!Character.isWhitespace(c))
					{
						chars.reset();
						state = 6;
					}
				}
				else if(state==6)
				{
					chars.mark();
					char c = chars.get();
					if(Character.isWhitespace(c))
					{
						state = 5;
						chars.reset();
						
						conlist.limit(conlist.position());
						conlist.position(0);
						
						String conl = conlist.toString();
						if(conl.startsWith("true("))
						{
							cond.t = conl.substring(5, conl.length()-1);
						}
						else if(conl.startsWith("false("))
						{
							cond.f = conl.substring(6, conl.length()-1);
						}
						else if(conl.startsWith("remove()"))
						{
							cond.remove = true;
						}
						
						conlist.limit(256);
					}
					else
					{
						conlist.put(c);
					}
				}
				else if(state==7)
				{
					curr.conditions = new TAI.Condition[conds.size()];
					conds.toArray(curr.conditions);
					conds.clear();
					
					elems.add(curr);
					state = 0;
				}
				else if(state==8)
				{
					char c = chars.get();
					if(c==10)
					{
						state = 0;
					}
				}
				else if(state==9)
				{
					char c = chars.get();
					if(c==10)
					{
						state = 3;
					}
				}
			}
		}
		
		TAI t = new TAI(elems.size());
		elems.toArray(t.elems);
		
		return t;
	}
}
