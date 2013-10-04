package robotica;

import java.util.ArrayList;
import java.util.Iterator;

public class SimulationModel implements Iterable<Agent>
{
	private ArrayList<Agent> agents;

	public SimulationModel()
	{
		agents = new ArrayList<Agent>();
	}

	public void addAgent(Agent agent)
	{
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

}
