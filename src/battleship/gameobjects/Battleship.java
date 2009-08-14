package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import battleship.Event;
import battleship.EventManager;

/**
 * Class to handle any ships etc
 * @author Amec
 * @author Obi
 */

public class Battleship extends GameObject
{
	public enum SHIPS
	{
		NORMAL, FOLLOW, INIT //Doesn't use init yet :(
	}/*
	Normal - ship not moving
	Follow - ship following mouse
	Init - ship in initial position
	*/
	
	public SHIPS STATE = SHIPS.NORMAL;
	public BufferedImage mImgH, mImgV;
	private Point mMouse = new Point();
	protected static Rectangle mStatusScreen = new Rectangle(24, 240+24, 240, 240);
	protected static Position mPrevPos = new Position();
	protected static ArrayList<Battleship> mInits = new ArrayList<Battleship>();
	private boolean mRotated = false;
	
	/**
	 * Default constructor.
	 */
	public Battleship()
	{
		
	}

	/**
	 * Constructor to use if you're going to define the
	 * images later.
	 * @param name The name of the Battleship.
	 * @param bounds The bounds of the Battleship.
	 */
	public Battleship(String name, Rectangle bounds)
	{
		super(name, bounds);
		mGameObjEventMgr = new EventManager();
		this.mBounds.x = mInits.size()*30+400;
		this.mBounds.y = ((mInits.size()-mInits.size()%2)/2)*26;
		mInits.add(this);
	}
	
	/**
	 * Constructor to use if you're defining the image
	 * @param name The name of the Battleship.
	 * @param bounds The bounds of the Battleship.
	 * @param img The image of the Battleship.
	 */
	public Battleship(String name, Rectangle bounds, BufferedImage img)
	{
		super(name, bounds);
		mImgV = img;
		bounds.width = mImgV.getWidth();
		bounds.height = mImgV.getHeight();
		mGameObjEventMgr = new EventManager();
		this.mBounds.x = mInits.size()*30+400;
		this.mBounds.y = ((mInits.size()-mInits.size()%2)/2)*26;
		mInits.add(this);
	}
	
	/**
	 * Long constructor is long.
	 * @param name The name of the Battleship.
	 * @param bounds The bounds of the Battleship.
	 * @param img The image of the Battleship.
	 * @param img2 The rotated image of the Battleship.
	 */
	public Battleship(String name, Rectangle bounds, BufferedImage img, BufferedImage img2)
	{
		this(name, bounds, img);
		mImgH = img2;
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
		
		if(mRotated)
			g.drawImage(mImgH, mBounds.x, mBounds.y, null);
		else
			g.drawImage(mImgV, mBounds.x, mBounds.y, null);
	}

	@Override
	public EventManager getEvents()
	{
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
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).mEvent.startsWith("mouse"))
			{
				MouseEvent me = (MouseEvent) em.get(i).mParam;
				if(em.get(i).mEvent.equals("mouseDragged") && STATE == SHIPS.FOLLOW) 
				{//if ship is being dragged then follow mouse
					mBounds.x = me.getX() - mMouse.x;
					mBounds.y = me.getY() - mMouse.y;
					mGameObjEventMgr.add(new Event("repaint"));
				}
				else if(em.get(i).mEvent.equals("mouseReleased"))
				{
					if(STATE == SHIPS.FOLLOW && mStatusScreen.contains(mBounds))
					{
						STATE = SHIPS.NORMAL;
						//snap it to the grid
						mBounds.y = mBounds.y/24*24;
						mBounds.x = mBounds.x/24*24;
						if(mPrevPos.mSTATE == SHIPS.INIT)
							mInits.remove(this);
						mGameObjEventMgr.add(new Event("repaint"));
					}
					else if(STATE == SHIPS.FOLLOW && !mStatusScreen.contains(mBounds))
					{ //if the ship was dragged outside of the grid then return it to its prev. pos.
						mBounds.x = mPrevPos.mPos.x;
						mBounds.y = mPrevPos.mPos.y;
						STATE = mPrevPos.mSTATE;
						mGameObjEventMgr.add(new Event("repaint"));
					}
					else if(STATE == SHIPS.NORMAL)
					{
						mPrevPos.mPos.x = mBounds.x;
						mPrevPos.mPos.y = mBounds.y;
						mPrevPos.mSTATE = STATE;
					}
				}
				else if(em.get(i).mEvent.equals("mousePressed"))
				{
					if((STATE == SHIPS.INIT || STATE == SHIPS.NORMAL) && mBounds.contains(me.getPoint()))
					{
						mMouse.x = me.getX() - mBounds.x;
						mMouse.y = me.getY() - mBounds.y;
						mPrevPos.mPos.x = mBounds.x;
						mPrevPos.mPos.y = mBounds.y;
						mPrevPos.mSTATE = STATE;
						STATE = SHIPS.FOLLOW;
					}
				}
				else if(em.get(i).mEvent.equals("mouseWheelMoved")) //rotate ship
				{
					if(STATE == SHIPS.FOLLOW)
					{
						mRotated = !mRotated;
						if(mRotated)
						{
							mBounds.width = mImgH.getWidth();
							mBounds.height = mImgH.getHeight();
						}
						else
						{
							mBounds.width = mImgV.getWidth();
							mBounds.height = mImgV.getHeight();
						}
						mGameObjEventMgr.add(new Event("repaint"));
					}	
				}
			}
		}
	}
}
