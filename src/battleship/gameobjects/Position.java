package battleship.gameobjects;

import battleship.gameobjects.Battleship.SHIPS;

/**
 * A wrapper for storing the previous position of ships
 * @author Obi
 *
 */
public class Position {

	public int mX;
	public int mY;
	public SHIPS mSTATE;
	
	/**
	 * Constructor
	 * Positions are measured from top left of ship.
	 * @param x The current x position of ship
	 * @param y The current y position of ship
	 * @param STATE The current state of the ship
	 */
	public Position(int x, int y, SHIPS STATE) {
		this.mX = x;
		this.mY = y;
		this.mSTATE = STATE;
	}
	
	/**
	 * Default constructor
	 * Used when setting the position of ships later
	 */
	public Position() {

	}

	/**
	 * A setter to change the value of class variables
	 * @param x The current x position of ship
	 * @param y The current y position of ship
	 * @param STATE The current state of the ship
	 */
	public void set(int x, int y, SHIPS STATE) {
		this.mX = x;
		this.mY = y;
		this.mSTATE = STATE;
	}
}
