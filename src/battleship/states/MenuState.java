package battleship.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.Event;
import battleship.EventManager;
import battleship.Main;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameImage;
import battleship.gameobjects.GameObject;

/**
 * Menu State~
 * @author Amec
 * @author Obi
 */

public class MenuState extends State
{
	/**
	 * Default constructor
	 * Adds buttons and main title
	 */
	public MenuState()
	{
		mName = "MenuState";
		mObj.add(new Button("HostGame", new Rectangle((800-210)/2-210-32, 256+128, 1, 1), GameObject.loadImage("res/img/HostGame.png")));
		mObj.add(new Button("JoinGame", new Rectangle((800-210)/2, 256+128, 1, 1), GameObject.loadImage("res/img/JoinGame.png")));
		mObj.add(new Button("SinglePlayer", new Rectangle((800-210)/2+210+32, 256+128, 1, 1), GameObject.loadImage("res/img/SinglePlayer.png")));
		mObj.add(new GameImage("MainTitle", new Rectangle((800-635)/2, 64, 1, 1), GameObject.loadImage("res/img/GameTitle.png")));
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#enterState()
	 */
	@Override
	public void enterState()
	{
		mStateEventMgr.add(new Event("repaint"));
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#exitState()
	 */
	@Override
	public void exitState()
	{
		for(GameObject go : mObj)
			go.reInit();
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#run()
	 */
	@Override
	public void run()
	{
		for(GameObject go : mObj)
			go.update();//call 'update' of child objects
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
			if(em.get(i).mEvent.equals("buttonClicked"))
			{
				mStateEventMgr.add(new Event("addState", "GameState"));
				Button b = (Button)em.get(i).mParam;	
				if(b.mName.equals("HostGame"))
				{
					mStateEventMgr.add(new Event("setState", "GameState"));
					mStateEventMgr.add(new Event("mode", "Host"));
				}
				else if(b.mName.equals("JoinGame"))
				{
					mStateEventMgr.add(new Event("setState", "GameState"));
					mStateEventMgr.add(new Event("mode", "Join"));
				}
				else if(b.mName.equals("SinglePlayer"))
				{
					mStateEventMgr.add(new Event("setState", "GameState"));
					mStateEventMgr.add(new Event("mode", "Single"));
				}
				em.consume(i);
			}
		}
		for(GameObject go : mObj)
			go.pumpEvents(em);
	}
}