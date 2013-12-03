package agent;

import lejos.nxt.Motor;
import robotica.Agent;
import robotica.SimState;

public class Cook extends Agent
{
	private boolean init;

	public Cook()
	{
		super("Cook", new SimState("TEST"));
		init = true;
		Motor.A.resetTachoCount();
	}

	@Override
	public void update()
	{
		if (this.currentState().name().equals("TEST"))
		{
			Motor.A.setSpeed(720);
			Motor.A.rotateTo(90);
			if (Motor.A.getTachoCount() >= 85 && Motor.A.getTachoCount() <= 95)
			{
				this.setState(new SimState("BLUB"));
				setChanged();
			}
		}
		if (this.currentState().name().equals("BLUB"))
		{
			Motor.A.setSpeed(720);
			Motor.A.rotateTo(360);
		}

		notifyObservers();
	}
}
