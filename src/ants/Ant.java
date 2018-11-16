package ants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

public class Ant {

	// current cell, row, and column of ant
	private int row, col;
	private Cell currCell;

	// ID of ant
	private int id;

	// World containing all of the cells to be displayed
	private Cell[][][] world;

	// Class responsible for writing paint method for repainting the screen
	private Arena arena;

	private List<Activity> activity;

	// pheromone factor
	private int pherFactor;

	// if the ant is on a bridge & the bridge it's on
	private boolean onBridge;
	private Bridge currBridge;
	private Cell[][] bridgeWorld;
	private int currLayer;

	private Cell entryCell;

	// random generator to determine ant's movement
	private Random motionGen = new Random();

	// chances of exploration and turning

	// higher chance of exploration got more ants in nests
	private static final double CHANCE_EXPLORE = 0.5;
	// private static final double CHANCE_TURN = 0.02;

	// higher chance of turning got more ants in nests
	private static final double CHANCE_TURN = 0.2; //0.5

	// degree that an ant can turn or see
	private static final int TURNING_RANGE = 180;//was 270
	private static final int VISION_RANGE = 180;

	private static final int CHANCE_MOVE = 12;//
	private static final int CHANCE_EXIT = 500;

	// private static final int PHER_NOISE = 8;

	// direction that the ant is facing
	private Direction direction;

	/**
	 * Constructor for an ant
	 */
	public Ant(int antID, int row, int col, Cell[][][] world, Arena arena, int pherFactor, Direction direction,
			List<Activity> activity) {
		this.id = antID;
		this.world = world;
		this.row = row + motionGen.nextInt(10) - 5;
		this.col = col + motionGen.nextInt(10) - 5;
		this.arena = arena;
		this.pherFactor = pherFactor;
		this.direction = direction;
		this.activity = activity;

		currCell = world[0][row][col];
		currLayer = 0;
	}

	/**
	 * Move the ant
	 */
	synchronized public void move() {

		int chanceOut = 1000;

		if (currCell.getType() == Cell.NEST) {
			// determine the chance that the ant will come out of a nest
			chanceOut = motionGen.nextInt(1001);
		}

		// have a chance of turning
		if (Math.random() < CHANCE_TURN) {
			direction.turn(TURNING_RANGE);
		}

		// TODO: make probability of leaving lower

		// move if the ant is not in a nest or has the chance to come out of a nest

		// if (currCell.getType() == Cell.NEST && (chanceOut < 1 / (Math.pow(2, (double)
		// currCell.getPheromone() / pherFactor)))){
		// System.out.println(1);
		// }

		// double ok
		if (currCell.getType() != Cell.NEST
				|| chanceOut < CHANCE_EXIT / (Math.pow(2, (double) currCell.getPheromone() / pherFactor))) {
			makeNextMove();
		}

		// add pheromone if ant stays in nest
		else if (currCell.getType() == Cell.NEST) {
			currCell.addPher();
		}
		
//		if (currCell.getType() == Cell.NEST) {
//			System.out.println(arena.getNumSteps() + " "+ id + " " + currCell + " "+ currCell.getPheromone());
//		}
	}

	synchronized private void makeNextMove() {
		Cell nextCell = chooseWeightedMove(); // changed based on ranked/weighted moves

		// get off the bridge if you're on the ends
		if (onBridge && currBridge.crossable(nextCell)) {
			if (nextCell != entryCell) {
				activity.add(new Activity(arena.getNumSimulations(), arena.getNumSteps(), entryCell.getLayer(),
						entryCell.getRow(), entryCell.getCol(), nextCell.getLayer(), nextCell.getRow(),
						nextCell.getCol(), id, "BridgeCrossed", null));
			}
			currLayer = nextCell.getLayer();
			onBridge = false;
		}

		// get on the bridge if you're not on already
		else if (nextCell.getType() == Cell.BRIDGE && !onBridge) {
			currBridge = currCell.getBridge();
			bridgeWorld = currBridge.getBridgeWorld();

			entryCell = currCell;
			onBridge = true;
		}

		// make the move
		if (currCell != nextCell) {
			currCell.leave(id);
			currCell = nextCell;
			row = currCell.getRow();
			col = currCell.getCol();

//			if (arena.getNumSteps() > 100) {
				currCell.visit(id);
//			}
		}
	}

	/**
	 * Make the next move based on ranked cells
	 */
	synchronized private Cell chooseRankedMove() {

		ArrayList<Cell> orderedCells = findNeighbors(currCell);
		Cell nextCell = currCell;

		// move on to the next ranked cell with probability CHANCE_EXPLORE
		for (int i = orderedCells.size() - 1; i >= 0; i--) {
			nextCell = orderedCells.get(i);
			if (Math.random() > CHANCE_EXPLORE) {
				break;
			}
		}
		return nextCell;
	}

