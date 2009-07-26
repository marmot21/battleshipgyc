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
	
	public State getState()
	{
		return states.get(currentState);
	}
	
	public void setState(String s)
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
	
	public void setState(int i) //does this need to be public - internal call?
	{
		states.get(currentState).exitState();
		currentState = i;
		states.get(currentState).enterState();
	}
	
	public void run()
	{
		states.get(currentState).run();
		/*events.addAll(states.get(currentState).events());
		int e = 0;
		for(int i = 0; i < states.size(); i++)
		{
			for(int j = 0; j < states.get(i).enterEvents.size(); j++)
			{
				for(int k = 0; k < events.size(); k++)
				{
					if(states.get(i).enterEvents.get(j).event.equals(events.get(k).event))
					{ //if the event is equal to the entry condition for a state then enter it
						e++;
						if(e == states.get(i).enterEvents.size()) //what is this for?? 
							setState(i);
					}	
				}
			}
		}
		events.clear();*/
	}

	public void paint(Graphics g)
	{
		states.get(currentState).paint(g);
	}
}
