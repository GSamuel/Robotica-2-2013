package agent;

import java.util.Vector;

import robotica.Agent;
import robotica.AgentObserver;
import standard.BrickConnection;
import connection.ConnectionManager;
import connection.ConnectionObserver;

public class Communicator implements AgentObserver, ConnectionObserver
{
	private AgentCollection col;
	private Vector<String> messages;
	private ConnectionManager conMan = ConnectionManager.getInstance();

	public Communicator(AgentCollection col)
	{
		this.col = col;
		messages = new Vector<String>();
	}

	public void observe(Agent a)
	{
		a.registerObserver(this);
		messages.addElement("$NEW");
	}

	// Agent state changed
	@Override
	public void update(Agent a)
	{
		a.currentState();
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
			System.out.println(s);
		}
	}

	public void sendMessagesToServer()
	{
		if (conMan.isConnected())
		{
			BrickConnection bc = conMan.getBrickConnection();
			while( messages.size() > 0)
				bc.sendData(messages.elementAt(0));
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
