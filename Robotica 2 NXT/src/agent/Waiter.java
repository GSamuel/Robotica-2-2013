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
	int CounterLeft = 0;
	int CounterRight = 0;
	int RightMotor = 720;
	int LeftMotor = 720;
	int TurningSpeed = 360;
	int Draaicirkel = 2;
	boolean TurnLeft;
	boolean TurnRight;
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
			this.setState(new SimState("STATE_YAY"));
			setChanged();
			break;
		case "STATE_YAY":
			follow_path();
			break;
		
		}
		
		notifyObservers();
	}
	
	private void follow_path()
	{
		Motor.A.setSpeed(LeftMotor); //linkermotor
		Motor.B.setSpeed(RightMotor); //rechtermotor
		Motor.A.forward();
		Motor.B.forward();
	}
}
