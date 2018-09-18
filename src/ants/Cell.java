package ants;

public interface Cell {
	
	public static final int EMPTY = 0, WALL = 1, BRIDGE = 2, NEST = 3;
	
	public int getRow();
	
	public int getCol();

	public int getType();
	

}
