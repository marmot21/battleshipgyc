package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import battleship.EventManager;

public abstract class GameObject
{
	public Rectangle r;
	protected BufferedImage img;
	protected Graphics g;
	public String name = "";
	protected EventManager goem = new EventManager();
	
	public GameObject(Rectangle r)
	{
		this.r = r;
		
		if(img == null)
		{
			img = new BufferedImage(r.width, r.height, BufferedImage.TRANSLUCENT);
			g = img.getGraphics();
		}
	}
	
	public GameObject(Rectangle r, String s)
	{
		this.r = r;
		name = s;
		
		if(img == null)
		{
			img = new BufferedImage(r.width, r.height, BufferedImage.TRANSLUCENT);
			g = img.getGraphics();
		}
	}
	
	protected void resize(Rectangle r)
	{
		if(r.x != this.r.x || r.y != this.r.y || r.width != this.r.width || r.height != this.r.height)
		{
			this.r = r;
			img = new BufferedImage(this.r.width, this.r.height, BufferedImage.TRANSLUCENT);
			g = img.getGraphics();
			render();
		}
	}
	
	public abstract void update();
	public abstract void render();
	public abstract void paint(Graphics g);
	public abstract void pumpEvents(EventManager em);
	public abstract EventManager getEvents();
}