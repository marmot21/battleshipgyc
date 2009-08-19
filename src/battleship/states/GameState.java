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
	protected enum STATE { //we don't want this to be changed by an outside source
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
	public GameState()
	{
		mName = "GameState";
		//Create the grids 
		Playfield pStat = new Playfield("Status Screen", new Rectangle(24, 240+24, 10, 10), new Dimension(24, 24));
		Playfield pTrg = new Playfield("Targeting Screen", new Rectangle(24, 0, 10, 10), new Dimension(24, 24));
		
		//Add ships to the Status Screen
		pStat.mObj.add(new Battleship("BS1", new Rectangle(0, 0, 49, 24), GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		pStat.mObj.add(new Battleship("BS2", new Rectangle(0, 0, 49, 24), GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		
		//Add the Fuzzy logic thing to the Targeting screen
		pTrg.mFuSM = new FiniteStateMachine();
		pTrg.mFuSM.addState(new GenericState());
		pTrg.mFuSM.addState(new UserState());
		pTrg.mFuSM.addState(new AIBombState());
		
		//Add the grids to this state
		mObj.add(pTrg);
		mObj.add(pStat);
		
		//Add the buttons
		mObj.add(new Button("StartGame", new Rectangle(300+210+32, 50, 1,1), GameObject.loadImage("res/img/BeginGame.png")));
		mObj.add(new Button("EndGame", new Rectangle(300, 50, 1,1), GameObject.loadImage("res/img/EndGame.png")));
		//Repaint
		mStateEventMgr.add(new Event("repaint"));
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#enterState()
	 */
	@Override
	public void enterState()
	{
		
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#exitState()
	 */
	@Override
	public void exitState()
	{
		mStateEventMgr.add(new Event("deleteState", this));
		mServer = null;
		
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#run()
	 */
	@Override
	public void run()
	{
		for(GameObject go : mObj)
			go.update();
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, Main.mDim.width, Main.mDim.height);
		for(GameObject go : mObj) 
			go.paint(g);
	}
	
	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#getEvents()
	 */
	@Override
	public EventManager getEvents()//output of events
	{
		for(GameObject go : mObj)
			mStateEventMgr.addAll(go.getEvents());
		EventManager tmp = null;
		try
		{
			tmp = mStateEventMgr.clone();
			mStateEventMgr.clear();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace();
		}
		return tmp;
	}

	/**
	 * (non-Javadoc)
	 * @see battleship.states.State#pumpEvents(battleship.EventManager)
	 */
	@Override
	public void pumpEvents(EventManager em)//Input of events
	{
		for(int i = 0; i < em.size(); i++)
		{
			if(em.get(i).mEvent.equals("mode"))
			{
				if(em.get(i).mParam.equals("Host"))
				{
					cServer.addNetworkListener(this);
					//Start the server
					mServer = new cServer();
					mServer.start();
					mSTATE = STATE.HOST;
					em.consume(i);
				}
				else if(em.get(i).mParam.equals("Join"))
				{
					//insert join specific stuff here
					mStateEventMgr.add(new Event("setState", "MenuState"));
					mStateEventMgr.add(new Event("error", "Joining not implemented"));
					em.consume(i);
				}
				else if(em.get(i).mParam.equals("Single"))
				{
					//insert single specific stuff here
					mSTATE = STATE.SINGLE;
					mStateEventMgr.add(new Event("repaint"));
					em.consume(i);
				}
			}
			else if (em.get(i).mEvent.equals("socket")) 
			{
				if(em.get(i).mParam.equals("Open"))
				{
					cClient.addClientListener(this);
					mClient = new cClient();
					mClient.start();
					mSTATE = STATE.hRUNNING;
				}
			}
			else if(em.get(i).mEvent.equals("client"))
			{
				if(em.get(i).mParam.equals("Connected"))
				{
					cClient.logon("192.168.1.85");
				}
			}
			else if(em.get(i).mEvent.equals("buttonClicked"))
			{
				Button b = (Button)em.get(i).mParam;
				if(b.mName.equals("EndGame"))
				{
					mStateEventMgr.add(new Event("setState", "MenuState"));
					em.consume(i);
				}
				if(b.mName.equals("StartGame")) {
					mStateEventMgr.add(new Event("setShips", "SET"));
					mStateEventMgr.add(new Event("repaint"));//repaint
					if(mSTATE == STATE.SINGLE){
						mSTATE = STATE.sRUNING;
						mStateEventMgr.add(new Event("BeginGame", "SinglePlayer"));//begin single player game
					}
					if(mSTATE == STATE.HOST){
						mSTATE = STATE.hRUNNING;
						mStateEventMgr.add(new Event("BeginGame", "MulitPlayer"));//begin militplayer
					}
					em.consume(i);
				}
			}
		}
		for(GameObject go : mObj)
			go.pumpEvents(em);
	}

	@Override
	public void sockOpen() {
		mStateEventMgr.add(new Event("socket", "Open"));
	}

	@Override
	public void messageReceived(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sockClose() {
		mStateEventMgr.add(new Event("socket", "Close"));
		
	}

	@Override
	public void error(Event e) {
		mStateEventMgr.add(e);
		
	}

	@Override
	public void connected() {
		mStateEventMgr.add(new Event("client", "Connected"));
		
	}

	@Override
	public void clientMsg(Event e) {
		mStateEventMgr.add(e);
	}
}