import java.util.ArrayList;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import robotica.Agent;
import robotica.Loop;
import robotica.SimAgent;
import robotica.SimState;

public class NXTLoop extends Loop
{
	private BrickConnection brickCon;
	private ArrayList<Agent> agents;

	public NXTLoop(int tickTime)
	{
		super(tickTime);

		agents = new ArrayList<Agent>();
		agents.add(new SimAgent().setName("Cook").setState(new SimState("COOKING")));
		agents.add(new SimAgent().setName("Customer").setState(new SimState("EATING")));
		agents.add(new SimAgent().setName("Waiter").setState(new SimState("SLEEPING")));

		NXTConnection connection = Bluetooth.waitForConnection();
		brickCon = new BrickConnection(connection.openDataOutputStream(),
				connection.openDataInputStream());
		brickCon.start();
		
		brickCon.sendData("NEW$");
		brickCon.sendData("NEW$");
		brickCon.sendData("NEW$");
	}

	@Override
	public void loop()
	{
		while (!brickCon.isEmpty())
		{
			String s = brickCon.receiveData();
			String first = first(s);
			s = left(s);

			switch (first)
			{
			case "NEW":
				for (Agent a : agents)
				{
					if (!a.hasID())
					{
						a.setID(Integer.parseInt(first(s)));
						System.out.println("ID: "+a.getID());
						System.out.println("agent: "+a.name());
						brickCon.sendData("SETNAME$"+a.getID()+"$"+a.name()+"$");
						brickCon.sendData("SETSTATE$F$"+a.getID()+"$"+a.currentState().name()+"$");
						break;
					}
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
