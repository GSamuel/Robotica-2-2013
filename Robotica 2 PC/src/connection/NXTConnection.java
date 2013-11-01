package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import lejos.pc.comm.NXTInfo;

public class NXTConnection implements Runnable, NxtPcConnection
{
	private NXTInfo nxtInfo;
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
	private Vector<String> receive;
	private boolean working;

	public NXTConnection(NXTInfo info, OutputStream out, InputStream in)
	{
		receive = new Vector<String>();
		nxtInfo = info;
		dataOut = new DataOutputStream(out);
		dataIn = new DataInputStream(in);
		working = true;
	}
		
	public boolean isEqual(NXTInfo inf)
	{
		return nxtInfo.deviceAddress.equals(inf.deviceAddress);
	}

	public boolean sendData(String s)
	{
		try
		{
			dataOut.writeUTF(s);
			dataOut.flush();
		} catch (IOException e)
		{
			working = false;
			System.out.println("cant write to output: "+nxtInfo.name);
		}
		return working;
	}

	public String receiveData()
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
	
	public void start()
	{
		new Thread(this).start();
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
				receive.add(dataIn.readUTF());
			} catch (IOException e)
			{
				working = false;
				System.out.println("cant read from input: " + nxtInfo.name);
			}
		}
	}
}
