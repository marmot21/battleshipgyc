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
	public static Dimension dim = new Dimension(800, 600);
	private FiniteStateMachine fsm = new FiniteStateMachine();
	
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
		fsm.em.add(new Event("setState", "MenuState")); //enter the Menu state
		while(true)
		{
			fsm.run(); //update current state
			if(fsm.getState().repaint)
			{
				fsm.getState().paint(); //paint the current state
				repaint(); //paint state to screen
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
		g.drawImage(fsm.getState().getImage(), 0, 0, this);	
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