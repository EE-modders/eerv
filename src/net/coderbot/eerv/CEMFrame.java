package net.coderbot.eerv;

import net.coderbot.math.Mat44;
import net.coderbot.math.Vector3;

public class CEMFrame 
{
	float radius;
	int vertices;
	float[] pos;//flat array size = vertices*3
	float[] normal;//flat array size = vertices*3
	float[] uv;//flat array size = vertices*3
	float[] tagpoints;//flat array size = vertices*3
	
	Mat44 transform;
	Vector3 boxfrom;
	Vector3 boxto;
}
