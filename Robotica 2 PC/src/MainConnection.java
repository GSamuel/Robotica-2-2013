import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommException;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;

public class MainConnection
{

	public static void main(String[] args)
	{
		NXTComm nxtComm;
		NXTInfo[] nxtInfo;
		DataOutputStream dataOut = null;
		DataInputStream dataIn = null;

		try
		{
			nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.USB);
			nxtInfo = nxtComm.search(null);
			if (nxtInfo.length > 0) 
			{
				nxtComm.open(nxtInfo[0]);
				dataOut = new DataOutputStream (nxtComm.getOutputStream());
				dataIn = new DataInputStream( nxtComm.getInputStream());
				
				System.out.println("connected");
				while(true)
				{
					System.out.println(dataIn.readChar());
				}
			}
			

		} catch (NXTCommException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
