package battleship.gameobjects;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;
import battleship.Main;

public class Background extends GameObject
{
	private BufferedImage mImg;
	private List<Cloud> clouds = new ArrayList<Cloud>();
	
	public Background(String s, Rectangle r)
	{
		super(s, r);
		mImg = new BufferedImage(Main.mDim.width, Main.mDim.height, BufferedImage.OPAQUE);
		int n = 10 + (int)(Math.random()*10);
		for(int i = 0; i < n; i++)
			clouds.add(new Cloud());
		render();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(mImg, 0, 0, null);
		for(int i = 0; i < clouds.size(); i++)
			clouds.get(i).paint(g);
	}
	
	@Override
	public void render()
	{
		Graphics2D g2d = (Graphics2D) mImg.getGraphics();
		GradientPaint gp = new GradientPaint(0, 0, new Color(0, 167, 228), 0, 300, new Color(165, 230, 254), true);
		Shape s = new Rectangle(0, 0, 800, 300);
		g2d.setPaint(gp);
		g2d.fill(s);
		g2d.dispose();
	}
	
	@Override
	public void run()
	{
		for(int i = 0; i < clouds.size(); i++)
			clouds.get(i).run();
	}
	
	@Override
	public void processEvents()
	{
		
	}
}

class Cloud extends GameObject
{
	private Rectangle mRect;
	
	public Cloud()
	{
		mRect = new Rectangle(10, 10, 100, 100);
	}
	
	@Override
	public void run()
	{
		mRect.x++;
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.WHITE);
		g.drawOval(mRect.x, mRect.y, mRect.width, mRect.height);
	}

	@Override
	public void render()
	{
		
	}
	
	@Override
	public void processEvents()
	{
		
	}
}