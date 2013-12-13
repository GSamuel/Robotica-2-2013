package agent;

import java.util.Random;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import robotica.Agent;
import robotica.SimState;

public class Customer extends Agent {
	private boolean init;
	private long starttime;
	private long currenttime;
	private long stopwatch;
	
	//Motor.A vervangen door motor, zodat een customer aan een specifieke motor gekoppeld kan worden.   Yay dynamischheid
	private NXTRegulatedMotor motor;

	public Customer(NXTRegulatedMotor motor) {
		super("Customer", new SimState("NEUTRAAL"));
		init = true;
		starttime = System.currentTimeMillis();
		currenttime = System.currentTimeMillis();
		stopwatch = 0;
		//this.setState(new SimState("NEUTRAAL"));
		//setChanged();
		
		this.motor = motor;
		motor.resetTachoCount();
	}

	@Override
	public void update() {
		currenttime = System.currentTimeMillis();
		stopwatch = currenttime - starttime;
		//System.out.println(stopwatch);
		switch (currentState().name()) {
		case "NEUTRAAL":
			neutraal();
			 this.setState(new SimState("WBESTELLEN"));
			 setChanged();
			break;
		case "WBESTELLEN":
			wBestellen();			
			this.setState(new SimState("WETEN"));
			setChanged();
			break;
		case "WETEN":
			wEten();
			this.setState(new SimState("ETEN"));
			setChanged();
			break;
		case "ETEN":
			eten();
			this.setState(new SimState("WBETALEN"));
			setChanged();
			break;
		case "WBETALEN":
			wBetalen();
			this.setState(new SimState("NEUTRAAL"));
			setChanged();
			break;
		}
		notifyObservers();
	}

	private void neutraal() {
			motor.setSpeed(720);
			motor.rotateTo(0);
			System.out.println("NEUTRAAL");
			Random rand= new Random();
			try {
				Thread.sleep(rand.nextInt(2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void wBestellen() {
			motor.setSpeed(720);
			motor.rotateTo(90);
			System.out.println("wBestellen");
			Random rand= new Random();
			try {
				Thread.sleep(rand.nextInt(2000)+1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void wEten() {
			motor.setSpeed(720);
			motor.rotateTo(180);
			System.out.println("wEten");
			Random rand= new Random();
			try {
				Thread.sleep(rand.nextInt(2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void eten() {
			motor.setSpeed(300);
			motor.forward();
			System.out.println("ETEN");
			Random rand= new Random();
			try {
				Thread.sleep(rand.nextInt(5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	private void wBetalen() {
			motor.setSpeed(720);
			int lol= motor.getTachoCount()/360;
			motor.rotateTo(lol*360);
			motor.resetTachoCount();
			motor.setSpeed(720);
			motor.rotateTo(270);
			System.out.println("wBetalen");
			Random rand= new Random();
			try {
				Thread.sleep(rand.nextInt(2000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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