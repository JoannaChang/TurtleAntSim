package ants;

public class BridgeCell implements Cell {

	// row and column the cell is in
	private int row, col;

	// type of cell
	private int type = Cell.BRIDGE;

	// keep track of pheromone and ants in nest
	private int pheromone = 0;
	private int numAnts = 0;

	private int newPher = 0;
	

	// if the cell has been visited by an ant
	private boolean visited = false;

	// how much pheromone each ant adds to cell with visit
	private int pherStrength;

	// the maximum effect pheromones can have on ant movement
	private int maxPher;

	private Bridge bridge = null;
	
	private double decayRate;

	/**
	 * Constructor for a nest cell
	 * 
	 * @param row
	 * @param col
	 */
	public BridgeCell(int row, int col, int pherStrength, int maxPher, double decayRate) {
		this.pherStrength = pherStrength;
		this.maxPher = maxPher;
		this.row = row;
		this.col = col;
		this.decayRate=decayRate;
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
		// do nothing
		return 0;
	}

	synchronized public void visit(int antID) {
//		pheromone = Math.min(pheromone + pherStrength, maxPher);

		newPher = Math.min(newPher + pherStrength, maxPher);
		numAnts++;
		visited = true;

	}

	synchronized public void leave(int antID) {
		numAnts--;
	}

	public void addPher() {
		// do nothing
	}

	synchronized public void pherDecay() {
		pheromone = newPher;
		pheromone = Math.max((int) (pheromone - pheromone*decayRate), 0); // was divided by 2
		if (pheromone <= pherStrength) {
			visited = false;
		}
		newPher = pheromone;
	}

	synchronized public void updatePher() {
//		pheromone = newPher;
	}

	synchronized public int getPheromone() {
		return pheromone;
	}

	synchronized public int getAnts() {
		return numAnts;
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
	}

	public String toString() {
		return "Cell: " + row + " " + col;
	}

	// public Bridge getBridge(Cell cell) {
	// return null;
	// }

	public void addBridge(Bridge bridge) {
		this.bridge = bridge;
	}

	public boolean hasBridge() {
		return bridge != null;
	}

	public Bridge getBridge() {
		return bridge;
	}
}
