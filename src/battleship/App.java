package battleship;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import battleship.states.*;

public class App extends Applet implements Runnable, MouseMotionListener, MouseListener, MouseWheelListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	public static int FPS = 30;
	public static int SLEEP = 1000/FPS;
	public Image img;
	public Graphics g;
	public FiniteStateMachine fsm = new FiniteStateMachine();
	public long time;
	public int loop, fps;
	
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
		
		fsm.addState(new GenericState());
		fsm.addState(new MenuState());
		fsm.addState(new GameState());
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run()
	{
		//fsm.em.triggerEvent(new Event("init"));
		fsm.setState("MenuState");
		while(true)
		{
			loop++;
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
		this.g.setColor(Color.GRAY);
		this.g.fillRect(0, 0, getWidth(), getHeight());
		fsm.paint(this.g);
		this.g.setColor(Color.BLACK);
		this.g.fillRect(0, 0, 128, 64);
		this.g.setColor(Color.RED);
		fps+=(1000/(System.currentTimeMillis() - time));
		this.g.drawString("FPS: "+(1000/(System.currentTimeMillis() - time)), 0, 10);
		this.g.drawString("Average FPS: "+fps/loop, 0, 20);
		this.g.drawString("Threads: "+Thread.activeCount(), 0, 30);
		g.drawImage(img, 0, 0, this);
		time = System.currentTimeMillis();
	}

	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		fsm.iem.trigger(new Event("mouseDragged", arg0));
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		fsm.iem.trigger(new Event("mouseMoved", arg0));
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		fsm.iem.trigger(new Event("mouseClicked", arg0));
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		fsm.iem.trigger(new Event("mouseEntered", arg0));
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		fsm.iem.trigger(new Event("mouseExited", arg0));
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		fsm.iem.trigger(new Event("mousePressed", arg0));
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		fsm.iem.trigger(new Event("mouseReleased", arg0));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		fsm.iem.trigger(new Event("mouseWheelMoved", arg0));
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		fsm.iem.trigger(new Event("keyPressed", arg0));
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{	
		fsm.iem.trigger(new Event("keyReleased", arg0));
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		fsm.iem.trigger(new Event("keyTyped", arg0));
	}
}