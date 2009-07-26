package battleship;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public abstract class GameObject
{
	public Rectangle r;
	public BufferedImage img;
	public Graphics g;
	public boolean collision, destroy = false;
	
	public GameObject(Rectangle r)
	{
		this.r = r;
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
}

abstract class Buttons extends GameObject
{
	public static List<Buttons> rObj  = new ArrayList<Buttons>(); //used in register
	protected BufferedImage normal, hover, pressed;
	public static enum BUTTON
	{
		NORMAL, HOVER, PRESSED
	}
	public BUTTON STATE = BUTTON.NORMAL;
	
	public Buttons(Rectangle r)
	{
		super(r);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(img, r.x, r.y, null);
	}

	@Override
	public void render()
	{
		switch(STATE)
		{
			case NORMAL:
				resize(new Rectangle(r.x, r.y, normal.getWidth(), normal.getHeight()));
				g.drawImage(normal, 0, 0, null);
				break;
			case HOVER:
				resize(new Rectangle(r.x, r.y, hover.getWidth(), hover.getHeight()));
				g.drawImage(hover, 0, 0, null);
				break;
			case PRESSED:
				resize(new Rectangle(r.x, r.y, pressed.getWidth(), pressed.getHeight()));
				g.drawImage(pressed, 0, 0, null);
				break;
		}
	}

	static public void initChildren () {
		for (Buttons go : rObj) {
			go.STATE = BUTTON.NORMAL;
			go.render();
			go.paint(go.g);
		}
		
	}
	
	@Override
	public void update()
	{
		//only need to render when changing image
	}
	public final void register(Buttons o) { //Handy way to keep track of children, clever me
		rObj.add(o); //TODO: work out way to get it to remove when object is deleted
	}
}

final class BHostGame extends Buttons {

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

final class BJoinGame extends Buttons {

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

 final class BSinglePlayer extends Buttons {

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
	
}
