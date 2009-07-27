package battleship;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import battleship.states.State;

public class FiniteStateMachine
{
	private List<State> states = new ArrayList<State>();
	private int currentState = 0;
	public EventManager em = new EventManager();
	public EventManager iem = new EventManager();
	
	public void addState(State s)
	{
		states.add(s);
	}
	
	private State getState()
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
		if(App.DEBUG)
			System.out.println("Exiting State: " + getState().name); //debugging purposes, remove later
		getState().exitState();
		currentState = i;
		if(App.DEBUG)
			System.out.println("Entering State: " + getState().name); //debugging purposes, remove later
		getState().enterState();
	}
	
	public void run()
	{
		getState().run();
		em.flush(); //get rid of old input events
		for(int i = 0; i < iem.size(); i++) //add new input events
			em.add(iem.get(i));
		iem.clear(); //clear Input Event Manager, no longer needed
		getState().pumpEvents(em); //'pump' events to current state
		
		//get events from current state
		EventManager em2 = getState().getEvents();
		if(em2 != null)
		{
			for(int i = 0; i < em2.size(); i++)
			{
				em.add(em2.get(i));
			}
		}
		
		//loops through all events to check if something needs to be done
		for(int i = 0; i < em.size(); i++)
		{
			//change state if needed (if event "setState" is fired)
			if(em.get(i).event.equals("setState"))
			{
				setState((String)em.get(i).param);
				em.consume(i);
				run(); //makes sure it changes straight away
			}
			else if(em.get(i).event.equals("error"))
			{
				System.out.println(em.get(i).param);
				em.consume(i);
			}
		}
	}

	public void paint(Graphics g)
	{
		getState().paint(g); //paint the current state to double buffer
	}
}
