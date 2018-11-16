package ants;

public class EmptyCell implements Cell {

	// row, column, and layer the cell is in
	private int row, col, layer;

	// type of cell
	private int type = Cell.EMPTY;

	// keep track of pheromone and ants in nest
	private int pheromone = 0;
	private int newPher = 0;
	private int numAnts = 0;

	// if the cell has been visited by an ant
	private boolean visited = false;

	// how much pheromone each ant adds to cell with visit
	private int pherStrength;

	// the maximum effect pheromones can have on ant movement
	private int maxPher;

	private Bridge bridge = null;

	private boolean hasBridge = false;
	
	private double decayRate;

	/**
	 * Constructor for a nest cell
	 * 
	 * @param row
	 * @param col
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

	synchronized public void visit(int antID) {
		 pheromone = Math.min(pheromone + pherStrength, maxPher);

//		newPher = Math.min(newPher + pherStrength, maxPher);
		numAnts++;
		visited = true;
	}

	synchronized public void leave(int antID) {
		numAnts--;
	}

	public void addPher() {
		// do nothing
	}

	synchronized public void updatePher() {
//		System.out.println(newPher);
//		pheromone = newPher;
	}

	synchronized public void pherDecay() {
//		System.out.println("before " + pheromone);
//		pheromone = newPher;
//		System.out.println("before" + pheromone);
//		System.out.println("before" + pheromone*decayRate);

		pheromone = Math.max((int) (pheromone - pheromone*decayRate), 0); // was divided by 2
		if (pheromone <= pherStrength) {
			visited = false;
		}
//		System.out.println("after" + pheromone);

//		newPher = pheromone;
	}

	synchronized public int getPheromone() {
//		System.out.println(pheromone);
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

	public void addBridge(Bridge bridge) {
		this.bridge = bridge;
		hasBridge = true;
	}

	public boolean hasBridge() {
		return hasBridge;
	}

	public Bridge getBridge() {
		return bridge;
	}

}
