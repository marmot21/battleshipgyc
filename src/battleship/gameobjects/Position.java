package battleship.gameobjects;

import battleship.gameobjects.Battleship.SHIPS;

public class Position {

	public int x;
	public int y;
	public SHIPS STATE;
	
	public Position(int x, int y, SHIPS STATE) {
		this.x = x;
		this.y = y;
		this.STATE = STATE;
	}
	
	public Position() {

	}

	public void set(int x, int y, SHIPS STATE) {
		this.x = x;
		this.y = y;
		this.STATE = STATE;
	}
}
