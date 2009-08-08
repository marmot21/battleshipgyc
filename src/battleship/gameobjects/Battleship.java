package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import battleship.Event;
import battleship.EventManager;

public class Battleship extends GameObject
{
	public enum SHIPS
	{
		NORMAL, FOLLOW, INIT
	}
	
	public SHIPS STATE = SHIPS.NORMAL;
	public BufferedImage img;
	private Point mouse = new Point();
	private Battleship rotatedImg;
	protected static Rectangle statusScreen = new Rectangle(24, 240+24, 241+24, 241+240+24);
	protected static Position prevPos = new Position();
	protected static ArrayList<Battleship> inits = new ArrayList<Battleship>();
	
	public Battleship()
	{
		
	}
	
	public Battleship(String name, Rectangle r)
	{
		super(name, r);
		goem = new EventManager();
		this.r.x = inits.size()*30+400;
		this.r.y = ((inits.size()-inits.size()%2)/2)*26;
		inits.add(this);
	}
	
	public Battleship(String name, Rectangle r, BufferedImage i)
	{
		super(name, r);
		img = i;
		r.width = img.getWidth();
		r.height = img.getHeight();
		goem = new EventManager();
		this.r.x = inits.size()*30+400;
		this.r.y = ((inits.size()-inits.size()%2)/2)*26;
		inits.add(this);
	}
	
	public Battleship(String name, Rectangle r1, BufferedImage i1, Rectangle r2, BufferedImage i2) {
		this(name, r1, i1);
		rotatedImg = new Battleship(name, r2, i2);
	}

	@Override
	public void update()
	{
		
	}
	
	@Override
	public void render()
	{
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img, r.x, r.y, null);
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
				if(em.get(i).event.equals("mouseDragged") && STATE == SHIPS.FOLLOW) 
				{//if ship is being dragged then follow mouse
					r.x = me.getX() - mouse.x;
					r.y = me.getY() - mouse.y;
					goem.add(new Event("repaint"));
				}
				else if(em.get(i).event.equals("mouseReleased"))
				{
					if(STATE == SHIPS.FOLLOW && statusScreen.contains(me.getPoint()))
					{
						STATE = SHIPS.NORMAL;
						//snap it to the grid
						r.y = r.y/24*24;
						r.x = r.x/24*24;
						if(prevPos.STATE == SHIPS.INIT)
							inits.remove(this);
						goem.add(new Event("repaint"));
					}
					else if(STATE == SHIPS.FOLLOW && !statusScreen.contains(me.getPoint()))
					{ //if the ship was dragged outside of the grid then return it to its prev. pos.
						r.x = prevPos.x;
						r.y = prevPos.y;
						STATE = prevPos.STATE;
						goem.add(new Event("repaint"));
					}
					else if(STATE == SHIPS.NORMAL)
					{
						prevPos.x = r.x;
						prevPos.y = r.y;
						prevPos.STATE = STATE;
					}
				}
				else if(em.get(i).event.equals("mousePressed"))
				{
					if((STATE == SHIPS.INIT || STATE == SHIPS.NORMAL) && r.contains(me.getPoint()))
					{
						mouse.x = me.getX() - r.x;
						mouse.y = me.getY() - r.y;
						prevPos.x = r.x;
						prevPos.y = r.y;
						prevPos.STATE = STATE;
						STATE = SHIPS.FOLLOW;
					}
				}
				else if(em.get(i).event.equals("mouseWheelMoved"))
				{//rotate ship
					if(STATE == SHIPS.FOLLOW)
					{
						Battleship temp = new Battleship();
						copy(rotatedImg, temp);
						copy(this, rotatedImg);
						copy(temp, this);
						//set the position of the ship to mouse position
						r.x = me.getX() - mouse.x;
						r.y = me.getY() - mouse.y;
						goem.add(new Event("repaint"));
					}
						
				}
			}
		}
	}
	
	protected void copy(Battleship in, Battleship out)
	{
		out.r = in.r;
		out.img = in.img;
	}
}
