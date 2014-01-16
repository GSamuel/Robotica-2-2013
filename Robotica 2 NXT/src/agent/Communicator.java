package agent;

import java.util.Vector;

import loop.Loop;
import robotica.CompletedTask;
import robotica.CoupledState;
import standard.Agent;
import standard.AgentObserver;
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
	public void update(Agent a)
	{
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

		if (a.currentState().hasTarget())
		{
			messages.addElement("SETSTATE$" + a.getID() + "$"
					+ a.currentState().name() + "$T$"
					+ a.currentState().target() + "$");
		} else
		{
			messages.addElement("SETSTATE$" + a.getID() + "$"
					+ a.currentState().name() + "$F$");
		}

		while (a.hasCompletedTask())
		{
			CompletedTask task = a.removeCompletedTask();
			System.out.println(task.getTask() + " " + task.getTarget());
			messages.addElement("TASKDONE$" + task.getTask() + "$"
					+ task.getTarget() + "$");
		}

		for (int i = 0; i < a.coupledStateSize(); i++)
		{
			CoupledState cs = a.getCoupledState(i);
			if (cs.getOwnState().name().equals(a.currentState().name()))
			{
				messages.addElement("ADDCSTATE$" + a.getID() + "$"
						+ cs.getOwnState().name() + "$"
						+ cs.getTargetState().name() + "$");
			}
		}
		
		if(a.requestNewState())
		{
			a.setRequestNewState(false);
			messages.addElement("REQUEST$"+a.getID()+"$");
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
			//col.processData(s);
			col.addData(s);
		}
	}

	public void sendMessagesToServer()
	{
		if (conMan.isConnected())
		{
			BrickConnection bc = conMan.getBrickConnection();

			while (!messages.isEmpty())
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
		if (!conMan.getBrickConnection().isEmpty())
			update();
	}
}
