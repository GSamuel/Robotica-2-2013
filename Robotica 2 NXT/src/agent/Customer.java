package agent;

import java.util.Random;

import lejos.nxt.NXTRegulatedMotor;
import robotica.Agent;
import robotica.SimState;

public class Customer extends Agent {
	private String name;
	private boolean init;
	private long starttime;
	private long currenttime;
	private long stopwatch;
	private CustomerBehavior behavior;
	private int wachttijd;
	
	//Motor.A vervangen door motor, zodat een customer aan een specifieke motor gekoppeld kan worden.   Yay dynamischheid
	private NXTRegulatedMotor motor;

	public Customer(NXTRegulatedMotor motor, String name) {
		super(name, new SimState("IDLE"));
		this.wachttijd = 3;
		this.behavior = new CustomerBehavior(wachttijd);
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
		case "IDLE":
			idle();
			break;
		case "WBESTELLEN":
			wBestellen();
			break;
		case "WETEN":
			wEten();
			break;
		case "ETEN":
			eten();
			break;
		case "WBETALEN":
			wBetalen();
			break;
		}
		notifyObservers();
	}

	private void idle() {
			motor.setSpeed(720);
			motor.rotateTo(0);
			behavior.idle();
			this.setState(new SimState("WBESTELLEN"));
			setChanged();
	}

	private void wBestellen() {
			motor.setSpeed(720);
			motor.rotateTo(90);
	}

	private void wEten() {
			motor.setSpeed(720);
			motor.rotateTo(180);
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
 * -idle"IDLE" Niks boven. -Wachten op bestellen: "WBESTELLEN" Rood
 * boven. -Wachten op eten: "WETEN" Blauw boven. -Eten: "ETEN" Motor draait
 * rond. -Betalen: "WBETALEN" Zwart boven.
 */