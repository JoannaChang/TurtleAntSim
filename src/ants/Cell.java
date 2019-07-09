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
<<<<<<< HEAD
	public int getPheromone();
=======
	public double getPheromone(); // was int
>>>>>>> d813a055bec855a0f3c04b6473c02220e9e5a0b4
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
