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
	 * Returns the current state.
	 * @return
	 */
	public State getState()
	{
		return mStates.get(mCurrentState);
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
		if(Main.DEBUG)
			System.out.println("Exiting State: " + getState().mName); //debugging purposes, remove later
		getState().exitState();
		mCurrentState = stateNum;
		if(Main.DEBUG)
			System.out.println("Entering State: " + getState().mName); //debugging purposes, remove later
		getState().enterState();
	}

	/**
	 * Pumps all events to FSM, where it is tested whether
	 * a change of state is required.
	 * @param eventMgr The EventManager to be tested against.
	 */
	public void pumpEvents(EventManager eventMgr)
	{
		for(int i = 0; i < eventMgr.size(); i++)
		{
			if(eventMgr.get(i).mEvent.equals("setState"))
			{//if current event is a change state then change it and consume event
				if(setState((String)eventMgr.get(i).mParam)) {
					if(Main.DEBUG)
						System.out.println("state event consumed " + eventMgr.get(i).mParam.toString());
					eventMgr.consume(i);
				}
			}
		}
		getState().pumpEvents(eventMgr); //pump events to current state
	}
}
