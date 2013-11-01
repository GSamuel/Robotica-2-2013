import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class Yay
{

	public static void main(String[] args)
	{
		final int SPEED = 1000;// 750

		new StopProgram().start();

		NXTRegulatedMotor mA = new NXTRegulatedMotor(MotorPort.A);
		NXTRegulatedMotor mC = new NXTRegulatedMotor(MotorPort.C);

		while (true)
		{
			Button.waitForAnyPress();
			// TESTETSTETSETSET
			mA.setSpeed(SPEED);
			mA.forward();
			mC.setSpeed(SPEED);
			mC.forward();
			Button.waitForAnyPress();
			mA.suspendRegulation();
			mC.suspendRegulation();
		}

	}

}
