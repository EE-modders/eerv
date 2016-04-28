package net.coderbot.math;

import java.text.DecimalFormat;

public class Vector3 
{
	private static DecimalFormat TWOD = new DecimalFormat("0.0000");
	public double x,y,z;
	
	public Vector3()
	{
		this(0,0,0);
	}
	
	public Vector3(double x, double y)
	{
		this(x,y,0);
	}
	
	public Vector3(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static Vector3 fromAngle(double yaw, double pitch)
	{
		double pitchRadians = Math.toRadians(pitch);
		double yawRadians = Math.toRadians(yaw);
		double cosPitch = Math.cos(pitchRadians);
		
		return new Vector3(-cosPitch * Math.sin(yawRadians), Math.sin(pitchRadians), -cosPitch * Math.cos(yawRadians));
	}
	
	public Vector3 add(Vector3 v)
	{
		x+=v.x;
		y+=v.y;
		z+=v.z;
		return this;
	}
	
	public Vector3 sub(Vector3 v)
	{
		x-=v.x;
		y-=v.y;
		z-=v.z;
		return this;
	}
	
	public Vector3 mul(Vector3 v)
	{
		x*=v.x;
		y*=v.y;
		z*=v.z;
		return this;
	}
	
	public Vector3 mul(double v)
	{
		x*=v;
		y*=v;
		z*=v;
		return this;
	}
	
	public Vector3 div(Vector3 v)
	{
		x/=v.x;
		y/=v.y;
		z/=v.z;
		return this;
	}
	
	public Vector3 div(double v)
	{
		if(v==0)
		{
			x=0;
			y=0;
			z=0;
			return this;
		}
		
		x/=v;
		y/=v;
		z/=v;
		return this;
	}
	
	public double length()
	{
		return Math.sqrt((x*x)+(y*y)+(z*z));
	}
	
	public Vector3 normalize()
	{
		double len = length();
		if(len==0)return this;
		return div(len);
	}
	
	@Override
	public Vector3 clone()
	{
		Vector3 v = new Vector3();
		v.x = x;
		v.y = y;
		v.z = z;
		return v;
	}
	
	public boolean equals(Vector3 v)
	{
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
		return true;
	}
	
	public void cross(Vector3 b, Vector3 res)
	{
		res.x = (y * b.z) - (z * b.y);
		res.y = (z * b.x) - (x * b.z);
		res.z = (x * b.y) - (y * b.x);
	}
	
	public double dot(Vector3 b)
	{
		return (x*b.x)+(y*b.y)+(z*b.z);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o != null && o instanceof Vector3)
		{
			return equals((Vector3)o);
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
		return "Vector3 {x: "+TWOD.format(x)+", y: "+TWOD.format(y)+", z: "+TWOD.format(z)+"}";
	}
}
