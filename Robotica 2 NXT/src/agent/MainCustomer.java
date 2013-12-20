package agent;
import lejos.nxt.Motor;
import standard.AgentLoop;
import standard.StopProgram;
import connection.ConnectionManager;

public class MainCustomer
{
	public static void main(String args[])
	{
		new StopProgram().start();

		ConnectionManager conMan = ConnectionManager.getInstance();
		conMan.start();
		
		AgentCollection col = new AgentCollection();
		Communicator com = new Communicator(col);
	
		
		Customer cus= new Customer (Motor.A);
		col.addAgent(cus);
		com.observe(cus);
		
		
		cus= new Customer (Motor.B);
		col.addAgent(cus);
		com.observe(cus);
		
		cus= new Customer (Motor.C);
		col.addAgent(cus);
		com.observe(cus);

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
		
	}
}
