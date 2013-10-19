import robotica.Simulation;
import robotica.SimulationModel;

public class MainPC
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Simulation sim = new Simulation(new SimulationModel(), 1000);
		sim.start();
	}

}