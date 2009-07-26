package battleship.states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import battleship.Event;
import battleship.gameobjects.*;

public abstract class State
{
	public String name;
	public List<Event> enterEvents = new ArrayList<Event>();
	public List<Event> events = new ArrayList<Event>();
	public List<GameObject> obj = new ArrayList<GameObject>();
	
	public abstract void enterState();	
	public abstract void exitState();
	public abstract void run();
	public abstract void paint(Graphics g);
	public abstract List<Event> events();
	public abstract void onEvent(Event e, List<Object> params);
}