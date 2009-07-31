package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import battleship.EventManager;

public class Battleship extends GameObject
{
	public enum SHIPS {
		NORMAL, FOLLOW, INIT
	}
	public SHIPS STATE = SHIPS.NORMAL;
	
	public GameImage Image;
	
	public Battleship(GameImage i)
	{
		super(new Rectangle(0, 0, 1, 1));
		Image = i;
	}
	
	public Battleship(Rectangle r, GameImage i)
	{
		super(r);
		Image = i;
	}
	
	public Battleship(Rectangle r, String s, GameImage i)
	{
		super(r, s);
		Image = i;
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
						r.y = me.getY();
						resize();
					}
					if(r.contains(me.getPoint()) && STATE == SHIPS.NORMAL){
						STATE = SHIPS.FOLLOW;
					}
					
				}
				else if(em.get(i).event.equals("mouseReleased")) {
					if(STATE == SHIPS.FOLLOW){
						STATE = SHIPS.NORMAL;
						//snap to grid()
						
					}
				}
				else if(em.get(i).event.equals("mousePressed")) {
					if(STATE == SHIPS.NORMAL && r.contains(me.getPoint())) {
						STATE = SHIPS.FOLLOW;
					}
				}
			}
		}
	}
}
