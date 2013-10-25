import robotica.Agent;
import robotica.SimState;


public class Customer extends Agent
{
	public Customer()
	{
		super("Customer", new SimState("Eating"));
	}

	@Override
	public void update()
	{
		this.currentState();
	}
}
