package net.coderbot.eerv.scn.entity;

public class Entity
{
	int type;
	
	public boolean isResource()
	{
		return false;
	}
	
	public boolean isExtended()
	{
		return false;
	}
	
	public ResourceEntity asResource()
	{
		return null;
	}
	
	public ExtendedEntity asExtended()
	{
		return null;
	}
}
