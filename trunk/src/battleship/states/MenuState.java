package battleship.states;

import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.Event;
import battleship.EventManager;
import battleship.gameobjects.*;

public class MenuState extends State// implements Runnable
{
	boolean threadSuspended = false;
	
	public MenuState()
	{
		name = "MenuState";
		obj.add(new Button(new Rectangle(128, 128, 1, 1), "HostGame", "battleship/res/img/", "HostGame0.gif", "HostGame1.gif", "HostGame2.gif"));
		obj.add(new Button(new Rectangle(378, 128, 1, 1), "JoinGame", "battleship/res/img/", "JoinGame0.gif", "JoinGame1.gif", "JoinGame2.gif"));
		obj.add(new Button(new Rectangle(628, 128, 1, 1), "SinglePlayer","battleship/res/img/", "SinglePlayer0.gif", "SinglePlayer1.gif", "SinglePlayer2.gif"));
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
	public EventManager getEvents()
	{
		return null;
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		for(GameObject go : obj)
			go.pumpEvents(em);
		for(int i = 0; i < em.size(); i++)
		{
			if(em.getEvent(i).event.equals("buttonClicked"))
			{
				Button b = (Button)em.getEvent(i).param;
				if(b.name.equals("HostGame"))
					em.trigger(new Event("setState", "GameState"));
			}
				
		}
	}
}