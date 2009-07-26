package battleship.states;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;
import battleship.gameobjects.GameObject;

public class GameState extends State
{
	public List<GameObject> obj = new ArrayList<GameObject>();
	public GameState()
	{
		name = "GameState";
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
	public void paint(Graphics g)
	{
		g.setColor(Color.RED);
		g.drawString("lol", 30, 30);
	}

	@Override
	public EventManager getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pumpEvents(EventManager iem) {
		// TODO Auto-generated method stub
		
	}
}