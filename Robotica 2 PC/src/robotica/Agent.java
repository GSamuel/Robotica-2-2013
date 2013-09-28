package robotica;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Agent
{
	private int localID;
	private ArrayList<State> states;
	private HashMap<String,Object> map;

	public Agent()
	{
		states = new ArrayList<State>();
		map = new HashMap<String,Object>();
	}
	
	public void setVar(String key, int value)
	{
		map.put(key, value);
	}
	
	public Object getValue(String key)
	{
		return map.get(key);
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
