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
		new Thread(this).start();;
	}
	
	@Override
	public void run()
	{
		
		
		NXTInfo[] nxtInfo;
		OutputStream dataOut = null;
		InputStream dataIn = null;
		
		try
		{
			usbComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			BTComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			
			nxtInfo = usbComm.search(null);
			
			for(int i = 0; i <nxtInfo.length; i++)
			{
				
			}
			
			if (nxtInfo.length > 0)
			{
				usbComm.open(nxtInfo[0]);
				dataOut = nxtComm.getOutputStream();
				dataIn = nxtComm.getInputStream();
				System.out.println("Connection Succesfull");
			}

		} catch (NXTCommException e)
		{
			e.printStackTrace();
		}
	}
	

}
