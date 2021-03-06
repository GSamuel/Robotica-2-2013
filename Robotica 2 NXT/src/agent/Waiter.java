package agent;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import standard.Agent;
import robotica.CompletedTask;
import robotica.CoupledState;
import robotica.SimState;
import robotica.State;
import standard.ColorHistory;

public class Waiter extends Agent
{
	private TouchSensor touch;
	private UltrasonicSensor sonar;

	private int currentStep = 0;

	private int first;
	private int richting;

	private int currentCustomer;
	private boolean hasBall;

	private boolean turn = false;
	private boolean moveBack = true;
	private boolean cookHasFood = false;
	private boolean cookIsPreparingFood = false;

	private final int[] custColor = { 1, 2, 3 };
	private final int cookColor = 4;
	private final int fieldColor = 6;
	private final int pathInner = 7, pathOuter = 0;

	ColorHistory color = new ColorHistory(SensorPort.S1);

	public Waiter()
	{
		super("OBER", new SimState("IDLE"));
		this.addCoupledState(new CoupledState("IDLE", "WBESTELLEN",
				"OPNEMEN_BESTELLING"));
		this.addCoupledState(new CoupledState("IDLE", "WETEN",
				"BRENG_VOEDSEL_NAAR_KLANT"));
		this.addCoupledState(new CoupledState("IDLE", "WBETALEN",
				"BRENG_REKENING"));

		// this.setState(new SimState("OPNEMEN_BESTELLING", "KLANT 1"));
		// this.setChanged();

		/*
		 * this.setState(new SimState("BRENG_VOEDSEL_NAAR_KLANT", "KLANT 1"));
		 * this.setChanged();
		 */

		color.start();

		init();
	}

	private void init()
	{
		first = -1;
		richting = -1;
		currentCustomer = -1;
		turn = false;
		moveBack = false;
		hasBall = false;

		currentStep = 0;

		Motor.C.setSpeed(60);
		Motor.C.rotateTo(-180);
		Motor.C.resetTachoCount();

		closeGrabber();
	}

	public void reset()
	{
		first = -1;
		richting = -1;
		currentCustomer = -1;
		currentStep = 0;

		turn = false;
		moveBack = false;
		hasBall = false;

		Motor.A.suspendRegulation();
		Motor.B.suspendRegulation();
		openGrabber();
		// Motor.C.suspendRegulation();
	}

