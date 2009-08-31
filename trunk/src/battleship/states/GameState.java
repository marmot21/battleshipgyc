package battleship.states;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import battleship.Event;
import battleship.EventManager;
import battleship.FiniteStateMachine;
import battleship.Main;
import battleship.client.Client;
import battleship.gameobjects.Battleship;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameObject;
import battleship.gameobjects.Playfield;
import battleship.gameobjects.Battleship.SHIPS;
import battleship.server.Server;

/**
 * The game state.
 * @author Amec
 * @author Obi
 */

public class GameState extends State implements Server, Client
{
	/**
	 * Single - Single player. 
	 * sRUNNING - Single player game running. 
	 * Host - Host a game. 
	 * hRUNNING - host is up and running. 
	 * hMULTI - hosting a multiplayer game, game is running. 
	 *
	 */
	public enum STATE
	{
		SINGLE, sRUNING, HOST, hRUNNING, hMULTI, jMULTI
	}
	
	/**
	 * The current state
	 *///we don't want this to be changed by an outside source
	private static STATE mSTATE; 
	
	protected cServer mServer;
	protected cClient mClient;
	
	protected ArrayList<String> mPrint = new ArrayList<String>();
	
	/**
	 * Default constructor, does a few things:
	 * 1. sets the name 2. Adds the targeting and status screens 3. Adds objects to screens
	 */
	public GameState(EventManager mEventMgr)
	{
		super();
		//Initialise variables
		mServer = null;
		mClient = null;
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
		pTrg.mFuSM.addState(new Multiplayer(mEventMgr));
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
		Battleship.mShips.clear();
		//mEventMgr.add(new Event("removeState", "UserState", "FuSM"));
		//mEventMgr.add(new Event("removeState", "AIBombState", "FuSM"));
		mObj.clear();
		//mServer.finalize();
		mServer = null;
		mClient = null;
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
		g.setColor(new Color(255,0,0));
		while(mPrint.size()>10)
			mPrint.remove(0);
		for(int i = 0; i < mPrint.size(); i++)
			g.drawString(mPrint.get(i), 400, 250+15*i);
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
					//set the state
					mSTATE = STATE.HOST;
					//set button "StartGame" to invisible
					mEventMgr.add(new Event("visibility", false, "StartGame"));
					//add thing to output
					mPrint.add("Server started.");
				}
				else if(mEventMgr.get(i).mParam.equals("Join"))
				{
					//insert join specific stuff here
					//mEventMgr.add(new Event("setState", "MenuState", "Main"));
					//mEventMgr.add(new Event("error", "Joining not implemented"));
					//make sure there is no server running
					mServer = null;
					mClient = new cClient();
					cClient.addClientListener(this);
					mClient.setName("Client");
					mClient.start();
					mClient.login("127.0.0.1", "client");
					//set button "StartGame" to invisible
					mEventMgr.add(new Event("visibility", false, "StartGame"));
					//output to screen
					mPrint.add("Client started");
					mSTATE = STATE.jMULTI;
				}
				else if(mEventMgr.get(i).mParam.equals("Single"))
				{
					//insert single specific stuff here
					System.out.println("Fuzzy logic being developed");
					mEventMgr.add(new Event("setState", "MenuState", "Main"));
					//mSTATE = STATE.SINGLE;
					//mEventMgr.add(new Event("repaint"));
				}
				mEventMgr.consume(i);
			}
			else if (mEventMgr.get(i).mEvent.equals("socket")) 
			{
				if(mEventMgr.get(i).mParam.equals("Open"))
				{//when socket is opened...
					mPrint.add("Socket open, waiting for local client...");
					cClient.addClientListener(this);
					mClient = new cClient();
					mClient.setName("Client");
					mClient.start();
					mClient.login("127.0.0.1", "host");
					mSTATE = STATE.hRUNNING;
				}
				mEventMgr.consume(i);
			}
			else if(mEventMgr.get(i).mEvent.equals("client"))
			{
				if(mEventMgr.get(i).mParam.equals("Connected"))
				{
				        InetAddress addr = null;
						try {
							addr = InetAddress.getLocalHost();
						} catch (UnknownHostException e) {}
					mPrint.add("client connected");
					if(mSTATE == STATE.hRUNNING)//local client joined
						mPrint.add("waiting for player to join game on ip: " + addr.getHostAddress());
					else//other user
						mPrint.add("joining game");
					mEventMgr.add(new Event("repaint"));
				}
				//if some has joined the game
				else if(mEventMgr.get(i).mParam.equals("joined")){
					mPrint.add("Someone joined the game!");
					mEventMgr.add(new Event("visibility", true, "StartGame"));
				}
				//they left the game
				else if(mEventMgr.get(i).mParam.equals("clientlogout"))
				{
					mPrint.add("Your opponent left the game :(");
					mPrint.add("The Game will now exit");
					mEventMgr.add(new Event("repaint"));
					System.exit(1);
				}
				mEventMgr.consume(i);
			}
			else if(mEventMgr.get(i).mEvent.equals("buttonClicked"))
			{
				if(mEventMgr.get(i).mParam.equals("EndGame"))
				{
					mEventMgr.add(new Event("setState", "MenuState", "Main"));
					mEventMgr.consume(i);
				}
				//if start game is pressed
				if(mEventMgr.get(i).mParam.equals("StartGame")) {
					if(chkShips()){
						//make ships non-movable
						mEventMgr.add(new Event("setShips", "SET"));
						mEventMgr.add(new Event("repaint"));//repaint
						if(mSTATE == STATE.SINGLE){
							mSTATE = STATE.sRUNING;
							mEventMgr.add(new Event("setState", "UserState", "FuSM"));//begin single player game
						}
						else if(mSTATE == STATE.hRUNNING || mSTATE == STATE.jMULTI){
							mSTATE = STATE.hMULTI;
							mEventMgr.add(new Event("setState", "MultiPlayer", "FuSM"));//begin militplayer
						}
						cClient.sendMessage("gameStarted");
					}
					else
					{
						mEventMgr.add(new Event("repaint"));
						mPrint.add("Error, all ships must be on the grid, and be seperate");
					}
					mEventMgr.consume(i);
				}
			}
		}
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).processEvents();//pass on events
	}

	/**
	 * Checks if all the ships have been moved
	 * @return false if one has not been moved.
	 */
	private boolean chkShips()
	{
		if(!Multiplayer.setGrid())
			return false;
		for(Battleship go : Battleship.mShips)
		{
			if(go.STATE == SHIPS.INIT)
				return false;
		}
		return true;
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

	@Override
	public void shipsRecieve(String str) {
		if(str.startsWith("grid"))
			mEventMgr.add(new Event("grid", Multiplayer.convertString(str.substring(4))));
		else
			mEventMgr.add(new Event("clientMessage", str));
	}

	/**
	 * @param mSTATE the mSTATE to set
	 */
	public void setMSTATE(STATE mSTATE) {
		this.mSTATE = mSTATE;
	}

	/**
	 * @return the mSTATE
	 */
	public static STATE getMSTATE() {
		return mSTATE;
	}
}