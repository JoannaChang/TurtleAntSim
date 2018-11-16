package ants;

/**
 * Stores the data for nest occupation
 * 
 * @author Joanna
 *
 */
public class NestOccupation {

	// number of ants in the nest
	private int numAnts;

	// name of the nest
	private String nestName;

	// number of simulations that occurred
	private int numSimulation;

	private int pherFactor, pher;

	public NestOccupation(String nestName, int pher, int numAnts, int numSimulation, int pherFactor) {
		this.numAnts = numAnts;
		this.pher = pher;
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

	public int getPher() {
		return pher;
	}
}
