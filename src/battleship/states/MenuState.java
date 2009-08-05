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

public class MenuState extends State
{
	public MenuState()
	{
		name = "MenuState";
		//TODO Edit constructor once one image is loaded.
		Button b1 = new Button("HostGame", new Rectangle((800-210)/2-210-32, 256+128, 1, 1));
		b1.setImages("res/img/", "HostGame0.png", "HostGame1.png", "HostGame2.png");
		obj.add(b1);
		Button b2 = new Button("JoinGame", new Rectangle((800-210)/2, 256+128, 1, 1));
		b2.setImages("res/img/", "JoinGame0.png", "JoinGame1.png", "JoinGame2.png");
		obj.add(b2);
		Button b3 = new Button("SinglePlayer", new Rectangle((800-210)/2+210+32, 256+128, 1, 1));
		b3.setImages("res/img/", "SinglePlayer0.png", "SinglePlayer1.png", "SinglePlayer2.png");
		obj.add(b3);
		obj.add(new GameImage("MainTitle", new Rectangle((800-635)/2, 64, 1, 1), GameObject.loadImage("res/img/GameTitle.png")));
	}

	@Override
	public void enterState()
	{
		sem.add(new Event("repaint"));
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
	public EventManager getEvents()
	{
		for(GameObject go : obj)
			sem.addAll(go.getEvents());
		EventManager tmp = new EventManager();
		tmp.addAll(sem);
		sem.clear();
		return tmp;
	}

	@Override
	public void pumpEvents(EventManager em)
	{
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
		for(GameObject go : obj)
			go.pumpEvents(em);
	}
}