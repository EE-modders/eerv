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
			if(b==0)
			{
				System.out.println("0: "+bb.getInt()+" - "+bb.getInt());
			}
			else
			{
				big++;
				System.out.println("1: "+bb.getInt()+" "+bb.getInt());
				System.out.println(" # "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat());
				System.out.println(" # "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat());
				System.out.println(" # "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat());
				System.out.println(" # "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat()+" "+bb.getFloat());
				int a = bb.getInt();
				int c = bb.getInt();
				System.out.println(" # 0x"+Integer.toHexString(a)+" "+a+" 0x"+Integer.toHexString(c)+" "+c);
				System.out.println(" # "+bb.getFloat()+" "+bb.getInt()+" "+bb.getFloat());
				System.out.println(" # ["+bb.getInt()+" "+bb.getInt()+"] <byte:"+bb.get()+">");
			}
		}
		
		return big;
	}
}
