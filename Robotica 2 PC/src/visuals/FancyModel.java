package visuals;

import loop.Simulation;
import robotica.Agent;

public class FancyModel
{
	private Simulation sim;

	public FancyModel(Simulation sim)
	{
		this.sim = sim;
	}
	
	public int size()
	{
		return sim.simulationModel().amountAgents();
	}
	
	public Agent getAgentAt(int index)
	{
		return sim.simulationModel().getAgentAt(index);
	}

}
