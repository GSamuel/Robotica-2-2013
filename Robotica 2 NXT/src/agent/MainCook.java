package agent;
import standard.StopProgram;
import connection.ConnectionManager;

public class MainCook
{
	public static void main(String args[])
	{
		new StopProgram().start();

		ConnectionManager.getInstance().start();
		
		/*
		ConnectionLoop loop = new ConnectionLoop(10);
		loop.addAgent(new Cook());
		
		loop.connect();
		loop.start();
		*/
		
	}
}
