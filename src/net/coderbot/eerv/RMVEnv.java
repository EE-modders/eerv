package net.coderbot.eerv;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;

import net.coderbot.log.Log;
import net.coderbot.util.Charsets;

public class RMVEnv 
{
	HashMap<String,RMV> loaded;
	Path folder;
	boolean killuppercase = true;
	
	public RMVEnv(Path path) 
	{
		folder = path;
		loaded = new HashMap<String,RMV>();
	}

	public RMV load(String rmv)
	{
		if(killuppercase)
		{
			rmv = rmv.toLowerCase();
		}
		
		RMV load = loaded.get(rmv);
		if(load!=null)
		{
			return load;
		}
		try 
		{
			load = load0(rmv);
			loaded.put(rmv, load);
			
			return load;
		} 
		catch (IOException e) 
		{
			Log.log("RMV","failed to load "+rmv+": "+e);
			return null;
		}
	}
	
	private RMV load0(String rmv) throws IOException
	{
		Path path = folder.resolve(rmv);
		Log.log("RMV","loading "+rmv);
		FileChannel fc = FileChannel.open(path, StandardOpenOption.READ);
		
		ByteBuffer block = ByteBuffer.allocateDirect(4096);
		CharBuffer chars = CharBuffer.allocate(8192);
		CharsetDecoder dec = Charsets.ASCII.newDecoder();
		
		CharBuffer keyword = CharBuffer.allocate(256);
		CharBuffer argument = CharBuffer.allocate(256);
		
		String key = "";
		ArrayList<String> arguments = new ArrayList<String>(20);
		int state = 0;//0NextLine 1ReadKeyword 2ReadinKeyword 3SkipWhite 4ReadArgs 5ReadingArgs 6Comment
		
		RMV load = new RMV();
		ArrayList<RMV.Instruction> insList = new ArrayList<RMV.Instruction>(64);
		//Not allowed in string literal: , ( )
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
				//System.out.println(state+" "+chars.get(chars.position()));
				
				if(state==0)
				{
					if(!key.isEmpty())
					{
						RMV.Instruction ins = new RMV.Instruction();
						ins.ins = key;
						ins.args = new String[arguments.size()];
						arguments.toArray(ins.args);
						insList.add(ins);
						
						if(ins.ins.equals("#include"))
						{
							load(ins.args[0].substring(1, ins.args[0].length()-1)+".rmv");
						}
						//System.out.println(ins);
					}
					
					arguments.clear();
					key = "";
					state = 1;
					
					if(chars.remaining()>=2&&chars.get(chars.position())=='/'&&chars.get(chars.position()+1)=='/')
					{
						state = 6;
					}
				}
				else if(state==1)
				{
					keyword.position(0);
					keyword.limit(256);
					state = 2;
				}
				else if(state==2)
				{
					char c = chars.get();
					if(c==10)
					{
						state = 0;
					}
					else if(Character.isWhitespace(c))
					{
						if(keyword.position()!=0)
						{
							keyword.limit(keyword.position());
							keyword.position(0);
							key = keyword.toString();
							
							state = 3;
						}
					}
					else if(c==13)
					{
						continue;
					}
					else
					{
						keyword.put(c);
					}
				}
				else if(state==3)
				{
					char c = chars.get();
					if(c==13)
					{
						continue;
					}
					if(c==10)
					{
						state = 0;
					}
					if(!Character.isWhitespace(c))
					{
						chars.position(chars.position()-1);
						state = 4;
					}
				}
				else if(state==4)
				{
					argument.limit(256);
					argument.position(0);
					char c = chars.get();
					
					if(chars.remaining()>=1&&c=='/'&&(chars.get(chars.position())=='/'))
					{
						state = 6;
						continue;
					}
					
					if(c=='('||c==13)
					{
						continue;
					}
					else
					{
						chars.position(chars.position()-1);
					}
					state = 5;
				}
				else if(state==5)
				{
					char c = chars.get();
					if(c==13||c==')')
					{
						continue;
					}
					else if(c=='\\')
					{
						argument.put('/');//die, backslash file names
					}
					else if(c==10)
					{
						state = 0;
					}
					else if(c==',')
					{
						state = 3;
					}
					else
					{
						argument.put(c);
					}
					
					if(state!=5)
					{
						argument.limit(argument.position());
						argument.position(0);
						
						String arg = argument.toString();
						arguments.add(arg.trim());
					}
				}
				else if(state==6)
				{
					char c = chars.get();
					if(c==10)
					{
						state = 0;
					}
				}
			}
		}
		
		load.instructions = new RMV.Instruction[insList.size()];
		insList.toArray(load.instructions);
		
		return load;
	}
}
