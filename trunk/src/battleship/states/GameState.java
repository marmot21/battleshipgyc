package battleship.states;

import java.awt.Color;
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
		g.setColor(Color.RED);
		g.drawString("lol", 30, 30);
	}

	@Override
	public EventManager getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.getEvent(i).event.equals("mode"))
			{
				if(em.getEvent(i).param.equals("Host"))
				{
					//insert host specific stuff here
					
					em.consume(i);
				}
				else if(em.getEvent(i).param.equals("Join"))
				{
					//insert join specific stuff here
					
					em.consume(i);
				}
				else if(em.getEvent(i).param.equals("Single"))
				{
					//insert single specific stuff here
					
					em.consume(i);
				}
			}
		}
	}
}