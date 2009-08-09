package battleship.gameobjects;

import java.awt.Point;

import battleship.gameobjects.Battleship.SHIPS;

/**
 * A wrapper for storing the previous position of ships
 * @author Obi
 * @author Amec
 */

public class Position
{
	public Point mPos;
	public SHIPS mSTATE;
	
	/**
	 * Default constructor
	 * Used when setting the position of ships later
	 */
	public Position()
	{

	}
	
	/**
	 * Constructor
	 * Positions are measured from top left of ship.
	 * @param x The current x position of ship
	 * @param y The current y position of ship
	 * @param STATE The current state of the ship
	 */
	public Position(Point pos, SHIPS STATE)
	{
		this.mPos = pos;
		mSTATE = STATE;
	}

	/**
	 * A setter to change the value of class variables
	 * @param x The current x position of ship
	 * @param y The current y position of ship
	 * @param STATE The current state of the ship
	 */
	public void set(Point pos, SHIPS STATE)
	{
		this.mPos = pos;
		mSTATE = STATE;
	}
}
