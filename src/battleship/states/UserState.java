/**
 * 
 */
package battleship.states;

import java.awt.Graphics;

import battleship.Event;
import battleship.EventManager;
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
	public UserState() {
		mName = "UserState";
	}

	@Override
	public void enterState() {
		mStateEventMgr.add(new Event("setField", "TargetArrows"));
	}

	@Override
	public void exitState() {
		mStateEventMgr.add(new Event("setField", "Normal"));
	}

	@Override
	public EventManager getEvents() {
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

	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pumpEvents(EventManager em) {
		for(GameObject go : mObj) {
			go.update(); //pass on events to children
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
