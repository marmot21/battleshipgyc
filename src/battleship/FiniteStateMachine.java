package battleship;

import java.util.ArrayList;
import java.util.List;

import battleship.states.State;

/**
 * A Finite State Machine which is used to separate
 * the game's logic in to separate 'states', each of
 * which has it's own unique code.
 * @author Amec
 * @author OBi
 */

public class FiniteStateMachine
{
	/**
	 * A list of all the states
	 */
	private List<State> m_States = new ArrayList<State>();
	
	/**
	 * The current state
	 */
	private int mCurrentState = 0;
	
	/**
	 * The name of the FSM
	 */
	public String m_Name = "";
	
	/**
	 * Default constructor.
	 */
	public FiniteStateMachine()
	{
		
	}
	
	/**
	 * Adds the state to the State Machine 
	 * @param state The state to be added.
	 */
	public void addState(State state)
	{
		m_States.add(state);
	}
	
	/**
	 * Removes the state from the State Machine
	 * @param state The state to be removed
	 */
	private void removeState(int state)
	{
		m_States.remove(state);
	}
	
	/**
	 * Removes the state from the State Machine
	 * @param str The name of the state to be removed
	 */
	public void removeState(String str)
	{
		for(int i=0; i<m_States.size(); i++)
			if(m_States.get(i).mName.equals(str))
				removeState(i);
	}
	
	/**
	 * Removes the state from the State Machine
	 * @param state The state to be removed
	 */
	public void removeState(State state)
	{
		State st = 	m_States.get(get(state));
		st = null;
		m_States.remove(state);
	}
	
	/**
	 * Returns the current state.
	 * @return
	 */
	public State getState()
	{
			return m_States.get(mCurrentState);
	}
	
	/**
	 * Returns a int place holder for the state
	 * @param state
	 * The state
	 * @return
	 * Index of the state. -1 if not found.
	 */
	protected int get(State state)
	{
		for(int i=0; i<m_States.size(); i++)
			if(m_States.get(i).equals(state))
				return i;
		return -1;
	}
	
	/**
	 * Sets the current state to a string str which is equal to
	 * State.mName of the current state. If the string does not
	 * match, then nothing is changed.
	 * @param str The string to check for equality.
	 * @return True if state change was successful
	 */
	private boolean setState(String str)
	{
		for(int i = 0; i < m_States.size(); i++)
		{
			if(m_States.get(i).mName.equals(str))
			{
				setState(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets the current state to the value of stateNum 
	 * @param stateNum 	The number of which state the
	 * 					current state is changed to. 
	 */
	private void setState(int stateNum)
	{
		getState().exitState();
		mCurrentState = stateNum;
		getState().enterState();
	}

	/**
	 * Pumps all events to FSM, where it is tested whether
	 * a change of state is required.
	 * @param eventMgr The EventManager to be tested against.
	 */
	public void processEvents()
	{
		for(int i = 0; i < Events.get().size(); i++)
		{
			if(Events.get().get(i).m_Target.equals(m_Name))
			{
				//if current event is a delete state then delete it and consume event
				if(Events.get().get(i).m_Event.equals("removeState"))
				{
					//if(mCurrentState < mStates.indexOf((String)eventMgr.get(i).mParam))
						//mCurrentState--;//assuming that the surrent state is removed
					//removeState((State)eventMgr.get(i).mParam);
					removeState((String)Events.get().get(i).m_Param);
					Events.get().remove(i);
				}
				//if current event is a change state then change it and consume event
				else if(Events.get().get(i).m_Event.equals("setState"))
				{
					//if(eventMgr.get(i).mTarget.equals("FuSM"))
					//	System.out.println("state shange consumed FuSM: " + 
					//			eventMgr.get(i).mParam);
					if(setState((String)Events.get().get(i).m_Param))
						Events.get().remove(i);
				}
				else if(Events.get().get(i).m_Event.equals("addState"))
				{
					addState((State)Events.get().get(i).m_Param);
					Events.get().remove(i);
				}
			}
		}
	}
}
