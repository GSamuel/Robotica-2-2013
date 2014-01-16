package loop;

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
			try
			{
				Thread.sleep(5);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public abstract void loop();

}
