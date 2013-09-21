import robotica.Loop;
import robotica.TestLoop;

/*
 * Main voor de NXT. Koppel eerst de NXT brick aan via USB en run als Lejos application. 
 */

public class MainNXT {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		new StopProgram().start();
		
		Loop loop = new TestLoop(1000);
		loop.start();

		
		/*
		System.out.println("press button");
		Button.waitForAnyPress();
		
		
		
		Motor.A.forward();
		Motor.A.setSpeed(1000);
		Button.waitForAnyPress();
		Motor.A.backward();
		Motor.A.setSpeed(1000);
		Button.waitForAnyPress();
		*/
		
		/*
		TouchSensor touch1, touch2;
		touch1 = new TouchSensor(SensorPort.S1);
		touch2 = new TouchSensor(SensorPort.S2);
		
		while(true)
		{
			LCD.clear();
			LCD.drawString(""+touch1.isPressed(), 0,0);
			LCD.drawString(""+touch2.isPressed(), 0,1);
			Thread.sleep(100);
		}*/
		

		
	}
	
}
