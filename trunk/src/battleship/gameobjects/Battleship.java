package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import battleship.EventManager;

public class Battleship extends GameObject
{
	public enum SHIPS {
		NORMAL, FOLLOW, INIT
	}
	public SHIPS STATE = SHIPS.NORMAL;
	protected GameImage Image;
	protected static Rectangle statusScreen = new Rectangle(24, 240+24, 241+24, 241+240+24);
	protected static Position prevPos = new Position();
	protected static ArrayList<Battleship> inits = new ArrayList<Battleship>();
	
	public Battleship(GameImage i)
	{
		super(new Rectangle(0, 0, 1, 1));
		Image = i;
		r.x = inits.size()*30+400;
		r.y = ((inits.size()-inits.size()%2)/2)*26;
		inits.add(this);
	}
	
	public Battleship(Rectangle r, GameImage i)
	{
		super(r);
		Image = i;
		r.x = inits.size()*30+400;
		r.y = (inits.size()-inits.size()%2/2)*26;
		inits.add(this);
	}
	
	public Battleship(Rectangle r, String s, GameImage i)
	{
		super(r, s);
		Image = i;
		r.x = inits.size()*30+400;
		r.y = (inits.size()-inits.size()%2/2)*26;
		inits.add(this);
	}


	@Override
	public void update()
	{

	}
	
	@Override
	public void render()
	{
		//g.drawImage(Image.img, r.x, r.y, null);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(Image.img, r.x, r.y, null);
	}

	@Override
	public EventManager getEvents()
	{
		return null;
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).event.startsWith("mouse"))
			{
				MouseEvent me = (MouseEvent) em.get(i).param;
				if(em.get(i).event.equals("mouseDragged"))
				{
					
					if(STATE == SHIPS.FOLLOW) {
						r.x = me.getX()-r.width/2;
						r.y = me.getY();//-r.height/2;
					}
					
				}
				else if(em.get(i).event.equals("mouseReleased")) {
					if(STATE == SHIPS.FOLLOW && statusScreen.contains(me.getPoint())) {
						STATE = SHIPS.NORMAL;
						//snap it to the grid
						r.y = r.y/24*24;
						r.x = r.x/24*24;
						if(prevPos.STATE == SHIPS.INIT) {
							inits.remove(this);
						}
					}
					else if(STATE == SHIPS.FOLLOW && !statusScreen.contains(me.getPoint())) {
						r.x = prevPos.x;
						r.y = prevPos.y;
						STATE = prevPos.STATE;
					}
					else if(STATE == SHIPS.NORMAL) {
						prevPos.x = r.x;
						prevPos.y = r.y;
						prevPos.STATE = STATE;
					}
				}
				else if(em.get(i).event.equals("mousePressed")) {
					if(STATE == SHIPS.INIT || STATE == SHIPS.NORMAL && r.contains(me.getPoint())) {
						prevPos.x = r.x;
						prevPos.y = r.y;
						prevPos.STATE = STATE;
						STATE = SHIPS.FOLLOW;
					}
				}
			}
		}
	}
}
