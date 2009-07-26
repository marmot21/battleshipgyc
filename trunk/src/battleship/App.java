package battleship;

import java.applet.Applet;
import java.awt.*;
import java.util.List;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;

import battleship.states.*;

public class App extends Applet implements Runnable, MouseMotionListener, MouseListener, MouseWheelListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	public static int FPS = 30;
	public static int SLEEP = 1000/FPS;
	public Image img;
	public Graphics g;
	public FiniteStateMachine fsm = new FiniteStateMachine();
	
	@Override
	public void init()
	{
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		setSize(900, 600);
		if(img == null)
		{
			img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TRANSLUCENT);
			g = img.getGraphics();
		}
		
		
		fsm.addState(new MenuState());
		fsm.addState(new GameState());
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run()
	{
		while(true)
		{
			fsm.run();
			//TODO: Create a separate thread for fsm.
			//Why?
			repaint();
			try
			{
				Thread.sleep(SLEEP);
			}
			catch (InterruptedException e)
			{
				System.exit(0);
			}
		}
	}
	
	@Override
	public void update(Graphics g)
	{
		paint(g);
	}
	
	@Override
	public void paint(Graphics g)
	{
		this.g.setColor(Color.WHITE);
		this.g.fillRect(0, 0, getWidth(), getHeight());
		fsm.paint(this.g);
		g.drawImage(img, 0, 0, this);
	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("mouseDragged"), params);
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("mouseMoved"), params);
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("mouseClicked"), params);
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("mouseEntered"), params);
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("mouseExited"), params);
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("mousePressed"), params);
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("mouseReleased"), params);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("mouseWheelMoved"), params);
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("keyPressed"), params);
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{	
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("keyReleased"), params);
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		List<Object> params = new ArrayList<Object>();
		params.add(arg0);
		fsm.triggerEvent(new Event("keyTyped"), params);
	}
}