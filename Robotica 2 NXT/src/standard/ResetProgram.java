package standard;

import lejos.nxt.Button;
import agent.AgentCollection;

public class ResetProgram extends Thread
{
	private AgentCollection col;

	public ResetProgram(AgentCollection col)
	{
		this.col = col;
	}

	public void run()
	{

		int b;

		do
		{
			b = Button.waitForAnyPress();
			if(b == Button.ID_LEFT || b== Button.ID_RIGHT)
				col.reset();

		} while (true);

	}
}
