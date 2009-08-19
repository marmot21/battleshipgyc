package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import battleship.Event;
import battleship.EventManager;

/**
 * A GameObject used to draw custom buttons etc.
 * @author Obi
 * @author Amec
 */

public class Button extends GameObject
{
	private BufferedImage mImg, mDrawImg;
	
	public static enum BUTTON
	{
		NORMAL, HOVER, PRESSED, ACTIVE
	}
	
	public BUTTON STATE = BUTTON.NORMAL;
	
	public boolean visibility = true;
	
	/**
	 * Constructor when storing the button later
	 * @param name The name of the Button - for reference
	 * @param bounds The bounds of the button
	 */
	public Button(String name, Rectangle bounds, EventManager mEventMgr)
	{
		super(name, bounds, mEventMgr);
	}
	
	/**
	 * Constructor when you are storing the image too
	 * @param name The name of the Button - for reference
	 * @param bounds The bounds of the button
	 * @param img The image of the button
	 */
	public Button(String name, Rectangle bounds, EventManager mEventMgr, BufferedImage img)
	{
		super(name, bounds, mEventMgr);
		mImg = img;
		render();
	}

	@Override
	public void paint(Graphics g)
	{
		if(visibility)
			g.drawImage(mDrawImg, mBounds.x, mBounds.y, null);
	}

	/**
	 * Sets the subimage of the button
	 * @see battleship.gameobjects.GameObject#render()
	 */
	@Override
	public void render()
	{
		mDrawImg = null;
		mBounds.height = mImg.getHeight()/3;
		mBounds.width = mImg.getWidth();
		switch(STATE)
		{
			case NORMAL:
			case ACTIVE:
				mDrawImg = mImg.getSubimage(0, 0, mImg.getWidth(), mImg.getHeight()/3);
				break;
			case HOVER:
				mDrawImg = mImg.getSubimage(0, mImg.getHeight()/3, mImg.getWidth(), mImg.getHeight()/3);
				break;
			case PRESSED:
				mDrawImg = mImg.getSubimage(0, mImg.getHeight()*2/3, mImg.getWidth(), mImg.getHeight()/3);
				break;
		}
	}

	@Override
	public void run()
	{
		
	}
	
	@Override
	public void processEvents()
	{
		for(int i = 0; i < mEventMgr.size(); i++)
		{
			if(mEventMgr.get(i).mTarget.equals(mName))
			{
				if(mEventMgr.get(i).mEvent.equals("visibility"))
				{
					if(mEventMgr.get(i).mParam.equals(new Boolean(true)))
					{
						visibility = true;
					}
					else if(mEventMgr.get(i).mParam.equals(new Boolean(false)))
					{
						visibility = false;
					}
					else if(mEventMgr.get(i).mParam.equals("toggle"))
					{
						visibility = !visibility;
					}
				}
				mEventMgr.add(new Event("repaint"));
				mEventMgr.consume(i);
			}
			else if(mEventMgr.get(i).mEvent.startsWith("mouse"))
			{
				MouseEvent me = (MouseEvent) mEventMgr.get(i).mParam;
				if(mEventMgr.get(i).mEvent.equals("mouseMoved") || mEventMgr.get(i).mEvent.equals("mouseDragged"))
				{
					if(mBounds.contains(me.getPoint()))
					{
						if(STATE == BUTTON.NORMAL)
						{
							STATE = BUTTON.HOVER;
							render();
							mEventMgr.add(new Event("repaint"));
						}
						else if(STATE == BUTTON.ACTIVE)
						{
							STATE = BUTTON.PRESSED;
							render();
							mEventMgr.add(new Event("repaint"));
						}
					}
					else
					{
						if(STATE == BUTTON.HOVER)
						{
							STATE = BUTTON.NORMAL;
							render();
							mEventMgr.add(new Event("repaint"));
						}
						else if(STATE == BUTTON.PRESSED)
						{
							STATE = BUTTON.ACTIVE;
							render();
							mEventMgr.add(new Event("repaint"));
						}
					}
				}
				else if(mEventMgr.get(i).mEvent.equals("mousePressed") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(mBounds.contains(me.getPoint()))
					{
						STATE = BUTTON.PRESSED;
						render();
						mEventMgr.add(new Event("repaint"));
					}
				}
				else if(mEventMgr.get(i).mEvent.equals("mouseReleased") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(STATE == BUTTON.PRESSED)
					{
						if(mBounds.contains(me.getPoint()))
						{
							mEventMgr.add(new Event("buttonClicked", mName));
							STATE = BUTTON.HOVER;
							render();
							mEventMgr.add(new Event("repaint"));
						}
						else//this method is never called because the state will be "Active"
						{
							STATE = BUTTON.NORMAL;
							render();
							mEventMgr.add(new Event("repaint"));
						}
					}
					else if(STATE == BUTTON.ACTIVE) {
						if(!mBounds.contains(me.getPoint())) {
							STATE = BUTTON.NORMAL;
							render();
							mEventMgr.add(new Event("repaint"));
						}
					}
				}//end mouse event
			}//end if mouse event
		}//end for loop
	}//end pump events
}//end class
