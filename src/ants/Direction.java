package ants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Determines the directionality of ant movement.
 * 
 * TODO: make class better with tuple representation rather than string
 * representation
 * 
 * @author Joanna Chang
 */

public class Direction {

	// TODO: make constants, representing the 8 directions
	// public static final Direction UP = new Direction(-1, 0), DOWN = new
	//// Direction(1, 0), LEFT = new Direction(0, -1),
	// RIGHT = new Direction(0, 1), UP_RIGHT = new Direction(-1, 1), UP_LEFT = new
	//// Direction(-1, -1),
	// DOWN_RIGHT = new Direction(1, 1), DOWN_LEFT = new Direction(1, -1);

	// Steps you can take based on your current direction.
	// index 0: current position, index 1 & 2: 90 degrees range, index 3 & 4: 180
	// degrees, index 5 & 6: 270 degrees, index 7: 360 degrees
	// (e.g.: ints in UP_COL_DIR refer to changes in column indices when you're
	// facing "UP")

	/**
	 * Steps you can take based on your current direction.
	 * 
	 * index 0: current position, index 1 & 2: 90 degrees range, index 3 & 4: 180
	 * degrees, index 5 & 6: 270 degrees, index 7: 360 degrees
	 * 
	 * (e.g.: ints in UP_COL_DIR refer to changes in column indices when you're
	 * facing "UP")
	 */
	private static final int[] UP_ROW_DIR = new int[] { 0, -1, -1, -1, 0, 0, 1, 1, 1 },
			UP_COL_DIR = new int[] { 0, 0, -1, 1, -1, 1, -1, 1, 0 },

			DOWN_ROW_DIR = new int[] { 0, 1, 1, 1, 0, 0, -1, -1, -1 },
			DOWN_COL_DIR = new int[] { 0, 0, -1, 1, -1, 1, -1, 1, 0 },

			LEFT_ROW_DIR = new int[] { 0, 0, -1, 1, -1, 1, -1, 1, 0 },
			LEFT_COL_DIR = new int[] { 0, -1, -1, -1, 0, 0, 1, 1, 1 },

			RIGHT_ROW_DIR = new int[] { 0, 0, -1, 1, -1, 1, -1, 1, 0 },
			RIGHT_COL_DIR = new int[] { 0, 1, 1, 1, 0, 0, -1, -1, -1 },

			UPLEFT_ROW_DIR = new int[] { 0, -1, -1, 0, -1, 1, 0, 1, 1 },
			UPLEFT_COL_DIR = new int[] { 0, -1, 0, -1, 1, -1, 1, 0, 1 },

			UPRIGHT_ROW_DIR = new int[] { 0, -1, -1, 0, -1, 1, 0, 1, 1 },
			UPRIGHT_COL_DIR = new int[] { 0, 1, 0, 1, -1, 1, -1, 0, -1 },

			DOWNLEFT_ROW_DIR = new int[] { 0, 1, 1, 0, -1, 1, -1, 0, -1 },
			DOWNLEFT_COL_DIR = new int[] { 0, -1, 0, -1, -1, 1, 0, 1, 1 },

			DOWNRIGHT_ROW_DIR = new int[] { 0, 1, 0, 1, 1, -1, -1, 0, -1 },
			DOWNRIGHT_COL_DIR = new int[] { 0, 1, 1, 0, -1, 1, 0, -1, -1 };

	// TODO: make this class better by using tuples instead?
	// private static final Map<Tuple, int[]> COL_STEPS = new HashMap<Tuple,
	// int[]>();
	// static {
	// COL_STEPS.put(new Tuple(0,-1), UP_COL_DIR);
	// COL_STEPS.put(new Tuple(0,1), DOWN_COL_DIR);
	// COL_STEPS.put(new Tuple(-1,0), LEFT_COL_DIR);
	// COL_STEPS.put(new Tuple(1,0), RIGHT_COL_DIR);
	// COL_STEPS.put(new Tuple(-1,-1), UPLEFT_COL_DIR);
	// COL_STEPS.put(new Tuple(1,-1), UPRIGHT_COL_DIR);
	// COL_STEPS.put(new Tuple(-1,1), DOWNLEFT_COL_DIR);
	// COL_STEPS.put(new Tuple(1,1), DOWNRIGHT_COL_DIR);
	// }
	//
	// private static final Map<Tuple, int[]> ROW_STEPS = new HashMap<Tuple,
	// int[]>();
	// static {
	// ROW_STEPS.put(new Tuple(0,-1), UP_ROW_DIR);
	// ROW_STEPS.put(new Tuple(0,1), DOWN_ROW_DIR);
	// ROW_STEPS.put(new Tuple(-1,0), LEFT_ROW_DIR);
	// ROW_STEPS.put(new Tuple(1,0), RIGHT_ROW_DIR);
	// ROW_STEPS.put(new Tuple(-1,-1), UPLEFT_ROW_DIR);
	// ROW_STEPS.put(new Tuple(1,-1), UPRIGHT_ROW_DIR);
	// ROW_STEPS.put(new Tuple(-1,1), DOWNLEFT_ROW_DIR);
	// ROW_STEPS.put(new Tuple(1,1), DOWNRIGHT_ROW_DIR);
	// }

