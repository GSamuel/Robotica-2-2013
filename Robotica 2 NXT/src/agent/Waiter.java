package agent;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
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
	
	public Waiter()
	{
		super("Waiter", new SimState("START"));
	}

	@Override
	public void update()
	{
		State state = this.currentState();

		switch(state.name())
		{
		case "START":
			Button.waitForAnyPress();
			this.setState(new SimState("FIND_PATH"));
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
	
	private void follow_path()
	{
		Motor.A.setSpeed(leftMotor); //linkermotor
		Motor.B.setSpeed(rightMotor); //rechtermotor
		Motor.A.forward();
		Motor.B.forward();
	}
	
	private void find_path()
	{
		if (motorStop < 300)
		{
			Motor.A.setSpeed(0);
			Motor.B.setSpeed(0);
			motorStop++;
		}
			
		else if (counterLeft == draaicirkel || counterRight == draaicirkel)
		{
				if (counterLeft == draaicirkel)
				{
					counterRight = 0;
					turnDirection = false;
				}
				else if (counterRight == draaicirkel)
				{
					counterLeft = 0;
					turnDirection = true;
				}
			n += 10;
			draaicirkel = n;
		}
		else if (counterLeft < draaicirkel && turnDirection)
		{
			Motor.A.setSpeed(turningSpeed); 
			Motor.B.setSpeed(turningSpeed);
			Motor.A.forward();
			Motor.B.backward();
			counterLeft++;
		}
		else if (counterRight < draaicirkel && !turnDirection )
		{
			Motor.A.setSpeed(turningSpeed); 
			Motor.B.setSpeed(turningSpeed);
			Motor.A.backward();
			Motor.B.forward();
			counterRight++;
		}
	}
}
