/**
 * 
 */
package battleship.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.StringTokenizer;

import battleship.Event;
import battleship.Events;
import battleship.Input;
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
	private static int[][][] m_PGrid = new int[10][10][2];
	private static int[][][] m_OGrid = new int[10][10][2];
	
	/**
	 * The number of ships which have been hit
	 */
	private static int m_ShipCount;
	/**
	 * The number of ship hits required to win the game
	 */
	private int m_Ships_in_Game = 6;
	
	/**
	 * Wait: waiting to receive the ship positions from the other user. 
	 * Received: got the info from the other player. 
	 * Current: the current user's turn. 
	 * Other: the other player's turn. 
	 * End: the other player has won the game :(
	 */
	private enum STATE {
		WAIT, RECEIVED, CURRENT, OTHER, END
	} 
	
	private STATE mSTATE = STATE.WAIT;
	
	/**
	 * Default Constructor
	 */
	public Multiplayer()
	{
		mName = "MultiPlayer";
	}

	@Override
	public void enterState()
	{
		Events.get().add(new Event("visibility", false, "StartGame"));//set button "StartGame" to invisible
		cClient.getClient().addClientListener(this);//Receive events from the client
		Events.get().add(new Event("setGrid"));
	}

	/**
	 * Converts the grid into a string
	 * @return the string of the array
	 */
	private String convertArray(int [][][] grid) {
		StringBuffer buf = new StringBuffer();
		buf.append("grid");
		for(int iX = 0; iX<10; iX++){
			for(int iY = 0; iY<10; iY++){
				if(grid[iX][iY][0]!=0)
				{
					buf.append(iX);
					buf.append(iY);
					buf.append(0);
					buf.append(grid[iX][iY][0]+"&");
				}
				if(grid[iX][iY][1]!=0)
				{
					buf.append(iX);
					buf.append(iY);
					buf.append(1);
					buf.append(grid[iX][iY][1]+"&");
				}
			}
		}
		if (buf.length() >0 ) buf.setLength(buf.length()-1);
		return buf.toString();
	}
	/**
	 * Convert a string back into an array
	 * @param str The string to convert
	 * @return The grid with the converted string
	 */
	static int[][][] convertString(String str)
	{
		StringTokenizer st = new StringTokenizer(str,"&");
		String tmp;
		int[][][] grid = new int[10][10][2];
		int iTemp = st.countTokens();
		for(int i = 0; i<iTemp; i++)
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
		m_PGrid = new int[10][10][2];
		for(Battleship go : Battleship.mShips)
			for(int i = 0; i<go.mXY.size(); i++)
				if(m_PGrid[go.mXY.get(i).x][go.mXY.get(i).y][0] == 0)
					m_PGrid[go.mXY.get(i).x][go.mXY.get(i).y][0] = getShipType(go.mXY.size());
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
		g.setFont(new Font("Dialog", Font.BOLD, 14));
		if(m_ShipCount<m_Ships_in_Game)
		{
			if(mSTATE == STATE.CURRENT)
				g.drawString("Your Turn", 450, 400);
			else if(mSTATE == STATE.OTHER)
				g.drawString("Opponents Turn", 450, 400);
			else if (mSTATE == STATE.END)
				g.drawString("You lost", 450, 400);
			else 
				g.drawString("Waiting for other player", 450, 400);
		}
		else
			g.drawString("You have won", 450, 400);
	}


	@Override
	public void processEvents()
	{
		for(int i=0; i < Events.get().size(); i++)
		{
			if(Events.get().get(i).m_Event.equals("GameEnded"))
			{
				mSTATE = STATE.END;
				Events.get().remove(i);
			}
			else if(Events.get().get(i).m_Event.equals("setGrid"))
			{
				setGrid();
				cClient.getClient().sendGrid(convertArray(m_PGrid));
				if(mSTATE==STATE.WAIT)
					Events.get().add(new Event("setField", "TargetArrows"));
				Events.get().remove(i);
			}
			else if(Events.get().get(i).m_Event.equals("grid"))
			{//Received a grid from the other player
				//Draw targeting lines
				mSTATE = STATE.RECEIVED;
				if(GameState.getMSTATE() == GameState.STATE.hMULTI ||
						GameState.getMSTATE() ==  GameState.STATE.jMULTI)
					Events.get().add(new Event("setField", "TargetArrows"));
				m_OGrid = (int[][][])Events.get().get(i).m_Param;
				Events.get().remove(i);
			}
			//update the grid to show if ships have been hit
			else if(Events.get().get(i).m_Event.equals("updategrid"))
			{
				m_OGrid = convertString((String)Events.get().get(i).m_Param);
				Events.get().add(new Event("updatefield", m_OGrid));//draw the new bomb drops to the status grid
				Events.get().remove(i);
			}
			
			else if(Events.get().get(i).m_Event.equals("clientMessage"))
			{
				if(Events.get().get(i).m_Param.equals("turn"))
				{
					mSTATE = STATE.CURRENT;
					Events.get().add(new Event("setField", "TargetArrows"));
				}
				else if (Events.get().get(i).m_Param.equals("gameStarted") && mSTATE != STATE.CURRENT)
				{
					cClient.getClient().sendMessage("turn");
				}
				Events.get().add(new Event("repaint"));
				Events.get().remove(i);
			}
			
		}
		//mouse events
		if(Input.get().mouseIsPressed(MouseEvent.BUTTON1))
		{
			//point used in this function only
			Point p = Playfield.getgridPoint();
			//if a grid position is pressed - add a bomb
			if(p.x >=0 && p.y >= 0 && mSTATE == STATE.CURRENT)
			{
				mSTATE = STATE.OTHER;
				Events.get().add(new Event("addBomb", m_PGrid));
				//Events.get().add(new Event("setField", "Normal"));
				//Set the other players turn
				if(m_PGrid[p.x][p.y][1] == 0)
					cClient.getClient().sendMessage("turn");
				//if there are no ships in that position
				if(m_OGrid[p.x][p.y][0] == 0)
					m_PGrid[p.x][p.y][1] = 1;
				//if there are ships in given position
				else if(m_OGrid[p.x][p.y][0] > 0)
				{
					m_PGrid[p.x][p.y][1] = 2;
					m_ShipCount++;
				}
				if(m_ShipCount>=m_Ships_in_Game)
					cClient.getClient().sendMessage("GameEnded");
				cClient.getClient().sendGrid("update"+convertArray(m_PGrid));
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
		//System.out.println("message recieved "+e.m_Event+e.m_Param);
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
		else if(str.startsWith("updategrid"))
			Events.get().add(new Event("updategrid", str.substring(10)));
		System.out.println("message recieved "+str);
	}
}
