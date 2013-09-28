import lejos.nxt.Button;

/*
 * Waits For the Enter Button to be pressed and then Stops the program running on te NXT
 */
public class StopProgram extends Thread
{
	public void run()
	{
		int b;

		do
		{
			b = Button.waitForAnyPress();
		} while (b != Button.ID_ENTER);

		System.exit(0);
	}
}
