package battleship.gameobjects;

import java.awt.*;
import java.awt.image.*;

import battleship.EventManager;

public abstract class GameObject
{
	public Rectangle r;
	public BufferedImage img;
	public Graphics g;
	public boolean collision, destroy = false;
	public String name = "";
	
	public GameObject(Rectangle r)
	{
		this.r = r;
		if(img == null)
		{
			img = new BufferedImage(r.width, r.height, BufferedImage.TRANSLUCENT);
			g = img.getGraphics();
		}
	}
	
	public GameObject(Rectangle r, String s)
	{
		this.r = r;
		name = s;
		if(img == null)
		{
			img = new BufferedImage(r.width, r.height, BufferedImage.TRANSLUCENT);
			g = img.getGraphics();
		}
	}
	
	public void resize(Rectangle r)
	{
		if(r.x != this.r.x || r.y != this.r.y || r.width != this.r.width || r.height != this.r.height)
		{
			this.r = r;
			img = new BufferedImage(this.r.width, this.r.height, BufferedImage.TRANSLUCENT);
			g = img.getGraphics();
			render();
		}
	}
	
	public abstract void update();
	public abstract void render();
	public abstract void paint(Graphics g);
	public abstract void pumpEvents(EventManager em);
	public abstract EventManager getEvents();
}

//what the fuck is this?

/*public class BHostGame extends Button {

	public BHostGame(Rectangle r) {
		super(r);
		register (this);
		try
		{
			normal = ImageIO.read(new File("Images/HostGame0.gif"));
			hover = ImageIO.read(new File("Images/HostGame1.gif"));
			pressed = ImageIO.read(new File("Images/HostGame2.gif"));
		}
		catch (Exception e)
		{
			System.out.println("Unable to load image, bro.");
		}
		resize(new Rectangle(this.r.x, this.r.y, normal.getWidth(), normal.getHeight()));
	}
}

final class BJoinGame extends Button {

	public BJoinGame(Rectangle r) {
		super(r);
		register (this);
		try
		{
			normal = ImageIO.read(new File("Images/JoinGame0.gif"));
			hover = ImageIO.read(new File("Images/JoinGame1.gif"));
			pressed = ImageIO.read(new File("Images/JoinGame2.gif"));
		}
		catch (Exception e)
		{
			System.out.println("Unable to load image, bro.");
		}
		resize(new Rectangle(this.r.x, this.r.y, normal.getWidth(), normal.getHeight()));
	}
	
}

 final class BSinglePlayer extends Button {

	public BSinglePlayer(Rectangle r) {
		super(r);
		register (this);
		try
		{
			normal = ImageIO.read(new File("Images/SinglePlayer0.gif"));
			hover = ImageIO.read(new File("Images/SinglePlayer1.gif"));
			pressed = ImageIO.read(new File("Images/SinglePlayer2.gif"));
		}
		catch (Exception e)
		{
			System.out.println("Unable to load image, bro.");
		}
		resize(new Rectangle(this.r.x, this.r.y, normal.getWidth(), normal.getHeight()));
	}
	
}*/
