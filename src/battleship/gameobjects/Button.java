package battleship.gameobjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Button extends GameObject
{
	//public static List<Button> rObj  = new ArrayList<Button>(); //used in register //what the fuck is this?
	protected BufferedImage normal, hover, pressed;
	public static enum BUTTON
	{
		NORMAL, HOVER, PRESSED
	}
	public BUTTON STATE = BUTTON.NORMAL;
	
	public Button(Rectangle r)
	{
		super(r);
	}
	
	public Button(Rectangle r, BufferedImage n, BufferedImage h, BufferedImage p)
	{
		super(r);
		normal = n;
		hover = h;
		pressed = p;
	}
	
	public Button(Rectangle r, String b, String n, String h, String p)
	{
		super(r);
		try
		{
			normal = ImageIO.read(new File(b+n));
		}
		catch (IOException e)
		{
			System.out.println("Unable to load \"" + b+n + "\"");
		}
		try
		{
			hover = ImageIO.read(new File(b+h));
		}
		catch (IOException e)
		{
			System.out.println("Unable to load \"" + b+h + "\"");
		}
		try
		{
			pressed = ImageIO.read(new File(b+p));
		}
		catch (IOException e)
		{
			System.out.println("Unable to load \"" + b+p + "\"");
		}
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

	/*static public void initChildren () {
		for (Button go : rObj) {
			go.STATE = BUTTON.NORMAL;
			go.render();
			go.paint(go.g);
		}
		
	}*/
	
	@Override
	public void update()
	{
		//only need to render when changing image
	}
	/*public final void register(Button o) { //Handy way to keep track of children, clever me
		rObj.add(o); //TODO: work out way to get it to remove when object is deleted
	}*/
}
