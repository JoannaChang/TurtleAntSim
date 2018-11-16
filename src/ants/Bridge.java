package ants;

import java.util.List;

public class Bridge {

	private Cell[][] theBridge;

	private Cell end1, end2;

	private int length;

	/**
	 * Constructor for a bridge
	 * 
	 * @param row
	 * @param col
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

	// public boolean isFirstEnd(Cell entryCell) {
	// return entryCell == end1;
	// }

	// if the bridge can be crossed from entryCell
	public boolean crossable(Cell entryCell) {
		return entryCell == end1 || entryCell == end2;
	}

	public Cell firstCell(Cell entryCell) {
		if (entryCell == end1) {
			return theBridge[1][1];
		} else {
			return theBridge[1][length];
		}
	}

	public boolean onEdges(Cell currCell) {
		return currCell == theBridge[1][1] || currCell == theBridge[1][length];
	}

	public Cell[][] getBridgeWorld() {
		return theBridge;
	}

	synchronized public void pherDecay() {
		for (int i = 1; i < length + 1; i++) {
			theBridge[1][i].pherDecay();
		}
	}

	/**
	 * Reset the cell
	 */
	synchronized public void reset() {

		for (int i = 0; i < length + 2; i++) {
			theBridge[1][i].reset();
		}
	}

}
