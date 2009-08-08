package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import battleship.Event;
import battleship.EventManager;

public class Button extends GameObject
{
	private BufferedImage img, img2;
	
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
	
	public Button(String name, Rectangle r, BufferedImage i)
	{
		super(name, r);
		img = i;
		render();
		goem = new EventManager();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img2, r.x, r.y, null);
	}

	@Override
	public void render()
	{
		img2 = null;
		r.height = img.getHeight()/3;
		r.width = img.getWidth();
		switch(STATE)
		{
			case NORMAL:
			case ACTIVE:
				img2 = img.getSubimage(0, 0, img.getWidth(), img.getHeight()/3);
				break;
			case HOVER:
				img2 = img.getSubimage(0, img.getHeight()/3, img.getWidth(), img.getHeight()/3);
				break;
			case PRESSED:
				img2 = img.getSubimage(0, img.getHeight()*2/3, img.getWidth(), img.getHeight()/3);
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
		EventManager tmp = null;
		try
		{
			tmp = goem.clone();
			goem.clear();
		}
		catch(CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
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
					if(r.contains(me.getPoint()))
					{
						if(STATE == BUTTON.NORMAL)
						{
							STATE = BUTTON.HOVER;
							render();
							goem.add(new Event("repaint"));
						}
						else if(STATE == BUTTON.ACTIVE)
						{
							STATE = BUTTON.PRESSED;
							render();
							goem.add(new Event("repaint"));
						}
					}
					else
					{
						if(STATE == BUTTON.HOVER)
						{
							STATE = BUTTON.NORMAL;
							render();
							goem.add(new Event("repaint"));
						}
						else if(STATE == BUTTON.PRESSED)
						{
							STATE = BUTTON.ACTIVE;
							render();
							goem.add(new Event("repaint"));
						}
					}
				}
				else if(em.get(i).event.equals("mousePressed") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(r.contains(me.getPoint()))
					{
						STATE = BUTTON.PRESSED;
						render();
						goem.add(new Event("repaint"));
					}
				}
				else if(em.get(i).event.equals("mouseReleased") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(STATE == BUTTON.PRESSED)
					{
						if(r.contains(me.getPoint()))
						{
							goem.add(new Event("buttonClicked", (Object)this));
							STATE = BUTTON.HOVER;
							render();
							goem.add(new Event("repaint"));
						}
						else
						{
							STATE = BUTTON.NORMAL;
							render();
							goem.add(new Event("repaint"));
						}
					}
				}
			}
		}
	}
}
