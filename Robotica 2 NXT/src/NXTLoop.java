import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import robotica.Loop;

public class NXTLoop extends Loop
{
	private DataOutputStream dataOut;
	private DataInputStream dataIn;

	public NXTLoop(int tickTime)
	{
		super(tickTime);
		NXTConnection connection = Bluetooth.waitForConnection();
		dataOut = connection.openDataOutputStream();
		dataIn = connection.openDataInputStream();
	}

	@Override
	public void loop()
	{
		try
		{
			dataOut.writeInt(getTotalTicks());
			dataOut.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			while (true)
				System.out.println(dataIn.readInt());
		} catch (IOException e)
		{
			System.exit(0);
		}
	}

}
