package agent;

import java.util.Random;

import robotica.Agent;
import robotica.SimState;

public class CustomerBehavior {

	private String karakter;
	private int wachttijd;

	public CustomerBehavior(int wachttijd) {
		this.wachttijd = wachttijd;
	}

	public int  idle() {
			return 1000 * randInt(2 * wachttijd, 4 * wachttijd);
	}



	public int eten() {
		return 25000;
			//return 1000 * randInt(20 * wachttijd, 40 * wachttijd);
	}


	private int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}


}
