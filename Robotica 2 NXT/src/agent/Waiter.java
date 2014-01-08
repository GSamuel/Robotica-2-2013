package agent;

import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;
import robotica.Agent;
import robotica.CoupledState;
import robotica.SimState;
import robotica.State;

public class Waiter extends Agent
{
	private TouchSensor touch;
	private UltrasonicSensor sonar;

	private int first = -1, snd = -1;
	private int richting = -1;

	private final int[] custColor = { 1, 2, 3 };
	private final int cookColor = 4;
	private final int fieldColor = 6;
	private final int pathInner = 7, pathOuter = 0;

	ColorHTSensor color = new ColorHTSensor(SensorPort.S1);

	public Waiter()
	{
		super("Waiter", new SimState("IDLE"));
		this.addCoupledState(new CoupledState("IDLE", "WBESTELLEN",
				"OPNEMEN_BESTELLING"));

		this.setState(new SimState("OPNEMEN_BESTELLING", "KLANT 1"));
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
			else
				rijNaarKlant();
			break;
		}

		notifyObservers();
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
		} else if (snd == -1)
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

			} else
			{
				if (first == pathOuter)
				{
					if (onColor(pathInner))
						richting = 1;
					else if (onColor(fieldColor))
						richting = 2;

				}
			}
		}
	}

	private void rijNaarKlant()
	{
		
		

		if (onColor(pathInner))
		{
			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			Motor.A.forward();
			Motor.B.forward();
		} else if (onColor(pathOuter) && richting == 1 || onColor(fieldColor) && richting == 2)
		{
			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			Motor.A.forward();
			Motor.B.backward();
		}
		else if (onColor(fieldColor) && richting == 1 || onColor(pathOuter) && richting == 2)
		{
			Motor.A.setSpeed(200);
			Motor.B.setSpeed(200);
			Motor.A.backward();
			Motor.B.forward();
			
		}
	}
}
