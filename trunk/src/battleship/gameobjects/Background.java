package battleship.gameobjects;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import battleship.EventManager;

public class Background extends GameObject
{
	private List<Wave> waves = new ArrayList<Wave>();
	private List<Cloud> clouds = new ArrayList<Cloud>();
	private double wind = Math.random()-Math.random();
	private double calm = Math.random()-Math.random();
	
	public Background(Rectangle r, String s)
	{
		super(r, s);
		int n = (int)(5+Math.random()*20);
		for(int i = 0; i < n; i++)
		{
			waves.add(new Wave());
			clouds.add(new Cloud());
		}
		render();
		
	}

	@Override
	public EventManager getEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void paint(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		GradientPaint gp = new GradientPaint(0, 0, new Color(0, 167, 228), 0, 300, new Color(165, 230, 254), true);
		Shape s = new Rectangle(0, 0, 800, 300);
		g2d.setPaint(gp);
		g2d.fill(s);
		for(Wave w : waves)
			w.paint(g);
		for(Cloud c : clouds)
			c.paint(g);	
	}

	@Override
	public void pumpEvents(EventManager em)
	{
		
	}

	@Override
	public void render()
	{
		for(Wave w : waves)
			w.render();
		for(Cloud c : clouds)
			c.render();
	}
	
	@Override
	public void update()
	{
		wind += Math.random()/10;
		wind -= Math.random()/10;
		calm += Math.random()/10;
		calm -= Math.random()/10;
		if(calm >= 1)
			calm = 0.9999;
		for(Wave w : waves)
		{
			w.wind = wind;
			w.update();
		}
		for(Cloud c : clouds)
		{
			c.wind = wind;
			c.calm = calm;
			c.update();
		}
		
	}
	
}

class Wave extends GameObject
{
	public double wind;

	@Override
	public EventManager getEvents()
	{
		return null;
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pumpEvents(EventManager em) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		render();
	}
	
}

class Cloud extends GameObject
{
	public double calm, wind, x, z;
	private List<CloudPart> cloudparts = new ArrayList<CloudPart>();
	
	public Cloud()
	{
		super(new Rectangle(0, (int)(Math.random()*100), 100+(int)(Math.random()*100), 50+(int)(Math.random()*50)));
		x = Math.random()*(800+r.width)-r.width;
		z = Math.random();
		int n = (int)(5+Math.random()*5);
		for(int i = 0; i < n; i++)
		{
			cloudparts.add(new CloudPart());
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
		g.setColor(new Color((int)(255*(1-calm)), (int)(255*(1-calm)), (int)(255*(1-calm)), 240));
		g.fillOval(r.width/4, r.height/4, r.width-r.width/4-1, r.height-r.height/4-1);
		for(CloudPart cp : cloudparts)
			cp.render();
	}

	@Override
	public void update()
	{
		x += wind*z;
		if(x > 800 && wind > 0)
			x -= 800 + r.width;
		if(x < 0 - r.width && wind < 0)
			x += 800 + r.width;
		r.x = (int) x;
		for(CloudPart cp : cloudparts)
			cp.update();
	}
}

class CloudPart extends GameObject
{

	@Override
	public EventManager getEvents()
	{
		return null;
	}

	@Override
	public void paint(Graphics g)
	{
		
	}

	@Override
	public void pumpEvents(EventManager em) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}