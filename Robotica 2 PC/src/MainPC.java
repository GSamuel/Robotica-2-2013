import robotica.Loop;
import robotica.TestLoop;

public class MainPC
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Loop loop = new TestLoop(1000);
		loop.start();
	}

}
