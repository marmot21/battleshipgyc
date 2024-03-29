package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * A class used to store an image and its position/bounds
 * Used in MenuState to store the title image
 * @author Amec
 */

public class GameImage extends GameObject
{
	private BufferedImage mImg;
	
	/**
	 * Default constructor
	 */
	public GameImage()
	{
		
	}
	
	/**
	 * Constructor used when defining the image later
	 * @param name The name used for reference
	 * @param bounds The bounds of the object - x and y should be non-zero - unless used within another image
	 */
	public GameImage(String name, Rectangle bounds)
	{
		super(name, bounds);
	}
	
	/**
	 * Constructor used when defining the image
	 * @param name The name used for referencing
	 * @param bounds The bounds of the object - x and y should be non-zero - unless used within another image
	 * @param img The image to be used
	 */
	public GameImage(String name, Rectangle bounds, BufferedImage img)
	{
		super(name, bounds);
		mImg = img;
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(mImg, mBounds.x, mBounds.y, null);
	}

	@Override
	public void render()
	{
	
	}

	@Override
	public void run()
	{
		
	}
	
	@Override
	public void processEvents()
	{
		
	}
}
