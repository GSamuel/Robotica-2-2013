package agent;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;
import robotica.Agent;
import robotica.SimState;
import robotica.State;

public class Waiter extends Agent
{
	int counterLeft = 0;
	int counterRight = 0;
	int rightMotor = 300;
	int leftMotor = 300;
	int turningSpeed = 400;
	int n = 10;
	int draaicirkel = 5;
	int wait = 0;
	int motorStop = 15;
	boolean turnDirection = true; // left if true, right if false
	private TouchSensor touch;
	private UltrasonicSensor sonar;
	ColorHTSensor color = new ColorHTSensor(SensorPort.S1);

	public Waiter()
	{
		super("Waiter", new SimState("START"));
	}

	@Override
	public void update()
	{
		State state = this.currentState();
		switch (state.name())
		{
		case "START":
			Button.waitForAnyPress();
			this.setState(new SimState("FOLLOW_PATH"));
			setChanged();
			break;
		case "FOLLOW_PATH":
			follow_path();
			break;
		case "FIND_PATH":
			find_path();
			break;
		}

		notifyObservers();
	}

	private boolean onPath()
	{
		if (color.getColorID() == 3)
			return true;
		else
		{
			return false;
		}
	}

	private void follow_path()
	{

		if (onPath())
		{
			Motor.A.setSpeed(150);
			Motor.B.setSpeed(300);
			Motor.A.forward();
			Motor.B.forward();
			/*
			Motor.A.setSpeed(leftMotor); // linkermotor
			Motor.B.setSpeed(rightMotor); // rechtermotor
			Motor.A.forward();
			Motor.B.forward();*/
		} else
		{

			Motor.A.setSpeed(300);
			Motor.B.setSpeed(150);
			Motor.A.forward();
			Motor.B.forward();
			//this.setState(new SimState("FIND_PATH"));
		}

	}

	private void find_path()
	{

		if (onPath())
		{
			counterLeft = 0;
			counterRight = 0;
			draaicirkel = 5;
			this.setState(new SimState("FOLLOW_PATH"));
		} else
		{
			if (counterLeft > draaicirkel || counterRight > draaicirkel)
			{
				counterLeft = 0;
				counterRight = 0;
			} else if (counterLeft == draaicirkel
					|| counterRight == draaicirkel)
			{
				if (counterLeft == draaicirkel)
				{
					counterRight = 0;
					turnDirection = false;
				} else if (counterRight == draaicirkel)
				{
					counterLeft = 0;
					turnDirection = true;
				}
				n += 10;
				draaicirkel = n;
			} else if (counterLeft < draaicirkel && turnDirection)
			{
				Motor.A.setSpeed(turningSpeed);
				Motor.B.setSpeed(turningSpeed);
				Motor.A.forward();
				Motor.B.backward();
				counterLeft++;
			} else if (counterRight < draaicirkel && !turnDirection)
			{
				Motor.A.setSpeed(turningSpeed);
				Motor.B.setSpeed(turningSpeed);
				Motor.A.backward();
				Motor.B.forward();
				counterRight++;
			}
		}
	}

}
