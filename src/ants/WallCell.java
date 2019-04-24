package ants;

/**
 * Class representing a cell that's a wall. Ants cannot go on it.
 * 
 * @author Joanna Chang
 */

public class WallCell implements Cell {

	// row, column, and layer the cell is in
	private int row, col, layer;

	// type of cell
	private int type = Cell.WALL;

	/**
	 * Constructor for a wall cell
	 */
	public WallCell(int layer, int row, int col) {
		this.row = row;
		this.col = col;
		this.layer = layer;
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

	public int getLayer() {
		return layer;
	}

	//methods below: N/A for Wall Cell
	
	public void addPher() {
		// do nothing
	}

	@Override
	public void visit(int nothing) {
		// do nothing
	}

	@Override
	public void leave(int nothing) {
		// do nothing
	}

	@Override
	public void pherDecay() {
		// do nothing
	}

	@Override
	public double getPheromone() { //was int
		// do nothing
		return 0;
	}

	@Override
	public int getAnts() {
		// do nothing
		return 0;
	}

	@Override
	public boolean visited() {
		// do nothing
		return false;
	}

	public void reset() {
		// do nothing
	}

	public void addBridge(Bridge bridge) {
		// do nothing
	}

	public boolean hasBridge() {
		return false;
	}

	public Bridge getBridge() {
		return null;
	}

	public String toString() {
		return "Cell: " + row + " " + col;
	}

}
