package agent;

import lejos.nxt.NXTRegulatedMotor;
import robotica.SimState;
import standard.Agent;

public class Customer extends Agent
{
	private long previousTime;
	private long currentTime;
	private long timeToWait = 0;
	private CustomerBehavior behavior;
	private boolean rotating = false;

	private NXTRegulatedMotor motor;

	public Customer(NXTRegulatedMotor motor, String name)
	{
		super(name, new SimState("IDLE"));
		this.behavior = new CustomerBehavior(4);
		resetTime();

		this.motor = motor;
		motor.resetTachoCount();
	}

	public void reset()
	{
		resetTime();
		resetRotation();
		this.setState(new SimState("IDLE"));
		this.setChanged();
	}

	@Override
	public void update()
	{
		switch (currentState().name())
		{
		case "IDLE":
			idle();
			break;
		case "WBESTELLEN":
			wBestellen();
			break;
		case "WETEN":
			wEten();
			break;
		case "ETEN":
			eten();
			break;
		case "WBETALEN":
			wBetalen();
			break;
		}

		this.updateState();
		notifyObservers();
	}

	private long timePast()
	{
		currentTime = System.currentTimeMillis();
		long timePast = currentTime - previousTime;
		previousTime = currentTime;

		return timePast;
	}

	private boolean resetTimeToWait()
	{
		if (timeToWait <= 0)
		{
			timeToWait = 0;
			return true;
		} else
			return false;
	}

	private void resetTime()
	{
		currentTime = System.currentTimeMillis();
		previousTime = currentTime;
		timeToWait = 0;
	}

	private void idle()
	{
		resetRotation();
		if (resetTimeToWait())
			timeToWait = behavior.idle();

		timeToWait -= timePast();

		System.out.println("IDLE");
		motor.setSpeed(720);
		motor.rotateTo(0);
		if (timeToWait < 0)
		{
			this.setState(new SimState("WBESTELLEN"));
			setChanged();
			resetTime();
		}
	}

	private void wBestellen()
	{
		resetRotation();
		motor.setSpeed(720);
		motor.rotateTo(90);
		resetTime();
	}

	private void wEten()
	{
		resetRotation();
		motor.setSpeed(720);
		motor.rotateTo(180);

		resetTime();
	}

	private void eten()
	{
		if (resetTimeToWait())
			timeToWait = behavior.eten();

		timeToWait -= timePast();

		System.out.println("ETEN");

		if (timeToWait < 0)
		{
			this.setState(new SimState("WBETALEN"));
			setChanged();
			resetTime();
		}
		rotating = true;
		motor.setSpeed(300);
		motor.forward();
	}

	private void wBetalen()
	{
		resetRotation();
		motor.setSpeed(720);
		motor.setSpeed(720);
		motor.rotateTo(270);
	}

	private void resetRotation()
	{
		if (rotating)
		{
			int lol = motor.getTachoCount() / 360;
			motor.rotateTo(lol * 360);
			motor.resetTachoCount();
		}
	}

	public void processCompletedTask(String task)
	{
		System.out.println(task);
		switch (task)
		{
		case "BESTELLING_OPGENOMEN":
			if (this.currentState().name().equals("WBESTELLEN"))
			{
				this.setState(new SimState("WETEN"));
				this.setChanged();
			}
			break;
		case "ETEN_BEZORGD":
			if (this.currentState().name().equals("WETEN"))
			{
				this.setState(new SimState("ETEN"));
				this.setChanged();
			}
			break;
		}
	}
}

/*
 * 5 states:
 * 
 * -idle"IDLE" Niks boven. -Wachten op bestellen: "WBESTELLEN" Rood boven.
 * -Wachten op eten: "WETEN" Blauw boven. -Eten: "ETEN" Motor draait rond.
 * -Betalen: "WBETALEN" Zwart boven.
 */