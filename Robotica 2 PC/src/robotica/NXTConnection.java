package robotica;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import lejos.pc.comm.NXTInfo;

public class NXTConnection implements Runnable
{
	private NXTInfo nxtInfo;
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
	private Vector<Integer> receive;
	private boolean working;

	public NXTConnection(NXTInfo info, OutputStream out, InputStream in)
	{
		receive = new Vector<Integer>();
		nxtInfo = info;
		dataOut = new DataOutputStream(out);
		dataIn = new DataInputStream(in);
		working = true;
	}
		
	public boolean isEqual(NXTInfo inf)
	{
		return nxtInfo.deviceAddress.equals(inf.deviceAddress);
	}

	public boolean sendData(int i)
	{
		try
		{
			dataOut.writeInt(i);
			dataOut.flush();
		} catch (IOException e)
		{
			working = false;
			System.out.println("cant write in output stream");
		}
		return working;
	}

	public int receiveData()
	{
		return receive.remove(0);
	}

	public boolean isEmpty()
	{
		return receive.isEmpty();
	}

	public NXTInfo getNXTInfo()
	{
		return nxtInfo;
	}

	public String getDeviceAdress()
	{
		return nxtInfo.deviceAddress;
	}
	
	public boolean isWorking()
	{
		return working;
	}

	@Override
	public void run()
	{
		while (working)
		{
			try
			{
				receive.add(dataIn.readInt());
			} catch (IOException e)
			{
				working = false;
				System.out.println("cant read from input stream");
			}
		}
	}
}
