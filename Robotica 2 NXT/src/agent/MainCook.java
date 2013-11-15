package agent;
import standard.AgentLoop;
import standard.StopProgram;
import connection.ConnectionManager;

public class MainCook
{
	public static void main(String args[])
	{
		new StopProgram().start();

		ConnectionManager.getInstance().start();
		
		AgentCollection col = new AgentCollection();
		Communicator com = new Communicator(col);
	
		Cook cook = new Cook ();
		com.observe(cook);
		col.addAgent(cook);
		
		AgentLoop loop = new AgentLoop(10, col);
		loop.start();
		
		/*
		ConnectionLoop loop = new ConnectionLoop(10);
		loop.addAgent(new Cook());
		
		loop.connect();
		loop.start();
		*/
		
	}
}
