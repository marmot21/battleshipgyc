package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.EventManager;

public class Battleship extends GameObject
{
	public Battleship()
	{
		super(new Rectangle());
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
		
	}
	
	@Override
	public void paint(Graphics g)
	{

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
