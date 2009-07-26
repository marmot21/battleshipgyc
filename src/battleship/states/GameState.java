package battleship.states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;
import battleship.gameobjects.GameObject;

public class GameState extends State
{
	public List<GameObject> obj = new ArrayList<GameObject>();
	
	public GameState()
	{
		name = "GameState";
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
	public void run()
	{
		
	}

	@Override
	public void paint(Graphics g)
	{
		
	}

	@Override
	public EventManager getEvents()
	{
		return sem;
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).event.equals("mode"))
			{
				if(em.get(i).param.equals("Host"))
				{
					//insert host specific stuff here
					
					em.consume(i);
				}
				else if(em.get(i).param.equals("Join"))
				{
					//insert join specific stuff here
					
					em.consume(i);
				}
				else if(em.get(i).param.equals("Single"))
				{
					//insert single specific stuff here
					
					em.consume(i);
				}
			}
		}
	}
}