
import lejos.nxt.Motor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.pathfinding.*;

//Behavior Programming jonguh


public class Pathfinder {

int ColorAreaGuest1;
int ColorAreaGuest2;
int ColorAreaGuest3;
int PathColor;
int FloorColor;
int CurrentColor;
int CounterLeft = 0;
int CounterRight = 0;
int RightMotor = 360;
int LeftMotor = 360;
int TurningSpeed = 360;
int Draaicirkel = 2;
boolean TurnLeft;
boolean TurnRight;
private TouchSensor touch;
private UltrasonicSensor sonar;


public void Recognize_Underground()
{
	if (CurrentColor == PathColor)
	{
		Path_Following();
	}
	else if(CurrentColor == ColorAreaGuest1 || 
			CurrentColor == ColorAreaGuest2 || 
			CurrentColor == ColorAreaGuest3)
	{
		taskAtGuest();
	}
	else if(CurrentColor == FloorColor)
	{
		find_Path();
	}
	else
	{
		// check again
	}
}


public void taskAtGuest()
{
	if (sonar.getDistance() > 40)
	{
	Motor.A.setSpeed(TurningSpeed); 
	Motor.B.setSpeed(TurningSpeed);
	Motor.A.forward();
	Motor.B.backward();
	}
	else
	{
		Motor.A.setSpeed(0);
		Motor.B.setSpeed(0);
	}
}

public void Path_Following()
{
	while(CurrentColor == PathColor)
	Motor.A.setSpeed(LeftMotor); //linkermotor
	Motor.B.setSpeed(RightMotor); //rechtermotor
	Motor.A.forward();
	Motor.B.forward();
	try {
		Thread.sleep(200);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public boolean TurnLeft()
{
	return CounterLeft < Draaicirkel;
}

public boolean TurnRight()
{
    return CounterRight < Draaicirkel;
}

public void find_Path()
{
		while (TurnLeft())
		{
			if(CurrentColor == FloorColor)
			{
			Motor.A.setSpeed(TurningSpeed); 
			Motor.B.setSpeed(TurningSpeed);
			Motor.A.forward();
			Motor.B.backward();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CounterLeft++;
			}
			else if (CurrentColor == PathColor)
			{
				RightMotor += 30;
				LeftMotor -= 30;
			}
			else
			{
				// check again
			}
		}
		Draaicirkel += 2;
		while (TurnRight())
		{
			if (CurrentColor == FloorColor)
			{
				Motor.A.setSpeed(TurningSpeed); 
				Motor.B.setSpeed(TurningSpeed);
				Motor.A.backward();
				Motor.B.forward();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				CounterRight++;
			}
			else if (CurrentColor == PathColor)
			{
				LeftMotor += 30;
				RightMotor -= 30;
			}
			else
			{
				// check again
			}
		}
		Draaicirkel += 2;
		CounterLeft = 0;
		CounterRight = 0;

}


public static void main(String args[])
		{
		  
		}
}
