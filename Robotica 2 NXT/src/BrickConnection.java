

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

public class BrickConnection implements Runnable
{
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
	private Vector<String> receive;
	private boolean working;

	public BrickConnection(OutputStream out, InputStream in)
	{
		receive = new Vector<String>();
		dataOut = new DataOutputStream(out);
		dataIn = new DataInputStream(in);
		working = true;
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
			System.out.println("cant write to output");
		}
		return working;
	}
	
	public void start()
	{
		new Thread(this).start();
	}

	public String receiveData()
	{
		String s = receive.elementAt(0);
		receive.removeElementAt(0);
		return s;
	}

	public boolean isEmpty()
	{
		return receive.isEmpty();
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
				receive.addElement(dataIn.readUTF());
			} catch (IOException e)
			{
				working = false;
				System.out.println("cant read from input");
			}
		}
	}
}
