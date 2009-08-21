package battleship.states;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import battleship.Event;
import battleship.EventManager;
import battleship.FiniteStateMachine;
import battleship.Main;
import battleship.client.Client;
import battleship.gameobjects.Battleship;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameObject;
import battleship.gameobjects.Playfield;
import battleship.server.Server;

/**
 * The game state.
 * @author Amec
 * @author Obi
 */

public class GameState extends State implements Server, Client
{
	 //we don't want this to be changed by an outside source
	protected enum STATE
	{
		SINGLE, sRUNING, HOST, hRUNNING
	}
	
	/**
	 * The current state
	 */
	protected STATE mSTATE; 
	
	protected static cServer mServer;
	protected static cClient mClient;
	
	/**
	 * Default constructor, does a few things:
	 * 1. sets the name 2. Adds the targeting and status screens 3. Adds objects to screens
	 */
	public GameState(EventManager mEventMgr)
	{
		mName = "GameState";
		this.mEventMgr = mEventMgr;
		//Create the grids 
		Playfield pStat = new Playfield("Status Screen", new Rectangle(24, 240+24, 10, 10), mEventMgr, new Dimension(24, 24));
		Playfield pTrg = new Playfield("Targeting Screen", new Rectangle(24, 0, 10, 10), mEventMgr, new Dimension(24, 24));
		
		//Add ships to the Status Screen
		pStat.mObj.add(new Battleship("BS1", new Rectangle(0, 0, 49, 24), mEventMgr, GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		pStat.mObj.add(new Battleship("BS2", new Rectangle(0, 0, 49, 24), mEventMgr, GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		
		//Add the Fuzzy logic thing to the Targeting screen
		pTrg.mFuSM = new FiniteStateMachine();
		pTrg.mFuSM.addState(new GenericState());
		pTrg.mFuSM.addState(new UserState(mEventMgr));
		pTrg.mFuSM.addState(new AIBombState());
		pTrg.mFuSM.mName = "FuSM";
		//Add the grids to this state
		mObj.add(pTrg);
		mObj.add(pStat);
		
		//Add the buttons
		mObj.add(new Button("StartGame", new Rectangle(300+210+32, 50, 1,1), mEventMgr, GameObject.loadImage("res/img/BeginGame.png")));
		mObj.add(new Button("EndGame", new Rectangle(300, 50, 1,1), mEventMgr, GameObject.loadImage("res/img/EndGame.png")));
		//Repaint
		this.mEventMgr.add(new Event("repaint"));
	}

	@Override
	public void enterState()
	{
		mEventMgr.add(new Event("repaint"));
	}

	@Override
	public void exitState()
	{
		mEventMgr.add(new Event("removeState", mName, "Main"));
		mObj.clear();
		mServer = null;
	}

	@Override
	public void run()
	{
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).run();
	}

	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Main.mDim.width, Main.mDim.height);
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).paint(g);
	}
	
	@Override
	public void processEvents()
	{
		for(int i = 0; i < mEventMgr.size(); i++)
		{
			if(mEventMgr.get(i).mEvent.equals("mode"))
			{
				if(mEventMgr.get(i).mParam.equals("Host"))
				{
					cServer.addNetworkListener(this);
					//Start the server
					mServer = new cServer();
					mServer.start();
					mSTATE = STATE.HOST;
				}
				else if(mEventMgr.get(i).mParam.equals("Join"))
				{
					//insert join specific stuff here
					mEventMgr.add(new Event("setState", "MenuState", "Main"));
					mEventMgr.add(new Event("error", "Joining not implemented"));
				}
				else if(mEventMgr.get(i).mParam.equals("Single"))
				{
					//insert single specific stuff here
					mSTATE = STATE.SINGLE;
					mEventMgr.add(new Event("repaint"));
				}
				mEventMgr.consume(i);
			}
			else if (mEventMgr.get(i).mEvent.equals("socket")) 
			{
				if(mEventMgr.get(i).mParam.equals("Open"))
				{
					cClient.addClientListener(this);
					mClient = new cClient();
					mClient.start();
					mSTATE = STATE.hRUNNING;
				}
			}
			else if(mEventMgr.get(i).mEvent.equals("client"))
			{
				if(mEventMgr.get(i).mParam.equals("Connected"))
				{
					cClient.logon("192.168.1.85");
				}
			}
			else if(mEventMgr.get(i).mEvent.equals("buttonClicked"))
			{
				if(mEventMgr.get(i).mParam.equals("EndGame"))
				{
					mEventMgr.add(new Event("setState", "MenuState", "Main"));
					mEventMgr.consume(i);
				}
				if(mEventMgr.get(i).mParam.equals("StartGame")) {
					mEventMgr.add(new Event("setShips", "SET"));
					mEventMgr.add(new Event("repaint"));//repaint
					if(mSTATE == STATE.SINGLE){
						mSTATE = STATE.sRUNING;
						mEventMgr.add(new Event("setState", "UserState", "FuSM"));//begin single player game
					}
					if(mSTATE == STATE.HOST){
						mSTATE = STATE.hRUNNING;
						mEventMgr.add(new Event("setState", "UserState", "FuSM"));//begin militplayer
					}
					mEventMgr.consume(i);
				}
			}
		}
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).processEvents();
	}

	@Override
	public void sockOpen() {
		mEventMgr.add(new Event("socket", "Open"));
	}

	@Override
	public void messageReceived(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sockClose() {
		mEventMgr.add(new Event("socket", "Close"));
		
	}

	@Override
	public void error(Event e) {
		mEventMgr.add(e);
		
	}

	@Override
	public void connected() {
		mEventMgr.add(new Event("client", "Connected"));
		
	}

	@Override
	public void clientMsg(Event e) {
		mEventMgr.add(e);
	}
}