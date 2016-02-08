package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.coderbot.util.Charsets;

public class DBRandomMap 
{
	public static RandomMapEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		int unk = data.getInt();
		System.out.println(unk);
		RandomMapEntry[] sr = new RandomMapEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new RandomMapEntry(data);
		}
		
		return sr;
	}
	
	/*

9 	1 	0 	20 	2 	Grass
5 	2 	0 	20 	17 	Grass Tufts
47 	3 	0 	20 	64 	Grass Tall
48 	4 	0 	20 	16 	Grass Patches
51 	5 	0 	20 	67 	Snow
53 	6 	0 	20 	27 	Snow Tufts
54 	7 	0 	20 	68 	Snow Grass
57 	8 	0 	20 	69 	Snow Dirt
70 	9 	0 	20 	20 	Ice


14 	1 	-2 	-1 	31 	Grass
4 	2 	-2 	-1 	31 	Grass Tufts
46 	3 	-2 	-1 	31 	Grass Tall
49 	4 	-2 	-1 	31 	Grass Patches
50 	5 	-2 	-1 	31 	Snow
52 	6 	-2 	-1 	31 	Snow Tufts
55 	7 	-2 	-1 	31 	Snow Grass
56 	8 	-2 	-1 	31 	Snow Dirt
69 	9 	-2 	-1 	31 	Ice

65 	1 	-10 	-3 	26 	Grass
58 	2 	-10 	-3 	65 	Grass Tufts
59 	3 	-10 	-3 	65 	Grass Tall
60 	4 	-10 	-3 	65 	Grass Patches
67 	5 	-10 	-4 	26 	Snow
61 	6 	-10 	-3 	65 	Snow Tufts
62 	7 	-10 	-3 	65 	Snow Grass
63 	8 	-10 	-3 	65 	Snow Dirt
68 	9 	-10 	-3 	65 	Ice

//Invalid?
71 	10 	-10 	20 	71 	StoneWork
66 	4 	-10 	-4 	65 	Grass Patches

	 */
	public static class RandomMapEntry
	{
		int gameid;
		int genid;
		byte[] name;
		
		int temperature;
		
		//-4, -3, -1, 20
		int u3;
		
		int terrain;
		int terrain1;
		int terrain2;
		int terrain3;
		
		RandomMapEntry(ByteBuffer data)
		{
			gameid = data.getInt();
			genid = data.getInt();
			
			name = new byte[100];
			data.get(name);
			
			temperature = data.getInt();
			u3 = data.getInt();
			terrain = data.getInt();
			terrain1 = data.getInt();
			terrain2 = data.getInt();
			terrain3 = data.getInt();
			
			String sname = new String(name, Charsets.ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			System.out.println(gameid+" \t"+genid+" \t"+temperature+" \t"+u3+" \t"+terrain+" \t"+sname);
		}
	}
}
