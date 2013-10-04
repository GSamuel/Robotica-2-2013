import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;

public class Yay
{

	public static void main(String[] args)
	{
		final int SPEED = 300;//750
		
		new StopProgram().start();
		
		NXTRegulatedMotor mA = new NXTRegulatedMotor(MotorPort.A);
		NXTRegulatedMotor mC = new NXTRegulatedMotor(MotorPort.C);
		
		
		//TESTETSTETSETSET
		mA.setSpeed(SPEED);
		mA.forward();
		mC.setSpeed(SPEED);
		mC.backward();

	}

}
