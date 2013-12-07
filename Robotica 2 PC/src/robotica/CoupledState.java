package robotica;

public class CoupledState
{
	private State own, target;
	public CoupledState(State own, State target)
	{
		this.own = own;
		this.target = target;
	}
	
	public CoupledState(String own, String target)
	{
		this.own = new SimState(own);
		this.target = new SimState(target);
	}
	
	public State getOwnState()
	{
		return own;
	}
	
	public State getTargetState()
	{
		return target;
	}
}
