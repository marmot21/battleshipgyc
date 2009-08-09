package battleship.states;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.Event;
import battleship.EventManager;
import battleship.Main;
import battleship.gameobjects.Battleship;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameObject;
import battleship.gameobjects.Playfield;

/**
 * The game state.
 * @author Amec
 * @author Obi
 */

public class GameState extends State
{
	/**
	 * Default constructor, does a few things:
	 * 1. sets the name 2. Adds the targeting and status screens 3. Adds objects to screens
	 */
	public GameState()
	{
		mName = "GameState";
		Playfield p = new Playfield("Status Screen", new Rectangle(24, 240+24, 10, 10), new Dimension(24, 24));
		p.mObj.add(new Battleship("BS", new Rectangle(0, 0, 49, 24), GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		mObj.add(new Playfield("Targeting Screen", new Rectangle(24, 0, 10, 10), new Dimension(24, 24)));
		mObj.add(p);
		mStateEventMgr.add(new Event("repaint"));
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
	 * @see battleship.states.State#run()
	 */
	@Override
	public void run()
	{
		for(GameObject go : mObj)
			go.update();
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Main.mDim.width, Main.mDim.height);
		for(GameObject go : mObj) 
			go.paint(g);
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#getEvents()
	 */
	@Override
	public EventManager getEvents()//output of events
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
	public void pumpEvents(EventManager em)//Input of events
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).mEvent.equals("mode"))
			{
				if(em.get(i).mParam.equals("Host"))
				{
					//insert host specific stuff here
					mStateEventMgr.add(new Event("setState", "MenuState"));
					mStateEventMgr.add(new Event("error", "Hosting not implemented"));
					em.consume(i);
				}
				else if(em.get(i).mParam.equals("Join"))
				{
					//insert join specific stuff here
					mStateEventMgr.add(new Event("setState", "MenuState"));
					mStateEventMgr.add(new Event("error", "Joining not implemented"));
					em.consume(i);
				}
				else if(em.get(i).mParam.equals("Single"))
				{
					//insert single specific stuff here
					mStateEventMgr.add(new Event("repaint"));
					em.consume(i);
				}
			}
			else if(em.get(i).mEvent.equals("buttonClicked"))
			{
				Button b = (Button)em.get(i).mParam;
				if(b.mName.equals("TestButton"))
				{
					mStateEventMgr.add(new Event("setState", "MenuState"));
					em.consume(i);
				}
			}
		}
		for(GameObject go : mObj)
			go.pumpEvents(em);
	}
}