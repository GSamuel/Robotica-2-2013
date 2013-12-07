package robotica;

public abstract class State
{
	private final String name;

	public State(String name)
	{
		this.name = name;
	}

	public String name()
	{
		return name;
	}
}
