package agent;
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
		System.out.println(System.currentTimeMillis());
		/*
		if(init)
		{
			Motor.C.rotateTo(90);
		}
		*/
	}
}
