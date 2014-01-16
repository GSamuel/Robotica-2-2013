package standard;

import loop.Loop;
import agent.AgentCollection;

public class AgentLoop extends Loop
{
	private AgentCollection col;

	public AgentLoop(int tickTime, AgentCollection col)
	{
		super(tickTime);
		this.col = col;
	}
	

	public void loop()
	{
		col.update();
		col.processData();
	}

}
