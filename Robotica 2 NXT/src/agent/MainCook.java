package agent;
import standard.AgentLoop;
import standard.ResetProgram;
import standard.StopProgram;
import connection.ConnectionManager;

public class MainCook
{
	public static void main(String args[])
	{
		new StopProgram().start();

		ConnectionManager conMan = ConnectionManager.getInstance();
		conMan.start();
		
		AgentCollection col = new AgentCollection();

		new ResetProgram(col).start();

		new ResetProgram(col).start();
		Communicator com = new Communicator(col);
				
		Cook cook = new Cook ();
		col.addAgent(cook);
		com.observe(cook);

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
		
		/*
		ConnectionLoop loop = new ConnectionLoop(10);
		loop.addAgent(new Cook());
		
		loop.connect();
		loop.start();
		*/
		
	}
}
