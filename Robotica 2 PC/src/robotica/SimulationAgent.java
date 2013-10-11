package robotica;

public class SimulationAgent extends Agent
{
	public SimulationAgent()
	{
		addState(new NoState(this));
	}
}
