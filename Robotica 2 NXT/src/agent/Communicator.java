package agent;

import java.util.Vector;

import loop.Loop;
import robotica.Agent;
import robotica.AgentObserver;
import robotica.SimAgent;
import standard.BrickConnection;
import connection.ConnectionManager;
import connection.ConnectionObserver;

public class Communicator extends Loop implements AgentObserver,
		ConnectionObserver
{
	private AgentCollection col;
	private Vector<String> messages;
	private ConnectionManager conMan = ConnectionManager.getInstance();

	public Communicator(AgentCollection col)
	{
		super(10);
		this.col = col;
		messages = new Vector<String>();
	}

	public void observe(Agent a)
	{
		a.registerObserver(this);
		messages.addElement("NEW$");
	}

	public void start()
	{
		new Thread(this).start();
		;
	}

	// Agent state changed
	@Override
	public void update(Agent a)
	{
		a.currentState();
		if (a.hasToSendAll() && a.hasID())
		{
			messages.addElement("SETNAME$" + a.getID() + "$" + a.name() + "$");
			messages.addElement("SETSTATE$F$" + a.getID() + "$"
					+ a.currentState().name() + "$");
			a.allSended();
		} else if (a.hasID())
			messages.addElement("SETSTATE$F$" + a.getID() + "$"
					+ a.currentState().name() + "$");
	}

	// New Input
	@Override
	public void update()
	{
		BrickConnection bc = conMan.getBrickConnection();
		while (!bc.isEmpty())
		{
			String s = bc.receiveData();
			col.processData(s);
		}
	}

	public void sendMessagesToServer()
	{
		if (conMan.isConnected())
		{
			BrickConnection bc = conMan.getBrickConnection();
			while (messages.size() > 0)
			{
				bc.sendData(messages.elementAt(0));
				messages.removeElementAt(0);
			}
		}

	}

	@Override
	public void loop()
	{
		sendMessagesToServer();

	}

}
