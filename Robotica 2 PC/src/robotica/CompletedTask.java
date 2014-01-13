package robotica;

public class CompletedTask
{

	private String task, target;

	public CompletedTask(String task, String target)
	{
		this.task = task;
		this.target = target;
	}

	public String getTask()
	{
		return task;
	}

	public String getTarget()
	{
		return target;
	}
}
