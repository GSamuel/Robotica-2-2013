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
		for (int i = 0; i < agents.size(); i++)
			agents.elementAt(i).update();
	}

	public void processData(String data)
	{
		String first = first(data);
		data = left(data);

		switch (first)
		{
		case "NEW":
			for (int i = 0; i < agents.size(); i++)
			{
				Agent a = agents.elementAt(i);
				if (!a.hasID())
				{
					a.setID(Integer.parseInt(first(data)));
					a.setSendAll();
					break;
				}
			}
		}
	}

	private String first(String s)
	{
		return s.substring(0, s.indexOf('$'));
	}

	private String left(String s)
	{
		return s.substring(s.indexOf('$') + 1);
	}
}
