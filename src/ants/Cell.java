package ants;

public interface Cell {

	public static final int EMPTY = 0, WALL = 1, BRIDGE = 2, NEST = 3;

	public int getRow();

	public int getCol();

	public int getLayer();

	public int getType();

	public void visit(int antID);

	public void leave(int antID);

	public void pherDecay();

	public void addPher();

	public int getPheromone();

	public int getAnts();

	public boolean visited();

	public void reset();

	public void updatePher();

	// only for bridge cell
	// public Bridge getBridge(Cell cell);
	public void addBridge(Bridge bridge);
	// public boolean crossable(Cell cell);

	public boolean hasBridge();

	public Bridge getBridge();

	public String toString();
}
