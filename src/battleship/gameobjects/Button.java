package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import battleship.Event;
import battleship.EventManager;

public class Button extends GameObject
{
	private BufferedImage normal, hover, pressed;
	
	//the states a button can be in
	public static enum BUTTON
	{
		NORMAL, HOVER, PRESSED, ACTIVE
	}
	
	public BUTTON STATE = BUTTON.NORMAL;
	
	public Button(String name, Rectangle r)
	{
		super(name, r);
		goem = new EventManager();
	}
	
	//TODO Change it to one image, and clip it - more efficient.
	public void setImages(String b, String n, String h, String p)
	{
		if(!(b+n).equals(""))
			normal = loadImage(b+n);
		if(!(b+h).equals(""))
			hover = loadImage(b+h);
		if(!(b+p).equals(""))
			pressed = loadImage(b+p);
	}
	
	@Override
	public void paint(Graphics g)
	{
		switch(STATE)
		{
			case NORMAL:
			case ACTIVE:
				g.drawImage(normal, r.x, r.y, null);
				r.width = normal.getWidth();
				r.height = normal.getHeight();
				break;
			case HOVER:
				g.drawImage(hover, r.x, r.y, null);
				r.width = hover.getWidth();
				r.height = hover.getHeight();
				break;
			case PRESSED:
				g.drawImage(pressed, r.x, r.y, null);
				r.width = pressed.getWidth();
				r.height = pressed.getHeight();
				break;
		}
	}

	@Override
	public void render()
	{
		
	}

	@Override
	public void update()
	{
		
	}

	@Override
	public EventManager getEvents()
	{
		EventManager tmp = new EventManager();
		tmp.addAll(goem);
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
						goem.add(new Event("repaint"));
					}
					else if(r.contains(me.getPoint()) && STATE == BUTTON.ACTIVE)
					{
						STATE = BUTTON.PRESSED;
						goem.add(new Event("repaint"));
					}
					
					else if(!r.contains(me.getPoint()) && STATE == BUTTON.HOVER)
					{
						STATE = BUTTON.NORMAL;
						goem.add(new Event("repaint"));
					}
					else if(!r.contains(me.getPoint()) && STATE == BUTTON.PRESSED)
					{
						STATE = BUTTON.ACTIVE;
						goem.add(new Event("repaint"));
					}
				}
				else if(em.get(i).event.equals("mousePressed") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(r.contains(me.getPoint()))
					{
						STATE = BUTTON.PRESSED;
						goem.add(new Event("repaint"));
					}
				}
				else if(em.get(i).event.equals("mouseReleased") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(r.contains(me.getPoint()) && STATE == BUTTON.PRESSED)
					{
						goem.add(new Event("buttonClicked", (Object)this));
						STATE = BUTTON.HOVER;
						goem.add(new Event("repaint"));
					}
					else if(!r.contains(me.getPoint()))
					{
						STATE = BUTTON.NORMAL;
						goem.add(new Event("repaint"));
					}
				}
			}
		}
	}
}
