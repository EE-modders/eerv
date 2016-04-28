package net.coderbot.eerv;

import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

public class SSA 
{
	HashMap<String,Entry> files;
	HashMap<String,String> props;
	ByteBuffer store;
	
	static Field cap;
	static
	{
		try 
		{
			cap = Buffer.class.getDeclaredField("capacity");
			cap.setAccessible(true);
		} 
		catch (NoSuchFieldException e) 
		{
			e.printStackTrace();
		}
		catch (SecurityException e) 
		{
			e.printStackTrace();
		}
	}
	
	SSA(ByteBuffer store)
	{
		files = new HashMap<String,Entry>();
		props = new HashMap<String,String>();
		this.store = store;
	}
	
	public ByteBuffer map(String name)
	{
		Entry e = files.get(name.replace('\\', '/'));
		store.position((int)e.offs);
		ByteBuffer bb = store.slice();
		
		//make sure we honor the 'end' variable
		bb.limit((int)Math.min(e.size, e.end-e.offs+1));
		try 
		{
			//properly sat capacity
			cap.set(bb, bb.limit());
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		return bb;
	}
	
	public Set<String> getFiles()
	{
		return files.keySet();
	}
	
	public HashMap<String,String> getProperties()
	{
		return props;
	}
	
	static class Entry
	{
		long offs;
		long size;
		long end;
	}
}
