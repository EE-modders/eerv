package net.coderbot.eerv.scn;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class Player 
{
	boolean human;
	boolean inactive;
	boolean defeated;
	float unknown;
	int color;
	int id;
	int team;
	int startingCitizens;
	String name;
	
	public Player(ByteBuffer data)
	{
		data.get();
		data.get();
		data.get();
		int bitfield = data.getInt();
		human = (bitfield&1)==1;
		inactive = (bitfield&4)==4;
		defeated = (bitfield&2)==2;
		
		byte[] asciiZ = new byte[data.getInt()];
		data.get(asciiZ);
		name = (asciiZ.length>0)?new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.ISO_8859_1):"";
		
		unknown = data.getFloat();
		color = data.getInt();
		id = data.getInt();
		team = data.getInt();
		startingCitizens = data.getInt();
		
		//Dont mess up alignment, so throw at the end
		//If bits other than the bottom 3 are set
		if((bitfield&0xFFFFFFF8)!=0)
		{
			throw new IllegalArgumentException("unknown flags: "+Integer.toHexString(bitfield&0xFFFFFFF8));
		}
	}
	
	public String toString()
	{
		return (human?"H":"-")+(inactive?"I":"-")+(defeated?"D":"-")+" "+unknown+" \tcolor: "+color+" \tid: "+id+" \tteam: "+team+" \tstartingCitizens: "+startingCitizens
				+" \tname: "+name;
	}
}
