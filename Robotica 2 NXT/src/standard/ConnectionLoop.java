package standard;

import java.util.Vector;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import loop.Loop;
import robotica.Agent;

public class ConnectionLoop extends Loop
{
	private BrickConnection brickCon;
	private Vector<Agent> agents;
	private boolean connected = false;

	public ConnectionLoop(int tickTime)
	{
		super(tickTime);

		agents = new Vector<Agent>();
		new UpdateLoop(tickTime, agents).start();
	}

	public void connect()
	{

		System.out.println("waiting connection");
		NXTConnection connection = Bluetooth.waitForConnection();
		brickCon = new BrickConnection(connection.openDataOutputStream(),
				connection.openDataInputStream());
		brickCon.start();

		connected = true;
	}

	public void addAgent(Agent agent)
	{
		agents.addElement(agent);
		brickCon.sendData("NEW$");
	}

	@Override
	public void loop()
	{
		// read from Bluetooth stream
		while (connected && !brickCon.isEmpty())
		{
			String s = brickCon.receiveData();
			String first = first(s);
			s = left(s);

			switch (first)
			{
			case "NEW":
				for (int i = 0; i < agents.size(); i++)
				{
					Agent a = agents.elementAt(i);
					if (!a.hasID())
					{
						a.setID(Integer.parseInt(first(s)));
						System.out.println("ID: " + a.getID());
						System.out.println("agent: " + a.name());
						brickCon.sendData("SETNAME$" + a.getID() + "$"
								+ a.name() + "$");
						brickCon.sendData("SETSTATE$F$" + a.getID() + "$"
								+ a.currentState().name() + "$");
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
