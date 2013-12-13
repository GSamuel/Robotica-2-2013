package standard;

import lejos.nxt.ADSensorPort;
import lejos.nxt.TouchSensor;

public class TouchSens
{

	private TouchSensor touch;

	public TouchSens(ADSensorPort port)
	{
		touch = new TouchSensor(port);
	}
	
	public int isPressed()
	{
		if(touch.isPressed())
			return 1;
		return 0;
	}
}
