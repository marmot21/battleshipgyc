package battleship;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;

import battleship.states.GameState;
import battleship.states.GenericState;
import battleship.states.MenuState;

public class Main extends Canvas implements Runnable, MouseMotionListener, MouseListener, MouseWheelListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private static int FPS = 60, SLEEP = 1000/FPS;
	public static boolean DEBUG = false;
	private BufferStrategy strategy;
	public static Dimension dim = new Dimension(800, 600);
	private FiniteStateMachine fsm = new FiniteStateMachine();
	private EventManager em = new EventManager();
	private EventManager iem = new EventManager();
	
	public Main()
	{
		JFrame geo = new JFrame("Battleship");
		JPanel panel = (JPanel)geo.getContentPane();
		panel.setPreferredSize(dim);
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		
		setBounds(0, 0, dim.width, dim.height);
		panel.add(this);
		setIgnoreRepaint(true);
		geo.pack();
		geo.setVisible(true);
		geo.addWindowListener
		(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			}
		);
		requestFocus();
		
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);

		fsm.addState(new GenericState());
		fsm.addState(new MenuState());
		fsm.addState(new GameState());
		
		Thread t = new Thread(this);
		t.start();
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
	
	public void run()
	{
		em.add(new Event("setState", "MenuState")); //enter the Menu state
		while(true)
		{
			em.flush(); //get rid of old input events
			em.addAll(iem);
			iem.clear();
			fsm.getState().run(); //update current state
			fsm.pumpEvents(em);
			em.addAll(fsm.getState().getEvents());
			
			for(int i = 0; i < em.size(); i++)
			{
				if(em.get(i).event.equals("error"))
				{
					System.out.println(em.get(i).param);
					em.consume(i);
				}
				else if(em.get(i).event.equals("repaint"))
				{
					Graphics g = strategy.getDrawGraphics(); //get the graphics object to use
					fsm.getState().paint(g); //paint the current state
					g.dispose(); //destroy g object
					strategy.show(); //show the image on screen
					em.consume(i);
				}
			}
			
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
		
	}

	//trigger mouse/keyboard events
	
	@Override
	public void mouseDragged(MouseEvent arg0)
	{
		iem.add(new Event("mouseDragged", arg0));
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		iem.add(new Event("mouseMoved", arg0));
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		iem.add(new Event("mouseClicked", arg0));
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		iem.add(new Event("mouseEntered", arg0));
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		iem.add(new Event("mouseExited", arg0));
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		iem.add(new Event("mousePressed", arg0));
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		iem.add(new Event("mouseReleased", arg0));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		iem.add(new Event("mouseWheelMoved", arg0));
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		iem.add(new Event("keyPressed", arg0));
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{	
		iem.add(new Event("keyReleased", arg0));
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		iem.add(new Event("keyTyped", arg0));
	}
}