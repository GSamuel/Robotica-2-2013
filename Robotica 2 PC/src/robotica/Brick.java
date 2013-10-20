package robotica;

public class Brick
{
	private NXTConnection nxtCon;
	private Simulation sim;

	public Brick(NXTConnection con, Simulation sim)
	{
		this.nxtCon = con;
		this.sim = sim;
	}

	public boolean isConnected()
	{
		return nxtCon.isWorking();
	}

	public boolean isSameCon(NXTConnection con)
	{
		return nxtCon.isEqual(con.getNXTInfo());
	}

	public boolean refreshConnection(NXTConnection con)
	{
		if (!nxtCon.isWorking() && con.isWorking()
				&& nxtCon.isEqual(con.getNXTInfo()))
		{
			this.nxtCon = con;
			return true;
		}
		return false;
	}

	public void update()
	{
		if(nxtCon.isWorking())
		nxtCon.sendData(String.valueOf(sim.getTotalTicks()));
	}

}
