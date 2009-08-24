package battleship.states;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import battleship.gameobjects.GameObject;

/**
 * The state on which all the others are based
 * @author Amec
 */

public abstract class State
{
	public String mName = ""; //Name of the object
	protected List<GameObject> mObj = null;// = new ArrayList<GameObject>(); //All the objects which the state controls
	
	public State() {
		mObj = new ArrayList<GameObject>(); //All the objects which the state controls
	}
	
	/**
	 * The method called when entering a state
	 */
	public abstract void enterState();
	
	/**
	 * The method called when exiting a state
	 */
	public abstract void exitState();
	
	/**
	 * Makes the state execute its code
	 */
	public abstract void run();
	
	/**
	 * Draws stuff to the double buffer
	 * @param g The double buffer
	 */
	public abstract void paint(Graphics g);
	
	public abstract void processEvents();
}