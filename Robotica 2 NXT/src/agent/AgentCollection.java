package agent;

import java.util.Vector;

import robotica.CoupledState;
import robotica.SimState;
import standard.Agent;

public class AgentCollection
{
	private Vector<Agent> agents;
	private Vector<String> newData;
	private int count = 0;

	private boolean reset = false;

	public AgentCollection()
	{
		agents = new Vector<Agent>();
		newData = new Vector<String>();
	}

	public void addAgent(Agent agent)
	{

		agents.addElement(agent);

	}

	public void reset()
	{
		reset = true;
	}

	public void update()
	{
		if (reset)
		{
			for (int i = 0; i < agents.size(); i++)
			{
				agents.elementAt(i).reset();
			}
			reset = false;
		}

		for (int i = 0; i < agents.size(); i++)
		{
			agents.elementAt(i).update();
		}

	}

	public void addData(String data)
	{
		this.newData.addElement(data);
	}

	public void processData()
	{
		while (newData.size() > 0)
		{
			String data = newData.elementAt(0);
			newData.removeElementAt(0);
			
			if(data == null)
				break;
			
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
					if (a.getID() == id)
					{
						String name = first(data);
						data = left(data);

						String target = first(data);
						data = left(data);

						for (int j = 0; j < a.coupledStateSize(); j++)
						{
							CoupledState sta = a.getCoupledState(j);
							System.out.println(name + " "
									+ sta.getTargetState().name());
							if (sta.getTargetState().name().equals(name)
									&& a.currentState().name().equals("IDLE") && !a.hasFutureState())
							{
								a.setState(new SimState(sta.getNewState()
										.name(), target));
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
					if (a.getID() == id2)
					{
						a.processCompletedTask(task);
					}
				}

				break;
			}

		}
	}

	public void forceChanged()
	{
		for (int i = 0; i < agents.size(); i++)
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
