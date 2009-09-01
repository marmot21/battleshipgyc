package battleship;


import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Singleton input class
 * @author Amec
 * @author Obi
 *
 */
public class Input
{
	private static Input mInstance;
	private boolean[] mKeys = new boolean[256];
	private boolean mBUTTON1, mBUTTON2, mBUTTON3;
	private Point mMouse = new Point();
	
	private Input()
	{
		//stuff
	}
	
	public static Input get()
	{
		if (mInstance == null)
			mInstance = new Input();
		return mInstance;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		throw new CloneNotSupportedException(); 
		//prevent any cloning
	}
	
	public void init(Component c)
	{
		c.addKeyListener(new Key());
		c.addMouseListener(new Mouse());
		c.addMouseMotionListener(new MouseMotion());
		c.addMouseWheelListener(new MouseWeel());
	}
	
	public boolean keyIsPressed(int keyCode)
	{
		return mKeys[keyCode];
	}
	
	public boolean mouseIsPressed(int button)
	{
		switch(button)
		{
			case MouseEvent.BUTTON1:
				return mBUTTON1;
			case MouseEvent.BUTTON2:
				return mBUTTON2;
			case MouseEvent.BUTTON3:
				return mBUTTON3;
		}
		return false;
	}
	
	public Point getMouse()
	{
		return mMouse;
	}
	
	private class Key extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			mKeys[e.getKeyCode()] = true;
		}
		
		public void keyReleased(KeyEvent e)
		{
			mKeys[e.getKeyCode()] = false;
		}
	}
	
	private class MouseWeel extends MouseAdapter
	{

		@Override
		public void mouseWheelMoved(MouseWheelEvent arg0) {
			mBUTTON3 = !mBUTTON3;
		}

		
		
	}
	
	private class Mouse extends MouseAdapter
	{
		public void mousePressed(MouseEvent arg0)
		{
			switch(arg0.getButton())
			{
				case MouseEvent.BUTTON1:
					mBUTTON1 = true;
					break;
				case MouseEvent.BUTTON2:
					mBUTTON2 = true;
					break;
				//case MouseEvent.BUTTON3:
				//	mBUTTON3 = true;
				//	break;
			}
		}

		public void mouseReleased(MouseEvent arg0)
		{
			switch(arg0.getButton())
			{
				case MouseEvent.BUTTON1:
					mBUTTON1 = false;
					break;
				case MouseEvent.BUTTON2:
					mBUTTON2 = false;
					break;
				//case MouseEvent.BUTTON3:
				//	mBUTTON3 = false;
				//	break;
			}
		}
	}
	
	private class MouseMotion extends MouseMotionAdapter
	{
		public void mouseDragged(MouseEvent arg0)
		{
			mMouse = arg0.getPoint();
		}

		public void mouseMoved(MouseEvent arg0)
		{
			mMouse = arg0.getPoint();
		}
	}
}