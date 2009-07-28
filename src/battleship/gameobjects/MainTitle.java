/**
 * 
 */
package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import battleship.EventManager;

/**
 * @author Marmot.Daniel
 *
 */
public class MainTitle extends GameObject{ //Main title used in the game
	//TODO: make sure it is positioned correctly

	protected BufferedImage title;
	public MainTitle(Rectangle r) {
		super(r);
		title = loadImage("battleship/res/img/GameTitle.png");
		resize(new Rectangle(r.x, r.y, title.getWidth(), title.getHeight()));//only one pic so leave here
		render();//draw the image to the screen
	}

	public static BufferedImage loadImage(String path)
	{
		BufferedImage b = null;
		
		try
		{
			 b = ImageIO.read(new File(path));
		}
		catch (IOException e)
		{
			System.out.println("Unable to load \"" + path + "\"");
			b = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		}
		
		return b;
	}

	public void paint(Graphics g) {
		g.drawImage(img, r.x, r.y, null);
	}


	public void render() {
		g.drawImage(title, 0, 0, null);
		
	}


	public void update() {

	}

	@Override
	public EventManager getEvents() {
		// TODO Auto-generated method stub
		return null; //does not use any events at all
	}

	@Override
	public void pumpEvents(EventManager em) {
		// TODO Auto-generated method stub
		
	}

}
