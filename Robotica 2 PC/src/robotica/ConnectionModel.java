package robotica;

import java.util.ArrayList;
import java.util.Iterator;

import lejos.pc.comm.NXTInfo;

public class ConnectionModel implements Iterable<NXTConnection>
{
	private ArrayList<NXTConnection> connections;

	public ConnectionModel()
	{
		connections = new ArrayList<NXTConnection>();
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
