package battleship.states;

import battleship.EventManager;

public class GenericState extends State
{
	public GenericState()
	{
		name = "GenericState";
	}
	
	@Override
	public void enterState()
	{

	}

	@Override
	public void exitState()
	{
		
	}

	@Override
	public void paint()
	{

	}

	@Override
	public void run()
	{

	}

	@Override
	public EventManager getEvents()
	{
		return new EventManager();
	}

	@Override
	public void pumpEvents(EventManager iem)
	{
		
	}
}
