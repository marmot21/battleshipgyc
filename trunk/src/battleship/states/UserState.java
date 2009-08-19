/**
 * 
 */
package battleship.states;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import battleship.Event;
import battleship.EventManager;
import battleship.gameobjects.Battleship;
import battleship.gameobjects.GameObject;

/**
 * Handles the user dropping bombs etc
 * @author OBi
 *
 */
public class UserState extends State {

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
				MouseEvent me = (MouseEvent) mEventMgr.get(i).mParam;
				if(mEventMgr.get(i).mEvent.equals("mousePressed")
						&& Battleship.mStatusScreen.contains(me.getPoint()))
				{
					//Playfield.getgridPoint()
				}
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
