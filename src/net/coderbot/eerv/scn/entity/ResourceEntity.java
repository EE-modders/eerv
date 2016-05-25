package net.coderbot.eerv.scn.entity;

public class ResourceEntity extends Entity
{
	int tileX, tileY;
	float u0;
	int resources;
	
	@Override
	public boolean isResource()
	{
		return true;
	}
	
	@Override
	public ResourceEntity asResource()
	{
		return this;
	}
}
