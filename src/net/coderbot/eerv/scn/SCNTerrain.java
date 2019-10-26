package net.coderbot.eerv.scn;

import java.nio.ByteBuffer;

public class SCNTerrain
{
	public static int handleEntries(ByteBuffer bb, int entries)
	{
		int big = 0;
		
		for(int i = 0;i<entries;i++)
		{
			byte b = bb.get();
			
			System.out.println(b+": "+bb.getInt()+" - "+bb.getInt()+" ("+i+"->"+entries+")");
			if(b==1)
			{
				System.out.println(" ["+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+"]");
				System.out.println(" ["+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+"]");
				System.out.println(" ["+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+"]");
				System.out.println(" ["+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+"]");
				int a = bb.getInt();
				int c = bb.getInt();
				System.out.println(" ? 0x"+Integer.toHexString(a)+" "+a+" 0x"+Integer.toHexString(c)+" "+c);
				System.out.println(" ? "+bb.getFloat()+" "+bb.getInt()+" "+bb.getFloat());
				System.out.println(" x="+bb.getInt()+" y="+bb.getInt()+" <byte:"+bb.get()+">");
				
				big++;
			}
		}
		
		return big;
	}
}
