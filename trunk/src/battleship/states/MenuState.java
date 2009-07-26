package battleship.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import battleship.Event;
import battleship.EventManager;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameObject;

public class MenuState extends State// implements Runnable
{
	boolean threadSuspended = false;
	
	public MenuState()
	{
		name = "MenuState";
		
		obj.add(new Button(new Rectangle(128, 128, 1, 1), "HostGame", "battleship/res/img/", "HostGame0.gif", "HostGame1.gif", "HostGame2.gif"));
		obj.add(new Button(new Rectangle(378, 128, 1, 1), "JoinGame", "battleship/res/img/", "JoinGame0.gif", "JoinGame1.gif", "JoinGame2.gif"));
		obj.add(new Button(new Rectangle(628, 128, 1, 1), "SinglePlayer","battleship/res/img/", "SinglePlayer0.gif", "SinglePlayer1.gif", "SinglePlayer2.gif"));
		
		//example of creating a button at run-time
		BufferedImage img1 = new BufferedImage(192, 128, BufferedImage.TRANSLUCENT);
		BufferedImage img2 = new BufferedImage(192, 128, BufferedImage.TRANSLUCENT);
		BufferedImage img3 = new BufferedImage(192, 128, BufferedImage.TRANSLUCENT);
		Graphics g = img1.getGraphics();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, img1.getWidth(), img1.getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, img1.getWidth()-1, img1.getHeight()-1);
		g.drawString("LOL", 75, 50);
		g = img2.getGraphics();
		g.setColor(Color.ORANGE);
		g.fillRect(0, 0, img2.getWidth(), img2.getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, img2.getWidth()-1, img2.getHeight()-1);
		g.drawString("TEST", 75, 50);
		g = img3.getGraphics();
		g.setColor(Color.MAGENTA);
		g.fillRect(0, 0, img3.getWidth(), img3.getHeight());
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, img3.getWidth()-1, img3.getHeight()-1);
		g.drawString("BUTTON", 75, 50);
		obj.add(new Button(new Rectangle(208, 408, 192, 128), "TestButton", img1, img2, img3));
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
				if(b.name.equals("JoinGame"))
				{
					sem.add(new Event("setState", "GameState"));
					sem.add(new Event("mode", "Join"));
				}
				if(b.name.equals("SinglePlayer"))
				{
					sem.add(new Event("setState", "GameState"));
					sem.add(new Event("mode", "Single"));
				}
			}
				
		}
	}
}