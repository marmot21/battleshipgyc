/**
 * 
 */
package battleship.states;

import java.awt.Graphics;
import java.awt.Point;

import battleship.Event;
import battleship.Events;
import battleship.gameobjects.Playfield;

/**
 * Handles the user dropping bombs etc
 * @author OBi
 *
 */
public class UserState extends State
{
	/**
	 * Grid used to store ship positions and if they have been bombed etc.
	 * X and Y: the grid. 0-no ship, 1-battleship, 2-small ship
	 * Z: The status of that ship. 0-normal, 1-bombed, 2-ship hit
	 */
	private int[][][] mGrid = new int[10][10][2];
	
	/**
	 * Default Constructor
	 */
	public UserState()
	{
		mName = "UserState";
	}

	@Override
	public void enterState()
	{
		Events.get().add(new Event("setField", "TargetArrows"));//Draw targeting lines
		Events.get().add(new Event("visibility", false, "StartGame"));//set button "StartGame" to invisible
	}

	@Override
	public void exitState() {
		
	}

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

	}


	@Override
	public void processEvents()
	{
		for(int i=0; i < Events.get().size(); i++)
		{
			if(Events.get().get(i).m_Event.startsWith("mouse"))
			{
				//MouseEvent me = (MouseEvent) mEventMgr.get(i).mParam;
				if(Events.get().get(i).m_Event.equals("mousePressed"))
				{
					Events.get().add(new Event("addBomb", mGrid));
					//temp point
					Point p = Playfield.getgridPoint();
					if(p.x >=0 && p.y >= 0)
					{
						if(mGrid[p.x][p.y][0] == 0)
							mGrid[p.x][p.y][1] = 1;
						else if(mGrid[p.x][p.y][0] > 0)
							mGrid[p.x][p.y][1] = 2;
					}
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
