public class MainCook
{
	public static void main(String args[])
	{
		new StopProgram().start();

		NXTLoop loop = new NXTLoop(10);
		
		loop.addAgent(new Cook());
		loop.start();
	}
}
