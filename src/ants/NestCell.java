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

	// where nest cell exists
	private Arena arena;

	// keep track of pheromone and ants in nest
<<<<<<< HEAD
	private int pheromone = 8;
=======
	private double pheromone = 8; //was double
>>>>>>> d813a055bec855a0f3c04b6473c02220e9e5a0b4
	private int numAnts = 0;
	// private int newPher = 0; //uncomment to update pheromone after all the ants
	// have moved in a time step

	// how much pheromone each ant adds to cell with visit
	private int pherStrength;

	// the maximum effect pheromones can have on ant movement
	private int maxPher;

	// rate of pheromone decay
	private double decayRate;

	// if the cell has been visited by an ant
	private boolean visited = false;

	// list to add nest enters/exits
	private List<Activity> activity;

	// base pheromone represents greater likelihood an ant will explore a nest over
	// an empty space
	private static final int BASEPHER = 8;

	/**
	 * Constructor for a nest cell
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
	
<<<<<<< HEAD
	synchronized public int getPheromone() {
=======
	synchronized public double getPheromone() {
>>>>>>> d813a055bec855a0f3c04b6473c02220e9e5a0b4
		return pheromone;
	}

	synchronized public int getAnts() {
		return numAnts;
	}

	/**
	 * Update pheromone and record activity when ant visits a nest
	 */
	synchronized public void visit(int antID) {
		pheromone = Math.min(pheromone + pherStrength, maxPher); // comment to only update pher after one time step
		// newPher = Math.min(newPher + pherStrength, maxPher); //uncomment to only
		// update pher after one time step
		numAnts++;
		visited = true;
		activity.add(new Activity(arena.getNumSimulations(), arena.getNumSteps(), layer, row, col, -1, -1, -1, antID,
				"NestEntered", name));
	}

	/**
	 * Update number of ants and record activity when ant exits a nest 
	 * 
	 * TODO: see if this should be synchronized
	 */
	synchronized public void leave(int antID) {
		numAnts--;
		activity.add(new Activity(arena.getNumSimulations(), arena.getNumSteps(), layer, row, col, -1, -1, -1, antID,
				"NestExited", name));
	}

	/**
	 * Decrease amount of pheromone in cell
	 */
	synchronized public void pherDecay() {
		// pheromone = newPher;
<<<<<<< HEAD
		pheromone = Math.max((int) (pheromone - pheromone * decayRate), BASEPHER); 
		if (pheromone <= BASEPHER) {
=======
//		pheromone = Math.max((int) (pheromone - pheromone * decayRate), BASEPHER);
		pheromone = Math.max((pheromone - pheromone * decayRate), BASEPHER); // added this
		if (pheromone <= BASEPHER) { 
//		if (pheromone < BASEPHER) {//added 4/17

>>>>>>> d813a055bec855a0f3c04b6473c02220e9e5a0b4
			visited = false;
		}
		// newPher = pheromone;
	}

	/**
	 * Add pheromone (without increasing number of ants)
	 */
	synchronized public void addPher() {
		pheromone = Math.min(pheromone + pherStrength, maxPher); 
		// newPher = Math.min(newPher + pherStrength, maxPher);
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
//		newPher = BASEPHER;
	}

	public String toString() {
		return "Cell: " + row + " " + col;
	}

	// bridge methods: N/A
	public void addBridge(Bridge bridge) {
		// do nothing
	}

	public boolean hasBridge() {
		return false;
	}

	public Bridge getBridge() {
		return null;
	}

}
