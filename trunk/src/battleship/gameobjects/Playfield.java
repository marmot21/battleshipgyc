package battleship.gameobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;

/**
 * The grid which handles ships and bombing etc.
 * @author Amec
 * @author Obi
 */

public class Playfield extends GameObject
{
	static public Dimension mGridSize;//TODO:see if we can get this to be non-static - if need be
	//All the ships and bombs etc in the grid
	public List<GameObject> mObj = new ArrayList<GameObject>();
	
	/**
	 * 
	 * @param sName The name of the grid
	 * @param bounds The position of the grid and its dimensions in squares
	 * @param dim The dimensions of each grid square
	 */
	public Playfield(String sName, Rectangle bounds, Dimension dim)
	{
		this.mBounds = new Rectangle(bounds.x, bounds.y, bounds.width*dim.width, bounds.height*dim.height);
		mGridSize = dim;
		mName = sName;
		mGameObjEventMgr = new EventManager();
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.gameobjects.GameObject#update()
	 */
	@Override
	public void update()
	{
		for(GameObject go : mObj)
			go.update();
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.gameobjects.GameObject#render()
	 */
	@Override
	public void render()
	{
		
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.gameobjects.GameObject#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		for(int i = 0; i <= mBounds.width/mGridSize.width; i++)
		{
			g.drawLine(mBounds.x+i*mGridSize.width, mBounds.y, mBounds.x+i*mGridSize.width, mBounds.y+mBounds.height);
		}
		for(int i = 0; i <= mBounds.height/mGridSize.height; i++)
		{
			g.drawLine(mBounds.x, mBounds.y+i*mGridSize.height, mBounds.x+mBounds.width, mBounds.y+i*mGridSize.height);
		}
		for(GameObject go : mObj)
			go.paint(g);//draw the ships etc.
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.gameobjects.GameObject#getEvents()
	 */
	@Override
	public EventManager getEvents()//output of events
	{
		for(GameObject go : mObj)
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

	/**
	 * (non-Javadoc)
	 * @see battleship.gameobjects.GameObject#pumpEvents(battleship.EventManager)
	 */
	@Override
	public void pumpEvents(EventManager em)
	{
		for(GameObject go : mObj)
			go.pumpEvents(em); //pass on events to children - ships
	}
	
	/**
	 * Returns the size of the individual grid blocks
	 * @return Dimension Object
	 */
	public static Dimension getgridSize()
	{
		return mGridSize;
	}
}
