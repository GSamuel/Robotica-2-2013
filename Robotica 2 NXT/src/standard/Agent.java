package standard;

import java.util.Vector;

import robotica.CompletedTask;
import robotica.CoupledState;
import robotica.State;

public abstract class Agent
{
	private int id;
	private String name;
	private boolean hasID = false, hasName = false;
	private State currentState, futureState;
	// private ArrayList<State> states;
	private Vector<CoupledState> cStates;
	private Vector<AgentObserver> observers;
	private Vector<CompletedTask> completedTasks;
	private boolean changed = false;
	private boolean sendAll = false;
	private boolean hasFutureState = false;
	private boolean requestNewState = false;

	public Agent(String name, State state)
	{
		// states = new ArrayList<State>();
		observers = new Vector<AgentObserver>();
		cStates = new Vector<CoupledState>();
		completedTasks = new Vector<CompletedTask>();
		setName(name);
		currentState = state;
		sendAll = true;
		setChanged();
	}

	public boolean requestNewState()
	{
		return requestNewState;
	}
	
	public void setRequestNewState(boolean bool)
	{
		requestNewState = bool;
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
		return cStates.elementAt(index);

	}

	public void registerObserver(AgentObserver o)
	{
		observers.addElement(o);

	}

	public void removeObserver(AgentObserver o)
	{
		observers.removeElement(o);
	}

	public void addCompletedTask(CompletedTask task)
	{

		completedTasks.addElement(task);

	}

	public boolean hasCompletedTask()
	{
		return completedTasks.size() > 0;

	}

	public CompletedTask removeCompletedTask()
	{

		CompletedTask task = completedTasks.elementAt(0);
		completedTasks.removeElementAt(0);
		return task;

	}

	public void notifyObservers()
	{
		if (changed)
		{
			for (int i = 0; i < observers.size(); i++)
			{
				AgentObserver o = observers.elementAt(i);
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
		futureState = state;
		hasFutureState = true;
		//currentState = state;
		return this;
	}
	
	public boolean hasFutureState()
	{
		return hasFutureState;
	}
	
	public void updateState()
	{
		if(hasFutureState)
		{
			currentState = futureState;
			hasFutureState = false;
		}
		
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
			if (cStates.elementAt(i).isSame(cstate))
			{
				same = true;
				break;
			}
		}
		if (!same)
		{
			cStates.addElement(cstate);
			setSendAll();
		}

	}

	public void removeCoupledState(CoupledState cstate)
	{
		cStates.removeElement(cstate);

	}

	public void removeCoupledState(String own, String target)
	{
		for(int i = 0; i < cStates.size(); i++)
		{
			CoupledState cstate = cStates.elementAt(i);
			if (own == cstate.getOwnState().name()
					&& target == cstate.getTargetState().name())
				cStates.removeElement(cstate);
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
