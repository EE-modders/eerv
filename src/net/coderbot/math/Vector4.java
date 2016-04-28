package net.coderbot.math;

import java.text.DecimalFormat;

public class Vector4 
{
	private static DecimalFormat TWOD = new DecimalFormat("0.0000");
	public double x,y,z,w;
	
	public Vector4()
	{
		this(0,0,0,0);
	}
	
	public Vector4(double x, double y)
	{
		this(x,y,0,0);
	}
	
	public Vector4(double x, double y, double z)
	{
		this(x,y,z,0);
	}
	
	public Vector4(double x, double y, double z, double w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public Vector4 add(Vector4 v)
	{
		x+=v.x;
		y+=v.y;
		z+=v.z;
		w+=v.w;
		return this;
	}
	
	public Vector4 sub(Vector4 v)
	{
		x-=v.x;
		y-=v.y;
		z-=v.z;
		w-=v.w;
		return this;
	}
	
	public Vector4 mul(Vector4 v)
	{
		x*=v.x;
		y*=v.y;
		z*=v.z;
		w*=v.w;
		return this;
	}
	
	public Vector4 mul(double v)
	{
		x*=v;
		y*=v;
		z*=v;
		w*=v;
		return this;
	}
	
	public Vector4 div(Vector4 v)
	{
		x/=v.x;
		y/=v.y;
		z/=v.z;
		w/=v.w;
		return this;
	}
	
	public Vector4 div(double v)
	{
		if(v==0)
		{
			x=0;
			y=0;
			z=0;
			w=0;
			return this;
		}
		
		x/=v;
		y/=v;
		z/=v;
		w/=v;
		return this;
	}
	
	public double length()
	{
		return Math.sqrt((x*x)+(y*y)+(z*z)+(w*w));
	}
	
	public Vector4 normalize()
	{
		double len = length();
		if(len==0)return this;
		return mul(1/len);
	}
	
	@Override
	public Vector4 clone()
	{
		Vector4 v = new Vector4();
		v.x = x;
		v.y = y;
		v.z = z;
		v.w = w;
		return v;
	}
	
	public boolean equals(Vector4 v)
	{
		if(v==null)
		{
			return false;
		}
		if(v.x!=x)
		{
			return false;
		}
		if(v.y!=y)
		{
			return false;
		}
		if(v.z!=z)
		{
			return false;
		}
		if(v.w!=w)
		{
			return false;
		}
		return true;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o != null && o instanceof Vector4)
		{
			return equals((Vector4)o);
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 3;
		
		hash = 53 * hash + (int)(x*4096);
		hash = 53 * hash + (int)(y*4096);
		hash = 53 * hash + (int)(z*4096);
		
		return hash;
	}
	
	public String toString()
	{
		return "Vector4 {x: "+TWOD.format(x)+", y: "+TWOD.format(y)+", z: "+TWOD.format(z)+", w: "+TWOD.format(w)+"}";
	}
}
