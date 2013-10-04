package robotica;

public abstract class State
{
	private final String name;
	private final Agent agent; //agent that has this state
	
	public State(String name, Agent agent)
	{
		this.name = name;
		this.agent = agent;
	}
	
	public String getName()
	{
		return name;
	}
	
	
}
