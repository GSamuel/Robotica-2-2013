package robotica;

public abstract class State
{
	private final String name;
	private final String target;
	private boolean hasTarget;

	public State(String name)
	{
		this.name = name;
		this.target = "";
		hasTarget = false;
	}
	
	public State(String name, String target)
	{
		this.name = name;
		this.target = target;
		hasTarget = true;
	}
	
	public boolean hasTarget()
	{
		return hasTarget;
	}

	public String name()
	{
		return name;
	}
	
	public String target()
	{
		return target;
	}
}
