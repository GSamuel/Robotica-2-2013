import java.util.Vector;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import robotica.Agent;
import robotica.Loop;
import robotica.SimAgent;
import robotica.SimState;

public class NXTLoop extends Loop
{
	private BrickConnection brickCon;
	private Vector<Agent> agents;

	public NXTLoop(int tickTime)
	{
		super(tickTime);

		agents = new Vector<Agent>();

		System.out.println("waiting connection");
		NXTConnection connection = Bluetooth.waitForConnection();
		brickCon = new BrickConnection(connection.openDataOutputStream(),
				connection.openDataInputStream());
		brickCon.start();
	}
	
	public void addAgent(Agent agent)
	{
		agents.addElement(agent);
		brickCon.sendData("NEW$");
	}

	@Override
	public void loop()
	{

		for (int i = 0; i < agents.size(); i++)
		{
			agents.elementAt(i).update();
		}
		
		//read from Bluetooth stream
		while (!brickCon.isEmpty())
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
