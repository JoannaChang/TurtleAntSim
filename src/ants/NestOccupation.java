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
<<<<<<< HEAD
	private int pherFactor, pher;
=======
	private int pherFactor;
	private double pher; //was int
>>>>>>> d813a055bec855a0f3c04b6473c02220e9e5a0b4

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
