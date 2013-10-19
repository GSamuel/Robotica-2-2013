package robotica;

import java.util.ArrayList;

public abstract class Agent
{
	private int id;
	private State currentState;
	private ArrayList<State> states;

	public Agent()
	{
		states = new ArrayList<State>();
	}
	
	public void setState(State state)
	{
		currentState = state;
	}

	public void addState(State state)
	{
		states.add(state);
	}

	public void removeState(State state)
	{
		states.remove(state);
	}

	public void removeState(String name)
	{
		for (State state : states)
		{
			if (name == state.name())
				states.remove(state);
		}
	}

	public Agent setID(int id)
	{
		this.id = id;
		return this;
	}

	public int getID()
	{
		return id;
	}
}
