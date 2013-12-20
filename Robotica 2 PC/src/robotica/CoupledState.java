package robotica;

public class CoupledState
{
	private State own, target, newState;
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
	
	public CoupledState(String own, String target, String newState)
	{
		this.own = new SimState(own);
		this.target = new SimState(target);
		this.newState = new SimState(newState);
	}
	
	public State getOwnState()
	{
		return own;
	}
	
	public State getTargetState()
	{
		return target;
	}
	
	public State getNewState()
	{
		return newState;
	}
	
	public boolean isSame(CoupledState cstate)
	{
		return cstate.getOwnState().name().equals(own.name()) && cstate.getTargetState().name().equals(target.name());
	}
}
