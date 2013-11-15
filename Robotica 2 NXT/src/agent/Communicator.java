package agent;

import java.util.LinkedList;

import robotica.Agent;
import robotica.AgentObserver;

public class Communicator implements AgentObserver
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
	
	@Override
	public void update(Agent a)
	{
		
	}

}
