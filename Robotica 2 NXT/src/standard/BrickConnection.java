package standard;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import connection.ConnectionObserver;
import connection.NxtPcConnection;

public class BrickConnection implements Runnable, NxtPcConnection
{
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
	private Vector<String> receive;
	private boolean working;
	private boolean changed;
	private Vector<ConnectionObserver> observers;

	public BrickConnection(OutputStream out, InputStream in)
	{
		observers = new Vector<ConnectionObserver>();
		receive = new Vector<String>();
		dataOut = new DataOutputStream(out);
		dataIn = new DataInputStream(in);
		working = true;
		changed = false;
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
	
	public void registerObserver(ConnectionObserver o)
	{
		observers.addElement(o);
	}
	
	public void removeObserver(ConnectionObserver o)
	{
		observers.removeElement(o);
	}
	
	public void notifyObservers()
	{
		if(changed)
		for(int i = 0; i < observers.size(); i++)
		{
			ConnectionObserver o = observers.elementAt(i);
			o.update();
			changed = false;
		}
	}
	
	public void setChanged()
	{
		changed = true;
		notifyObservers();
	}
	
	public boolean hasChanged()
	{
		return changed;
	}

	@Override
	public void run()
	{
		while (working)
		{
			try
			{
				receive.addElement(dataIn.readUTF());
				setChanged();
				notifyObservers();
			} catch (IOException e)
			{
				working = false;
				System.out.println("cant read from input");
			}
		}
	}
}
