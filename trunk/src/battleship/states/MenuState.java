package battleship.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.Event;
import battleship.Events;
import battleship.Main;
import battleship.gameobjects.Background;
import battleship.gameobjects.Button;
import battleship.gameobjects.DialogueBox;
import battleship.gameobjects.GameImage;
import battleship.gameobjects.GameObject;

/**
 * Menu State~
 * @author Amec
 * @author Obi
 */

public class MenuState extends State
{
	/**
	 * Default constructor
	 * Adds buttons and main title
	 */
	public MenuState()
	{
		mName = "MenuState";
		mObj.add(new Background("BG", new Rectangle(0, 0, Main.mDim.width, 300)));
		mObj.add(new Button("HostGame", new Rectangle((800-210)/2-210-32, 256+128, 1, 1), GameObject.loadImage("res/img/HostGame.png")));
		mObj.add(new Button("JoinGame", new Rectangle((800-210)/2, 256+128, 1, 1), GameObject.loadImage("res/img/JoinGame.png")));
		mObj.add(new Button("SinglePlayer", new Rectangle((800-210)/2+210+32, 256+128, 1, 1), GameObject.loadImage("res/img/SinglePlayer.png")));
		mObj.add(new GameImage("MainTitle", new Rectangle((800-635)/2, 64, 1, 1), GameObject.loadImage("res/img/GameTitle.png")));
		mObj.add(new DialogueBox(new Rectangle(100, 200, 300, 400)));
		Events.get().add(new Event("repaint"));
	}

	@Override
	public void enterState()
	{
		Events.get().add(new Event("repaint"));
	}

	@Override
	public void exitState()
	{
		
	}

	@Override
	public void run()
	{
		for(GameObject go : mObj)
			go.run();//call 'update' of child objects
		processEvents();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Main.mDim.width, Main.mDim.height);
		for(GameObject go : mObj)
			go.paint(g);
	}

	@Override
	public void processEvents()
	{
		for(int i = 0; i < Events.get().size(); i++)
		{
			if(Events.get().get(i).mEvent.equals("buttonClicked"))
			{
				Events.get().add(new Event("addState", new GameState(), "Main"));
				if(Events.get().get(i).mParam.equals("HostGame"))
				{
					Events.get().add(new Event("setState", "GameState", "Main"));
					Events.get().add(new Event("mode", "Host"));
				}
				else if(Events.get().get(i).mParam.equals("JoinGame"))
				{
					Events.get().add(new Event("setState", "GameState", "Main"));
					Events.get().add(new Event("mode", "Join"));
				}
				else if(Events.get().get(i).mParam.equals("SinglePlayer"))
				{
					Events.get().add(new Event("setState", "GameState", "Main"));
					Events.get().add(new Event("mode", "Single"));
				}
				Events.get().remove(i);
			}
		}
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).processEvents();
	}
}