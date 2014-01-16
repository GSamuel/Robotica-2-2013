package agent;

import standard.Agent;
import standard.AgentLoop;
import standard.StopProgram;
import connection.ConnectionManager;

public class MainTestAgent
{
	public static void main(String args[])
	{
		new StopProgram().start();

		ConnectionManager conMan = ConnectionManager.getInstance();
		conMan.start();

		AgentCollection col = new AgentCollection();
		Communicator com = new Communicator(col);

		Agent agent = new TestAgent();
		col.addAgent(agent);
		com.observe(agent);

		AgentLoop loop = new AgentLoop(10, col);
		loop.start();

		while (!conMan.isConnected())
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				System.out.println("fu gaat mis hiero");
			}

		System.out.println("yay connected");

		conMan.getBrickConnection().registerObserver(com);

		com.start();
	}
}
