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
	public UserState() {
		mName = "UserState";
	}

	@Override
	public void enterState() {
		mStateEventMgr.add(new Event("setField", "TargetArrows"));//Draw targeting lines
		mStateEventMgr.add(new Event("BStartGame", "inVisible"));//set button "StartGame" to invisible
	}

	@Override
	public void exitState() {
		
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
		for(int i=0; i<em.size(); i++) {
			if(em.get(i).mEvent.startsWith("mouse"))
			{
				MouseEvent me = (MouseEvent) em.get(i).mParam;
				if(em.get(i).mEvent.equals("mousePressed")
						&& Battleship.mStatusScreen.contains(me.getPoint()))
				{
					//Playfield.getgridPoint()
				}
			}
		}
		for(GameObject go : mObj) {
			go.update(); //pass on events to children
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

}
