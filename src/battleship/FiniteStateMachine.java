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
	private List<State> mStates = new ArrayList<State>();
	
	/**
	 * The current state
	 */
	private int mCurrentState = 0;
	
	/**
	 * The name of the FSM
	 */
	public String mName = "";
	
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
		mStates.add(state);
	}
	
	/**
	 * Removes the state from the State Machine
	 * @param state The state to be removed
	 */
	private void removeState(int state)
	{
		mStates.remove(state);
	}
	
	/**
	 * Removes the state from the State Machine
	 * @param str The name of the state to be removed
	 */
	public void removeState(String str)
	{
		for(int i=0; i<mStates.size(); i++)
			if(mStates.get(i).mName.equals(str))
				removeState(i);
	}
	
	/**
	 * Removes the state from the State Machine
	 * @param state The state to be removed
	 */
	public void removeState(State state)
	{
		State st = 	mStates.get(get(state));
		st = null;
		mStates.remove(state);
	}
	
	/**
	 * Returns the current state.
	 * @return
	 */
	public State getState()
	{
			return mStates.get(mCurrentState);
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
		for(int i=0; i<mStates.size(); i++)
			if(mStates.get(i).equals(state))
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
		for(int i = 0; i < mStates.size(); i++)
		{
			if(mStates.get(i).mName.equals(str))
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
	public void processEvents(EventManager eventMgr)
	{
		for(int i = 0; i < eventMgr.size(); i++)
		{
			if(eventMgr.get(i).mTarget.equals(mName))
			{
				//if current event is a delete state then delete it and consume event
				if(eventMgr.get(i).mEvent.equals("removeState"))
				{
					//if(mCurrentState < mStates.indexOf((String)eventMgr.get(i).mParam))
						//mCurrentState--;//assuming that the surrent state is removed
					//removeState((State)eventMgr.get(i).mParam);
					removeState((String)eventMgr.get(i).mParam);
					eventMgr.consume(i);
				}
				//if current event is a change state then change it and consume event
				else if(eventMgr.get(i).mEvent.equals("setState"))
				{
					if(setState((String)eventMgr.get(i).mParam))
						eventMgr.consume(i);
				}
				else if(eventMgr.get(i).mEvent.equals("addState"))
				{
					addState((State)eventMgr.get(i).mParam);
					eventMgr.consume(i);
				}
			}
		}
	}
}
