package robotica;

import java.util.Iterator;
import java.util.Vector;

import lejos.pc.comm.NXTInfo;

public class ConnectionModel implements Iterable<NXTConnection>
{
	private Vector<NXTConnection> connections;

	public ConnectionModel()
	{
		connections = new Vector<NXTConnection>();
	}
	
	public boolean contains(NXTInfo inf)
	{
		for(NXTConnection con : connections)
			if(con.getDeviceAdress() == inf.deviceAddress)
				return true;
		
		return false;
	}

	public void addConnection(NXTConnection con)
	{
		connections.add(con);
		new Thread(con).start();
	}

	public void removeConnection(NXTConnection con)
	{
		connections.remove(con);
	}

	@Override
	public Iterator<NXTConnection> iterator()
	{
		return connections.iterator();
	}
}
