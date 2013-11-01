package agent;
import robotica.Agent;
import robotica.SimState;


public class Waiter extends Agent
{
	public Waiter()
	{
		super("Waiter", new SimState("Sleeping"));
	}

	@Override
	public void update()
	{
		this.currentState();
	}
}
