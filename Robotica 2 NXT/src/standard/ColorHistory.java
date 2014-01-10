package standard;

import lejos.nxt.SensorPort;
import lejos.nxt.addon.ColorHTSensor;

public class ColorHistory implements Runnable
{
	ColorHTSensor color;
	private int[] colorID, colorArray;
	private int pos = 0;

	public ColorHistory(SensorPort sp)
	{
		color = new ColorHTSensor(sp);
		colorArray = new int[15];
		colorID = new int[15];
	}

	private void reset()
	{
		for (int i = 0; i < colorID.length; i++)
			colorID[i] = 0;
	}

	public void start()
	{
		new Thread(this).start();
	}

	public int getSafeColorID()
	{
		reset();
		for (int i = 0; i < colorArray.length; i++)
			colorID[colorArray[i]]++;

		int id = 0, amount = 0;

		for (int i = 0; i < colorID.length; i++)
			if (colorID[i] > amount)
			{
				amount = colorID[i];
				id = i;
			}

		return id;
	}
	
	public int getColorID()
	{
		return color.getColorID();
	}

	@Override
	public void run()
	{
		while (true)
		{
			colorArray[pos++] = color.getColorID();
			if(pos > colorArray.length-1)
				pos = 0;

			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
			}
		}
	}
}
