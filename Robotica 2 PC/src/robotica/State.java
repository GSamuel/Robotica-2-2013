package robotica;

public abstract class State
{
	private final String name;
	private final Agent target; // agent that has this state
	private boolean hasTarget;

	public State(String name)
	{
		this.name = name;
		this.target = null;
		hasTarget = false;
	}
	
	public State(String name, Agent agent)
	{
		this.name = name;
		this.target = agent;
		hasTarget = true;
	}
	
	public boolean hasTarget()
	{
		return hasTarget;
	}
	
	public Agent target()
	{
		return target;
	}

	public String name()
	{
		return name;
	}
}
