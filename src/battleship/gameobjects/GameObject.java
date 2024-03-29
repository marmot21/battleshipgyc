package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Abstract class used to define standards for
 * painting etc.
 * @author Amec
 * @author Obi
 */

public abstract class GameObject
{
	/**
	 * The bounds of the object
	 */
	public Rectangle mBounds = new Rectangle(); //the bounds of the object
	
	/**
	 * The name of the object
	 */
	public String mName = ""; //name, used to identify
	
	/**
	 * Default constructor
	 */
	public GameObject()
	{
		
	}
	
	/**
	 * Constructor to use if defining the name and bounds
	 * @param name The name of the object
	 * @param bounds The bounds of the object
	 */
	public GameObject(String name, Rectangle bounds)
	{
		this.mName = name;
		this.mBounds = bounds;
	}

	/**
	 * static method to load images easily
	 * @param path The path to the image
	 * @return BufferedImage
	 */
	public static BufferedImage loadImage(String path)
	{
		BufferedImage tempImage = null;
		try
		{
			tempImage = ImageIO.read(new File(path));
		}
		catch (IOException e)
		{
			System.out.println("Unable to load \"" + path + "\"");
			tempImage = new BufferedImage(1, 1, BufferedImage.OPAQUE);
		}
		return tempImage;
	}
	
	/**
	 * Makes the object run it code - not part of the thread system
	 */
	public abstract void run();
	
	/**
	 * renders graphics
	 * NOTE: Sometimes it is faster to draw primitives (circles etc)
	 * DIRECTLY to the double buffer instead of another image
	 */
	public abstract void render();
	
	/**
	 * paints to double buffer
	 * @param g The double buffer graphics context
	 */
	public abstract void paint(Graphics g);
	
	/**
	 * used for processing events
	 */
	public abstract void processEvents();
}