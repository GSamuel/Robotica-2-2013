package robotica;

import java.util.ArrayList;

public abstract class Agent
{
	private int localID;
	private ArrayList<State> states;

	public Agent()
	{
		states = new ArrayList<State>();
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
			if (name == state.getName())
				states.remove(state);
		}
	}

	public void setLocalID(int id)
	{
		localID = id;
	}

	public int getID()
	{
		return localID;
	}
}
