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

	public ConnectionManager()
	{

	}

	public void start()
	{
		new Thread(this).start();
		;
	}

	@Override
	public void run()
	{

		NXTInfo[] nxtInfo;
		OutputStream dataOut = null;
		InputStream dataIn = null;

		while (true)
		{
			try
			{
				usbComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
				BTComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);

				nxtInfo = usbComm.search(null);

				for (int i = 0; i < nxtInfo.length; i++)
				{
					usbComm.open(nxtInfo[i]);
					dataOut = usbComm.getOutputStream();
					dataIn = usbComm.getInputStream();
				}
				System.out.println(".");
				
				/*
				nxtInfo = BTComm.search("NXT2");

				for (int i = 0; i < nxtInfo.length; i++)
				{
					System.out.print(nxtInfo[i].name);
					BTComm.open(nxtInfo[i]);
					/*
					dataOut = BTComm.getOutputStream();
					dataIn = BTComm.getInputStream();
				}
				System.out.println(".");*/

			} catch (NXTCommException e)
			{
				e.printStackTrace();
			}
		}
	}

}