	@Override
	public void update()
	{
		State state = this.currentState();

		if (richting == -1 && !state.name().equals("IDLE"))
			checkRichting();

		boolean stateRequest = false;

		switch (state.name())
		{
		case "IDLE":
			reset();

			this.setRequestNewState(true);
			this.setChanged();
			stateRequest = true;
			break;
		case "OPNEMEN_BESTELLING":
			if (cookHasFood)
			{
				this.setState(new SimState("IDLE"));
			} else if (currentStep == 0)
				zoekKlant();
			else if (currentStep == 1)
			{
				// openGrabber();
				currentStep++;
			} else if (currentStep == 2)
				rijNaarKlant();
			else if (currentStep == 3)
			{
				this.addCompletedTask(new CompletedTask("BESTELLING_OPGENOMEN",
						currentState().target()));
				this.setChanged();
				moveBack = true;
				currentStep++;
			} else if (currentStep == 4)
				rijNaarBaan(true);
			else if (currentStep == 5)
				zoekKok();
			else if (currentStep == 6)
				rijNaarKok();
			else if (currentStep == 7)
			{
				openGrabber();
				moveBack = true;
				currentStep++;

				this.addCompletedTask(new CompletedTask(
						"BESTELLING_AFGELEVERD", "KOK"));
				this.setChanged();
			} else if (currentStep == 8)
			{
				rijNaarBaan(false);
			} else if (currentStep == 9)
			{
				reset();
				this.setState(new SimState("IDLE"));
				this.setChanged();
			}
			break;

		case "BRENG_VOEDSEL_NAAR_KLANT":

			if (!cookHasFood && !hasBall)
			{
				this.setState(new SimState("IDLE"));
			} else
			if (currentStep == 0)
			{
				zoekKok();
			} else if (currentStep == 1)
			{
				openGrabber();
				currentStep++;
			} else if (currentStep == 2)
				rijNaarKok();
			else if (currentStep == 3)
			{
				closeGrabber();
				cookHasFood = false;
				this.addCompletedTask(new CompletedTask("BESTELLING_OPGEHAALD",
						"KOK"));
				hasBall = true;
				moveBack = true;
				currentStep++;
			} else if (currentStep == 4)
				rijNaarBaan(false);
			else if (currentStep == 5)
				zoekKlant();
			else if (currentStep == 6)
				rijNaarKlant();
			else if (currentStep == 7)
			{
				openGrabber();
				this.addCompletedTask(new CompletedTask("ETEN_BEZORGD", this
						.currentState().target()));
				moveBack = true;
				currentStep++;
			} else if (currentStep == 8)
			{
				rijNaarBaan(true);
			} else if (currentStep == 9)
			{
				reset();
				this.setState(new SimState("IDLE"));
				this.setChanged();
			}
			break;
		case "BRENG_REKENING":
			if (cookHasFood || cookIsPreparingFood)
			{
				this.setState(new SimState("IDLE"));
			} else 
				
			if(currentStep == 0)
			{
				this.addCompletedTask(new CompletedTask("BRENG_AFWAS", "KOK"));
				currentStep++;
			}if (currentStep == 1)
				zoekKlant();
			else if (currentStep == 2)
			{
				openGrabber();
				currentStep++;
			} else if (currentStep == 3)
				rijNaarKlant();
			else if (currentStep == 4)
			{
				closeGrabber();
				this.addCompletedTask(new CompletedTask("BETALING_AFGEROND",
						currentState().target()));
				this.setChanged();
				hasBall = true;
				moveBack = true;
				currentStep++;
			} else if (currentStep == 5)
				rijNaarBaan(true);
			else if (currentStep == 6)
				zoekKok();
			else if (currentStep == 7)
				rijNaarKok();
			else if (currentStep == 8)
			{
				openGrabber();
				hasBall = false;
				moveBack = true;
				currentStep++;

				this.addCompletedTask(new CompletedTask("AFWAS_GEBRACHT", "KOK"));
				this.setChanged();
			} else if (currentStep == 9)
			{
				rijNaarBaan(false);
			} else if (currentStep == 10)
			{
				reset();
				this.setState(new SimState("IDLE"));
				this.setChanged();
			}
			break;
		}

		this.updateState();
		notifyObservers();

		try
		{
			if (stateRequest)
				Thread.sleep(4000);
			System.out.println("req send, wait 4s");
		} catch (InterruptedException e)
		{
		}
	}

	private boolean onColor(int num, boolean safe)
	{
		if (safe)
			return color.getSafeColorID() == num;
		else
			return onColor(num);
	}

	private boolean onColor(int num)
	{
		return color.getColorID() == num;
	}

	private boolean onColor(int[] num)
	{
		return onColor(num, false);
	}

	private boolean onColor(int[] num, boolean safe)
	{
		boolean bool = false;
		for (int i = 0; i < num.length; i++)
		{
			if (safe)
				bool = bool || color.getSafeColorID() == num[i];
			else
				bool = bool || color.getColorID() == num[i];
		}

		return bool;
	}

	private void checkRichting()
	{
		if (first == -1 && (onColor(pathInner) || onColor(pathOuter)))
		{
			first = color.getColorID();
		} else
		{
			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			Motor.A.forward();
			Motor.B.backward();

			if (first == pathInner)
			{
				if (onColor(pathOuter))
					richting = 2;
				else if (onColor(fieldColor))
					richting = 1;

			}

			else if (first == pathOuter)
			{
				if (onColor(pathInner))
					richting = 1;
				else if (onColor(fieldColor))
					richting = 3;

			}

			if (richting != -1)
			{
				Motor.A.stop();
				Motor.B.stop();
			}

		}
	}

	private void volgPad()
	{
		if (richting == 3)
		{

			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			Motor.A.backward();
			Motor.B.forward();

			if (onColor(pathOuter))
				richting = 2;
		} else if (onColor(pathInner))
		{
			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			Motor.A.forward();
			Motor.B.forward();
		} else if (onColor(pathOuter) && richting == 1 || onColor(fieldColor)
				&& richting == 2)
		{
			Motor.A.setSpeed(200);
			Motor.B.setSpeed(160);
			Motor.A.forward();
			Motor.B.backward();
		} else if (onColor(fieldColor) && richting == 1 || onColor(pathOuter)
				&& richting == 2)
		{
			Motor.A.setSpeed(160);
			Motor.B.setSpeed(200);
			Motor.A.backward();
			Motor.B.forward();

		}
	}

