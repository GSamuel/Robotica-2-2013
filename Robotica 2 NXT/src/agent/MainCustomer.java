package agent;
import lejos.nxt.Motor;
import standard.AgentLoop;
import standard.ResetProgram;
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
		
		new ResetProgram(col).start();
		
		Communicator com = new Communicator(col);
	
		
		Customer cus= new Customer (Motor.A, "Klant 1");
		col.addAgent(cus);
		com.observe(cus);
		
		
		cus= new Customer (Motor.B, "Klant 2");
		col.addAgent(cus);
		com.observe(cus);
		
		cus= new Customer (Motor.C, "Klant 3");
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
		
		col.forceChanged();
		
	}
}
