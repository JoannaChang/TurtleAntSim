package ants;

/**
 * This is a bridge. It exists as an array of empty cells of a specified length,
 * flanked on both sides by wall cells. Its two ends are bridge cells located
 * within the arena. Ants move on bridges in the same way that they move in the
 * arena.
 * 
 * @author Joanna Chang
 */

public class Bridge {

	// array of cells that make up the bridge
	private Cell[][] theBridge;

	// two ends of the cell that exist within the arena
	private Cell end1, end2;

	// length of bridge
	private int length;

	/**
	 * Constructor for a bridge
	 */
	public Bridge(int length, Cell end1, Cell end2, int pherStrength, int maxPher, double decayRate) {

		this.end1 = end1;
		this.end2 = end2;

		// length of the bridge
		this.length = length;

		// build the bridge with the two end cells and cells of number "length" in the
		// middle
		theBridge = new Cell[3][length + 2];
		theBridge[1][0] = end1;
		theBridge[1][length + 1] = end2;
		for (int i = 0; i < length + 2; i++) {
			theBridge[0][i] = new WallCell(0, 0, i);
			theBridge[2][i] = new WallCell(0, 2, i);
			if (i > 0 && i < length + 1) {
				theBridge[1][i] = new BridgeCell(1, i, pherStrength, maxPher, decayRate);
			}
		}
	}

	/**
	 * @return if the bridge can be crossed from entryCell
	 */
	public boolean crossable(Cell entryCell) {
		return entryCell == end1 || entryCell == end2;
	}

	/**
	 * @return the first cell the ant goes on, according to the cell it enters from
	 */
	public Cell firstCell(Cell entryCell) {
		if (entryCell == end1) {
			return theBridge[1][1];
		} else {
			return theBridge[1][length];
		}
	}

	/**
	 * @return if the current cell is on the edge of the bridge
	 */
	public boolean onEdges(Cell currCell) {
		return currCell == theBridge[1][1] || currCell == theBridge[1][length];
	}

	/**
	 * @return array representing the bridge
	 */
	public Cell[][] getBridgeWorld() {
		return theBridge;
	}

	/**
	 * Decrease the amount of pheromone in all the cells on the bridge
	 */
	synchronized public void pherDecay() {
		for (int i = 1; i < length + 1; i++) {
			theBridge[1][i].pherDecay();
		}
	}

	/**
	 * Reset the cells of the bridge
	 */
	synchronized public void reset() {

		for (int i = 0; i < length + 2; i++) {
			theBridge[1][i].reset();
		}
	}

}
