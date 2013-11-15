import standard.StopProgram;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.LCD;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.nxt.addon.ColorHTSensor;

public class SensorTester
{

	private TouchSensor touch;
	private UltrasonicSensor sonar;
	private ColorHTSensor color;

	public static void main(String[] args) throws Exception
	{
		new StopProgram().start();

		ColorHTSensor color = new ColorHTSensor(SensorPort.S4);
		String colore = "color";
		String r = "r";
		String b = "b";
		String g = "g";

		while (true)
		{
			LCD.clear();
			LCD.drawString(colore, 0, 3);
			LCD.drawInt(color.getColorID(), 7, 3);
			LCD.drawString(r, 0, 5);
			LCD.drawInt(color.getRGBComponent(Color.RED), 1, 5);
			LCD.drawString(b, 5, 5);
			LCD.drawInt(color.getRGBComponent(Color.BLUE), 6, 5);
			LCD.drawString(g, 10, 5);
			LCD.drawInt(color.getRGBComponent(Color.GREEN), 11, 5);
			LCD.refresh();
			Thread.sleep(500);
		}
	}

}