package net.coderbot.eerv.db;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import java.nio.charset.StandardCharsets;

public class DBTerrain
{
	public static TerrainEntry[] load(ByteBuffer data)
	{
		data.order(ByteOrder.LITTLE_ENDIAN);
		TerrainEntry[] sr = new TerrainEntry[data.getInt()];
		
		for(int i = 0;i<sr.length;i++)
		{
			sr[i] = new TerrainEntry(data);
		}
		
		return sr;
	}
	
	public static class TerrainEntry
	{
		/**
		 * Texture path
		 */
		String texture;
		/**
		 * Debug name for this object
		 */
		byte[] name;
		/**
		 * Game ID, referenced from other files
		 */
		int gameid;
		/**
		 * Index of the entry in this file
		 */
		int index;
		/**
		 * Index in terrainGrayTextures
		 */
		int grayindex;
		/**
		 * Language entry.
		 */
		int language;
		/**
		 * Is this a valid entry? (in-game)
		 */
		boolean valid;
		/**
		 * gameID in terrainType
		 */
		int type;
		/**
		 * Amount of tiles on the X axis
		 */
		int xstitch;
		/**
		 * Amount of tiles on the Y axis
		 */
		int ystitch;
		/**
		 * Is this available in the scenario editor?
		 */
		boolean availableInEditor;
		/**
		 * Does this terrain have multiple frames of animation?
		 */
		boolean animated;
		/**
		 * TODO: An unknown value.
		 */
		int u0, u1, u2, u3, u4, u5, u6, u7;
		/**
		 * Used for animated terrain.
		 */
		int anim0;
		/**
		 * Used for animated terrain.
		 */
		int anim1;
		/**
		 * RGB terrain color, blended with texture
		 */
		int r,g,b;
		/**
		 * Always 0
		 */
		int zero;
		/**
		 * 22.2 for RM Ambients - Marsh
		 */
		float ambMarsh0;
		/**
		 * 33.3 for RM Ambients - Marsh
		 */
		float ambMarsh1;
		/**
		 * Is this an ambient terrain
		 */
		boolean ambient;
		
		TerrainEntry(ByteBuffer data)
		{
			byte[] asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			
			texture = new String(asciiZ, StandardCharsets.ISO_8859_1).replace('\\', '/').toLowerCase();
			name = new byte[100];
			data.get(name);
			
			gameid = data.getInt();
			index = data.getInt();
			grayindex = data.getInt();//Index, not gameID
			language = data.getInt();
			valid = data.getInt()==1;
			type = data.getInt();
			
			xstitch = data.getInt();
			ystitch = data.getInt();
			
			int bf0 = data.getInt();
			availableInEditor = (bf0&65536)==65536;
			animated = (bf0&1)==1;
			
			u0 = data.getInt();
			u1 = data.getInt();
			u2 = data.getInt();
			u3 = data.getInt();
			u4 = data.getInt();
			u5 = data.getInt();
			u6 = data.getInt();
			u7 = data.getInt();
							//100  Forest - *
							//     Infantry
			
							//5    Editor Amb - *
			
							//8    CliffRock \ /
			
							//1    RM Ambients - Beach,Desert,Forest,Marsh 
							//     Sand - Fine && Grass - Patches 
							//     Desert w/ ambients
			
							//2    RM Ambients - Aquatic
			
			anim0 = data.getInt();
			anim1 = data.getInt();
			
			r = data.getInt();
			g = data.getInt();
			b = data.getInt();
			
			zero = data.getInt();
			ambMarsh0 = data.getFloat();
			ambMarsh1 = data.getFloat();
			ambient = (data.getInt()&16777216)==16777216;
		}
		
		public String toString()
		{
			String sname = new String(name, StandardCharsets.ISO_8859_1);
			int idx0 = sname.indexOf(0);
			if(idx0>-1)
			{
				sname = sname.substring(0, idx0);
			}
			
			return gameid+" \t"+index+" \t"+sname;
		}
	}
}
