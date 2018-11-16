package ants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Direction {

	//// /** constants, representing the 8 directions */
	// public static final Direction UP = new Direction(-1, 0), DOWN = new
	//// Direction(1, 0), LEFT = new Direction(0, -1),
	// RIGHT = new Direction(0, 1), UP_RIGHT = new Direction(-1, 1), UP_LEFT = new
	//// Direction(-1, -1),
	// DOWN_RIGHT = new Direction(1, 1), DOWN_LEFT = new Direction(1, -1);

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

	private int xDir = 0, yDir = 0;

	// private Tuple direction;

	private String direction;

	private Random randomGen = new Random();

	public Direction() {
		while (xDir == 0 && yDir == 0) {
			this.xDir = randomGen.nextInt(3) - 1;
			this.yDir = randomGen.nextInt(3) - 1;
		}

		// direction = new Tuple(xDir, yDir);
		direction = Integer.toString(xDir) + "," + Integer.toString(yDir);

	}

	public int getX() {
		return xDir;
	}

	public int getY() {
		return yDir;
	}

//	public void changeX(int newX) {
//		xDir = newX;
//	}
//
//	public void changeY(int newY) {
//		yDir = newY;
//	}

	/**
	 * 
	 * @param degrees
	 * @pre: degrees is 90,180, 270, or 360
	 */
	public void turn(int degrees) {
		int[] rowSteps, colSteps;

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
		direction = Integer.toString(xDir)+","+Integer.toString(yDir);
	}

	// // 50% chance each of turning left or right
	// double random = Math.random();
	//
	// if(xDir==0)
	// {
	// if (random > 0.5) {
	// changeX(1);
	// } else {
	// changeX(-1);
	// }
	// }else if(yDir==0)
	// {
	// if (random > 0.5) {
	// changeY(1);
	// } else {
	// changeY(-1);
	// }
	// }else
	// {
	// if (random > 0.5) {
	// changeX(0);
	// } else {
	// changeY(0);
	// }
	// }
	//
	// // direction = new Tuple(xDir, yDir);
	// direction=Integer.toString(xDir)+","+Integer.toString(yDir);
	// }

	/**
	 * 
	 * @param degrees
	 * @pre: degrees is 90,180, 270, or 360
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
