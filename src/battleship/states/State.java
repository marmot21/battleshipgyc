package battleship.states;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;
import battleship.gameobjects.GameObject;

public abstract class State
{
	public String name = "";
	public boolean repaint = false;
	protected BufferedImage img;
	protected Graphics g;
	protected List<GameObject> obj = new ArrayList<GameObject>();
	protected EventManager sem = new EventManager();
	
	public BufferedImage getImage()
	{
		return img;
	}
	
	public abstract void enterState();	
	public abstract void exitState();
	public abstract void run();
	public abstract void paint();
	public abstract void pumpEvents(EventManager em);
	public abstract EventManager getEvents();
}