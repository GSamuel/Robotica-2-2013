package agent;

import robotica.Agent;
import robotica.CoupledState;
import robotica.SimState;

public class TestAgent extends Agent
{

	public TestAgent()
	{
		super("Agent P", new SimState("IDLE"));
		this.addCoupledState(new CoupledState("IDLE", "IDLE"));
	}

	@Override
	public void update()
	{
		
		notifyObservers();
	}

}
