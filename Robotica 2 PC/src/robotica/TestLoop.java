package robotica;

public class TestLoop extends Loop
{

	public TestLoop(int tickTime)
	{
		super(tickTime);
	}

	@Override
	public void loop()
	{
		System.out.println(getTotalTicks());
		if (getTotalTicks() >= 10)
		{
			System.out.println("opnieuw");
			reset();
		}
	}

}
