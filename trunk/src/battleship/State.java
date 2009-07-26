package battleship;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
//import java.awt.Image;
//import java.awt.image.ImageObserver;
//import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

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

class MenuState extends State implements Runnable
{
	Thread t;
	boolean threadSuspended = false;
	boolean enter = false;
	public MenuState()
	{
		name = "MenuState";
		enterEvents.add(new Event("init"));
		//enterEvents.add(new Event("leaveGame"));
		
		obj.add(new BHostGame(new Rectangle(128, 128, 1, 1)));
		obj.add(new BJoinGame(new Rectangle(378, 128, 1, 1)));
		obj.add(new BSinglePlayer(new Rectangle(628, 128, 1, 1)));
		//t = new Thread(this);
	}

	@Override
	public void enterState()
	{
		System.out.println("Enter State: " + name);
		threadSuspended = false;
		try {
			//t.start();
			//notify();
		} catch (IllegalThreadStateException e) {}
	}

	@Override
	public void exitState()
	{
		threadSuspended = true;
		Buttons.initChildren();
		System.out.println("Exit State: " + name);
	}

	@Override
	public void run()
	{
		//Thread t = Thread.currentThread();
		//while (this.t == t) {
			//try {
				// put source code here...
			//while (threadSuspended)
				//wait();
			//} catch (InterruptedException e) {
			//	e.printStackTrace();
			//}
		//}
		for (GameObject go : obj)
			go.update();
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
		for (GameObject go : obj)
			go.paint(g);
	}

	@Override
	public void onEvent(Event e, List<Object> o)
	{
		MouseEvent me = (MouseEvent) o.get(0);
		for(GameObject go : obj)
		{
			enter = false;
			if(go.getClass().getName().contains(".BHostGame"))
				ButtonHover(e,o,(BHostGame)go,me);
			if(go.getClass().getName().contains(".BJoinGame"))
				ButtonHover(e,o,(BJoinGame)go,me);
			if(go.getClass().getName().contains(".BSinglePlayer")) {
				ButtonHover(e,o,(BSinglePlayer)go,me);
				if(enter)
					events.add(new Event("single"));
			}
		}
	}
	
	private void ButtonHover (Event e, List<Object> o, Buttons btn, MouseEvent me) {
		if(e.event.equals("mouseMoved"))
		{
			if(btn.r.contains(me.getPoint()) && btn.STATE == Buttons.BUTTON.NORMAL)
			{
				btn.STATE = Buttons.BUTTON.HOVER;
				btn.render();
			}
			else if(!btn.r.contains(me.getPoint()) && btn.STATE == Buttons.BUTTON.HOVER || btn.STATE == Buttons.BUTTON.PRESSED)
			{
				btn.STATE = Buttons.BUTTON.NORMAL;
				btn.render();
			}
		}
		
		else if(e.event.equals("mousePressed"))
		{
			if(btn.r.contains(me.getPoint()))
			{
				btn.STATE = Buttons.BUTTON.PRESSED;
				btn.render();
			}
		}
		else if(e.event.equals("mouseReleased"))
		{
			if(btn.r.contains(me.getPoint()))
			{
				btn.STATE = Buttons.BUTTON.HOVER;
				enter = true;
			}
			else
			{
				btn.STATE = Buttons.BUTTON.NORMAL;
			}
			btn.render();
		}
		else events.add(e);
	}
}

//class 

class GameState extends State
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