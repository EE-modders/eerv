package net.coderbot.eerv.scn;

public class SCNTriggers 
{
	public static class SCNTrigger
	{
		int zeros;
		int prefix;
		String name;
		String desc;
		
		public String toString()
		{
			return "SCNTrigger {type: "+TriggerType.values()[zeros]+", prefix: "+prefix+", name: "+name+", desc: "+desc+"}";
		}
	}
	
	public static enum TriggerType
	{
		TRIGGER, CONDITION, EFFECT, UNK3, AREA;
	}
}
