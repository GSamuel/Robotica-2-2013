import loop.Simulation;
import robotica.SimulationModel;
import visuals.FancyController;
import visuals.FancyFrame;
import visuals.FancyModel;

public class MainPC
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Simulation sim = new Simulation(new SimulationModel(), 10);
		sim.start();
		
		FancyModel model = new FancyModel(sim);
		FancyController controller = new FancyController(model);
		

		FancyFrame frame = new FancyFrame(model);
		frame.setDefaultCloseOperation(FancyFrame.EXIT_ON_CLOSE);
		
		frame.pack();
		
		frame.setVisible(true);
	}

}