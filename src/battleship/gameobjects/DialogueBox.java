package battleship.gameobjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class DialogueBox extends GameObject
{
	public DialogueBox(Rectangle rect)
	{
		mBounds = rect;
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.RED);
		g.fillRect(mBounds.x, mBounds.y, mBounds.width, mBounds.height);
	}

	@Override
	public void processEvents()
	{
		
	}

	@Override
	public void render()
	{
		
	}

	@Override
	public void run()
	{
		
	}

}
