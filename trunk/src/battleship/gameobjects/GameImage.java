package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import battleship.EventManager;

public class GameImage extends GameObject
{	
	public GameImage(Rectangle r, String name, String path)
	{
		super(r);
		this.name = name;
		loadImage(path);
	}
	
	public void loadImage(String path)
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
		finally
		{
			resize();
			img = b;
		}
	}

	@Override
	public EventManager getEvents()
	{
		return null;
	}

	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img, r.x, r.y, null);		
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		
	}

	@Override
	public void render()
	{
		
	}

	@Override
	public void update()
	{
		
	}
}
