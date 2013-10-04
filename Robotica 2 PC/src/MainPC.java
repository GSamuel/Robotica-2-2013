import robotica.ConnectionManager;
import robotica.NoAgent;
import robotica.Simulation;
import robotica.SimulationModel;

public class MainPC
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		SimulationModel model = new SimulationModel();
		model.addAgent(new NoAgent().setLocalID(42));
		model.addAgent(new NoAgent().setLocalID(27));
		model.addAgent(new NoAgent().setLocalID(93));
		
		Simulation sim = new Simulation(model,100);
		sim.start();
		
		new ConnectionManager().start();
			
		

		
		
		
	}

}
