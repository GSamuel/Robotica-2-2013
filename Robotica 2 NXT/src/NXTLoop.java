import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import robotica.Loop;

public class NXTLoop extends Loop
{
	private BrickConnection brickCon;

	public NXTLoop(int tickTime)
	{
		super(tickTime);
		NXTConnection connection = Bluetooth.waitForConnection();
		brickCon = new BrickConnection(connection.openDataOutputStream(),connection.openDataInputStream());
		brickCon.start();
	}

	@Override
	public void loop()
	{
		while(!brickCon.isEmpty())
			System.out.println(brickCon.receiveData());
	}

}
