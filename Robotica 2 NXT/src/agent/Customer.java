package agent;

import lejos.nxt.Motor;
import robotica.Agent;
import robotica.SimState;

public class Customer extends Agent {
	private boolean init;
	private long starttime;
	private long currenttime;
	private long stopwatch;

	public Customer() {
		super("Customer", new SimState("WBESTELLEN"));
		init = true;
		starttime = System.currentTimeMillis();
		currenttime = System.currentTimeMillis();
		stopwatch = 0;
		this.setState(new SimState("NEUTRAAL"));
		Motor.A.resetTachoCount();
	}

	@Override
	public void update() {
		currenttime = System.currentTimeMillis();
		stopwatch = currenttime - starttime;
		System.out.println(stopwatch);
		switch (currentState().name()) {
		case "NEUTRAAL":
			neutraal();
			// this.setState(new SimState("BLUB"));
			// setChanged();
			break;
		case "WBESTELLEN":
			wBestellen();
			break;
		case "WETEN":
			wBestellen();
			break;
		case "ETEN":
			wBetalen();
			break;
		case "WBETALEN":
			wBestellen();
			break;
		}
		notifyObservers();
	}

	private void neutraal() {
		if (this.currentState().name().equals("WBESTELLEN")) {
			Motor.A.setSpeed(720);
			Motor.A.rotateTo(0);
			if (Motor.A.getTachoCount() >= 85 && Motor.A.getTachoCount() <= 95) {
			}
		}

	}

	private void wBestellen() {
		if (this.currentState().name().equals("WBESTELLEN")) {
			Motor.A.setSpeed(720);
			Motor.A.rotateTo(90);
			this.setState(new SimState("WETEN"));
			setChanged();
		}
	}

	private void wEten() {
		if (this.currentState().name().equals("WBESTELLEN")) {
			Motor.A.setSpeed(720);
			Motor.A.rotateTo(90);
			if (Motor.A.getTachoCount() >= 85 && Motor.A.getTachoCount() <= 95) {
				this.setState(new SimState("BLUB"));
				setChanged();
			}
		}

	}

	private void eten() {
		if (this.currentState().name().equals("WBESTELLEN")) {
			Motor.A.setSpeed(720);
			Motor.A.rotateTo(90);
			if (Motor.A.getTachoCount() >= 85 && Motor.A.getTachoCount() <= 95) {
				this.setState(new SimState("BLUB"));
				setChanged();
			}
		}

	}

	private void wBetalen() {
		if (this.currentState().name().equals("WBESTELLEN")) {
			Motor.A.setSpeed(720);
			Motor.A.rotateTo(90);
			if (Motor.A.getTachoCount() >= 85 && Motor.A.getTachoCount() <= 95) {
				this.setState(new SimState("BLUB"));
				setChanged();
			}
		}

	}

}

/*
 * 5 states:
 * 
 * -Neutraal "NEUTRAAL" Niks boven. -Wachten op bestellen: "WBESTELLEN" Rood
 * boven. -Wachten op eten: "WETEN" Blauw boven. -Eten: "ETEN" Motor draait
 * rond. -Betalen: "WBETALEN" Zwart boven.
 */