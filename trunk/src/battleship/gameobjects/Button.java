package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import battleship.Event;
import battleship.Events;
import battleship.Input;

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
	public Button(String name, Rectangle bounds)
	{
		super(name, bounds);
	}
	
	/**
	 * Constructor when you are storing the image too
	 * @param name The name of the Button - for reference
	 * @param bounds The bounds of the button
	 * @param img The image of the button
	 */
	public Button(String name, Rectangle bounds, BufferedImage img)
	{
		super(name, bounds);
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
		for(int i = 0; i < Events.get().size(); i++)
		{
			if(Events.get().get(i).m_Target.equals(mName))
			{
				if(Events.get().get(i).m_Event.equals("visibility"))
				{
					if(Events.get().get(i).m_Param.equals(new Boolean(true)))
					{
						visibility = true;
					}
					else if(Events.get().get(i).m_Param.equals(new Boolean(false)))
					{
						visibility = false;
					}
					else if(Events.get().get(i).m_Param.equals("toggle"))
					{
						visibility = !visibility;
					}
				}
				Events.get().add(new Event("repaint"));
				Events.get().remove(i);
			}
		}
		if(mBounds.contains(Input.get().getMouse()))
		{
			if(STATE == BUTTON.NORMAL)
			{
				STATE = BUTTON.HOVER;
				render();
				Events.get().add(new Event("repaint"));
			}
			else if(STATE == BUTTON.ACTIVE)
			{
				STATE = BUTTON.PRESSED;
				render();
				Events.get().add(new Event("repaint"));
			}
		}
		else
		{
			if(STATE == BUTTON.HOVER)
			{
				STATE = BUTTON.NORMAL;
				render();
				Events.get().add(new Event("repaint"));
			}
			else if(STATE == BUTTON.PRESSED)
			{
				STATE = BUTTON.ACTIVE;
				render();
				Events.get().add(new Event("repaint"));
			}
		}
		if(Input.get().mouseIsPressed(MouseEvent.BUTTON1))
		{
			if(mBounds.contains(Input.get().getMouse()))
			{
				STATE = BUTTON.PRESSED;
				render();
				Events.get().add(new Event("repaint"));
			}
		}
		else
		{
			if(STATE == BUTTON.PRESSED)
			{
				if(mBounds.contains(Input.get().getMouse()))
				{
					if(visibility == true)
						Events.get().add(new Event("buttonClicked", mName));
					STATE = BUTTON.HOVER;
					render();
					Events.get().add(new Event("repaint"));
				}
				else
				{
					STATE = BUTTON.NORMAL;
					render();
					Events.get().add(new Event("repaint"));
				}
			}
			else if(STATE == BUTTON.ACTIVE)
			{
				if(!mBounds.contains(Input.get().getMouse()))
				{
					STATE = BUTTON.NORMAL;
					render();
					Events.get().add(new Event("repaint"));
				}
			}
		}
	}
}
