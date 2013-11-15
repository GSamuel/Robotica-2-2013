package standard;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import robotica.AgentObserver;
import connection.NxtPcConnection;

public class BrickConnection implements Runnable, NxtPcConnection
{
	private DataOutputStream dataOut;
	private DataInputStream dataIn;
	private Vector<String> receive;
	private boolean working;
	private boolean changed;
	private ArrayList<ConnectionObserver> observers;

	public BrickConnection(OutputStream out, InputStream in)
	{
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
	
	public void registerObserver(AgentObserver o)
	{
		observers.add(o);
	}
	
	public void removeObserver(AgentObserver o)
	{
		int i = observers.indexOf(o);
		if(i>=0)
			observers.remove(i);
	}
	
	public void notifyObservers()
	{
		if(changed)
		for(int i = 0; i < observers.size(); i++)
		{
			AgentObserver o = observers.get(i);
			o.update(this);
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
			} catch (IOException e)
			{
				working = false;
				System.out.println("cant read from input");
			}
		}
	}
}
