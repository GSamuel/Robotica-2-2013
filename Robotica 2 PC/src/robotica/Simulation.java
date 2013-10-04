package robotica;

public class Simulation extends Loop
{

	private SimulationModel model;

	public Simulation(int tickTime)
	{
		super(tickTime);
		model = new SimulationModel();
	}

	public Simulation(SimulationModel model, int tickTime)
	{
		super(tickTime);
		this.model = model;
	}

	@Override
	public void loop()
	{
		for(Agent agent: model)
		{
			System.out.print(agent.getID()+" ");
		}
		System.out.println();
	}

}
