package ants;

import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

public class NestCell implements Cell {

	// row, column, and layer the cell is in
	private int row, col, layer;

	// type of cell
	private int type = Cell.NEST;

	// name of the nest
	private String name;

	// keep track of pheromone and ants in nest
	private static final int BASEPHER = 8;
	private int pheromone = 8;
	private int newPher = 0;

	private int numAnts = 0;


	// if the cell has been visited by an ant
	private boolean visited = false;

	// how much pheromone each ant adds to cell with visit
	private int pherStrength;

	// the maximum effect pheromones can have on ant movement
	private int maxPher;

//	// amount of pheromone that cuts chance of leaving a nest in half
//	private int pherFactor;
//
//	// chance that ant will leave a nest without pheromones
//	private static final int CHANCE_LEAVE = 500; // should be 1/2
//
//	private Random exitGen = new Random();

	private List<Activity> activity;

	private Arena arena;
	
	private double decayRate = 0.05;

	/**
	 * Constructor for a nest cell
	 * 
	 * @param row
	 * @param col
	 */
	public NestCell(int layer, int row, int col, int pherStrength, int maxPher, String name, List<Activity> activity,
			Arena arena, double decayRate) {
		this.pherStrength = pherStrength;
		this.maxPher = maxPher;
		this.row = row;
		this.col = col;
		this.name = name;
		this.activity = activity;
		this.arena = arena;
		this.layer = layer;
//		this.decayRate = decayRate;
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
		// System.out.println("visit " + numAnts);

		visited = true;
		activity.add(new Activity(arena.getNumSimulations(), arena.getNumSteps(), layer, row, col, -1, -1, -1, antID,
				"NestEntered", name));
	}

	// TODO: see if this should be synchronized
	synchronized public void leave(int antID) {
		numAnts--;
		// System.out.println("leave " + numAnts);

		activity.add(new Activity(arena.getNumSimulations(), arena.getNumSteps(), layer, row, col, -1, -1, -1, antID,
				"NestExited", name));
	}

	synchronized public void pherDecay() {
//		pheromone = newPher;
		pheromone = Math.max((int) (pheromone - pheromone*decayRate), BASEPHER); // was divided by 2
		if (pheromone <= BASEPHER) {
			visited = false;
		}
//		newPher = pheromone;
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

	synchronized public void addPher() {
		pheromone = Math.min(pheromone + pherStrength, maxPher);

//		newPher = Math.min(newPher + pherStrength, maxPher);
		visited = true;
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
		pheromone = BASEPHER;
		newPher = BASEPHER;
	}

	public void addBridge(Bridge bridge) {
		// do nothing
	}

	public boolean hasBridge() {
		return false;
	}

	public Bridge getBridge() {
		return null;
	}

	public String toString() {
		return "Cell: " + row + " " + col;
	}

}
