package robotica;

import java.io.InputStream;
import java.io.OutputStream;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class ConnectionManager implements Runnable
{
	private NXTComm BTComm, usbComm;
	private ConnectionModel model;

	public ConnectionManager()
	{
		model = new ConnectionModel();
	}

	public void start()
	{
		new Thread(this).start();
	}

	@Override
	public void run()
	{

		NXTInfo[] nxtInfo;
		OutputStream dataOut = null;
		InputStream dataIn = null;

		try
		{
			BTComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
		} catch (NXTCommException e)
		{
			System.out.println("Unable to use Bluetooth");
		}

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
				if (!model.contains(nxtInfo[i]))
				{
					try
					{
						BTComm.open(nxtInfo[i]);
						dataOut = BTComm.getOutputStream();
						dataIn = BTComm.getInputStream();
						model.addConnection(new NXTConnection(nxtInfo[i],
								dataOut, dataIn));

						System.out.println("connected with: "+ nxtInfo[i].deviceAddress+" name: "+nxtInfo[i].name);
					} catch (NXTCommException e)
					{
						System.out.println("cant establish a connection with device: "+ nxtInfo[i].deviceAddress+" name: "+nxtInfo[i].name);
					}
				}
			}
		}
	}
}
