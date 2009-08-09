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
 * @author Obi
 * @author Amec
 *
 */
public class Playfield extends GameObject
{
	static public Dimension mGridSize;//TODO:see if we can get this to be non-static - if need be
	//All the ships and bombs etc in the grid
	public List<GameObject> mObj = new ArrayList<GameObject>();
	
	/**
	 * A constructor which creates a grid without a name
	 * @param rect The position of the grid and its dimensions in squares
	 * @param dim The dimensions of each grid square
	 */
	public Playfield(Rectangle rect, Dimension dim)
	{
		this.mBounds = new Rectangle(rect.x, rect.y, rect.width*dim.width, rect.height*dim.height);
		mGridSize = dim;
		mGameObjEventMgr = new EventManager();
	}

	/**
	 * 
	 * @param sName The name of the grid
	 * @param rect The position of the grid and its dimensions in squares
	 * @param dim The dimensions of each grid square
	 */
	public Playfield(String sName, Rectangle rect, Dimension dim)
	{
		this.mBounds = new Rectangle(rect.x, rect.y, rect.width*dim.width, rect.height*dim.height);
		mGridSize = dim;
		mName = sName;
		mGameObjEventMgr = new EventManager();
	}
	
	/**
	 * Updates all child objects - ships etc. 
	 */
	@Override
	public void update()
	{
		for(GameObject go : mObj)
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
	public static Dimension getgridSize() {
		return mGridSize;
	}
}
