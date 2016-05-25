package net.coderbot.eerv;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import net.coderbot.math.Mat44;
import net.coderbot.math.Vector3;
import net.coderbot.util.Decoder;
import net.coderbot.util.DecoderException;

public class CEMDecoder extends Decoder<CEM>
{
	ByteBuffer data;
	
	public CEMDecoder(ByteBuffer in) 
	{
		super(in);
	}
	
	public CEMDecoder(Path to) throws DecoderException
	{
		super(to);
	}

	@Override
	public void setInput(ByteBuffer input) 
	{
		data = input;
		if(input!=null)
		{
			data.order(ByteOrder.LITTLE_ENDIAN);
		}
	}

	@Override
	public CEM decode() throws DecoderException 
	{
		CEM cem = new CEM();
		
		//HeaderChunk{
		int magic = data.getInt();
		if(magic!=1179472723)
		{
			throw new DecoderException("cem","invalid magic, expected 'SSMF' at beginning of file");
		}
		
		boolean current = data.getInt()==2;
		if(!current)
		{
			throw new DecoderException("cem","cannot load legacy model");
		}
		//}
		
		//CountsChunk
		//{
			int nPolygons = data.getInt();
			int nVertices =  data.getInt();
			int nTagPoints = data.getInt();
			int nMaterials = data.getInt();
			int nFrames =    data.getInt();
			int nSubModels =  data.getInt();
			int nSets =  data.getInt();
			cem.subModels = nSubModels;
			cem.totalPolygons = nPolygons;
		//}
				
		//MiscChunk
		{
			byte[] asciiZ = new byte[data.getInt()];
			data.get(asciiZ);
			cem.misc = (asciiZ.length>0)?new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII):"";
		
			cem.center = new Vector3();
			cem.center.x = data.getFloat();
			cem.center.y = data.getFloat();
			cem.center.z = data.getFloat();
		}
		
		//PolygonsChunk
		{
			cem.indices0 = new int[nSets][];
			cem.indices1 = new int[nSets][];
			cem.indices2 = new int[nSets][];
			
			for(int i = 0;i<nSets;i++)
			{
				cem.indices0[i] = new int[data.getInt()];
				cem.indices1[i] = new int[cem.indices0[i].length];
				cem.indices2[i] = new int[cem.indices0[i].length];
				
				for(int s = 0;s<cem.indices0[i].length;s++)
				{
					cem.indices0[i][s] = data.getInt();
					cem.indices1[i][s] = data.getInt();
					cem.indices2[i][s] = data.getInt();
				}
			}
		}
		
		//MaterialsChunk
		{
			cem.materials = new CEMMaterial[nMaterials];
			
			for(int i = 0;i<nMaterials;i++)
			{
				CEMMaterial mat = new CEMMaterial();
				cem.materials[i] = mat;
				
				byte[] asciiZ;
				
				asciiZ = new byte[data.getInt()];
				data.get(asciiZ);
				mat.name = (asciiZ.length>0)?new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII):"";
				
				mat.textureIndex = data.getInt();
				mat.polygonStart = new int[nSets];
				mat.polygonLength = new int[nSets];
				for(int s = 0;s<nSets;s++)
				{
					mat.polygonStart[s] = data.getInt();
					mat.polygonLength[s] = data.getInt();
				}
				mat.vertexStart = data.getInt();
				mat.vertexLength = data.getInt();
				
				asciiZ = new byte[data.getInt()];
				
				data.get(asciiZ);
				mat.name2 = (asciiZ.length>0)?new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII):"";//11Ch
			}
		}
		
		//TagPoints
		{
			cem.tagPointNames = new String[nTagPoints];
			for(int i = 0;i<nTagPoints;i++)
			{
				byte[] asciiZ = new byte[data.getInt()];
		
				data.get(asciiZ);
				cem.tagPointNames[i] = (asciiZ.length>0)?new String(asciiZ, 0, asciiZ.length-1, StandardCharsets.US_ASCII):"";
			}
		}
		
		//Frames
		cem.frames = new CEMFrame[nFrames];
		for(int f = 0;f<nFrames;f++)
		{
			CEMFrame frame = new CEMFrame();
			cem.frames[f] = frame;
			frame.vertices = nVertices;
			
			frame.radius = data.getFloat();
			
			frame.pos = new float[nVertices*3];
			frame.normal = new float[nVertices*3];
			frame.uv = new float[nVertices*3];
			int p=0, n=0, u=0;
			
			//VertexSubChunk
			{
				for(int i = 0;i<nVertices;i++)
				{
					frame.pos[p++] = data.getFloat();
					frame.pos[p++] = data.getFloat();
					frame.pos[p++] = data.getFloat();
					
					frame.normal[n++] = data.getFloat();
					frame.normal[n++] = data.getFloat();
					frame.normal[n++] = data.getFloat();
					
					frame.uv[u++] = data.getFloat();
					frame.uv[u++] = data.getFloat();
				}
			}
			
			frame.tagpoints = new float[nTagPoints*3];
			int t = 0;
			//TagPointsSubChunk
			{
				for(int i = 0;i<nTagPoints;i++)
				{
					frame.tagpoints[t++] = data.getFloat();
					frame.tagpoints[t++] = data.getFloat();
					frame.tagpoints[t++] = data.getFloat();
				}
			}
			
			frame.transform = new Mat44();
			for(int a = 0;a<4;a++)
			{
				for(int b = 0;b<4;b++)
				{
					frame.transform.elems[a][b] = data.getFloat();
				}
			}
			
			frame.boxfrom = new Vector3(data.getFloat(), data.getFloat(), data.getFloat());
			frame.boxto = new Vector3(data.getFloat(), data.getFloat(), data.getFloat());
		}
		
		return cem;
	}
}
