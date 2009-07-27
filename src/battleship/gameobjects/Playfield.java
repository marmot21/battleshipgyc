package battleship.gameobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;

public class Playfield extends GameObject
{
	public Dimension gridSize;
	public List<GameObject> obj = new ArrayList<GameObject>();
	
	public Playfield(Rectangle r, Dimension d)
	{
		super(new Rectangle(r.x, r.y, r.width*d.width+1, r.height*d.height+1));
		gridSize = d;
		render();
	}

	public Playfield(String s, Rectangle r, Dimension d)
	{
		super(new Rectangle(r.x, r.y, r.width*d.width+1, r.height*d.height+1));
		gridSize = d;
		name = s;
		render();
	}
	
	@Override
	public void update()
	{
		for(GameObject go : obj)
		{
			go.update();
		}
	}
	
	@Override
	public void render()
	{
		//draw grid
		g.setColor(Color.BLACK);
		for(int i = 0; i <= r.width/gridSize.width; i++)
		{
			g.drawLine(i*gridSize.width, 0, i*gridSize.width, r.height);
		}
		for(int i = 0; i <= r.height/gridSize.height; i++)
		{
			g.drawLine(0, i*gridSize.height, r.width, i*gridSize.height);
		}
		
		//draw battleships
		for(GameObject go : obj)
		{
			go.render();
		}
		
		//draw battleships to grid
		for(GameObject go : obj)
		{
			go.paint(g);
		}
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img, r.x, r.y, null);
	}

	@Override
	public EventManager getEvents()
	{
		return new EventManager();
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		
	}
}
