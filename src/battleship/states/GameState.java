package battleship.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import battleship.Event;
import battleship.gameobjects.GameObject;

public class GameState extends State
{
	public List<GameObject> obj = new ArrayList<GameObject>();
	public GameState()
	{
		name = "GameState";
		enterEvents.add(new Event("single"));
	}

	@Override
	public void enterState()
	{
		System.out.println("Enter State: " + name);
	}

	@Override
	public void exitState()
	{
		System.out.println("Exit State: " + name);
	}

	@Override
	public void run()
	{
		
	}

	@Override
	public List<Event> events()
	{
		List<Event> e = new ArrayList<Event>();
		e.addAll(events);
		events.clear();
		return e;
	}

	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawString("lol", 30, 30);
	}

	@Override
	public void onEvent(Event e, List<Object> o)
	{
		if(e.event.equals("mouseClicked"))
			events.add(new Event("init"));
		else events.add(e);
		
	}
}