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
	static public Dimension gridSize;//TODO:see if we can get this to be non-static - if need be
	public List<GameObject> obj = new ArrayList<GameObject>();
	
	public Playfield(Rectangle r, Dimension d)
	{
		this.r = new Rectangle(r.x, r.y, r.width*d.width, r.height*d.height);
		gridSize = d;
		render();
	}

	public Playfield(String s, Rectangle r, Dimension d)
	{
		this.r = new Rectangle(r.x, r.y, r.width*d.width, r.height*d.height);
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
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		for(int i = 0; i <= r.width/gridSize.width; i++)
		{
			g.drawLine(r.x+i*gridSize.width, r.y, r.x+i*gridSize.width, r.y+r.height);
		}
		for(int i = 0; i <= r.height/gridSize.height; i++)
		{
			g.drawLine(r.x, r.y+i*gridSize.height, r.x+r.width, r.y+i*gridSize.height);
		}
		for(GameObject go :obj)
			go.paint(g);//draw the ships
	}

	@Override
	public EventManager getEvents()
	{
		return new EventManager();
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		for(GameObject go : obj)
			go.pumpEvents(em); //pass on events to children - ships
	}
	public static Dimension getgridSize() {
		return gridSize;
	}
}
