package loop;

import java.util.ArrayList;

import robotica.Brick;
import robotica.SimulationModel;
import connection.ConnectionManager;
import connection.NXTConnection;

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
		//check for new devices to add to the simulation or refreshes the connection of the devices already known that lost connection at some point
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
		
		//make sure all bricks int the simulation are up to date.
		for(Brick brick : bricks)
		{
			brick.update();
		}
		
	}

	public SimulationModel simulationModel()
	{
		return model;
	}

}
