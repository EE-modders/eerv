package net.coderbot.eerv.scn;

import java.nio.ByteBuffer;

public class Diplomacy
{
	int[][] allyGroups;
	
	int wondersForVictory;
	float u0;
	byte u1;
	float u2;
	int gameUnitLimit;
	float tickrate;
	
	boolean victoryAllowed;
	boolean teamsLocked;
	boolean cheatCodes;
	boolean unitImprovements;
	
	int u3;
	short u4;
	
	ChatMessage[] messages;
	
	public Diplomacy(ByteBuffer data)
	{
		
	}
}
