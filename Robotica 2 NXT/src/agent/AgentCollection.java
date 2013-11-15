package agent;

import java.util.Vector;

import robotica.Agent;

public class AgentCollection
{
	private Vector<Agent> agents;
	
	public AgentCollection()
	{
		agents = new Vector<Agent>();
	}

	public void addAgent(Agent agent)
	{
		agents.addElement(agent);
	}
	
	public void update()
	{
		for(int i = 0; i < agents.size(); i++)
			agents.elementAt(i).update();
	}
}
