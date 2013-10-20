package robotica;

public abstract class State
{
	private final String name;
	private final int target; // agent that has this state
	private boolean hasTarget;

	public State(String name)
	{
		this.name = name;
		this.target = 0;
		hasTarget = false;
	}
	
	public State(String name, int target)
	{
		this.name = name;
		this.target = target;
		hasTarget = true;
	}
	
	public boolean hasTarget()
	{
		return hasTarget;
	}
	
	public int target()
	{
		return target;
	}

	public String name()
	{
		return name;
	}
}
