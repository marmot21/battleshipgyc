package battleship.states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;
import battleship.gameobjects.GameObject;

public abstract class State
{
	public String name = "";
	protected List<GameObject> obj = new ArrayList<GameObject>();
	protected EventManager sem = new EventManager();
	public static boolean DEBUG = true;
	public abstract void enterState();	
	public abstract void exitState();
	public abstract void run();
	public abstract void paint(Graphics g);
	public abstract void pumpEvents(EventManager em);
	public abstract EventManager getEvents();
}