	private void zoekKok()
	{
		if (currentState().hasTarget())
		{
			if (currentState().target().equalsIgnoreCase("KLANT 1"))
				currentCustomer = custColor[0];
			else if (currentState().target().equalsIgnoreCase("KLANT 2"))
				currentCustomer = custColor[1];
			else if (currentState().target().equalsIgnoreCase("KLANT 3"))
				currentCustomer = custColor[2];
		}

		volgPad();

		if (onColor(cookColor, true))
		{
			currentStep++;
			turn = true;
		}
	}

	private void zoekKlant()
	{
		if (currentState().hasTarget())
		{
			if (currentState().target().equalsIgnoreCase("KLANT 1"))
				currentCustomer = custColor[0];
			else if (currentState().target().equalsIgnoreCase("KLANT 2"))
				currentCustomer = custColor[1];
			else if (currentState().target().equalsIgnoreCase("KLANT 3"))
				currentCustomer = custColor[2];
		}

		volgPad();

		if (onColor(currentCustomer, true))
		{
			currentStep++;
			turn = true;
		}

	}

	private void rijNaarLocatie(boolean isKlant)
	{

		if (turn)
		{
			forward();
			if (isKlant)
				turnNineTeen(richting == 2);
			else
				turnNineTeen(richting == 1);
			forward(400);
			turn = false;
		}

		if (onColor(currentCustomer) && isKlant || onColor(cookColor)
				&& !isKlant)
		{
			Motor.A.setSpeed(50);
			Motor.B.setSpeed(200);
		} else if (onColor(fieldColor))
		{
			Motor.A.setSpeed(240);
			Motor.B.setSpeed(20);
		}

		Motor.A.forward();
		Motor.B.forward();

		if (onColor(pathOuter) && isKlant || onColor(pathInner) && !isKlant)
		{
			currentStep++;
			Motor.A.stop();
			Motor.B.stop();
		}
	}

	private void rijNaarKok()
	{
		rijNaarLocatie(false);
	}

	private void rijNaarKlant()
	{
		rijNaarLocatie(true);
	}

	private void rijNaarBaan(boolean isKlant)
	{
		if (moveBack)
		{
			backward(400);
			moveBack = false;
		}

		Motor.A.setSpeed(160);
		Motor.B.setSpeed(160);
		Motor.A.backward();
		Motor.B.backward();

		if (onColor(pathOuter) && isKlant || onColor(pathInner) && !isKlant)
		{
			if (isKlant)
				forward();
			else
				forward(1000);
			turnNineTeen(true);
			forward(240);
			currentStep++;

			Motor.A.stop();
			Motor.B.stop();
			richting = -1;
			first = -1;

		}

	}

	private void forward()
	{
		forward(800);
	}

	private void forward(int ms)
	{
		Motor.A.setSpeed(160);
		Motor.B.setSpeed(160);
		Motor.A.forward();
		Motor.B.forward();
		try
		{
			Thread.sleep(ms);
			Motor.A.stop();
			Motor.B.stop();
			Thread.sleep(50);
		} catch (InterruptedException e)
		{
		}
	}

	private void backward(int ms)
	{
		Motor.A.setSpeed(160);
		Motor.B.setSpeed(160);
		Motor.A.backward();
		Motor.B.backward();
		try
		{
			Thread.sleep(ms);
			Motor.A.stop();
			Motor.B.stop();
			Thread.sleep(50);
		} catch (InterruptedException e)
		{
		}
	}

	private void turnNineTeen(boolean left)
	{
		try
		{
			Motor.A.stop();
			Motor.B.stop();
			Thread.sleep(50);

			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			if (left)
			{

				Motor.A.backward();
				Motor.B.forward();
			} else
			{
				Motor.A.forward();
				Motor.B.backward();
			}
			Thread.sleep(875);
			// Thread.sleep(420);
		} catch (InterruptedException e)
		{
		}
	}

	private void openGrabber()
	{
		Motor.A.stop();
		Motor.B.stop();
		Motor.C.rotateTo(68);
		Motor.C.stop();
	}

	private void closeGrabber()
	{
		Motor.A.stop();
		Motor.B.stop();
		Motor.C.rotateTo(4);
		Motor.C.stop();
	}

	public void processCompletedTask(String task)
	{
		System.out.println(task);
		switch (task)
		{
		case "FOOD_READY":
			cookHasFood = true;
			cookIsPreparingFood = false;
			break;
		case "PREPARING_FOOD":
			cookIsPreparingFood = true;
			break;
		}
	}
}
