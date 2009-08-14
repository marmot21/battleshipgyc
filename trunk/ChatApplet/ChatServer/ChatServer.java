package ChatServer;

import java.awt.*;
import java.awt.event.*;

public class ChatServer extends Frame
{
	public ChatServer()
	{
		this.addWindowListener	(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				dispose();
				System.exit(0);
			}
		});
	}
	
	public static void main(String args[])
	{
		System.out.println("Starting App");
		ChatServer f = new ChatServer();
		f.setSize(100,100);
		f.show();
	}
}