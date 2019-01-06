package ants;

/**
 * This is an empty cell. An ant can move onto an empty cell and deposit
 * pheromone into it. The cell can also have access to a bridge.
 * 
 * @author Joanna Chang
 *
 */
public class EmptyCell implements Cell {

	// row, column, and layer the cell is in
	private int row, col, layer;

	// type of cell
	private int type = Cell.EMPTY;

	// keep track of pheromone and ants in nest
	private int pheromone = 0;
	private int numAnts = 0;
	// private int newPher = 0; //uncomment to update pheromone after all the ants
	// have moved in a time step

	// if the cell has been visited by an ant
	private boolean visited = false;

	// how much pheromone each ant adds to cell with visit
	private int pherStrength;

	// the maximum effect pheromones can have on ant movement
	private int maxPher;

	// rate of pheromone decay
	private double decayRate;

	// bridge the cell has access to
	private Bridge bridge = null;

	/**
	 * Constructor for a empty cell
	 */
	public EmptyCell(int layer, int row, int col, int pherStrength, int maxPher, double decayRate) {
		this.pherStrength = pherStrength;
		this.maxPher = maxPher;
		this.row = row;
		this.col = col;
		this.layer = layer;
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
		return layer;
	}

	synchronized public int getPheromone() {
		return pheromone;
	}

	synchronized public int getAnts() {
		return numAnts;
	}

	/**
	 * Update pheromone and note when an ant visits the cell
	 */
	synchronized public void visit(int antID) {
		pheromone = Math.min(pheromone + pherStrength, maxPher);
		// newPher = Math.min(newPher + pherStrength, maxPher);
		numAnts++;
		visited = true;
	}

	/**
	 * Note when an ant leaves a cell
	 */
	synchronized public void leave(int antID) {
		numAnts--;
	}

	/**
	 * N/A for Empty Cell
	 */
	public void addPher() {
		// do nothing
	}

	/**
	 * Decrease the amount of pheromone in cell
	 */
	synchronized public void pherDecay() {
		// pheromone = newPher;
		pheromone = Math.max((int) (pheromone - pheromone * decayRate), 0);
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
	 * Add bridge that the cell can access
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
