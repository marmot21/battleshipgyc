package battleship.gameobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;

public class Playfield extends GameObject
{
	static public Dimension gridSize;//TODO:see if we can get this to be non-static - if need be
	public List<GameObject> obj = new ArrayList<GameObject>();
	
	public Playfield(Rectangle r, Dimension d)
	{
		this.mBounds = new Rectangle(r.x, r.y, r.width*d.width, r.height*d.height);
		gridSize = d;
		mGameObjEventMgr = new EventManager();
	}

	public Playfield(String s, Rectangle r, Dimension d)
	{
		this.mBounds = new Rectangle(r.x, r.y, r.width*d.width, r.height*d.height);
		gridSize = d;
		mName = s;
		mGameObjEventMgr = new EventManager();
	}
	
	@Override
	public void update()
	{
		for(GameObject go : obj)
			go.update();
	}
	
	@Override
	public void render()
	{
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		for(int i = 0; i <= mBounds.width/gridSize.width; i++)
		{
			g.drawLine(mBounds.x+i*gridSize.width, mBounds.y, mBounds.x+i*gridSize.width, mBounds.y+mBounds.height);
		}
		for(int i = 0; i <= mBounds.height/gridSize.height; i++)
		{
			g.drawLine(mBounds.x, mBounds.y+i*gridSize.height, mBounds.x+mBounds.width, mBounds.y+i*gridSize.height);
		}
		for(GameObject go : obj)
			go.paint(g);//draw the ships
	}
	
	@Override
	public EventManager getEvents()//output of events
	{
		for(GameObject go : obj)
			mGameObjEventMgr.addAll(go.getEvents());
		EventManager tmp = null;
		try
		{
			tmp = mGameObjEventMgr.clone();
			mGameObjEventMgr.clear();
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
		for(GameObject go : obj)
			go.pumpEvents(em); //pass on events to children - ships
	}
	public static Dimension getgridSize() {
		return gridSize;
	}
}
