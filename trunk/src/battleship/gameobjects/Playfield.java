package battleship.gameobjects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import battleship.Event;
import battleship.EventManager;
import battleship.FiniteStateMachine;

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
	 * The 3D array which stores the positions of the ships or bombs
	 * X and Y are the grid position
	 * Z is the status of the ship at that position
	 */
	public int Grid[][][] = new int[10][10][1];
	private static Point mXY = new Point(-1, -1);
	private boolean mTargetArrows = false;
	/**
	 * Fuzzy State Machine
	 * Used by the targeting window only
	 */
	public FiniteStateMachine mFuSM = null;
	
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
		for(GameObject go : mObj) {
			go.update();
		}
		if(mFuSM != null)
			mFuSM.getState().run();
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
		if(mTargetArrows && mXY.x>=0 && mXY.y>=0){
			g.setColor(new Color(255, 0, 0, 128));//Vert line
			g.fillRect(mXY.x*mGridSize.width+24, 0, mGridSize.width, mBounds.height);

			g.setColor(new Color(0, 0, 255, 128));//Hor line
			g.fillRect(24, mXY.y*mGridSize.height, mBounds.width, mGridSize.height);
		}
		for(GameObject go : mObj)
			go.paint(g);//draw the ships etc.
		if(mFuSM != null)
			mFuSM.getState().paint(g);
	}

	@Override
	public EventManager getEvents()//output of events
	{
		for(GameObject go : mObj)
			mGameObjEventMgr.addAll(go.getEvents());
		if(mFuSM != null)
			mGameObjEventMgr.addAll(mFuSM.getState().getEvents());//add the events from the FuSM
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
	if(mFuSM != null)//checks if it is necessary to do all this
		 for(int i = 0; i < em.size(); i++)
		 { 
			 if(em.get(i).mEvent.startsWith("setField"))
			 {
				 if(em.get(i).mParam.equals("TargetArrows"))
					 mTargetArrows = true;
				 else if(em.get(i).mParam.equals("Normal"))
					 mTargetArrows = false;
				 em.consume(i);
			 }
			 else if(em.get(i).mEvent.startsWith("mouse"))
			 {
				 MouseEvent me = (MouseEvent) em.get(i).mParam;
				 if(em.get(i).mEvent.equals("mouseMoved") || em.get(i).mEvent.equals("mouseDragged")) 
				 {
					 if(mBounds.contains(me.getPoint()))
					 { 
						 mXY.x = me.getX()/mGridSize.width - mBounds.x/mGridSize.width;   
						 mXY.y = me.getY()/mGridSize.height - mBounds.y/mGridSize.height;
						 mGameObjEventMgr.add(new Event("repaint"));
					 }
					 else
					 {
						 mXY.x = -1;
						 mXY.y = -1;
					 } 
				 }
			 }
		 }
		
		for(GameObject go : mObj)
			go.pumpEvents(em); //pass on events to children - ships
		//System.out.println("pumbing events");
		if(mFuSM != null){
			mFuSM.pumpEvents(em); //pass on events to the FuSM
		}
	}
	
	/**
	 * Returns the size of the individual grid blocks
	 * @return Dimension Object
	 */
	public static Dimension getgridSize()
	{
		return mGridSize;
	}
	public static Point getgridPoint()
	{
		return mXY;
	}

	@Override
	public void reInit() {
		for(GameObject go : mObj)
			go.reInit();
	}
}
