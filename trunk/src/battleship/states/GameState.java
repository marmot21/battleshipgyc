package battleship.states;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import battleship.Event;
import battleship.EventManager;
import battleship.gameobjects.*;

public class GameState extends State
{
	private List<GameObject> obj = new ArrayList<GameObject>();
	
	public GameState()
	{
		name = "GameState";
		Playfield p = new Playfield("Status Screen", new Rectangle(24, 0, 10, 10), new Dimension(24, 24));
		p.obj.add(new Battleship(new Rectangle(8*Playfield.gridSize.width, 4*Playfield.gridSize.height, 1*Playfield.gridSize.width, 2*Playfield.gridSize.height),
				new GameImage(new Rectangle(8*Playfield.gridSize.width, 4*Playfield.gridSize.height, 1*Playfield.gridSize.width, 2*Playfield.gridSize.height),"ship","battleship/res/img/Ships.png")));
		//p.render();
		//obj.add(p);
		obj.add(new Playfield("Targeting Screen", new Rectangle(24, 240+24, 10, 10), new Dimension(24, 24)));
		p.render();
		obj.add(p);
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
		for(GameObject go : obj)
			go.update();
	}

	@Override
	public void paint(Graphics g)
	{
		for(GameObject go : obj) 
			go.paint(g);
	}

	@Override
	public EventManager getEvents()//output of events
	{
		for(GameObject go : obj)
		{
			EventManager goem = go.getEvents();
			if(goem != null)
				for(int i = 0; i < goem.size(); i++)
					sem.add(goem.get(i));
		}
		
		EventManager tmp = new EventManager();
		for(int i = 0; i < sem.size(); i++)
			tmp.add(sem.get(i));
		sem.clear();
		return tmp;
	}

	@Override
	public void pumpEvents(EventManager em)//Input of events
	{
		for(GameObject go : obj)
			go.pumpEvents(em);
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).event.equals("mode"))
			{
				if(em.get(i).param.equals("Host"))
				{
					//insert host specific stuff here
					sem.add(new Event("setState", "MenuState"));
					sem.add(new Event("error", "Hosting not implemented"));
					em.consume(i);
				}
				else if(em.get(i).param.equals("Join"))
				{
					//insert join specific stuff here
					sem.add(new Event("setState", "MenuState"));
					sem.add(new Event("error", "Joining not implemented"));
					em.consume(i);
				}
				else if(em.get(i).param.equals("Single"))
				{
					//insert single specific stuff here
					
					em.consume(i);
				}
			}
			else if(em.get(i).event.equals("buttonClicked"))
			{
				Button b = (Button)em.get(i).param;
				if(b.name.equals("TestButton"))
				{
					sem.add(new Event("setState", "MenuState"));
					em.consume(i);
				}
			}
		}
	}
}