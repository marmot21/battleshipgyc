package battleship;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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

/**
 * The main class for Battleship. Sets up everything etc.
 * @author Amec
 * @author Obi
 */

public class Main extends Canvas implements Runnable, MouseMotionListener, MouseListener, MouseWheelListener, KeyListener
{
	private static final long serialVersionUID = 1L;
	private static int FPS = 60, SLEEP = 1000/FPS;
	public static boolean DEBUG = false;
	
	private BufferStrategy mStrategy;
	public static Dimension mDim = new Dimension(800, 600);
	private FiniteStateMachine mFSM = new FiniteStateMachine();
	private EventManager mEventMgr = new EventManager();
	private EventManager mInputEventMgr = new EventManager();//event manager used internally for input events, mouse etc.
	private boolean offScreen = false;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * Default constructor
	 */
	public Main()
	{
		final JFrame geo = new JFrame("Battleship");
		JPanel panel = (JPanel)geo.getContentPane();
		panel.setPreferredSize(mDim);
		panel.setLayout(null);
		panel.setBackground(Color.GRAY);
		
		setBounds(0, 0, mDim.width, mDim.height);
		panel.add(this);
		//setIgnoreRepaint(true);
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
		
		geo.addComponentListener
		(
			new ComponentAdapter()
		    {//Listens for the window moving
				public void componentMoved(ComponentEvent e)
				{
					Rectangle rect = new Rectangle (geo.getLocationOnScreen().x, geo.getLocationOnScreen().y, 
							mDim.width, mDim.height);
					if(!(new Rectangle(0,0,screenSize.width,screenSize.height)).contains(rect)) {
						offScreen = true;
					}
					else if(offScreen) 
					{//When the window is moved back onto the screen it redraws
						mInputEventMgr.add(new Event("repaint"));
						offScreen = false;
					}	
				}
		    }
		);

		requestFocus();
		
		/*
		 * Creates a bufferstrategy with 1 extra buffer
		 * mStrategy.show(); flips it
		 */
		createBufferStrategy(2);
		mStrategy = getBufferStrategy();
		
		//Add EventListeners
		addMouseMotionListener(this);
		addMouseListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		addFocusListener(new FocusListener());

		//Add states
		mFSM.mName = "Main";
		mFSM.addState(new GenericState());
		mFSM.addState(new MenuState(mEventMgr));
		//mFSM.addState(new GameState(mEventMgr));
		
		//Start the main thread
		Thread t = new Thread(this);
		t.setName("BattleShip-Main");
		t.start();
	}
	
	class FocusListener extends FocusAdapter //TODO: get it to redraw when when it goes of screen too
	{
	    public void focusGained(FocusEvent fe)
	    {
	    	mInputEventMgr.add(new Event("repaint"));
	    }
	    
	    public void focusLost(FocusEvent fe)
	    {
	    	
	    }
	  }

	
	/**
	 * Gee, I wonder what this does.
	 * Don't know Mr. P
	 * I'm black.
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Main();
	}
	
	/**
	 * Called when Thread t is started.
	 */
	public void run()
	{
		mEventMgr.add(new Event("setState", "MenuState", "Main")); //enter the Menu state
		mFSM.removeState("GenericState");//removes the generic state from the FSM
		boolean paint;
		while(true)
		{
			paint = false;
			mEventMgr.flush(); //get rid of old input events
			mEventMgr.addAll(mInputEventMgr);
			mInputEventMgr.clear();
			mFSM.getState().run(); //update current state
			mFSM.processEvents(mEventMgr);
			mFSM.getState().processEvents();
			//mEventMgr.print();
			for(int i = 0; i < mEventMgr.size(); i++)
			{
				if(mEventMgr.get(i).mEvent.equals("error"))
				{
					System.out.println(mEventMgr.get(i).mParam);
					mEventMgr.consume(i);
				}
				else if(mEventMgr.get(i).mEvent.equals("repaint"))
				{
					paint = true;
					mEventMgr.consume(i);
				}
			}
			
			for(int i = 0; i < mEventMgr.size(); i++)
			{
				if(mEventMgr.get(i).mEvent.equals("setState"))
				{
					paint = false;
				}
			}
			
			if(paint)
			{
				Graphics g = mStrategy.getDrawGraphics(); //get the graphics object to use
				mFSM.getState().paint(g); //paint the current state
				g.dispose(); //destroy g object
				mStrategy.show(); //show the image on screen
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
	public void mouseDragged(MouseEvent arg0)
	{
		mInputEventMgr.add(new Event("mouseDragged", arg0));
	}

	@Override
	public void mouseMoved(MouseEvent arg0)
	{
		mInputEventMgr.add(new Event("mouseMoved", arg0));
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
		mInputEventMgr.add(new Event("mouseClicked", arg0));
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		mInputEventMgr.add(new Event("mouseEntered", arg0));
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		mInputEventMgr.add(new Event("mouseExited", arg0));
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		mInputEventMgr.add(new Event("mousePressed", arg0));
	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		mInputEventMgr.add(new Event("mouseReleased", arg0));
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0)
	{
		mInputEventMgr.add(new Event("mouseWheelMoved", arg0));
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		mInputEventMgr.add(new Event("keyPressed", arg0));
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{	
		mInputEventMgr.add(new Event("keyReleased", arg0));
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{
		mInputEventMgr.add(new Event("keyTyped", arg0));
	}
}