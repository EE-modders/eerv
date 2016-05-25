package net.coderbot.eerv.scn.entity;

public class ExtendedEntity extends ResourceEntity
{
	//TODO
	
	@Override
	public boolean isExtended()
	{
		return true;
	}
	
	@Override
	public ExtendedEntity asExtended()
	{
		return this;
	}
}
