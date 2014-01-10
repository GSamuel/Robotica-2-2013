package agent;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;
import robotica.Agent;
import robotica.CoupledState;
import robotica.SimState;
import robotica.State;
import standard.ColorHistory;

public class Waiter extends Agent
{
	private TouchSensor touch;
	private UltrasonicSensor sonar;

	private int first;
	private int richting;

	private boolean customerFound;
	private int currentCustomer;

	private boolean turn = false;
	private boolean grabbing = true;

	private final int[] custColor = { 1, 2, 3 };
	private final int cookColor = 4;
	private final int fieldColor = 6;
	private final int pathInner = 7, pathOuter = 0;
	
	ColorHistory color = new ColorHistory(SensorPort.S1);

	public Waiter()
	{
		super("Waiter", new SimState("IDLE"));
		this.addCoupledState(new CoupledState("IDLE", "WBESTELLEN",
				"OPNEMEN_BESTELLING"));
		this.setState(new SimState("OPNEMEN_BESTELLING", "KLANT 2"));
		
		color.start();
		
		init();
	}

	private void init()
	{
		first = -1;
		richting = -1;
		customerFound = false;
		currentCustomer = -1;
		turn = false;
		grabbing = false;

		Motor.C.setSpeed(100);
		Motor.C.rotateTo(-360);
		Motor.C.resetTachoCount();
		
		openGrabber();
	}

	@Override
	public void update()
	{
		State state = this.currentState();
		switch (state.name())
		{
		case "IDLE":
			break;
		case "OPNEMEN_BESTELLING":
			if (richting == -1)
				checkRichting();
			else if (!customerFound)
				zoekKlant();
			else
				rijNaarKlant();
			break;
		}

		notifyObservers();
	}

	private boolean onColorSafe(int num)
	{
		return true;
	}
	
	private boolean onColor(int num)
	{
		return color.getColorID() == num;
	}

	private boolean onColor(int[] num)
	{
		boolean bool = false;
		for (int i = 0; i < num.length; i++)
			bool = bool || color.getColorID() == num[i];

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

		}
	}

	private void zoekKlant()
	{
		if (currentCustomer == -1)
		{

			if (currentState().target().equalsIgnoreCase("KLANT 1"))
				currentCustomer = custColor[0];
			if (currentState().target().equalsIgnoreCase("KLANT 2"))
				currentCustomer = custColor[1];
			if (currentState().target().equalsIgnoreCase("KLANT 3"))
				currentCustomer = custColor[2];
		}

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
			Motor.B.setSpeed(200);
			Motor.A.forward();
			Motor.B.backward();
		} else if (onColor(fieldColor) && richting == 1 || onColor(pathOuter)
				&& richting == 2)
		{
			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			Motor.A.backward();
			Motor.B.forward();

		}

		if (onColor(currentCustomer))
		{
			customerFound = true;
			turn = true;
		}

	}

	private void rijNaarKlant()
	{
		if(turn)
		{
			forward();
			turnNineTeen(richting == 2);
			forward();
			turn  = false;
		}
		
		if(onColor(currentCustomer))
		{
			Motor.A.setSpeed(50);
			Motor.B.setSpeed(200);
		}
		else if(onColor(fieldColor))
		{
			Motor.A.setSpeed(220);
			Motor.B.setSpeed(20);
		}
		
		Motor.A.forward();
		Motor.B.forward();
		
		if(onColor(pathOuter))
		{
			closeGrabber();
			Motor.A.stop();
			Motor.B.stop();
			try
			{
				Thread.sleep(4000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		/*
		Motor.A.setSpeed(150);
		Motor.B.setSpeed(150);

		if (turn)
		{
			if (richting == 1)
			{
				Motor.A.forward();
				Motor.B.backward();
			} else if (richting == 2)
			{
				Motor.A.backward();
				Motor.B.forward();
			}
			if (onColor(fieldColor))
				turn = false;
		} else if (richting == 1)
		{
			if (onColor(currentCustomer))
			{
				Motor.A.setSpeed(200);
				Motor.A.forward();
				Motor.B.suspendRegulation();
			} else if (onColor(fieldColor))
			{
				Motor.A.suspendRegulation();
				Motor.B.setSpeed(200);
				Motor.B.forward();
			}
		} else if (richting == 2)
		{
			if (onColor(currentCustomer))
			{
				Motor.A.suspendRegulation();
				Motor.B.setSpeed(200);
				Motor.B.forward();
			} else if (onColor(fieldColor))
			{
				Motor.A.setSpeed(200);
				Motor.A.forward();
				Motor.B.suspendRegulation();
			}
		}

		if (onColor(pathOuter))
		{
			Motor.A.suspendRegulation();
			Motor.B.suspendRegulation();
		}*/
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
			//Thread.sleep(420);
		} catch (InterruptedException e)
		{
		}
	}
	
	private void openGrabber()
	{
		Motor.C.rotateTo(120);
	}
	
	private void closeGrabber()
	{
		Motor.C.rotateTo(0);
	}
}
