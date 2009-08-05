package battleship;

import java.util.ArrayList;
import java.util.List;

import battleship.states.State;

public class FiniteStateMachine
{
	private List<State> states = new ArrayList<State>();
	private int currentState = 0;
	
	public void addState(State s)
	{
		states.add(s);
	}
	
	public State getState()
	{
		return states.get(currentState);
	}
	
	private void setState(String s)
	{
		for(int i = 0; i < states.size(); i++)
		{
			if(states.get(i).name.equals(s))
			{
				setState(i);
				i = states.size();
			}
		}
	}
	
	private void setState(int i)
	{
		if(Main.DEBUG)
			System.out.println("Exiting State: " + getState().name); //debugging purposes, remove later
		getState().exitState();
		currentState = i;
		if(Main.DEBUG)
			System.out.println("Entering State: " + getState().name); //debugging purposes, remove later
		getState().enterState();
	}

	public void pumpEvents(EventManager em)
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).event.equals("setState"))
			{
				setState((String)em.get(i).param);
				em.consume(i);
			}
		}
		getState().pumpEvents(em);
	}
}
