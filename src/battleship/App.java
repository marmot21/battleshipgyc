package battleship;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import battleship.states.GameState;
import battleship.states.GenericState;
import battleship.states.MenuState;

public class App extends Applet implements Runnable, MouseMotionListener, MouseListener, MouseWheelListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private static int FPS = 60, SLEEP = 1000/FPS;
	public static boolean DEBUG = false;
	private Image img; //used for double buffer,
	private Graphics g; //etc.
	private FiniteStateMachine fsm = new FiniteStateMachine();
	private long time;
	private int loop = 1, fps = 1; //used for debugging
	
	@Override
	public void init()
	{
		setSize(800, 600); //AR of 1/3...
		setBackground(Color.GRAY);
		
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		
		if(img == null)
		{
			img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TRANSLUCENT);
			g = img.getGraphics();
		}
		
		fsm.addState(new GenericState());
		fsm.addState(new MenuState());
		fsm.addState(new GameState());
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run()
	{
		fsm.em.add(new Event("setState", "MenuState")); //enter the Menu state
		while(true)
		{
			loop++;
			fsm.run(); //update current state
			repaint(); //paint state to screen
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
		//overridden so it doesn't clear the screen
		paint(g);
	}
	
	@Override
	public void paint(Graphics g)
	{
		//NOTE:
		//this.g = double buffer
		//g = screen graphics
		
		//clear the double buffer
		this.g.setColor(getBackground());
		this.g.fillRect(0, 0, getWidth(), getHeight());
		fsm.paint(this.g); //paint the current state to double buffer
		
		//debug box thing
		if(DEBUG)
		{
			this.g.setColor(Color.BLACK);
			this.g.fillRect(0, 0, 128, 64);
			this.g.setColor(Color.RED);
			if((System.currentTimeMillis() - time) > 0)
			{
				fps += (1000 / (System.currentTimeMillis() - time));
				this.g.drawString("FPS: " + (1000 / (System.currentTimeMillis() - time)), 0, 10);
			}
			this.g.drawString("Average FPS: " + fps/loop, 0, 20);
			this.g.drawString("Threads: " + Thread.activeCount(), 0, 30);
			time = System.currentTimeMillis();
		}
		
		//paint double buffer to screen
		g.drawImage(img, 0, 0, this);
	}

	//trigger mouse/keyboard events
	
	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		fsm.iem.add(new Event("mouseDragged", arg0));
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		fsm.iem.add(new Event("mouseMoved", arg0));
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		fsm.iem.add(new Event("mouseClicked", arg0));
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		fsm.iem.add(new Event("mouseEntered", arg0));
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		fsm.iem.add(new Event("mouseExited", arg0));
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		fsm.iem.add(new Event("mousePressed", arg0));
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		fsm.iem.add(new Event("mouseReleased", arg0));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		fsm.iem.add(new Event("mouseWheelMoved", arg0));
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		fsm.iem.add(new Event("keyPressed", arg0));
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{	
		fsm.iem.add(new Event("keyReleased", arg0));
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		fsm.iem.add(new Event("keyTyped", arg0));
	}
}