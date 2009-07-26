package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import battleship.Event;
import battleship.EventManager;

public class Button extends GameObject
{
	protected BufferedImage normal, hover, pressed;
	
	public static enum BUTTON
	{
		NORMAL, HOVER, PRESSED
	}
	
	public BUTTON STATE = BUTTON.NORMAL;
	
	public Button(Rectangle r, String s)
	{
		super(r, s);
		render();
	}
	
	public Button(Rectangle r, String s, BufferedImage n, BufferedImage h, BufferedImage p)
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
		normal = loadImage(b+n);
		hover = loadImage(b+h);
		pressed = loadImage(b+p);
		render();
	}
	
	public BufferedImage loadImage(String path)
	{
		BufferedImage b = null;
		
		try
		{
			 b = ImageIO.read(new File(path));
		}
		catch (IOException e)
		{
			System.out.println("Unable to load \"" + path + "\"");
			b = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		}
		
		return b;
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
				resize(new Rectangle(r.x, r.y, normal.getWidth(), normal.getHeight()));
				g.drawImage(normal, 0, 0, null);
				break;
			case HOVER:
				resize(new Rectangle(r.x, r.y, hover.getWidth(), hover.getHeight()));
				g.drawImage(hover, 0, 0, null);
				break;
			case PRESSED:
				resize(new Rectangle(r.x, r.y, pressed.getWidth(), pressed.getHeight()));
				g.drawImage(pressed, 0, 0, null);
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
				if(em.get(i).event.equals("mouseMoved"))
				{
					if(r.contains(me.getPoint()) && STATE == Button.BUTTON.NORMAL)
					{
						STATE = Button.BUTTON.HOVER;
						render();
					}
					else if(!r.contains(me.getPoint()) && STATE == Button.BUTTON.HOVER || STATE == Button.BUTTON.PRESSED)
					{
						STATE = Button.BUTTON.NORMAL;
						render();
					}
				}
				
				else if(em.get(i).event.equals("mousePressed"))
				{
					if(r.contains(me.getPoint()))
					{
						STATE = Button.BUTTON.PRESSED;
						render();
					}
				}
				else if(em.get(i).event.equals("mouseReleased"))
				{
					if(r.contains(me.getPoint()))
					{
						STATE = Button.BUTTON.HOVER;
						goem.add(new Event("buttonClicked", (Object)this));
					}
					else
					{
						STATE = Button.BUTTON.NORMAL;
					}
					render();
				}
			}
		}
	}
}
