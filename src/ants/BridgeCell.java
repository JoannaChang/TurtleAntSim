package ants;

/**
 * This is an bridge accessor cell. An ant can move onto the cell, deposit
 * pheromone, and go onto a bridge from the cell.
 * 
 * USED IN LATEST/FINAL VERSION (ignore comment from previous commit)
 * 
 * @author Joanna Chang
 */

public class BridgeCell implements Cell {

	// row and column the cell is in
	private int row, col;

	// type of cell
	private int type = Cell.BRIDGE;

	// keep track of pheromone and ants in nest
	private double pheromone = 0; // was int
	private int numAnts = 0;

	// private int newPher = 0; //uncomment to only update pher after all ants have
	// moved once

	// if the cell has been visited by an ant
	private boolean visited = false;

	// how much pheromone each ant adds to cell with visit
	private int pherStrength;

	// the maximum effect pheromones can have on ant movement
	private int maxPher;

	// rate of pheromone decay
	private double decayRate;

	// keep bridge the cell has access to
	private Bridge bridge = null;

	/**
	 * Constructor for a nest cell
	 */
	public BridgeCell(int row, int col, int pherStrength, int maxPher, double decayRate) {
		this.pherStrength = pherStrength;
		this.maxPher = maxPher;
		this.row = row;
		this.col = col;
		this.decayRate = decayRate;
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
		return 0; // do nothing
	}

	synchronized public double getPheromone() {
		return pheromone;
	}

	synchronized public int getAnts() {
		return numAnts;
	}

	/**
	 * Update pheromone when ant visits a cell
	 */
	synchronized public void visit(int antID) {
		pheromone = Math.min(pheromone + pherStrength, maxPher);
		// newPher = Math.min(newPher + pherStrength, maxPher);
		numAnts++;
		visited = true;

	}

	/**
	 * Note when ant leaves a cell
	 */
	synchronized public void leave(int antID) {
		numAnts--;
	}

	public void addPher() {
		// do nothing
	}

	/**
	 * Decrease pheromone in cells
	 */
	synchronized public void pherDecay() {
		// pheromone = newPher;
		// pheromone = Math.max((int) (pheromone - pheromone * decayRate), 0);

		pheromone = Math.max((pheromone - pheromone * decayRate), 0); // added this
		if (pheromone <= pherStrength) {
			visited = false;
		}
		// newPher = pheromone;
	}

	/**
	 * Determine if the cell has been visited.
	 */
	synchronized public boolean visited() {
		return visited;
	}

	/**
	 * Reset the cell
	 */
	synchronized public void reset() {
		visited = false;
		numAnts = 0;
		pheromone = 0;
		// newPher = 0;
	}

	public String toString() {
		return "Cell: " + row + " " + col;
	}

	/**
	 * Add bridge that the bridge cell can access
	 */
	public void addBridge(Bridge bridge) {
		this.bridge = bridge;
	}

	/**
	 * @return if the cell has access to a bridge
	 */
	public boolean hasBridge() {
		return bridge != null;
	}

	/**
	 * @return the bridge the cell has access to
	 */
	public Bridge getBridge() {
		return bridge;
	}
}
