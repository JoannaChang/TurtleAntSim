package ants;

/**
 * Class representing a cell that's a wall. Ants cannot go on it.
 * @author Joanna
 *
 */

public class WallCell implements Cell{

	// row and column the cell is in
	private int row, col;
	
	// type of cell
	private int type = Cell.WALL;
	
	/**
	 * Constructor for a wall cell
	 * @param row
	 * @param col
	 */
	public WallCell(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public int getType() {
		return type;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

}
