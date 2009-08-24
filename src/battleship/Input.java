package battleship;


import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


public class Input
{
	private static Input mInstance = new Input();
	private boolean[] mKeys = new boolean[256];
	private boolean mBUTTON1, mBUTTON2, mBUTTON3;
	private Point mMouse;
	
	public static Input get()
	{
		return mInstance;
	}
	
	public void init(Component c)
	{
		c.addKeyListener(new Key());
		c.addMouseListener(new Mouse());
		c.addMouseMotionListener(new MouseMotion());
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
				case MouseEvent.BUTTON3:
					mBUTTON3 = true;
					break;
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
				case MouseEvent.BUTTON3:
					mBUTTON3 = false;
					break;
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