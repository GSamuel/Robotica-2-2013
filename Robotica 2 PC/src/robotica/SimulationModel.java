package robotica;

import java.util.ArrayList;
import java.util.Iterator;

public class SimulationModel implements Iterable<Agent>
{
	private ArrayList<Agent> agents;
	private int id = 0;

	public SimulationModel()
	{
		agents = new ArrayList<Agent>();
	}

	public void addAgent(Agent agent)
	{
		agent.setID(id++);
		agents.add(agent);
	}

	public void removeAgent(Agent agent)
	{
		agents.remove(agent);
	}

	@Override
	public Iterator<Agent> iterator()
	{
		return agents.iterator();
	}

	public int amountAgents()
	{
		return agents.size();
	}
	
	public Agent getAgentAt(int index)
	{
		if(index < agents.size())
			return agents.get(index);
		else
			return null;
	}

}
