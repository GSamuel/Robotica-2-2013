package agent;
import lejos.nxt.Motor;
import robotica.Agent;
import robotica.SimState;




public class Cook extends Agent
{
	private boolean init;
	public Cook()
	{
		super("Cook", new SimState("Cooking"));
		init = true;
	}

	@Override
	public void update()
	{
		if(init)
		{
			Motor.C.rotateTo(90);
		}
	}
}
