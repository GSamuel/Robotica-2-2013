package robotica;

public class NoAgent extends Agent
{
	public NoAgent()
	{
		addState(new NoState(this));
		setVar("isDoingSomething", 0);
	}
}
