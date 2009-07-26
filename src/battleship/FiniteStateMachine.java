package battleship;

import java.awt.Graphics;
import java.util.*;

import battleship.states.State;

public class FiniteStateMachine
{
	private List<State> states = new ArrayList<State>();
	private int currentState = 0;
	public EventManager iem = new EventManager(); //ieq = Input EventManager
	public EventManager em = new EventManager();
	
	public FiniteStateMachine()
	{
		
	}
	
	public void addState(State s)
	{
		states.add(s);
	}
	
	private State getState()
	{
		return states.get(currentState);
	}
	
	public void setState(String s) //change to private later
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
		System.out.println("Exiting State: " + getState().name);
		getState().exitState();
		currentState = i;
		System.out.println("Entering State: " + getState().name);
		getState().enterState();
	}
	
	public void run()
	{
		getState().run();
	}

	public void paint(Graphics g)
	{
		getState().paint(g);
	}
}
