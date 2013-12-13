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

	private int customers[] = { 2, 0, 7 }; // blauw, rood, donkerblauw
	private int customerColor = 2;
	private int cookColor = 0;

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
		super("Waiter", new SimState("NEEM_BESTELLING_OP"));
		Button.waitForAnyPress();
	}

	@Override
	public void update()
	{
		State state = this.currentState();
		switch (state.name())
		{
		case "IDLE":
			// do nothing
			break;
		case "START":
			this.setState(new SimState("FOLLOW_PATH"));
			setChanged();
			break;
		case "NEEM_BESTELLING_OP":
			neemBestellingOp();
			break;
		case "AFREKENEN":
			// follow_path();
			break;
		case "BRENG_VOEDSEL":
			// follow_path();
			break;

		}

		notifyObservers();
	}

	private void neemBestellingOp()
	{
		if (color.getColorID() == customerColor)
		{

			Motor.A.suspendRegulation();
			Motor.B.suspendRegulation();
			
			try
			{
				Thread.sleep(2000);
			} catch (InterruptedException e)
			{
			}
			
		} else
		{
			follow_path();
		}
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
			 * Motor.A.setSpeed(leftMotor); // linkermotor
			 * Motor.B.setSpeed(rightMotor); // rechtermotor Motor.A.forward();
			 * Motor.B.forward();
			 */

		} else
		{
			/*
			 * for (int i = 0; i < 3; i++) { if (color.getColorID() ==
			 * customers[i]) { Motor.A.setSpeed(0); Motor.B.setSpeed(0);
			 * Motor.A.forward(); Motor.B.forward();
			 * 
			 * this.setState(new SimState("STOP")); setChanged(); }
			 * 
			 * }
			 */

			Motor.A.setSpeed(300);
			Motor.B.setSpeed(150);
			Motor.A.forward();
			Motor.B.forward();
			// this.setState(new SimState("FIND_PATH"));
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

// Breng Voedsel naar klant X.
// Bestelling opnemen.
// Afrekenen.
// Idle

