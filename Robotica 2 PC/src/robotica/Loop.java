package robotica;

public abstract class Loop implements Runnable
{
	private int tickTime, timePassed;
	private int totalTicks;

	public Loop(int tickTime)
	{
		this.tickTime = tickTime;
	}

	public void reset()
	{
		totalTicks = 0;
		timePassed = 0;
	}

	public int getTotalTicks()
	{
		return totalTicks;
	}

	public void start()
	{
		new Thread(this).start();
	}

	@Override
	public void run()
	{
		long oldTime = System.currentTimeMillis(), newTime;
		while (true)
		{
			newTime = System.currentTimeMillis();
			timePassed += newTime - oldTime;
			oldTime = newTime;
			if (timePassed >= tickTime)
			{
				timePassed -= tickTime;
				totalTicks++;
				loop();
			}
		}
	}

	public abstract void loop();

}