	/**
	 * Find the neighboring cells that the ant can move into based on its current
	 * direction. Rank the cells based on the amount of pheromone they have
	 * 
	 * @param cell
	 * @return
	 */
	synchronized private ArrayList<Cell> findNeighbors(Cell cell) {

		Map<Cell, Integer> cellPher = new LinkedHashMap<Cell, Integer>();

		// possible steps based on the direction
		int[] rowSteps = direction.getRowSteps(VISION_RANGE);
		int[] colSteps = direction.getColSteps(VISION_RANGE);

		// add the bridge as a potential step
		if (currCell.hasBridge()) {
			Cell firstCell = currCell.getBridge().firstCell(currCell);
			cellPher.put(firstCell, firstCell.getPheromone()); // + motionGen.nextInt(PHER_NOISE));
		}

		// boolean nestNeighbor = false;
		// int nest = -1;
		for (int i = 0; i < rowSteps.length; i++) {
			int r = row + rowSteps[i];
			int c = col + colSteps[i];

			Cell neighbor;

			// indicate which layer/bridge the ant is moving in
			if (onBridge) {
				neighbor = bridgeWorld[r][c];
			} else {
				neighbor = world[currLayer][r][c];
			}

			// add neighboring cells if they're not a wall
			if (i == 0) {
				cellPher.put(neighbor, CHANCE_MOVE); // + motionGen.nextInt(PHER_NOISE));
			} else if (neighbor.getType() != Cell.WALL) {
				cellPher.put(neighbor, neighbor.getPheromone()); // + motionGen.nextInt(PHER_NOISE));
				// System.out.println("getting pher " + neighbor.getPheromone());
			}
			// if (neighbor.getType() == Cell.NEST) {
			// nestNeighbor = true;
			// nest = i;
			// }
		}

		// if (nestNeighbor) {
		// System.out.println(nest + " "+cellPher );
		// }

		// if (currCell.getType() == Cell.NEST) {
		// System.out.println(currCell.getPheromone());
		// }

		// shuffle the map, then order it from smallest to greatest
		List<Cell> keys = new ArrayList<>(cellPher.keySet());
		Collections.shuffle(keys);
		Map<Cell, Integer> shuffleCells = new LinkedHashMap<>();
		keys.forEach(k -> shuffleCells.put(k, cellPher.get(k)));
		Map<Cell, Integer> orderedCells = shuffleCells.entrySet().stream().sorted(Entry.comparingByValue())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		return new ArrayList<Cell>(orderedCells.keySet());
	}

	/**
	 * Make the next move based on ranked cells
	 */
	synchronized private Cell chooseWeightedMove() {

		// possible steps based on the direction
		int[] rowSteps = direction.getRowSteps(VISION_RANGE);
		int[] colSteps = direction.getColSteps(VISION_RANGE);
		int numSteps = rowSteps.length;

		// weight (probability of moving to) of each cell
		int[] cellWeights = new int[numSteps + 1];

		// boolean nestNeighbor = false;
		// int nest = -1;
		for (int i = 0; i < rowSteps.length; i++) {
			int r = row + rowSteps[i];
			int c = col + colSteps[i];

			Cell tempCell;

			// indicate which layer/bridge the ant is moving in
			if (onBridge) {
				tempCell = bridgeWorld[r][c];
			} else {
				tempCell = world[currLayer][r][c];
			}

			if (tempCell.getType() != Cell.WALL) {
				if (i == 0) {
					cellWeights[i] = CHANCE_MOVE; // + tempCell.getPheromone();
				}
				// weight of staying in place is not affected by pheromones
				else {
					cellWeights[i] = cellWeights[i - 1] + CHANCE_MOVE + tempCell.getPheromone();
				}
			}
			// don't move into a wall
			else {
				cellWeights[i] = cellWeights[i - 1] + 0;
			}

			// if (tempCell.getType() == Cell.NEST) {
			// nestNeighbor = true;
			// nest = i;
			// }

		}

		// if (currCell.getType() == Cell.NEST) {
		// System.out.println(Arrays.toString(cellWeights));
		// // System.out.println(arena.getNumSteps());
		// }
		// //
		// if (nestNeighbor) {
		// System.out.println(nest + " " + Arrays.toString(cellWeights));
		// }

		// add the bridge as a potential step
		if (currCell.hasBridge()) {
			Cell firstCell = currCell.getBridge().firstCell(currCell);
			cellWeights[numSteps] = cellWeights[numSteps - 1] + CHANCE_MOVE + firstCell.getPheromone();
		} else {
			cellWeights[numSteps] = cellWeights[numSteps - 1];
		}

		// randomly generate number and find move that corresponds with number
		int num = motionGen.nextInt(cellWeights[numSteps] - 1) + 1;
		int move = binarySearch(cellWeights, 0, numSteps + 1, num);

		Cell nextCell;

		if (move == numSteps) {
			nextCell = currCell.getBridge().firstCell(currCell);
		} else {
			int r = row + rowSteps[move];
			int c = col + colSteps[move];

			if (onBridge) {
				nextCell = bridgeWorld[r][c];
			} else {
				nextCell = world[currLayer][r][c];
			}
		}

		return nextCell;
	}

	// find the leftmost mid that target < weights[mid]; eg.target=10,weight[mid]=12
	private static int binarySearch(int[] weights, int start, int end, int target) {
		while (start < end) {
			int mid = start + (end - start) / 2;
			if (target <= weights[mid]) {
				end = mid;
			} else {
				start = mid + 1;
			}
		}
		return start;
	}

	/**
	 * 
	 * @return if the ant is on the bridge
	 */
	synchronized public boolean onBridge() {
		return onBridge;
	}

	/**
	 * @return row where ant is currently
	 */
	synchronized public int getRow() {
		return row;
	}

	/**
	 * @return column where ant is currently
	 */
	synchronized public int getCol() {
		return col;
	}

	/**
	 * 
	 * @return ID of ant
	 */
	public int getID() {
		return id;
	}

	synchronized public int getLayer() {
		return currLayer;
	}

	//// /**
	// * Run method for Ant.
	// */
	// public void run() {
	// //TODO: fix run for diff simulations
	// while (true) {
	// move();
	// }
	// }
}