import java.util.Vector;

import robotica.Agent;
import robotica.Loop;

public class UpdateLoop extends Loop
{
	private Vector<Agent> agents;

	public UpdateLoop(int tickTime, Vector<Agent> agents)
	{
		super(tickTime);
		this.agents = agents;
	}

	@Override
	public void loop()
	{
		for(int i = 0; i < agents.size(); i++)
			agents.elementAt(i).update();

	}

}
