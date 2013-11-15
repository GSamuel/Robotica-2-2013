package agent;

import java.util.LinkedList;

import connection.ConnectionManager;
import connection.ConnectionObserver;
import robotica.Agent;
import robotica.AgentObserver;
import standard.BrickConnection;

public class Communicator implements AgentObserver, ConnectionObserver
{
	private AgentCollection col;
	private LinkedList<String> messages;
	
	public Communicator(AgentCollection col)
	{
		this.col = col;
		messages = new LinkedList<String>();
	}
	
	public void observe(Agent a)
	{
		a.registerObserver(this);
		messages.add("$NEW");
	}
	
	//Agent state changed
	@Override
	public void update(Agent a)
	{
		
	}

	
	//New Input
	@Override
	public void update()
	{
		BrickConnection bc = ConnectionManager.getInstance().getBrickConnection();
		while(!bc.isEmpty())
		{
			String s = bc.receiveData();
			
			
			
			
			
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
