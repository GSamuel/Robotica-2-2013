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
				usbComm.open(nxtInfo[i]);
				dataOut = usbComm.getOutputStream();
				dataIn = usbComm.getInputStream(); 
			}

		} catch (NXTCommException e)
		{
			e.printStackTrace();
		}
	}
	

}
