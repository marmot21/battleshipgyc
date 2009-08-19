package battleship.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.Event;
import battleship.EventManager;
import battleship.Main;
import battleship.gameobjects.Background;
import battleship.gameobjects.Button;
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
	public MenuState(EventManager mEventMgr)
	{
		mName = "MenuState";
		this.mEventMgr = mEventMgr;
		mObj.add(new Background("BG", new Rectangle(0, 0, Main.mDim.width, 300)));
		mObj.add(new Button("HostGame", new Rectangle((800-210)/2-210-32, 256+128, 1, 1), mEventMgr, GameObject.loadImage("res/img/HostGame.png")));
		mObj.add(new Button("JoinGame", new Rectangle((800-210)/2, 256+128, 1, 1), mEventMgr, GameObject.loadImage("res/img/JoinGame.png")));
		mObj.add(new Button("SinglePlayer", new Rectangle((800-210)/2+210+32, 256+128, 1, 1), mEventMgr, GameObject.loadImage("res/img/SinglePlayer.png")));
		mObj.add(new GameImage("MainTitle", new Rectangle((800-635)/2, 64, 1, 1), mEventMgr, GameObject.loadImage("res/img/GameTitle.png")));
		mEventMgr.add(new Event("repaint"));
	}

	@Override
	public void enterState()
	{
		mEventMgr.add(new Event("repaint"));
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
		for(int i = 0; i < mEventMgr.size(); i++)
		{
			if(mEventMgr.get(i).mEvent.equals("buttonClicked"))
			{
				mEventMgr.add(new Event("addState", new GameState(mEventMgr), "Main"));
				if(mEventMgr.get(i).mParam.equals("HostGame"))
				{
					mEventMgr.add(new Event("setState", "GameState", "Main"));
					mEventMgr.add(new Event("mode", "Host"));
				}
				else if(mEventMgr.get(i).mParam.equals("JoinGame"))
				{
					mEventMgr.add(new Event("setState", "GameState", "Main"));
					mEventMgr.add(new Event("mode", "Join"));
				}
				else if(mEventMgr.get(i).mParam.equals("SinglePlayer"))
				{
					mEventMgr.add(new Event("setState", "GameState", "Main"));
					mEventMgr.add(new Event("mode", "Single"));
				}
				mEventMgr.consume(i);
			}
		}
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).processEvents();
	}
}