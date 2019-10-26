package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBUIControls 
{
	public static ControlEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		ControlEntry[] sr = new ControlEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new ControlEntry(data);
		}
		
		return sr;
	}
	
	public static class ControlEntry
	{
		byte[] name;
		byte[] sub0;
		byte[] sub1;
		
		byte[] sub2;
		byte[] sub3;
		byte[] sub4;
		
		float u0;
		float u1;
		float u2;
		
		ControlEntry(ByteBuffer data)
		{
			name = new byte[100];
			data.get(name);
			sub0 = new byte[100];
			data.get(sub0);
			sub1 = new byte[100];
			data.get(sub1);
			sub2 = new byte[100];
			data.get(sub2);
			sub3 = new byte[100];
			data.get(sub3);
			sub4 = new byte[100];
			data.get(sub4);
			
			//00: 00000000 | 3F000000 | 3F800000 | 3F333333 | 3E99999A | 3F400000 | TODO
			//01: 00000000 | 3F000000 | 3F800000 | 3E4CCCCD
			//02: 00000000 | 3F000000 | 3F800000 | 3F333333 | 3F400000
			//03: 00000000
			//04: 00000000
			//05: 00000000
			//06: 00000000
			//07: 00000000
			//08: many
			//09: many
			//0A: many
			//0B: many
			//0C: gameid? small increasing values jumpy
			//0D: index?  small increasing values
			//0E: 1 to 0x27 [39]
			//0F: 0 to 8
			//10: 0 to 0x3FB [1019]
			//11: 0 to 0x5FB [1531]
			//12: 0 to 3
			//13: 2 to 4
			//14: 1, 3, or 4
			//15: 0, 3, or 4
			//16: 0, 3, or 4
			//17: 0, 14, 22, 4B, 64, 6E, C8, E9, F0, FF
			//18: 0, 14, 35, 3B, 55, 64, 7D, C8, DA, E2, F0, FF
			
			u0 = data.getFloat();
			u1 = data.getFloat();
			u2 = data.getFloat();
			
			data.position(data.position()+248);
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return "ControlEntry {name: "+sname+", u0: "+u0+" "+u1+" "+u2+"}";
		}
	}
}
