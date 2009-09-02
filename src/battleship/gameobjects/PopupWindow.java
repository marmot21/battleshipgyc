package battleship.gameobjects;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PopupWindow extends Applet implements ActionListener
{
	
	TextField t;
	Label l;
	java.awt.Button b;
	public Object p_parent;
	int flag;
	public String p_str;
	
	public void init()
	{
		repaint();
	}
	
	/**
	 * Popup window for joining the game.
	 */
	public void init(Object obj, String text, int flag)
	{
		//set variables
		p_str = new String();
		p_parent = obj;
		this.flag = flag;
		
		t = new TextField(12);
		l = new Label(text);
		b = new java.awt.Button("Return to game");
		removeAll();
		switch(flag)
		{
		case 0:
			add(l);
			add(t);
			break;
		case 1:
			add(b);
			add(l);
			//g.setFont(new Font("FAQfont", Font.ITALIC, 12));
			add(new Label("Q: How do I begin a game."));
			add(new Label("A: After someone has joined a game you may click 'Begin Game'"));
			add(new Label("Q: I've accidently draged the ships on top of each other"));
			add(new Label("A: We're sorry, you'll have to restart the game, but we are working on it"));
			add(new Label("Q: How do I play the game?"));
			add(new Label("A: After starting the game, you take it in turns to bomb the other player untill you win"));
			add(new Label("Q: What are the controls?"));
			add(new Label("A: Use mouse weel to rotate ships"));
			add(new Label("Use top grid to drop bombs, and the bottom to place your ships"));
			break;
		}
		t.addActionListener(this);
		b.addActionListener(this);
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		p_str = t.getText();
		synchronized (p_parent)
		{
			p_parent.notifyAll();
		}
	}
}
