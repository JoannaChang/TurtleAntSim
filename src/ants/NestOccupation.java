package ants;

/**
 * Stores the data for nest occupation
 * 
 * @author Joanna Chang
 *
 */
public class NestOccupation {

	// number of ants in the nest
	private int numAnts;

	// name of the nest
	private String nestName;

	// number of simulations that occurred
	private int numSimulation;

	// pheromone factor and amount of pheromone in nest
	private int pherFactor;
	private double pher; //was int

	public NestOccupation(String nestName, double pher, int numAnts, int numSimulation, int pherFactor) {
		this.numAnts = numAnts;
		this.pher = pher; // was int
		this.nestName = nestName;
		this.numSimulation = numSimulation;
		this.pherFactor = pherFactor;
	}

	public int getAnts() {
		return numAnts;
	}

	public int getPherFactor() {
		return pherFactor;
	}

	public String getName() {
		return nestName;
	}

	public int getSimulation() {
		return numSimulation;
	}

	public double getPher() { // was int
		return pher;
	}
}
