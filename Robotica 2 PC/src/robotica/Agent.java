package robotica;

import java.util.ArrayList;

public abstract class Agent
{
	private int id;
	private String name;
	private boolean hasID = false, hasName = false;
	private State currentState;
	// private ArrayList<State> states;
	private ArrayList<CoupledState> cStates;
	private ArrayList<AgentObserver> observers;
	private ArrayList<CompletedTask> completedTasks;
	private boolean changed = false;
	private boolean sendAll = false;

	public Agent(String name, State state)
	{
		// states = new ArrayList<State>();
		observers = new ArrayList<AgentObserver>();
		cStates = new ArrayList<CoupledState>();
		completedTasks = new ArrayList<CompletedTask>();
		setName(name);
		currentState = state;
		sendAll = true;
		setChanged();
	}

	public int coupledStateSize()
	{
		return cStates.size();

	}

	public CoupledState getCoupledState(int index)
	{

		if (index >= cStates.size())
		{
			System.out.println("hier gaat het fout!!!!");
			try
			{
				Thread.sleep(10000);
			} catch (InterruptedException e)
			{
			}
		}
		return cStates.get(index);

	}

	public void registerObserver(AgentObserver o)
	{
		observers.add(o);
	}

	public void removeObserver(AgentObserver o)
	{

		int i = observers.indexOf(o);
		if (i >= 0)
			observers.remove(i);

	}

	public void addCompletedTask(CompletedTask task)
	{

		completedTasks.add(task);

	}

	public boolean hasCompletedTask()
	{
		return completedTasks.size() > 0;

	}

	public CompletedTask removeCompletedTask()
	{
		return completedTasks.remove(0);

	}

	public void notifyObservers()
	{
		if (changed)
		{
			for (int i = 0; i < observers.size(); i++)
			{
				AgentObserver o = observers.get(i);
				o.update(this);
			}
			changed = false;

		}
	}

	public void setChanged()
	{
		changed = true;
	}

	public boolean hasChanged()
	{
		return changed;
	}

	public void setSendAll()
	{
		sendAll = true;
		setChanged();
	}

	public void allSended()
	{
		sendAll = false;
	}

	public boolean hasToSendAll()
	{
		return sendAll;
	}

	public Agent setState(State state)
	{
		currentState = state;
		return this;
	}

	/*
	 * public void addState(State state) { states.add(state); }
	 * 
	 * public void removeState(State state) { states.remove(state); }
	 * 
	 * public void removeState(String name) { for (State state : states) { if
	 * (name == state.name()) states.remove(state); } }
	 */

	public void addCoupledState(CoupledState cstate)
	{
		boolean same = false;
		for (int i = 0; i < cStates.size(); i++)
		{
			if (cStates.get(i).isSame(cstate))
			{
				same = true;
				break;
			}
		}
		if (!same)
		{
			cStates.add(cstate);
			setSendAll();
		}

	}

	public void removeCoupledState(CoupledState cstate)
	{
		cStates.remove(cstate);

	}

	public void removeCoupledState(String own, String target)
	{
		for (CoupledState cstate : cStates)
		{
			if (own == cstate.getOwnState().name()
					&& target == cstate.getTargetState().name())
				cStates.remove(cstate);
		}

	}

	public Agent setID(int id)
	{
		this.id = id;
		hasID = true;
		return this;
	}

	public int getID()
	{
		return id;
	}

	public boolean hasID()
	{
		return hasID;
	}

	public State currentState()
	{
		return currentState;
	}

	public boolean hasName()
	{
		return hasName;
	}

	public Agent setName(String name)
	{
		this.name = name;
		hasName = true;
		setSendAll();
		return this;
	}

	public String name()
	{
		return name;
	}

	public abstract void update();

	public abstract void reset();

	public void processCompletedTask(String task)
	{

	}
}