	// map direction to possible col steps
	private static final Map<String, int[]> COL_STEPS = new HashMap<String, int[]>();
	static {
		COL_STEPS.put("0,-1", UP_COL_DIR);
		COL_STEPS.put("0,1", DOWN_COL_DIR);
		COL_STEPS.put("-1,0", LEFT_COL_DIR);
		COL_STEPS.put("1,0", RIGHT_COL_DIR);
		COL_STEPS.put("-1,-1", UPLEFT_COL_DIR);
		COL_STEPS.put("1,-1", UPRIGHT_COL_DIR);
		COL_STEPS.put("-1,1", DOWNLEFT_COL_DIR);
		COL_STEPS.put("1,1", DOWNRIGHT_COL_DIR);
	}

	// map direction to possible row steps
	private static final Map<String, int[]> ROW_STEPS = new HashMap<String, int[]>();
	static {
		ROW_STEPS.put("0,-1", UP_ROW_DIR);
		ROW_STEPS.put("0,1", DOWN_ROW_DIR);
		ROW_STEPS.put("-1,0", LEFT_ROW_DIR);
		ROW_STEPS.put("1,0", RIGHT_ROW_DIR);
		ROW_STEPS.put("-1,-1", UPLEFT_ROW_DIR);
		ROW_STEPS.put("1,-1", UPRIGHT_ROW_DIR);
		ROW_STEPS.put("-1,1", DOWNLEFT_ROW_DIR);
		ROW_STEPS.put("1,1", DOWNRIGHT_ROW_DIR);
	}

	// directions are represented by x and y directionality
	private int xDir = 0, yDir = 0;

	// string representing direction
	private String direction;

	// random generator for turning
	private Random randomGen = new Random();

	/**
	 * Constructor for direction
	 */
	public Direction() {

		// initiate ants with random direction
		while (xDir == 0 && yDir == 0) {
			this.xDir = randomGen.nextInt(3) - 1;
			this.yDir = randomGen.nextInt(3) - 1;
		}

		// direction = new Tuple(xDir, yDir); // uncomment for Tuple directions
		direction = Integer.toString(xDir) + "," + Integer.toString(yDir);
	}

	public int getX() {
		return xDir;
	}

	public int getY() {
		return yDir;
	}
	
	//added 5/2/19
	public void setDir(int xDir,int yDir) {
		direction = Integer.toString(xDir) + "," + Integer.toString(yDir);
	}
	//
	
	/**
	 * Turn randomly within a certain range of degrees
	 * 
	 * @pre: degrees is 90, 180, 270, or 360
	 */
	public void turn(int degrees) {
		int[] rowSteps, colSteps;

		// get possible new directions based on range of degrees
		if (degrees == 90) {
			rowSteps = Arrays.copyOfRange(ROW_STEPS.get(direction), 2, 4);
			colSteps = Arrays.copyOfRange(COL_STEPS.get(direction), 2, 4);
		} else if (degrees == 180) {
			rowSteps = Arrays.copyOfRange(ROW_STEPS.get(direction), 2, 6);
			colSteps = Arrays.copyOfRange(COL_STEPS.get(direction), 2, 6);

		} else if (degrees == 270) {
			rowSteps = Arrays.copyOfRange(ROW_STEPS.get(direction), 2, 8);
			colSteps = Arrays.copyOfRange(COL_STEPS.get(direction), 2, 8);

		} else if (degrees == 360) {
			rowSteps = Arrays.copyOfRange(ROW_STEPS.get(direction), 2, 9);
			colSteps = Arrays.copyOfRange(COL_STEPS.get(direction), 2, 9);
		} else {
			throw new IllegalArgumentException();
		}

		int choice = randomGen.nextInt(rowSteps.length);
		xDir = colSteps[choice];
		yDir = rowSteps[choice];
		direction = Integer.toString(xDir) + "," + Integer.toString(yDir);
	}

	/**
	 * Get possible row steps based on range of degrees (vision_range)
	 * 
	 * @pre: degrees is 90, 180, 270, or 360
	 */
	public int[] getRowSteps(int degrees) {
		if (degrees == 90) {
			return Arrays.copyOfRange(ROW_STEPS.get(direction), 0, 4);
		} else if (degrees == 180) {
			return Arrays.copyOfRange(ROW_STEPS.get(direction), 0, 6);
		} else if (degrees == 270) {
			return Arrays.copyOfRange(ROW_STEPS.get(direction), 0, 8);
		} else if (degrees == 360) {
			return ROW_STEPS.get(direction);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Get possible col steps based on range of degrees (vision_range)
	 * 
	 * @pre: degrees is 90, 180, 270, or 360
	 */
	public int[] getColSteps(int degrees) {
		if (degrees == 90) {
			return Arrays.copyOfRange(COL_STEPS.get(direction), 0, 4);
		} else if (degrees == 180) {
			return Arrays.copyOfRange(COL_STEPS.get(direction), 0, 6);
		} else if (degrees == 270) {
			return Arrays.copyOfRange(COL_STEPS.get(direction), 0, 8);
		} else if (degrees == 360) {
			return COL_STEPS.get(direction);
		} else {
			throw new IllegalArgumentException();
		}
	}
}
