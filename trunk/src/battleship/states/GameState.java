package battleship.states;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import battleship.Event;
import battleship.Events;
import battleship.FiniteStateMachine;
import battleship.Main;
import battleship.client.Client;
import battleship.gameobjects.Battleship;
import battleship.gameobjects.Button;
import battleship.gameobjects.GameObject;
import battleship.gameobjects.Playfield;
import battleship.gameobjects.PopupWindow;
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
	private static STATE m_STATE;
	
	protected Object m_syncObj = new Object();
	//TODO: Change all things to m_
	protected Frame frame;
	protected PopupWindow popup;
	protected cServer m_Server;
	protected cClient m_Client;
	
	protected ArrayList<String> mPrint = new ArrayList<String>();
	
	/**
	 * Default constructor, does a few things:
	 * 1. sets the name 2. Adds the targeting and status screens 3. Adds objects to screens
	 */
	public GameState()
	{
		super();
		//Initialise variables
		m_Server = null;
		m_Client = null;
		mName = "GameState";
		
		//Create the grids 
		Playfield pStat = new Playfield("Status Screen", new Rectangle(24, 240+24, 10, 10), new Dimension(24, 24));
		Playfield pTrg = new Playfield("Targeting Screen", new Rectangle(24, 0, 10, 10), new Dimension(24, 24));
		
		//Add ships to the Status Screen
		pStat.m_Obj.add(new Battleship("BS1", new Rectangle(0, 0, 49, 24), GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		pStat.m_Obj.add(new Battleship("BS2", new Rectangle(0, 0, 49, 24), GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		//pStat.m_Obj.add(new Battleship("BS3", new Rectangle(0, 0, 49, 24), GameObject.loadImage("res/img/Ship1.png"), GameObject.loadImage("res/img/Ship2.png")));
		
		//Add the Fuzzy logic thing to the Targeting screen
		pTrg.m_FuSM = new FiniteStateMachine();
		pTrg.m_FuSM.addState(new GenericState());
		pTrg.m_FuSM.addState(new UserState());
		pTrg.m_FuSM.addState(new Multiplayer());
		pTrg.m_FuSM.m_Name = "FuSM";
		pTrg.setM_TargetGrid(true);
		//ensure m_FuSM is empty
		pStat.setM_TargetGrid(false);
		//Add the grids to this state
		mObj.add(pTrg);
		mObj.add(pStat);
		
		//Add the buttons
		mObj.add(new Button("StartGame", new Rectangle(300+210+32, 50, 1,1), GameObject.loadImage("res/img/BeginGame.png")));
		mObj.add(new Button("EndGame", new Rectangle(300, 50, 1,1), GameObject.loadImage("res/img/EndGame.png")));
		mObj.add(new Button("Help", new Rectangle(280, 200, 1, 1), GameObject.loadImage("res/img/Help.png")));
		
		//Add the popup window
		popup = new PopupWindow();
		popup.setBackground(Color.GREEN);
		frame = new Frame("IP Address");
		frame.addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					System.exit(0);
				}
			}); 
		frame.add(popup);
		frame.setSize(400, 100);
		//hide the window
		frame.setVisible(false);
		
		//Repaint
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
		Events.get().add(new Event("removeState", mName, "Main"));
		Battleship.mShips.clear();
		//mEventMgr.add(new Event("removeState", "UserState", "FuSM"));
		//mEventMgr.add(new Event("removeState", "AIBombState", "FuSM"));
		mObj.clear();
		//mServer.finalize();
		if (m_Server != null)
			m_Server.end();
		m_Server = null;
		m_Client = null;
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
		g.fillRect(0, 0, Main.m_Dim.width, Main.m_Dim.height);
		for(int i = 0; i < mObj.size(); i++)
			mObj.get(i).paint(g);
		g.setColor(new Color(255,0,0));
		g.setFont(new Font("Dialog", Font.BOLD, 14));
		while(mPrint.size()>6)
			mPrint.remove(0);
		for(int i = 0; i < mPrint.size(); i++)
			g.drawString(mPrint.get(i), 450, 250+18*i);
	}
	
	@Override
	public void processEvents()
	{
		for(int i = 0; i < Events.get().size(); i++)
		{
			//game mode events
			if(Events.get().get(i).m_Event.equals("mode"))
			{
				//host a game
				if(Events.get().get(i).m_Param.equals("Host"))
				{
					//Start the server
					m_Server = cServer.getServer();
					//listen to events from the server
					m_Server.addNetworkListener(this);
					//start the server
					m_Server.start();
					//set the state
					m_STATE = STATE.HOST;
					//set button "StartGame" to invisible
					Events.get().add(new Event("visibility", false, "StartGame"));
					//add thing to output
					mPrint.add("Server started.");
				}
				//join a game
				else if(Events.get().get(i).m_Param.equals("Join"))
				{
					//insert join specific stuff here
					//mEventMgr.add(new Event("setState", "MenuState", "Main"));
					//mEventMgr.add(new Event("error", "Joining not implemented"));
					//make sure there is no server running
					//display the popup window
					frame.setVisible(true);
					synchronized (m_syncObj)
						{
						//popup.init();
						popup.init(m_syncObj, "Enter ip of computer to join game", 0);
						//popup.start();
						try {
							m_syncObj.wait();
						} catch (InterruptedException e1) {}
					}
					frame.setVisible(false);
					//ensure there is no server available
					m_Server = null;
					m_Client = cClient.getClient();
					m_Client.addClientListener(this);
					m_Client.setName("Client");
					m_Client.start();
					m_Client.login(popup.p_str, "client");
					//set button "StartGame" to invisible
					Events.get().add(new Event("visibility", false, "StartGame"));
					//output to screen
					mPrint.add("Client started");
					m_STATE = STATE.jMULTI;
				}
				else if(Events.get().get(i).m_Param.equals("Single"))
				{
					//insert single specific stuff here
					System.out.println("Fuzzy logic being developed");
					Events.get().add(new Event("setState", "MenuState", "Main"));
					//mSTATE = STATE.SINGLE;
					//mEventMgr.add(new Event("repaint"));
				}
				Events.get().remove(i);
			}
			
			//socket events
			else if (Events.get().get(i).m_Event.equals("socket")) 
			{
				if(Events.get().get(i).m_Param.equals("Open"))
				{//when socket is opened...
					mPrint.add("Socket open, waiting for local client...");
					//Get client
					m_Client = cClient.getClient();
					//listen to events from the client
					m_Client.addClientListener(this);
					m_Client.setName("Client");
					//Begin the client
					m_Client.start();
					//Login to local host
					m_Client.login("127.0.0.1", "host");
					m_STATE = STATE.hRUNNING;
				}
				else if (Events.get().get(i).m_Param.equals("Error"))
				{
					mPrint.add("Error opening socket");
					mPrint.add("Reopen game");
				}
				Events.get().remove(i);
			}
			
			//client events
			else if(Events.get().get(i).m_Event.equals("client"))
			{
				if(Events.get().get(i).m_Param.equals("Connected"))
				{
				        InetAddress addr = null;
						try {
							addr = InetAddress.getLocalHost();
						} catch (UnknownHostException e) {}
					mPrint.add("client connected");
					if(m_STATE == STATE.hRUNNING)//local client joined
						mPrint.add("waiting for player to join game on ip: " + addr.getHostAddress());
					else//other user
						mPrint.add("joining game");
					Events.get().add(new Event("repaint"));
				}
				//if some has joined the game
				else if(Events.get().get(i).m_Param.equals("joined")){
					if (m_STATE == STATE.jMULTI)
						mPrint.add("You joined a game");
					else
						mPrint.add("Someone joined the game!");
					Events.get().add(new Event("visibility", true, "StartGame"));
				}
				//they left the game
				else if(Events.get().get(i).m_Param.equals("logout"))
				{
					mPrint.add("Your opponent left the game :(");
					mPrint.add("The Game will now exit");
					Events.get().add(new Event("repaint"));
					System.exit(1);
					//TODO: will not draw this
				}
				Events.get().remove(i);
			}
			
			//client errors
			else if (Events.get().get(i).m_Event.equals("clientError"))
			{
				if (Events.get().get(i).m_Param.equals("connection"))
				{
					mPrint.add("Unable to connect, retrying");
					Events.get().add(new Event("repaint"));
				}
				Events.get().remove(i);
			}
			//game lost
			else if (Events.get().get(i).m_Event.equals("gamelost"))
			{
				mPrint.add("You have lost the game");
				Events.get().remove(i);
			}
			
			//button clicked
			else if(Events.get().get(i).m_Event.equals("buttonClicked"))
			{
				if(Events.get().get(i).m_Param.equals("EndGame"))
				{
					if(m_STATE != STATE.sRUNING)// || m_STATE != STATE.SINGLE)
						m_Client.logout();
					System.exit(0);
					//Events.get().add(new Event("setState", "MenuState", "Main"));
					//Events.get().remove(i);
				}
				//if start game is pressed
				if(Events.get().get(i).m_Param.equals("StartGame")) {
					if(chkShips()){
						//make ships non-movable
						Events.get().add(new Event("setShips", "SET"));
						Events.get().add(new Event("repaint"));//repaint
						if(m_STATE == STATE.SINGLE){
							m_STATE = STATE.sRUNING;
							Events.get().add(new Event("setState", "UserState", "FuSM"));//begin single player game
						}
						else if(m_STATE == STATE.hRUNNING || m_STATE == STATE.jMULTI){
							m_STATE = STATE.hMULTI;
							m_Client.sendMessage("gameStarted");
							Events.get().add(new Event("setState", "MultiPlayer", "FuSM"));//begin militplayer
						}
					}
					else
					{
						Events.get().add(new Event("repaint"));
						mPrint.add("Error, all ships must be on the grid, and be seperate");
					}
					Events.get().remove(i);
				}
				else if (Events.get().get(i).m_Param.equals("Help"))
				{
					frame.setSize(500, 600);
					frame.setVisible(true);
					synchronized (m_syncObj)
						{
						//popup.init();
						popup.init(m_syncObj, "FAQ:", 1);
						frame.validate();
						popup.start();
						try {
							m_syncObj.wait();
						} catch (InterruptedException e1) {}
					}
					frame.setVisible(false);
					frame.setSize(400, 100);
					Events.get().remove(i);
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
		Events.get().add(new Event("socket", "Open"));
	}

	@Override
	public void messageReceived(Event e) {
		Events.get().add(e);
	}

	@Override
	public void sockClose() {
		Events.get().add(new Event("socket", "Close"));
		
	}

	@Override
	public void error(Event e) {
		Events.get().add(e);
		
	}

	@Override
	public void connected() {
		Events.get().add(new Event("client", "Connected"));
		
	}

	@Override
	public void clientMsg(Event e) {
		Events.get().add(e);
	}

	@Override
	public void shipsRecieve(String str) {
		if(str.startsWith("grid"))
			Events.get().add(new Event("grid", Multiplayer.convertString(str.substring(4))));
		else
			Events.get().add(new Event("clientMessage", str));
	}

	/**
	 * @param mSTATE the mSTATE to set
	 */
	public void setMSTATE(STATE mSTATE) {
		this.m_STATE = mSTATE;
	}

	/**
	 * @return the mSTATE
	 */
	public static STATE getMSTATE() {
		return m_STATE;
	}
}