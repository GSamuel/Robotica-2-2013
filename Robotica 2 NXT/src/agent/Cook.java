package agent;

import lejos.nxt.Motor;
import robotica.Agent;
import robotica.SimState;

public class Cook extends Agent
{
	private boolean init = false;
	private int count;
	private long start = System.currentTimeMillis();
	private boolean ligtBal[] = {true, true, true, false};
	private final int position[] = {10000, 6000,-10000,0};

	public Cook()
	{
		super("Cook", new SimState("IDLE"));
		init();
		count = 0;
		Motor.A.resetTachoCount();
		Motor.B.resetTachoCount();
		Motor.C.resetTachoCount();
	}

	private void init()
	{
		Motor.B.setSpeed(720);
		Motor.B.rotateTo(-360);
		Motor.B.resetTachoCount();
		init = true;
	}

	@Override
	public void update()
	{
		switch (currentState().name())
		{
		case "IDLE":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(720);
			Motor.C.setSpeed(720);
			Motor.A.rotateTo(0);
			Motor.B.rotateTo(0);
			Motor.C.rotateTo(0);
			this.setState(new SimState("GRAB"));
			setChanged();
			break;
		case "GRAB":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(720);
			Motor.C.setSpeed(720);
			
			Motor.C.rotateTo(position[1]);
			
			/*
			Motor.A.rotateTo(-6700);
			Motor.B.rotateTo(100);
			Motor.A.rotateTo(0);
			Motor.C.rotateTo(-7000);
			Motor.A.rotateTo(-6700);
			Motor.B.rotateTo(0);
			Motor.A.rotateTo(0);
			Motor.C.rotateTo(0);*/
			//this.setState(new SimState("STOP"));
			setChanged();
			break;
		case "STOP":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(720);
			Motor.C.setSpeed(720);
			Motor.A.rotateTo(0);
			Motor.B.rotateTo(0);
			Motor.C.rotateTo(0);
			this.setState(new SimState("FINISHED"));
			setChanged();
			break;
		case "FINISHED":

			Motor.A.suspendRegulation();
			Motor.B.suspendRegulation();
			Motor.C.suspendRegulation();
			break;

		}
		/*
		 * long timePassed = System.currentTimeMillis() - start; if
		 * (this.currentState().name().equals("TEST")) { Motor.A.setSpeed(720);
		 * Motor.A.rotateTo(90); if (Motor.A.getTachoCount() >= 85 &&
		 * Motor.A.getTachoCount() <= 95 && timePassed >= 20000) {
		 * this.setState(new SimState("BLUB")); setChanged(); } } if
		 * (this.currentState().name().equals("BLUB")) { Motor.A.setSpeed(720);
		 * Motor.A.rotateTo(360); }
		 */

		notifyObservers();
	}
	
	private void grab()
	{
		Motor.A.rotateTo(-6700);
		Motor.B.rotateTo(100);
		Motor.A.rotateTo(0);
	}
}
