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
	Object p_parent;
	public String p_str;
	
	/**
	 * Popup window for joining the game.
	 */
	public void init(Object obj, String text)
	{
		//set variables
		p_str = new String();
		p_parent = obj;
		
		t = new TextField(12);
		l = new Label(text);
		
		add(l);
		add(t);
		
		t.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		p_str = t.getText();
		synchronized (p_parent)
		{
			p_parent.notify();
		}
	}

}
