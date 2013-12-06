package visuals;

public class FancyController implements Runnable
{
	private FancyModel model;

	public FancyController(FancyModel model)
	{
		this.model = model;
	}

	public void start()
	{
		new Thread(this).start();
		;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(100);
		} catch (InterruptedException e)
		{
			System.out.println("geen zin om te slapen");
		}
	}

}
