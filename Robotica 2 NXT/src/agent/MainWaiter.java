package agent;
import standard.AgentLoop;
import standard.StopProgram;
import connection.ConnectionManager;

public class MainWaiter
{
	public static void main(String args[])
	{
		new StopProgram().start();

		ConnectionManager conMan = ConnectionManager.getInstance();
		conMan.start();
		
		AgentCollection col = new AgentCollection();
		Communicator com = new Communicator(col);
	
		Waiter waiter = new Waiter();
		col.addAgent(waiter);
		com.observe(waiter);

		AgentLoop loop = new AgentLoop(10, col);
		loop.start();
		
		while(!conMan.isConnected())
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
		col.forceChanged();
	}
}
