package battleship.states;

import java.awt.Graphics;

import battleship.EventManager;

/**
 * Generic State...
 * @author Amec
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
		return new EventManager();
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#pumpEvents(battleship.EventManager)
	 */
	@Override
	public void pumpEvents(EventManager iem)
	{
		
	}
}
