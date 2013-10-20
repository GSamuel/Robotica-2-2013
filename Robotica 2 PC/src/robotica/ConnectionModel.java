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
		Iterator<NXTConnection> it = iterator();
		while (it.hasNext())
		{
			NXTConnection con = it.next();
			if (!con.isWorking())
				it.remove();
			else if (con.isEqual(inf))
				return true;
		}
		return false;
	}

	public void addConnection(NXTConnection con)
	{
		connections.add(con);
		con.start();
	}

	public NXTConnection get(int index)
	{
		return connections.get(index);
	}

	public int size()
	{
		return connections.size();
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
