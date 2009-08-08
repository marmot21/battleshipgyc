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
 *
 */

public class Button extends GameObject
{
	private BufferedImage mImg, mDrawImg;
	
	public static enum BUTTON
	{
		NORMAL, HOVER, PRESSED, ACTIVE
	}
	
	public BUTTON STATE = BUTTON.NORMAL;
	
	public Button(String name, Rectangle bounds)
	{
		super(name, bounds);
		mGameObjEventMgr = new EventManager();
	}
	
	public Button(String name, Rectangle bounds, BufferedImage img)
	{
		super(name, bounds);
		mImg = img;
		render();
		mGameObjEventMgr = new EventManager();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(mDrawImg, mBounds.x, mBounds.y, null);
	}

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
	public void update()
	{
		
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
				if(em.get(i).mEvent.equals("mouseMoved") || em.get(i).mEvent.equals("mouseDragged"))
				{
					if(mBounds.contains(me.getPoint()))
					{
						if(STATE == BUTTON.NORMAL)
						{
							STATE = BUTTON.HOVER;
							render();
							mGameObjEventMgr.add(new Event("repaint"));
						}
						else if(STATE == BUTTON.ACTIVE)
						{
							STATE = BUTTON.PRESSED;
							render();
							mGameObjEventMgr.add(new Event("repaint"));
						}
					}
					else
					{
						if(STATE == BUTTON.HOVER)
						{
							STATE = BUTTON.NORMAL;
							render();
							mGameObjEventMgr.add(new Event("repaint"));
						}
						else if(STATE == BUTTON.PRESSED)
						{
							STATE = BUTTON.ACTIVE;
							render();
							mGameObjEventMgr.add(new Event("repaint"));
						}
					}
				}
				else if(em.get(i).mEvent.equals("mousePressed") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(mBounds.contains(me.getPoint()))
					{
						STATE = BUTTON.PRESSED;
						render();
						mGameObjEventMgr.add(new Event("repaint"));
					}
				}
				else if(em.get(i).mEvent.equals("mouseReleased") && me.getButton() == MouseEvent.BUTTON1)
				{
					if(STATE == BUTTON.PRESSED)
					{
						if(mBounds.contains(me.getPoint()))
						{
							mGameObjEventMgr.add(new Event("buttonClicked", (Object)this));
							STATE = BUTTON.HOVER;
							render();
							mGameObjEventMgr.add(new Event("repaint"));
						}
						else
						{
							STATE = BUTTON.NORMAL;
							render();
							mGameObjEventMgr.add(new Event("repaint"));
						}
					}
				}
			}
		}
	}
}
