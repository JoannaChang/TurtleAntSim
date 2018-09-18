package ants;

public class NestCell implements Cell {

	// row and column the cell is in
	private int row, col;

	// type of cell
	private int type = Cell.NEST;
	
	// keep track of pheromone and ants in nest
	private int pheromone = 0;
	private int numAnts = 0;
	
	private String name;
	
	private int pherStrength;
	
	/**
	 * Constructor for a wall cell
	 * 
	 * @param row
	 * @param col
	 */
	public NestCell(int row, int col, String name) {
		this.row = row;
		this.col = col;
		this.name = name;
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
	
	public void visit() {
//		pheromone = Math.min(pheromone + pherStrength, maxPher);

	}

}
