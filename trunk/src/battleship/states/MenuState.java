package battleship.states;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import battleship.*;
import battleship.gameobjects.*;

public class MenuState extends State implements Runnable
{
	Thread t;
	boolean threadSuspended = false;
	boolean enter = false; //what does this do?
	public MenuState()
	{
		name = "MenuState";
		enterEvents.add(new Event("init"));
		//enterEvents.add(new Event("leaveGame"));
		
		obj.add(new Button(new Rectangle(128, 128, 1, 1), "", "HostGame0.gif", "HostGame1.gif", "HostGame2.gif"));
		obj.add(new Button(new Rectangle(378, 128, 1, 1), "", "JoinGame0.gif", "JoinGame1.gif", "JoinGame2.gif"));
		obj.add(new Button(new Rectangle(628, 128, 1, 1), "", "SinglePlayer0.gif", "SinglePlayer1.gif", "SinglePlayer2.gif"));
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
		//Button.initChildren();
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
			if(go.getClass().getName().contains(".Button"))
				/*buttonHover(e,o,(BHostGame)go,me);
			if(go.getClass().getName().contains(".BJoinGame"))
				buttonHover(e,o,(BJoinGame)go,me);
			if(go.getClass().getName().contains(".BSinglePlayer")) {
				buttonHover(e,o,(BSinglePlayer)go,me);*/
			
				if(enter)
					events.add(new Event("single"));
		}
	}
	
	private void buttonHover(Event e, List<Object> o, Button btn, MouseEvent me)
	{
		if(e.event.equals("mouseMoved"))
		{
			if(btn.r.contains(me.getPoint()) && btn.STATE == Button.BUTTON.NORMAL)
			{
				btn.STATE = Button.BUTTON.HOVER;
				btn.render();
			}
			else if(!btn.r.contains(me.getPoint()) && btn.STATE == Button.BUTTON.HOVER || btn.STATE == Button.BUTTON.PRESSED)
			{
				btn.STATE = Button.BUTTON.NORMAL;
				btn.render();
			}
		}
		
		else if(e.event.equals("mousePressed"))
		{
			if(btn.r.contains(me.getPoint()))
			{
				btn.STATE = Button.BUTTON.PRESSED;
				btn.render();
			}
		}
		else if(e.event.equals("mouseReleased"))
		{
			if(btn.r.contains(me.getPoint()))
			{
				btn.STATE = Button.BUTTON.HOVER;
				enter = true;
			}
			else
			{
				btn.STATE = Button.BUTTON.NORMAL;
			}
			btn.render();
		}
		else
			events.add(e);
	}
}