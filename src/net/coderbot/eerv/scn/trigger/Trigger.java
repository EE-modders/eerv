package net.coderbot.eerv.scn.trigger;

public class Trigger
{
	/**
	 * Name of the trigger.
	 */
	String name;
	
	/**
	 * Description of the trigger.
	 */
	String desc;
	
	/**
	 * Is the trigger active?
	 */
	boolean active;
	/**
	 * If true, this trigger does not turn off after firing.
	 */
	boolean looping;
	/**
	 * Has this trigger fired?
	 */
	boolean fired;
	/**
	 * Is it active when in a cinematic?
	 */
	boolean inCinematic;
	
	/**
	 * How much time, in seconds, this trigger will be active for, relative to the level start.<br>
	 * If it is 0, it is always active.
	 */
	int activeFor;
	
	/**
	 * How much time, in GameSeconds, this trigger has been active for.
	 */
	int secondsActive;
	
	/**
	 * ID of this trigger.
	 */
	int id;
	
	/**
	 * Binds the conditions to this trigger.
	 */
	ConditionBinding[] conditions;
	
	/**
	 * Binds the effects to this trigger.
	 */
	EffectBinding[] effects;
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return desc;
	}

	public void setDescription(String desc)
	{
		this.desc = desc;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public boolean isLooping()
	{
		return looping;
	}

	public void setLooping(boolean looping)
	{
		this.looping = looping;
	}

	public boolean isFired()
	{
		return fired;
	}

	public void setFired(boolean fired)
	{
		this.fired = fired;
	}

	public boolean isInCinematic()
	{
		return inCinematic;
	}

	public void setInCinematic(boolean inCinematic)
	{
		this.inCinematic = inCinematic;
	}

	public int getActiveFor()
	{
		return activeFor;
	}

	public void setActiveFor(int activeFor)
	{
		this.activeFor = activeFor;
	}

	public int getSecondsActive()
	{
		return secondsActive;
	}

	public void setSecondsActive(int secondsActive)
	{
		this.secondsActive = secondsActive;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public ConditionBinding[] getConditions()
	{
		return conditions;
	}

	public void setConditions(ConditionBinding[] conditions)
	{
		this.conditions = conditions;
	}

	public EffectBinding[] getEffects()
	{
		return effects;
	}

	public void setEffects(EffectBinding[] effects)
	{
		this.effects = effects;
	}

	public String toString()
	{
		return "{name: "+name+", desc: "+desc+", active: "+active+", looping: "+looping+", fired: "+fired+", inCinematic: "+inCinematic+", activeFor: "+activeFor+", secondsActive: "+secondsActive+", id: "+id+"}";
	}
	
	public static enum ChainType
	{
		END, AND, OR;
	}
	
	public static class ConditionBinding
	{
		int conditionId;
		boolean not;
		ChainType chainType;
		
		public ConditionBinding(int conditionId, boolean not, ChainType chainType)
		{
			this.conditionId = conditionId;
			this.not = not;
			this.chainType = chainType;
		}
		
		public int getConditionId()
		{
			return conditionId;
		}

		public void setConditionId(int conditionId)
		{
			this.conditionId = conditionId;
		}

		public boolean isNot()
		{
			return not;
		}

		public void setNot(boolean not)
		{
			this.not = not;
		}

		public ChainType getChainType()
		{
			return chainType;
		}

		public void setChainType(ChainType chainType)
		{
			this.chainType = chainType;
		}

		public String toString()
		{
			return "{conditionId: "+conditionId+", not: "+not+" chainType: "+chainType+"}";
		}
	}
	
	public static class EffectBinding
	{
		int effectId;
		/**
		 * How much time, in seconds, the effect will be delayed for after the parent trigger fires.
		 */
		int delay;
		ChainType chainType;
		
		public EffectBinding(int effectId, int delay, ChainType chainType)
		{
			this.effectId = effectId;
			this.delay = delay;
			this.chainType = chainType;
		}
		
		public int getEffectId()
		{
			return effectId;
		}

		public void setEffectId(int effectId)
		{
			this.effectId = effectId;
		}

		public int getDelay()
		{
			return delay;
		}

		public void setDelay(int delay)
		{
			this.delay = delay;
		}

		public ChainType getChainType()
		{
			return chainType;
		}

		public void setChainType(ChainType chainType)
		{
			this.chainType = chainType;
		}
		
		public String toString()
		{
			return "{effectId: "+effectId+", delay: "+delay+" chainType: "+chainType+"}";
		}
	}
}
