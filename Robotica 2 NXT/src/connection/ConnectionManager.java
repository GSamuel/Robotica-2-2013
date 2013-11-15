package connection;

import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;
import standard.BrickConnection;

public class ConnectionManager implements Runnable
{
	private static ConnectionManager instance;
	private BrickConnection brickCon;
	
	protected ConnectionManager()
	{
		// Exist only so you can't make an instance
	}
	
	public synchronized static ConnectionManager getInstance()
	{
		if (instance == null)
			instance = new ConnectionManager();

		return instance;
	}
	
	public ConnectionManager start()
	{
		new Thread(this).start();
		return this;
	}
	
	public BrickConnection getBrickConnection()
	{
		return brickCon;
	}
	
	public boolean isConnected()
	{
		if(brickCon == null)
			return false;
		return brickCon.isWorking();
	}

	
	@Override
	public void run()
	{
		System.out.println("waiting connection");
		NXTConnection connection = Bluetooth.waitForConnection();
		brickCon = new BrickConnection(connection.openDataOutputStream(),
				connection.openDataInputStream());
		brickCon.start();
	}
}
