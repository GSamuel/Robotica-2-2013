import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import robotica.Loop;


public class NXTLoop extends Loop
{
	private DataOutputStream dataOut;
	public NXTLoop(int tickTime)
	{
		super(tickTime);		
		NXTConnection connection = Bluetooth.waitForConnection();
		dataOut = connection.openDataOutputStream();
	}

	@Override
	public void loop()
	{
		try
		{
			dataOut.writeInt(getTotalTicks());
			dataOut.flush();
			System.out.println(getTotalTicks());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
