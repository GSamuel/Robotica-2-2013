package agent;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import robotica.Agent;
import robotica.SimState;
import standard.TouchSens;

public class Cook extends Agent
{
	private boolean init = false;
	private int count;
	private long start = System.currentTimeMillis();
	private TouchSens touch;
	private boolean ligtBal[] = { true, false, false, true };
	private final int position[] = { 10000, 6000, -10000, 0 };

	public Cook()
	{
		super("Cook", new SimState("RECYCLEVOEDSEL"));
		init();
		touch = new TouchSens(SensorPort.S1);
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

		case "MAAKVOEDSEL":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(720);
			Motor.C.setSpeed(720);
			if (!ligtBal[3])
			{
				for (int i = 0; i < 3; i++)
					if (ligtBal[i] && !ligtBal[3])
						verleg(i, true);
			}

			this.setState(new SimState("STOP"));
			setChanged();

			break;

		case "RECYCLEVOEDSEL":

			Motor.A.setSpeed(720);
			Motor.B.setSpeed(720);
			Motor.C.setSpeed(720);
			// System.out.println(ligtBal[0]+" "+ligtBal[1]+" "+ligtBal[2]+" "+ligtBal[3]);
			if (ligtBal[3])
			{
				for (int i = 0; i < 3; i++)
					if (!ligtBal[i] && ligtBal[3])
						verleg(i, false);
			}
			// System.out.println(ligtBal[0]+" "+ligtBal[1]+" "+ligtBal[2]+" "+ligtBal[3]);

			this.setState(new SimState("STOP"));
			setChanged();
			break;
		case "GRAB":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(720);
			Motor.C.setSpeed(720);

			verleg(1, false);

			/*
			 * Motor.A.rotateTo(-6700); Motor.B.rotateTo(100);
			 * Motor.A.rotateTo(0); Motor.C.rotateTo(-7000);
			 * Motor.A.rotateTo(-6700); Motor.B.rotateTo(0);
			 * Motor.A.rotateTo(0); Motor.C.rotateTo(0);
			 */
			this.setState(new SimState("STOP"));
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

	private void verleg(int pos, boolean naarAfhaal)
	{
		boolean succes = true;
		if (naarAfhaal)
		{
			Motor.C.rotateTo(position[pos]);
			succes = grab();
			if (succes)
			{
				Motor.C.rotateTo(position[3]);
				put();
				ligtBal[pos] = false;
				ligtBal[3] = true;
			}
		} else
		{
			Motor.C.rotateTo(position[3]);
			succes = grab();
			if (succes)
			{
				Motor.C.rotateTo(position[pos]);
				put();
				ligtBal[pos] = true;
				ligtBal[3] = false;
			}
		}
	}

	private boolean grab()
	{
		Motor.A.rotateTo(-6700);
		Motor.B.rotateTo(100);
		if (touch.isPressed() == 0)
		{
			Motor.B.rotateTo(0);
			return false;
		}
		Motor.A.rotateTo(0);
		return true;
	}

	private boolean put()
	{
		Motor.A.rotateTo(-6700);
		Motor.B.rotateTo(0);
		Motor.A.rotateTo(0);
		return true;
	}
}
