package agent;

import standard.Agent;
import robotica.CoupledState;
import robotica.SimState;

public class TestAgent extends Agent
{

	public TestAgent()
	{
		super("Agent P", new SimState("IDLE"));
		this.addCoupledState(new CoupledState("IDLE", "IDLE", "POEP"));
	}

	@Override
	public void update()
	{
		notifyObservers();
	}

	@Override
	public void reset()
	{
		// TODO Auto-generated method stub
		
	}

}
