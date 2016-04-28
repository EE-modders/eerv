package net.coderbot.eerv.scn;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import net.coderbot.util.DecoderException;

public class Diplomacy
{
	int[][] teams;
	
	int wondersForVictory;
	float u0;
	byte u1;
	float u2;
	int gameUnitLimit;
	float tickrate;
	
	boolean victoryAllowed;
	boolean lockTeams;
	boolean cheatCodes;
	boolean unitImprovements;
	
	int u3;
	short u4;
	
	ChatMessage[] messages;
	
	public Diplomacy(ByteBuffer data) throws DecoderException
	{
		int nTeams = data.getInt();
		teams = new int[nTeams][];
		
		for(int i = 0;i<nTeams;i++)
		{
			int count = data.getInt();
			teams[i] = new int[count];
			
			for(int j = 0;j<count;j++)
			{
				teams[i][j] = data.getInt();
			}
		}
		
		wondersForVictory = data.getInt();
		u0 = data.getFloat();
		u1 = data.get();
		u2 = data.getFloat();
		gameUnitLimit = data.getInt();
		tickrate = data.getFloat();
		
		//0?0?0?01
		int bf0 = data.getInt();
		victoryAllowed = (bf0&1)==1;
		unitImprovements = (bf0&256)==256;
		lockTeams = (bf0&65536)==65536;
		cheatCodes = (bf0&0x01000000)==0x01000000;
		
		
		u3 = data.getInt();
		u4 = data.getShort();
		
		int count = data.getInt();
		messages = new ChatMessage[count];
		
		int len = -1;
		for(int m = 0;m<count;m++)
		{
			ChatMessage message = new ChatMessage();
			message.zero = data.getInt();
			
			len = data.getInt();
			byte[] asciiZ = new byte[len];
			if(len==0)
			{
				break;
			}
			data.get(asciiZ);
			message.sender = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			
			message.r = data.getFloat();
			message.g = data.getFloat();
			message.b = data.getFloat();
			asciiZ = new byte[data.getInt()];
			
			data.get(asciiZ);
			message.message = new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII);
			message.unk = data.getInt();
			
			messages[m] = message;
		}
		
		System.out.println("end: "+data.getShort());
	}
}
