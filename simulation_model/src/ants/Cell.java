package ants;

/**
 * Interface for all cells. Cells can be empty, a wall, a bridge accessor, or a
 * nest.
 * 
 * @author Joanna Chang
 */

public interface Cell {

	public static final int EMPTY = 0, WALL = 1, NEST = 2, BRIDGE = 3;

	public int getRow();
	public int getCol();
	public int getLayer();
	public int getType();
	public double getPheromone(); // was int
	public int getAnts();

	public void visit(int antID);
	public void leave(int antID);

	public void pherDecay();
	public void addPher();

	public boolean visited();
	public void reset();

	// only for bridge cell
	public void addBridge(Bridge bridge);
	public boolean hasBridge();
	public Bridge getBridge();

	public String toString();
}
