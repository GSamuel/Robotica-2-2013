package agent;

import java.util.Vector;

import robotica.Agent;
import robotica.CoupledState;
import robotica.SimState;

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
			break;
		case "CSTATE":
			int id = Integer.parseInt(first(data));
			System.out.println(first(data));
			System.out.println(data);
			data = left(data);
			System.out.println(data);
			for (int i = 0; i < agents.size(); i++)
			{
				Agent a = agents.elementAt(i);
				if(a.getID() == id)
				{
					String name = first(data);
					data = left(data);
					
					String target = first(data);
					data = left(data);

					for(int j = 0; j < a.coupledStateSize(); j++)
					{
						CoupledState sta = a.getCoupledState(j);
						System.out.println(name+" "+ sta.getTargetState().name());
						if(sta.getTargetState().name().equals(name))
						{
							a.setState(new SimState(sta.getNewState().name(), target));
							a.setChanged();
							System.out.println(a.currentState().name());
						}
					}
				}

			}
			break;
		case "TASKDONE":
			int id2 = Integer.parseInt(first(data));
			data = left(data);
			String task = first(data);
			data = left(data);
			
			System.out.println("received task message");
			

			for (int i = 0; i < agents.size(); i++)
			{
				Agent a = agents.elementAt(i);
				if(a.getID() == id2)
				{
					a.processCompletedTask(task);
				}
			}
			
			
			break;
		}
	}
	
	public void forceChanged()
	{
		for(int i = 0; i < agents.size(); i++)
			agents.elementAt(i).setChanged();
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
