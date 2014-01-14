package robotica;

import java.util.ArrayList;
import java.util.Iterator;

import loop.Simulation;
import connection.NXTConnection;

public class Brick implements AgentObserver
{
	private NXTConnection nxtCon;
	private Simulation sim;
	private ArrayList<Agent> agents;

	public Brick(NXTConnection con, Simulation sim)
	{
		this.nxtCon = con;
		this.sim = sim;
		agents = new ArrayList<Agent>();
	}

	public boolean isConnected()
	{
		return nxtCon.isWorking();
	}

	public boolean isSameCon(NXTConnection con)
	{
		return nxtCon.isEqual(con.getNXTInfo());
	}

	public boolean refreshConnection(NXTConnection con)
	{
		if (!nxtCon.isWorking() && con.isWorking()
				&& nxtCon.isEqual(con.getNXTInfo()))
		{
			this.nxtCon = con;
			return true;
		}
		return false;
	}

	public void update()
	{
		while (!nxtCon.isEmpty())
		{
			String s = nxtCon.receiveData();
			String first = first(s);
			s = left(s);

			switch (first)
			{
			case "NEW":
				Agent a = new SimAgent();
				agents.add(a);
				a.registerObserver(this);
				sim.simulationModel().addAgent(a);
				nxtCon.sendData("NEW$".concat(String.valueOf(a.getID()))
						.concat("$"));
				System.out.println("New Agent Found");
				break;
			case "SETSTATE":
				int agentID = Integer.parseInt(first(s));
				s = left(s);
				String stateName = first(s);
				s = left(s);
				
				String bool = first(s);
				s = left(s);
				
				SimState state;
				
				if(bool.equals("T"))
				{
					String target = first(s);
					s = left(s);
					
					state = new SimState(stateName, target);
				}
				else
					state = new SimState(stateName);

				Agent age = getAgentWithId(agentID);
				age.setState(state);
				age.setChanged();
				age.notifyObservers();

				System.out.println("State changed of "
						+ getAgentWithId(agentID).name() + " into "
						+ getAgentWithId(agentID).currentState().name());
				break;
			case "SETNAME":

				int Aid = Integer.parseInt(first(s));
				Agent ag = getAgentWithId(Aid);
				s = left(s);
				ag.setName(first(s));

				System.out.println("agent with id " + Aid
						+ " name changed into " + getAgentWithId(Aid).name());

				break;
				
			case "TASKDONE":
				String task = first(s);
				s= left(s);
				String target = first(s);
				s = left(s);
				
				taskDone(new CompletedTask(task,target));
				
				break;
			case "ADDCSTATE":
				int agID = Integer.parseInt(first(s));
				s = left(s);
				String ownState = first(s);
				s = left(s);
				String targetState = first(s);
				s = left(s);

				Agent agentje = getAgentWithId(agID);
				agentje.addCoupledState(new CoupledState(ownState, targetState));

				System.out.println("Coupled State added to "
						+ getAgentWithId(agID).name() + ": " + ownState
						+ " --- " + targetState);

				break;
			default:
				System.out.println("can't process stream data: " + first + "$"
						+ s);
				break;
			}

		}

		if (!nxtCon.isWorking())
		{
			Iterator<Agent> it = agents.iterator();
			while (it.hasNext())
			{
				Agent a = it.next();
				sim.simulationModel().removeAgent(a);
				it.remove();
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

	private Agent getAgentWithId(int id)
	{
		for (Agent a : agents)
		{
			if (a.hasID() && a.getID() == id)
				return a;
		}

		return null;
	}
	
	public void taskDone(CompletedTask task)
	{
		for(int i = 0; i < sim.simulationModel().amountAgents(); i++)
		{
			Agent a = sim.simulationModel().getAgentAt(i);

			System.out.println(a.name()+"-- task --"+ task.getTarget());
			if(a.name().equalsIgnoreCase(task.getTarget()))
			{
				a.addCompletedTask(task);
				a.setChanged();
				a.notifyObservers();
			}
		}
	}

	@Override
	public void update(Agent a)
	{
		while(a.hasCompletedTask())
		{
			System.out.println(a.name()+"wants to send task");
			CompletedTask task = a.removeCompletedTask();
			nxtCon.sendData("TASKDONE$"+a.getID()+"$"+task.getTask()+"$");
		}
		
		
		for (int i = 0; i < sim.simulationModel().amountAgents(); i++)
		{
			Agent b = sim.simulationModel().getAgentAt(i);
			for (int j = 0; j < a.coupledStateSize(); j++)
			{
				if (a.getCoupledState(j).getTargetState().name()
						.equals(b.currentState().name()) && a.getCoupledState(j).getOwnState().name().equals(a.currentState().name()))
				{

					nxtCon.sendData("CSTATE$"+a.getID()+"$"+b.currentState().name()+"$"+b.name()+"$");
				}
			}

			for (int j = 0; j < b.coupledStateSize(); j++)
			{
				if (b.getCoupledState(j).getTargetState().name()
						.equals(a.currentState().name()))
				{
					b.setChanged();
					b.notifyObservers();
				}
			}
		}
	}
}
