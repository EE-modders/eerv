package net.coderbot.eerv;

public class RMV
{
	Instruction[] instructions;
	
	static class Instruction
	{
		String ins;
		String[] args;
		
		public String toString()
		{
			StringBuilder a = new StringBuilder("[");
			for(int i = 0;i<args.length;i++)
			{
				a.append(args[i]);
				a.append(',');
			}
			if(args.length==0)
			{
				a.append(']');
			}
			else
			{
				a.setCharAt(a.length()-1, ']');
			}
			
			return ins+((args.length>0)?" : "+a:"");
		}
	}
}
