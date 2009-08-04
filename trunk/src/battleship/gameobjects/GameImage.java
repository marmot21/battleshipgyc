package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import battleship.EventManager;

public class GameImage extends GameObject
{
	private BufferedImage img;
	
	public GameImage()
	{
		
	}
	
	public GameImage(String name, Rectangle r)
	{
		super(name, r);
	}
	
	public GameImage(String name, Rectangle r, BufferedImage i)
	{
		super(name, r);
		img = i;
	}

	@Override
	public EventManager getEvents()
	{
		
		return null;
	}

	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img, r.x, r.y, null);
	}

	@Override
	public void pumpEvents(EventManager em)
	{

	}

	@Override
	public void render()
	{
	
	}

	@Override
	public void update()
	{
		
	}

}
