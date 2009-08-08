package battleship.states;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import battleship.Event;
import battleship.EventManager;
import battleship.Main;
import battleship.gameobjects.Battleship;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameObject;
import battleship.gameobjects.Playfield;

public class GameState extends State
{
	private List<GameObject> obj = new ArrayList<GameObject>();
	
	public GameState()
	{
		name = "GameState";
		Playfield p = new Playfield("Status Screen", new Rectangle(24, 240+24, 10, 10), new Dimension(24, 24));
		p.obj.add(new Battleship("BS", new Rectangle(0, 0, 49, 24), GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		obj.add(new Playfield("Targeting Screen", new Rectangle(24, 0, 10, 10), new Dimension(24, 24)));
		obj.add(p);
		sem.add(new Event("repaint"));
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
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Main.dim.width, Main.dim.height);
		for(GameObject go : obj) 
			go.paint(g);
	}

	@Override
	public EventManager getEvents()//output of events
	{
		for(GameObject go : obj)
			sem.addAll(go.getEvents());
		EventManager tmp = null;
		try
		{
			tmp = sem.clone();
			sem.clear();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return tmp;
	}

	@Override
	public void pumpEvents(EventManager em)//Input of events
	{
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
					sem.add(new Event("repaint"));
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
		for(GameObject go : obj)
			go.pumpEvents(em);
	}
}