package net.coderbot.eerv.compression;

import java.nio.ByteBuffer;

public class ExplodeBuffer
{
	private ByteBuffer[] bufs;
	int position;
	
	public ExplodeBuffer()
	{
		bufs = new ByteBuffer[3];
		bufs[0] = ByteBuffer.allocateDirect(4096);
		bufs[1] = ByteBuffer.allocateDirect(4096);
		bufs[2] = ByteBuffer.allocateDirect(4096);
		bufs[2].limit(516);
		position = 4096;
	}
	
	public void rotate()
	{
		ByteBuffer head = bufs[0];
		bufs[0] = bufs[1];
		bufs[0].position(0);
		
		bufs[1] = bufs[2];
		bufs[1].limit(4096);
		
		bufs[2] = head;
		bufs[2].position(0);
		for(int i = 0;i<512;i++)
		{
			//512x8 instead of 4096x1
			bufs[2].putLong(0);
		}
		
		bufs[2].limit(516);
		position = position-4096;
	}
	
	public byte get(int position)
	{
		return bufs[position>>12].get(position&0x4095);
	}
	
	public void put(int position, byte b)
	{
		bufs[position>>12].put(position&0x4095, b);
	}
	
	public byte get()
	{
		return bufs[position>>12].get((position++)&0x4095);
	}
	
	public void put(byte b)
	{
		bufs[position>>12].put(position&0x4095, b);
		position++;
	}
	
	public ByteBuffer getDict()
	{
		return bufs[0];
	}
	
	public ByteBuffer getWork()
	{
		return bufs[1];
	}
}
