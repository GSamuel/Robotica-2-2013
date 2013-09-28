package robotica;

public class Task
{
	private int time;

	public Task()
	{
		time = 10;
	}

	public int getTime()
	{
		return time;
	}

	public void decreaseTime()
	{
		time--;
	}
}
