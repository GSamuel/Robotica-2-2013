import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;

/**
 * sample of selecting channel at run time
 */
public class MainConnection
{
	public static void main(String[] args)
	{

		new StopProgram().start();

		NXTConnection connection = null;

		LCD.drawString("waiting for USB", 0, 1);
		connection = USB.waitForConnection();
		LCD.drawString("connected", 0, 2);
		

		DataOutputStream dataOut = connection.openDataOutputStream();
		DataInputStream dataIn = connection.openDataInputStream();

		try
		{
			dataOut.writeChars("yayayayayayyyayyayayyayy");
			dataOut.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		while(true);
		
	}
}