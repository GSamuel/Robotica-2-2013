package agent;
import standard.ConnectionLoop;
import standard.StopProgram;

public class MainCustomer
{
	public static void main(String args[])
	{
		new StopProgram().start();

		ConnectionLoop loop = new ConnectionLoop(10);
		
		loop.addAgent(new Customer());
		loop.start();
	}
}
