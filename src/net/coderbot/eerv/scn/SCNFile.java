package net.coderbot.eerv.scn;

import java.nio.ByteBuffer;

import net.coderbot.eerv.scn.SCN.FileID;

public class SCNFile
{
	ByteBuffer data;
	FileID id;
	
	boolean hasAttribs;
	int u0;
	byte u1, u2, u3;
	
	boolean hasExtendedAttribs;
	float u4, u5, u6;
	int u7, u8;
}
