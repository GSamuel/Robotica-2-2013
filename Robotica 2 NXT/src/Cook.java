import lejos.nxt.Motor;
import robotica.Agent;
import robotica.SimState;


public class Cook extends Agent
{
	public Cook()
	{
		super("Cook", new SimState("Cooking"));
	}

	@Override
	public void update()
	{
		this.currentState();
	}
}
