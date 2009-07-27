package battleship.gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.EventManager;

public class Battleship extends GameObject
{
	public Battleship()
	{
		super(new Rectangle(0, 0, 1, 1));
	}
	
	public Battleship(Rectangle r)
	{
		super(r);
	}
	
	public Battleship(Rectangle r, String s)
	{
		super(r, s);
	}


	@Override
	public void update()
	{
		
	}
	
	@Override
	public void render()
	{
		g.setColor(Color.RED);
		g.fillRect(1, 1, r.width-1, r.height-1);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img, r.x, r.y, null);
	}

	@Override
	public EventManager getEvents()
	{
		return null;
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		
	}
}
