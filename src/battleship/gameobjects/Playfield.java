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
import battleship.Events;
import battleship.FiniteStateMachine;

/**
 * The grid which handles ships and bombing etc.
 * @author Amec
 * @author Obi
 */

public class Playfield extends GameObject
{
	public static Dimension mGridSize;//TODO:see if we can get this to be non-static - if need be
	//All the ships and bombs etc in the grid
	public List<GameObject> mObj = new ArrayList<GameObject>();
	/**
	 * The 3D array which stores the positions of the ships or bombs
	 * X and Y are the grid position
	 * Z is the status of the ship at that position
	 */
	public int mGrid[][][] = new int[10][10][2];
	private static Point mXY = new Point(-1, -1);
	private boolean mTargetArrows = false;
	/**
	 * Fuzzy State Machine.
	 * Used by the targeting window only.
	 * Used as a FSM in multiplayer mode
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
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.gameobjects.GameObject#update()
	 */
	@Override
	public void run()
	{
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).run();
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
			g.setColor(new Color(255, 0, 0, 128));//Vert line - red
			g.fillRect(mXY.x*mGridSize.width+24, 0, mGridSize.width, mBounds.height);

			g.setColor(new Color(0, 0, 255, 128));//Hor line - blue
			g.fillRect(24, mXY.y*mGridSize.height, mBounds.width, mGridSize.height);
			
			//Draw hits and dropped bombs
			for(int iX = 0; iX<10; iX++)
				for(int iY = 0; iY<10; iY++)
					if(mGrid[iX][iY][1] == 1)
					{
						g.setColor(new Color(255,255,255,125));
						g.fillRect(iX*mGridSize.width+24, iY*mGridSize.height, mGridSize.width, mGridSize.height);
					}
					else if(mGrid[iX][iY][1] == 2)
					{
						g.setColor(new Color(0,255,0,125));
						g.fillRect(iX*mGridSize.width+24, iY*mGridSize.height, mGridSize.width, mGridSize.height);
					}
		}
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).paint(g);
		if(mFuSM != null)
			mFuSM.getState().paint(g);
	}
	
	@Override
	public void processEvents()
	{
		if(mFuSM != null)//checks if it is necessary to do all this
		{
			 for(int i = 0; i < Events.get().size(); i++)
			 { 
				 if(Events.get().get(i).m_Event.equals("addBomb"))
				 {
					 mGrid = (int[][][]) Events.get().get(i).m_Param;
					 System.out.println("bomb added");
					 Events.get().add(new Event("repaint"));
					 Events.get().remove(i);
				 }
				 else if(Events.get().get(i).m_Event.startsWith("setField"))
				 {
					 System.out.println("arrows");
					 if(Events.get().get(i).m_Param.equals("TargetArrows"))
						 mTargetArrows = true;
					 else if(Events.get().get(i).m_Param.equals("Normal"))
						 mTargetArrows = false;
					 else if(Events.get().get(i).m_Param.equals("Toggle"))
						 mTargetArrows = !mTargetArrows;
					 Events.get().remove(i);
				 }
				 else if(Events.get().get(i).m_Event.startsWith("mouse"))
				 {
					 MouseEvent me = (MouseEvent) Events.get().get(i).m_Param;
					 if(Events.get().get(i).m_Event.equals("mouseMoved") || Events.get().get(i).m_Event.equals("mouseDragged")) 
					 {
						 if(mBounds.contains(me.getPoint()))
						 { 
							 //set which grid square the mouse is in
							 mXY.x = me.getX()/mGridSize.width - mBounds.x/mGridSize.width;   
							 mXY.y = me.getY()/mGridSize.height - mBounds.y/mGridSize.height;
							 Events.get().add(new Event("repaint"));
						 }
						 else
						 {
							 //mouse not over the grid
							 mXY.x = -1;
							 mXY.y = -1;
						 } 
					 }
				 }
			 }
		 }
		
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).processEvents();
		if(mFuSM != null)
		{
			mFuSM.processEvents(); //pass on events to the FuSM
			mFuSM.getState().processEvents();
		}
	}
	
	/*
	 * Returns the size of the individual grid blocks
	 * @return Dimension Object
	 */
	//public static Dimension getgridSize()
	//{
	//	return mGridSize;
	//}
	public static Point getgridPoint()
	{
		return mXY;
	}
}
