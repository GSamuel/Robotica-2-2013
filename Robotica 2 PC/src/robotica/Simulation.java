package robotica;

import java.util.ArrayList;

public class Simulation extends Loop
{

	private SimulationModel model;
	private ConnectionManager conMan;
	private ArrayList<Brick> bricks;

	public Simulation(int tickTime)
	{
		super(tickTime);
		model = new SimulationModel();
		init();
	}

	public Simulation(SimulationModel model, int tickTime)
	{
		super(tickTime);
		this.model = model;
		init();
	}

	private void init()
	{
		bricks = new ArrayList<Brick>();
		conMan = ConnectionManager.getInstance().start();
	}

	@Override
	public void loop()
	{
		for (NXTConnection con : conMan)
		{
			boolean brickWithCon = false;
			for (Brick brick : bricks)
			{
				if (brick.isSameCon(con))
				{
					brick.refreshConnection(con);
					brickWithCon = true;
				}
			}
			if(!brickWithCon)
				bricks.add(new Brick(con, this));
		}
		
		System.out.println("amount of bricks: "+ bricks.size());
	}

}
