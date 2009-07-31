package battleship.states;

import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.Event;
import battleship.EventManager;
import battleship.gameobjects.Background;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameImage;
import battleship.gameobjects.GameObject;

public class MenuState extends State
{
	public MenuState()
	{
		name = "MenuState";
		obj.add(new Background(new Rectangle(0, 0, 800, 600), "BG"));
		obj.add(new Button(new Rectangle((800-210)/2-210-32, 256+128, 1, 1), "HostGame", "battleship/res/img/", "HostGame0.png", "HostGame1.png", "HostGame2.png"));
		obj.add(new Button(new Rectangle((800-210)/2, 256+128, 1, 1), "JoinGame", "battleship/res/img/", "JoinGame0.png", "JoinGame1.png", "JoinGame2.png"));
		obj.add(new Button(new Rectangle((800-210)/2+210+32, 256+128, 1, 1), "SinglePlayer","battleship/res/img/", "SinglePlayer0.png", "SinglePlayer1.png", "SinglePlayer2.png"));
		obj.add(new GameImage(new Rectangle((800-635)/2, 64, 1, 1), "MainTitle", "battleship/res/img/GameTitle.png"));
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
	public void pumpEvents(EventManager em)
	{
		for(GameObject go : obj)
			go.pumpEvents(em);
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).event.equals("buttonClicked"))
			{
				Button b = (Button)em.get(i).param;	
				if(b.name.equals("HostGame"))
				{
					sem.add(new Event("setState", "GameState"));
					sem.add(new Event("mode", "Host"));
				}
				else if(b.name.equals("JoinGame"))
				{
					sem.add(new Event("setState", "GameState"));
					sem.add(new Event("mode", "Join"));
				}
				else if(b.name.equals("SinglePlayer"))
				{
					sem.add(new Event("setState", "GameState"));
					sem.add(new Event("mode", "Single"));
				}
				em.consume(i);
			}
		}
	}
}