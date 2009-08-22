/**
 * 
 */
package battleship.states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.StringTokenizer;

import battleship.Event;
import battleship.EventManager;
import battleship.client.Client;
import battleship.gameobjects.Battleship;
import battleship.gameobjects.Playfield;

/**
 * Handles the user dropping bombs etc
 * @author OBi
 *
 */
public class Multiplayer extends State implements Client
{
	/**
	 * Grid used to store ship positions and if they have been bombed etc.
	 * X and Y: the grid. 0-no ship, 1-battleship, 2-small ship
	 * Z: The status of that ship. 0-normal, 1-bombed, 2-ship hit
	 */
	private static int[][][] mPGrid = new int[10][10][2];
	private static int[][][] mOGrid = null;
	
	/**
	 * Wait: waiting to receive the ship positions from the other user. 
	 * Received: got the info from the other player. 
	 * Current: the current user's turn. 
	 * Other: the other player's turn. 
	 */
	private enum STATE {
		WAIT, RECEIVED, CURRENT, OTHER
	} 
	
	private STATE mSTATE = STATE.WAIT;
	
	/**
	 * Default Constructor
	 */
	public Multiplayer(EventManager mEventMgr)
	{
		mName = "MultiPlayer";
		this.mEventMgr = mEventMgr;
	}

	@Override
	public void enterState()
	{
		mEventMgr.add(new Event("visibility", false, "StartGame"));//set button "StartGame" to invisible
		cClient.addClientListener(this);//Receive events from the client
		mEventMgr.add(new Event("setGrid"));
	}

	/**
	 * Converts the grid into a string and vice-versa
	 * @return the string of the array
	 */
	private String convertArray() {
		StringBuffer buf = new StringBuffer();
		buf.append("grid");
		for(int iX = 0; iX<10; iX++){
			for(int iY = 0; iY<10; iY++){
				if(mPGrid[iX][iY][0]!=0)
				{
					buf.append(iX);
					buf.append(iY);
					buf.append(0);
					buf.append(mPGrid[iX][iY][0]+"&");
				}
				if(mPGrid[iX][iY][1]!=0)
				{
					buf.append(iX);
					buf.append(iY);
					buf.append(1);
					buf.append(mPGrid[iX][iY][0]+"&");
				}
			}
		}
		if (buf.length() >0 ) buf.setLength(buf.length()-1);
		return buf.toString();
	}
	
	static int[][][] convertString(String str)
	{
		StringTokenizer st = new StringTokenizer(str,"&");
		String tmp;
		int[][][] grid = new int[10][10][2];
		for(int i = 0; i<=st.countTokens(); i++)
		{
			tmp = st.nextToken();
			grid[(int)tmp.charAt(0)-48][(int)tmp.charAt(1)-48][(int)tmp.charAt(2)-48] = (int)tmp.charAt(3)-48;
		}
		return grid;
	}
	
	/**
	 * Inputs the ship positions into the grid
	 * @return true if no ships overlap each other - else false
	 */
	public static boolean setGrid()
	{
		mPGrid = new int[10][10][2];
		for(Battleship go : Battleship.mShips)
			for(int i = 0; i<go.mXY.size(); i++)
				if(mPGrid[go.mXY.get(i).x][go.mXY.get(i).y][0] == 0)
					mPGrid[go.mXY.get(i).x][go.mXY.get(i).y][0] = getShipType(go.mXY.size());
				else
					return false;
		return true;
	}

	private static int getShipType(int size) {
		return ((int)size%2)==0 ? 2 : 1;
	}

	@Override
	public void exitState() {
		
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		if(mSTATE == STATE.CURRENT)
			g.drawString("Your Turn", 400, 400);
		else //if(mSTATE == STATE.OTHER)
			g.drawString("Opponents Turn", 400, 400);
	}


	@Override
	public void processEvents()
	{
		for(int i=0; i < mEventMgr.size(); i++)
		{
			if(mEventMgr.get(i).mEvent.equals("setGrid"))
			{
				setGrid();
				cClient.sendMessage(convertArray());
				if(mSTATE==STATE.WAIT)
					mEventMgr.add(new Event("setField", "TargetArrows"));
				mEventMgr.consume(i);
			}
			else if(mEventMgr.get(i).mEvent.equals("grid"))
			{
				//Draw targeting lines
				mSTATE = STATE.CURRENT;
				if(GameState.getMSTATE() == GameState.STATE.hMULTI)
					mEventMgr.add(new Event("setField", "TargetArrows"));
				mOGrid = (int[][][])mEventMgr.get(i).mParam;
				mEventMgr.consume(i);
			}
			else if(mEventMgr.get(i).mEvent.equals("clientMessage"))
			{
				if(mEventMgr.get(i).mParam.equals("turn"))
				{
					mSTATE = STATE.CURRENT;
					mEventMgr.add(new Event("setField", "TargetArrows"));
					mEventMgr.consume(i);
				}
			}
			else if(mEventMgr.get(i).mEvent.startsWith("mouse"))
			{
				//MouseEvent me = (MouseEvent) mEventMgr.get(i).mParam;
				if(mEventMgr.get(i).mEvent.equals("mousePressed"))
				{
					//temp point
					Point p = Playfield.getgridPoint();
					if(p.x >=0 && p.y >= 0 && mSTATE == STATE.CURRENT)
					{
						mSTATE = STATE.OTHER;
						mEventMgr.add(new Event("addBomb", mPGrid));
						//mEventMgr.add(new Event("setField", "Normal"));
						cClient.sendMessage("turn");
						if(mOGrid[p.x][p.y][0] == 0)
							mPGrid[p.x][p.y][1] = 1;
						else if(mOGrid[p.x][p.y][0] > 0)
							mPGrid[p.x][p.y][1] = 2;
					}
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clientMsg(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void connected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(Event e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shipsRecieve(String str) {
		if(str.startsWith("grid"))
			mSTATE = STATE.RECEIVED;
		System.out.println("message recieved "+str);
	}
}
