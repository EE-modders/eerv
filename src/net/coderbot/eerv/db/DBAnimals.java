package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import net.coderbot.util.Charsets;

public class DBAnimals 
{
	public static AnimalEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		AnimalEntry[] sr = new AnimalEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new AnimalEntry(data);
		}
		
		return sr;
	}
	
	public static class AnimalEntry
	{
		byte[] name;
		int gameid;
		int index;
		
		AnimalEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			gameid = data.getInt();
			index = data.getInt();
			
			//var
			//1/2
			//2/3/6/8
			//1, 0 for tiger
			
			//var
			//same as last
			//0
			//1/2/3/4/5/6/9
			
			//-1, 9deer, 6horse, 3shark, 1eagle, 3walrus
			//-1, 1horse, 6eagle, 2walrus
			//-1, 2 for eagle
			//0
			
			//floating point
			//120 to 2000
			//200 to 10000
			//2, 10 for shark+dolphin+eagle
			
			//2, 50 for shark, dolphin, eagle
			//50, 25 for chicken, 90 for elephant, 0 for shark+dolphin+eagle, 65 for walrus
			//180, 65 for chicken, 0 for shark+dolphin+eagle
			//0, 1 for chicken+ostrich, 65536 for tiger+giraffe
			
			//0, 1 for wolf
			//var
			//var
			//4, 10 for horse+giraffe+walrus, 3 for chicken, 5 for tiger+shark+dolphin+eagle, 15 for elephant
			
			//20
			//300
			//900
			//floating-point
			
			//120
			//260
			//-1
			//-1
			
			//-1
			//-1
			//0
			//0
			
			//0
			//0
			//0
			//0
			
			//0
			
			data.position(data.position()+96);
			
			String sname = new String(name, Charsets.ASCII);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			System.out.println(data.getInt()+" \t"+data.getInt()+" \t"+data.getInt()+" \t"+data.getInt()+" \t"+sname.substring(5, sname.length()));
			
			data.position(data.position()+52);
		}
		
		public String toString()
		{
			return "AnimalEntry {}";
		}
	}
}
