package agent;

import java.util.Vector;

import loop.Loop;
import robotica.Agent;
import robotica.AgentObserver;
import robotica.CoupledState;
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
			createMessages(a);
			a.allSended();
		} else if (a.hasID())
			createMessages(a);
	}
	
	private void createMessages(Agent a)
	{
		messages.addElement("SETSTATE$" + a.getID() + "$"
				+ a.currentState().name() + "$");
		
		for(int i = 0; i < a.coupledStateSize(); i++)
		{
			CoupledState cs = a.getCoupledState(i);
			if(cs.getOwnState().name().equals(a.currentState().name()))
			{
				messages.addElement("ADDCSTATE$" + a.getID()+"$"+ cs.getOwnState().name()+"$"+cs.getTargetState().name() + "$");
			}
		}
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
		if(!conMan.getBrickConnection().isEmpty())
			update();
	}
}
