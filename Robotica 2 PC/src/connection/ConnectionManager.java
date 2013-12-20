package connection;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class ConnectionManager implements Runnable, Iterable<NXTConnection>
{
	private static ConnectionManager instance;
	private NXTComm BTComm;
	private ConnectionModel model = new ConnectionModel();

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
	
	
	private void newBTComm()
	{
		try
		{
			BTComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException e)
		{
			System.out.println("Unable to make new comm port");
		}
	}

	@Override
	public void run()
	{

		NXTInfo[] nxtInfo;
		OutputStream dataOut = null;
		InputStream dataIn = null;

		newBTComm();
		
		while (true)
		{

			try
			{
				nxtInfo = BTComm.search(null);
			} catch (NXTCommException e)
			{
				System.out.println("Cant search for devices");
				break;
			}

			for (int i = 0; i < nxtInfo.length; i++)
			{
				if (!model.contains(nxtInfo[i]) && nxtInfo[i].name.contains("Good") )
				{
					try
					{
						BTComm.open(nxtInfo[i]);
						dataOut = BTComm.getOutputStream();
						dataIn = BTComm.getInputStream();
						model.addConnection(new NXTConnection(nxtInfo[i],
								dataOut, dataIn));

						System.out.println("connected with: "
								+ nxtInfo[i].deviceAddress + " name: "
								+ nxtInfo[i].name);

						newBTComm();
					} catch (NXTCommException e)
					{
						System.out
								.println("cant establish a connection with device: "
										+ nxtInfo[i].deviceAddress
										+ " name: "
										+ nxtInfo[i].name);
					}
				}
			}
		}
	}



	@Override
	public Iterator<NXTConnection> iterator()
	{
		return model.iterator();
	}
}
