package agent;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import standard.Agent;
import robotica.CompletedTask;
import robotica.SimState;
import standard.TouchSens;

public class Cook extends Agent
{
	private int amountOfFood = 0;
	private TouchSens touch;
	private boolean ligtBal[] = { true, true, true, false};
	private final int position[] = { 11000, 6850, -9500, 0 };
	
	private boolean afwasWordtGebracht = false;
	private boolean recycleFood = false;

	public Cook()
	{
		super("KOK", new SimState("IDLE"));
		init();
		touch = new TouchSens(SensorPort.S1);
	}

	private void init()
	{
		Motor.B.setSpeed(720);
		Motor.B.rotateTo(-360);
		Motor.B.resetTachoCount();
		Motor.A.resetTachoCount();
		Motor.C.resetTachoCount();
		reset();
	}

	public void reset()
	{
		ligtBal[0] = true;
		ligtBal[1] = true;
		ligtBal[2] = true;
		ligtBal[3] = false;
		afwasWordtGebracht = false;
		recycleFood = false;
		amountOfFood = 0;
		this.setState(new SimState("IDLE"));
		this.setChanged();
	}

	@Override
	public void update()
	{
		switch (currentState().name())
		{
		case "IDLE":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(720);
			Motor.C.setSpeed(720);
			Motor.A.rotateTo(0);
			Motor.B.rotateTo(0);
			Motor.C.rotateTo(0);
			
			if(recycleFood == true)
			{
				System.out.println("RECYCLEVOEDSEL");
				this.setState(new SimState("MAAKVOEDSEL"));
				this.setChanged();
			}
			
			if(amountOfFood > 0 && !ligtBal[3] && !afwasWordtGebracht)
			{
				System.out.println("MAAKVOEDSEL");
				this.setState(new SimState("MAAKVOEDSEL"));
				this.addCompletedTask(new CompletedTask("PREPARING_FOOD", "OBER"));
				this.setChanged();
				amountOfFood --;
			}
			break;

		case "MAAKVOEDSEL":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(150);
			Motor.C.setSpeed(720);
			if (!ligtBal[3])
			{
				for (int i = 0; i < 3; i++)
					if (ligtBal[i] && !ligtBal[3])
					{
						verleg(i, true);
					}
			}

			if (ligtBal[3])
				this.addCompletedTask(new CompletedTask("FOOD_READY", "OBER"));

			this.setState(new SimState("IDLE"));
			setChanged();

			break;

		case "RECYCLEVOEDSEL":

			Motor.A.setSpeed(720);
			Motor.B.setSpeed(150);
			Motor.C.setSpeed(720);
			if (ligtBal[3])
			{
				for (int i = 0; i < 3; i++)
					if (!ligtBal[i] && ligtBal[3])
						verleg(i, false);
			}
			recycleFood = false;
			this.setState(new SimState("IDLE"));
			setChanged();
			break;
		case "TEST":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(150);
			Motor.C.setSpeed(720);

			/*
			 * Motor.C.rotateTo(position[0]); if(grab()) put();
			 * Motor.C.rotateTo(position[1]); if(grab()) put();
			 * Motor.C.rotateTo(position[2]); if(grab()) put();
			 */
			Motor.C.rotateTo(position[1]);
			if (grab())
				put();

			this.setState(new SimState("IDLE"));
			setChanged();
			break;
		case "STOP":
			Motor.A.setSpeed(720);
			Motor.B.setSpeed(720);
			Motor.C.setSpeed(720);
			Motor.A.rotateTo(0);
			Motor.B.rotateTo(0);
			Motor.C.rotateTo(0);
			this.setState(new SimState("FINISHED"));
			setChanged();
			break;
		case "FINISHED":

			Motor.A.suspendRegulation();
			Motor.B.suspendRegulation();
			Motor.C.suspendRegulation();
			break;

		}

		this.updateState();
		notifyObservers();
	}

	private void verleg(int pos, boolean naarAfhaal)
	{
		boolean succes = true;
		if (naarAfhaal)
		{
			Motor.C.rotateTo(position[pos]);
			succes = grab();
			if (succes)
			{
				Motor.C.rotateTo(position[3]);
				put();
				ligtBal[pos] = false;
				ligtBal[3] = true;
			}
		} else
		{
			Motor.C.rotateTo(position[3]);
			succes = grab();
			if (succes)
			{
				Motor.C.rotateTo(position[pos]);
				put();
				ligtBal[pos] = true;
				ligtBal[3] = false;
			}
		}
	}

	private boolean grab()
	{
		Motor.A.rotateTo(-7200);
		Motor.B.rotateTo(100);
		if (touch.isPressed() == 0)
		{
			Motor.B.rotateTo(0);
			Motor.A.rotate(0);
			return false;
		}
		Motor.A.rotateTo(0);
		return true;
	}

	private boolean put()
	{
		Motor.A.rotateTo(-6700);
		Motor.B.rotateTo(0);
		Motor.A.rotateTo(0);
		return true;
	}

	public void processCompletedTask(String task)
	{
		switch (task)
		{
		case "BESTELLING_AFGELEVERD":
			System.out.println("Besetlling afgeleverd");
			amountOfFood ++;
			break;
		case "BESTELLING_OPGEHAALD":
			
			if (ligtBal[3])
				System.out.println("Bestelling opgehaald");
			else
				System.out.println("er was geen bestelling");
			
			ligtBal[3] = false;
			break;
		case "BRENG_AFWAS":
			afwasWordtGebracht = true;
			break;
		case "AFWAS_GEBRACHT":
			afwasWordtGebracht = false;
			recycleFood = true;
			break;
		}
	}
}
