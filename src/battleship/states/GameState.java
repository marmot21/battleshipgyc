package battleship.states;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import battleship.Event;
import battleship.EventManager;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameObject;
import battleship.gameobjects.Playfield;

public class GameState extends State
{
	private List<GameObject> obj = new ArrayList<GameObject>();
	
	public GameState()
	{
		name = "GameState";
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
		obj.add(new Playfield(new Point(128, 0), new Dimension(20, 20), new Dimension(16, 16)));
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