/**
 * 
 */
package battleship.states;

import java.awt.Graphics;
import java.awt.Point;

import battleship.Event;
import battleship.EventManager;
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
	public UserState(EventManager mEventMgr)
	{
		mName = "UserState";
		this.mEventMgr = mEventMgr;
	}

	@Override
	public void enterState()
	{
		mEventMgr.add(new Event("setField", "TargetArrows"));//Draw targeting lines
		mEventMgr.add(new Event("visibility", false, "StartGame"));//set button "StartGame" to invisible
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
		for(int i=0; i < mEventMgr.size(); i++)
		{
			if(mEventMgr.get(i).mEvent.startsWith("mouse"))
			{
				//MouseEvent me = (MouseEvent) mEventMgr.get(i).mParam;
				if(mEventMgr.get(i).mEvent.equals("mousePressed"))
				{
					mEventMgr.add(new Event("addBomb", mGrid));
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
