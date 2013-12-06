package agent;
import robotica.Agent;
import robotica.SimState;
import robotica.State;


public class Waiter extends Agent
{
	public Waiter()
	{
		super("Waiter", new SimState("STATE_YAY"));
	}

	@Override
	public void update()
	{
		State state = this.currentState();

		switch(state.name())
		{
		case "STATE_YAY":
			test();
			break;
		
		}
		
		
		notifyObservers();
	}
	
	private void test()
	{
		
	}
}
