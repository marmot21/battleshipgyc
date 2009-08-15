package battleship.states;

import java.awt.Graphics;

import battleship.Event;
import battleship.EventManager;
import battleship.gameobjects.GameObject;

/**
 * Generic State...
 * @author Amec
 * @author OBi
 *
 */

public class GenericState extends State
{
	/**
	 * Default constructor
	 */
	public GenericState()
	{
		mName = "GenericState";
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#enterState()
	 */
	@Override
	public void enterState()
	{

	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#exitState()
	 */
	@Override
	public void exitState()
	{
		
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{

	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#run()
	 */
	@Override
	public void run()
	{

	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#getEvents()
	 */
	@Override
	public EventManager getEvents()
	{
		for(GameObject go : mObj)
			mStateEventMgr.addAll(go.getEvents());
		EventManager tmp = null;
		try
		{
			tmp = mStateEventMgr.clone();
			mStateEventMgr.clear();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return tmp;
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#pumpEvents(battleship.EventManager)
	 */
	@Override
	public void pumpEvents(EventManager em)
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).mEvent.startsWith("BeginGame")) {
				if(em.get(i).mParam.equals("SinglePlayer")){
					mStateEventMgr.add(new Event("setState", "UserState"));
					em.consume(i);
				}
			}
		}//end for loop
	}//end PumpEvents
}
