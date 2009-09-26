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
import battleship.Input;

/**
 * The grid which handles ships and bombing etc.
 * @author Amec
 * @author Obi
 */

public class Playfield extends GameObject
{
	public static Dimension m_GridSize;//TODO:see if we can get this to be non-static - if need be
	//All the ships and bombs etc in the grid
	public List<GameObject> m_Obj = new ArrayList<GameObject>();
	/**
	 * The 3D array which stores the positions of the ships or bombs
	 * X and Y are the grid position
	 * Z is the status of the ship at that position
	 */
	public int m_Grid[][][] = new int[10][10][2];
	private static Point m_XY = new Point(-1, -1);
	private boolean m_TargetGrid = false;
	private boolean m_TargetArrows = false;
	/**
	 * Fuzzy State Machine.
	 * Used by the targeting window only.
	 * Used as a FSM in multiplayer mode
	 */
	public FiniteStateMachine m_FuSM = null;
	
	/**
	 * 
	 * @param sName The name of the grid
	 * @param bounds The position of the grid and its dimensions in squares
	 * @param dim The dimensions of each grid square
	 */
	public Playfield(String sName, Rectangle bounds, Dimension dim)
	{
		this.mBounds = new Rectangle(bounds.x, bounds.y, bounds.width*dim.width, bounds.height*dim.height);
		m_GridSize = dim;
		mName = sName;
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.gameobjects.GameObject#update()
	 */
	@Override
	public void run()
	{
		for(int i = 0; i < m_Obj.size(); i++)
			m_Obj.get(i).run();
		if(m_TargetGrid)
			m_FuSM.getState().run();
	}
	
	@Override
	public void render()
	{
		
	}

	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		for(int i = 0; i <= mBounds.width/m_GridSize.width; i++)
		{
			g.drawLine(mBounds.x+i*m_GridSize.width, mBounds.y, mBounds.x+i*m_GridSize.width, mBounds.y+mBounds.height);
		}
		for(int i = 0; i <= mBounds.height/m_GridSize.height; i++)
		{
			g.drawLine(mBounds.x, mBounds.y+i*m_GridSize.height, mBounds.x+mBounds.width, mBounds.y+i*m_GridSize.height);
		}
		if(m_TargetArrows && m_XY.x>=0 && m_XY.y>=0){
			g.setColor(new Color(255, 0, 0, 128));//Vert line - red
			g.fillRect(m_XY.x*m_GridSize.width+24, 0, m_GridSize.width, mBounds.height);

			g.setColor(new Color(0, 0, 255, 128));//Hor line - blue
			g.fillRect(24, m_XY.y*m_GridSize.height, mBounds.width, m_GridSize.height);
			
			//Draw hits and dropped bombs
			for(int iX = 0; iX<10; iX++)
				for(int iY = 0; iY<10; iY++)
					if(m_Grid[iX][iY][1] == 1)
					{
						g.setColor(new Color(255,255,255,125));
						g.fillRect(iX*m_GridSize.width+24, iY*m_GridSize.height, m_GridSize.width, m_GridSize.height);
					}
					else if(m_Grid[iX][iY][1] == 2)
					{
						g.setColor(new Color(0,255,0,125));
						g.fillRect(iX*m_GridSize.width+24, iY*m_GridSize.height, m_GridSize.width, m_GridSize.height);
					}
		}
		//Draw hits and dropped bombs for the status screen
		if(!m_TargetGrid)
			for(int iX = 0; iX<10; iX++)
				for(int iY = 0; iY<10; iY++)
					if(m_Grid[iX][iY][1] == 1)
					{
						g.setColor(new Color(255,255,255,125));
						g.fillRect(iX*m_GridSize.width+24, iY*m_GridSize.height+m_GridSize.height*10+24, m_GridSize.width, m_GridSize.height);
					}
					else if(m_Grid[iX][iY][1] == 2)
					{
						g.setColor(new Color(255,0,0,125));
						g.fillRect(iX*m_GridSize.width+24, iY*m_GridSize.height+m_GridSize.height*10+24, m_GridSize.width, m_GridSize.height);
					}
		
		for(int i = 0; i < m_Obj.size(); i++)
			m_Obj.get(i).paint(g);
		if(m_TargetGrid)
			m_FuSM.getState().paint(g);
	}
	
	@Override
	public void processEvents()
	{
		if(m_TargetGrid)//checks if it is necessary to do all this
		{
			 for(int i = 0; i < Events.get().size(); i++)
			 { 
				 //add bomb to the grid
				 if(Events.get().get(i).m_Event.equals("addBomb"))
				 {
					 m_Grid = (int[][][]) Events.get().get(i).m_Param;
					 //System.out.println("bomb added"); //debugging stuff
					 Events.get().add(new Event("repaint"));
					 Events.get().remove(i);
				 }
				 else if(Events.get().get(i).m_Event.startsWith("setField"))
				 {
					 System.out.println("arrows");
					 if(Events.get().get(i).m_Param.equals("TargetArrows"))
						 m_TargetArrows = true;
					 else if(Events.get().get(i).m_Param.equals("Normal"))
						 m_TargetArrows = false;
					 else if(Events.get().get(i).m_Param.equals("Toggle"))
						 m_TargetArrows = !m_TargetArrows;
					 Events.get().remove(i);
				 }
			 }
				 //else if(Events.get().get(i).m_Event.startsWith("mouse"))
				 //{
					 //MouseEvent me = (MouseEvent) Events.get().get(i).m_Param;
					 //if(Events.get().get(i).m_Event.equals("mouseMoved") || Events.get().get(i).m_Event.equals("mouseDragged")) 
					 //{
			 if(mBounds.contains(Input.get().getMouse()))
			 { 
				 //set which grid square the mouse is in
				 m_XY.x = Input.get().getMouse().x/m_GridSize.width - mBounds.x/m_GridSize.width;   
				 m_XY.y = Input.get().getMouse().y/m_GridSize.height - mBounds.y/m_GridSize.height;
				 Events.get().add(new Event("repaint"));
			 }
			 else
			 {
				 //mouse not over the grid
				 m_XY.x = -1;
				 m_XY.y = -1;
			 }
					 //}
				 //}
		 }//end of stuff concerning the FuSM
		
		else
		for(int i = 0; i < Events.get().size(); i++)
		{
			 if(Events.get().get(i).m_Event.equals("updatefield"))
			 {
				 m_Grid = (int[][][]) Events.get().get(i).m_Param;
				 Events.get().add(new Event("repaint"));
				 Events.get().remove(i);
			 }
		}
		
		for(int i = 0; i < m_Obj.size(); i++)
			m_Obj.get(i).processEvents();
		if(m_TargetGrid)
		{
			m_FuSM.processEvents(); //pass on events to the FuSM
			m_FuSM.getState().processEvents();
		}
	}
	
	

	/**
	 * @param m_TargetGrid the m_TargetGrid to set
	 */
	public void setM_TargetGrid(boolean m_TargetGrid) {
		this.m_TargetGrid = m_TargetGrid;
	}

	/**
	 * @return the m_TargetGrid
	 */
	public boolean isM_TargetGrid() {
		return m_TargetGrid;
	}

	/*
	 * Returns the size of the individual grid blocks
	 * @return Dimension Object
	 */
	//public static Dimension getgridSize()
	//{
	//	return mGridSize;
	//}
	/**
	 * Returns the current mouse position the Target grid
	 */
	public static Point getgridPoint()
	{
		return m_XY;
	}
}
