package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import battleship.Event;
import battleship.EventManager;

public class Button extends GameObject
{
	protected GameImage normal, hover, pressed;
	
	//the states a button can be in
	public static enum BUTTON
	{
		NORMAL, HOVER, PRESSED, ACTIVE
	}
	
	public BUTTON STATE = BUTTON.NORMAL;
	
	public Button(Rectangle r, String s)
	{
		super(r, s);
		render();
	}
	
	public Button(Rectangle r, String s, GameImage n, GameImage h, GameImage p)
	{
		super(r, s);
		normal = n;
		hover = h;
		pressed = p;
		render();
	}
	
	public Button(Rectangle r, String s, String b, String n, String h, String p)
	{
		super(r, s);
		normal = new GameImage(new Rectangle(0, 0, 1, 1), "normal", b+n);
		hover = new GameImage(new Rectangle(0, 0, 1, 1), "hover", b+h);
		pressed = new GameImage(new Rectangle(0, 0, 1, 1), "pressed", b+p);
		render();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img, r.x, r.y, null);
	}

	@Override
	public void render()
	{
		switch(STATE)
		{
			case NORMAL:
			case ACTIVE:
				resize(new Rectangle(r.x, r.y, normal.img.getWidth(), normal.img.getHeight()));
				g.drawImage(normal.img, 0, 0, null);
				break;
			case HOVER:
				resize(new Rectangle(r.x, r.y, hover.img.getWidth(), hover.img.getHeight()));
				g.drawImage(hover.img, 0, 0, null);
				break;
			case PRESSED:
				resize(new Rectangle(r.x, r.y, pressed.img.getWidth(), pressed.img.getHeight()));
				g.drawImage(pressed.img, 0, 0, null);
				break;
		}
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public EventManager getEvents()
	{
		EventManager tmp = new EventManager();
		
		for(int i = 0; i < goem.size(); i++)
			tmp.add(goem.get(i));
		
		goem.clear();
		return tmp;
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).event.startsWith("mouse"))
			{
				MouseEvent me = (MouseEvent) em.get(i).param;
				if(em.get(i).event.equals("mouseMoved") || em.get(i).event.equals("mouseDragged"))
				{
					if(r.contains(me.getPoint()) && STATE == BUTTON.NORMAL)
					{
						STATE = BUTTON.HOVER;
						render();
					}
					else if(r.contains(me.getPoint()) && STATE == BUTTON.ACTIVE)
					{
						STATE = BUTTON.PRESSED;
						render();
					}
					
					else if(!r.contains(me.getPoint()) && STATE == BUTTON.HOVER)
					{
						STATE = BUTTON.NORMAL;
						render();
					}
					else if(!r.contains(me.getPoint()) && STATE == BUTTON.PRESSED)
					{
						STATE = BUTTON.ACTIVE;
						render();
					}
				}
				else if(em.get(i).event.equals("mousePressed") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(r.contains(me.getPoint()))
					{
						STATE = BUTTON.PRESSED;
						render();
					}
				}
				else if(em.get(i).event.equals("mouseReleased") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(r.contains(me.getPoint()) && STATE == BUTTON.PRESSED)
					{
						goem.add(new Event("buttonClicked", (Object)this));
						STATE = BUTTON.HOVER;
						render();
					}
					else if(!r.contains(me.getPoint()))
					{
						STATE = BUTTON.NORMAL;
						render();
					}
				}
			}
		}
	}
}
