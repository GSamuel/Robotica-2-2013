package robotica;

import java.util.ArrayList;
import java.util.Iterator;

import loop.Simulation;
import connection.NXTConnection;

public class Brick
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
				sim.simulationModel().addAgent(a);
				nxtCon.sendData("NEW$".concat(String.valueOf(a.getID()))
						.concat("$"));
				break;
			case "SETSTATE":

				boolean target = first(s) == "T";
				s = left(s);
				int agentID = Integer.parseInt(first(s));
				s = left(s);
				String stateName = first(s);
				s = left(s);

				if (target)

					getAgentWithId(agentID)
							.setState(
									new SimState(stateName, Integer
											.parseInt(first(s))));
				else
					getAgentWithId(agentID).setState(new SimState(stateName));
				System.out.println(getAgentWithId(agentID).currentState().name());
				break;
			case "SETNAME":
				
				int Aid = Integer.parseInt(first(s));
				Agent ag = getAgentWithId(Aid);
				s = left(s);
				ag.setName(first(s));
				
				System.out.println(getAgentWithId(Aid).name());

				break;
			default :
				System.out.println("can't process stream data: "+ first+ "$" + s);
				break;
			}

		}
		
		if(!nxtCon.isWorking())
		{
			Iterator<Agent> it = agents.iterator();
			while(it.hasNext())
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
			if (a.hasID() && a.getID() == id)
				return a;

		return null;
	}
}
