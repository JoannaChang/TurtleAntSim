package ants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

/**
 * This creates the arrays to represent an arena and the bridges within the
 * arena. The arena can be wholly customized with the "custom" methods, or it
 * can be made based on a box grid with the "box" methods.
 * 
 * @author Joanna Chang
 */

public class ArenaArray {
	// string array to represent the arena
	private String[][] arena;

	// list of bridges
	private List<String> bridges = new ArrayList<String>();

	// size (number of cells in) of each box
	private int boxSize;

	// total rows and columns of cells in arena
	private int totalRows, totalCols;

	/**
	 * Make custom or box arena
	 */
	public ArenaArray(int rows, int cols, int size, boolean custom) {

		if (custom) {
			makeCustomArena(rows, cols);
		} else {
			makeBoxArena(rows, cols, size);
		}
	}

	/**
	 * Make custom arena with specified numbers of rows and columns
	 */
	public void makeCustomArena(int tRows, int tCols) {
		this.totalRows = tRows + 2;
		this.totalCols = tCols + 2;
		arena = new String[totalRows][totalCols];

		for (int r = 0; r < totalRows; r++) {
			for (int c = 0; c < totalCols; c++) {
				// make walls around the arena
				if (r == 0 || r == totalRows - 1 || c == 0 || c == totalRows - 1) {
					arena[r][c] = "X";
				}
				// make everything else empty for now
				else {
					arena[r][c] = "O";
				}
			}
		}
	}

	/**
	 * Add a custom bridge at row, col with the name bridgeName
	 */
	public void addCustomBridge(String bridgeName, int row, int col) {
		arena[row + 1][col + 1] = "B" + bridgeName;
	}

	/**
	 * Add a custom nest at row, col with the name nestName
	 */
	public void addCustomNest(String nestName, int row, int col) {
		arena[row + 1][col + 1] = "N" + nestName;
	}

	/**
	 * Add a custom wall at row, col
	 */
	public void addCustomWall(int row, int col) {
		arena[row + 1][col + 1] = "X";
	}

	/**
	 * Add a custom bridge with a certain length and with the two ends as specified
	 * cells
	 */
	public void addCustomBridge(int row1, int col1, int array1, int row2, int col2, int array2, int length) {
		// bridge info: length, layer1, row1, col1, layer2, row2, col2
		bridges.add(length + "," + array1 + "," + row1 + "," + col1 + "," + array2 + "," + row2 + "," + col2);
	}

	/**
	 * Make box arena based on specified numbers of box rows and columns
	 * 
	 * @param boxRows
	 *            number of boxes in each row
	 * @param boxCols
	 *            number of boxes in each column
	 * @param size
	 *            number of cells spanning the length of each box
	 */
	public void makeBoxArena(int boxRows, int boxCols, int size) {
		this.totalRows = boxRows * size + boxRows + 1;
		this.totalCols = boxCols * size + boxCols + 1;
		arena = new String[totalRows + 1][totalCols + 1];
		this.boxSize = size;

		// make everything empty for now
		for (int r = 0; r <= totalRows; r++) {
			for (int c = 0; c <= totalCols; c++) {
				arena[r][c] = "O";
			}
		}

		// make vertical walls
		for (int r = 0; r <= totalRows; r++) {
			for (int c = 0; c <= boxCols; c++) {
				arena[r][c * boxSize + c] = "X";
			}
		}

		// make horizontal walls
		for (int c = 0; c <= totalCols; c++) {
			for (int r = 0; r <= boxRows; r++) {
				arena[r * boxSize + r][c] = "X";
			}
		}
	}

	/**
	 * Make bridge between specified boxes
	 * 
	 * pre: (box2Row >= box1Row) && (box2Col >= box1Col)
	 * 
	 * @param orientation
	 *            V(vertical), H(horizontal), or D(diagonal)
	 */
	public void addBoxBridge(int box1Row, int box1Col, int box2Row, int box2Col, char orientation, int length) {
		int rPos, cPos;

		if (box1Row == box2Row) {
			rPos = box2Row * boxSize + box2Row + boxSize / 2 + 1;
		} else {
			rPos = box2Row * boxSize + box2Row;
		}

		if (box1Col == box2Col) {
			cPos = box2Col * boxSize + box2Col + boxSize / 2 + 1;
		} else {
			cPos = box2Col * boxSize + box2Col;
		}

		// make bridges with the length and locations of the 2 ends of the bridge
		// bridge info: length, layer1, row1, col1, layer2, row2, col2
		if (orientation == 'V') {
			bridges.add(length + "," + 0 + "," + (rPos - 1) + "," + cPos + "," + 0 + "," + (rPos + 1) + "," + cPos);
		} else if (orientation == 'H') {
			bridges.add(length + "," + 0 + "," + rPos + "," + (cPos - 1) + "," + 0 + "," + rPos + "," + (cPos + 1));
		} else if (orientation == 'D') {
				bridges.add(length + "," + 0 + "," + (rPos + 2) + "," + (cPos + 2) + "," + 0 + "," + (rPos - 2) + ","
						+ (cPos - 2));
				bridges.add(length + "," + 0 + "," + (rPos + 2) + "," + (cPos - 2) + "," + 0 + "," + (rPos - 2) + ","
						+ (cPos + 2));
		}
		
		//ants have to travel by bridge
		arena[rPos][cPos] = "X";
	}

	/**
	 * Make nest in specified box
	 */
	public void addBoxNest(int boxRow, int boxCol, String nestName) {
		int rPos = (boxRow * boxSize) + boxRow + boxSize / 2 + 1;
		int cPos = (boxCol * boxSize) + boxCol + boxSize / 2 + 1;
		arena[rPos][cPos] = "N" + nestName;
	}

	/**
	 * @return number of rows in the arena
	 */
	public int totalRows() {
		return totalRows;
	}

	/**
	 * @return number of columns in the arena
	 */
	public int totalCols() {
		return totalCols;
	}

	/**
	 * @return array to represent the arena
	 */
	public String[][] getArenaArray() {
		return arena;
	}

	/**
	 * @return list of bridges
	 */
	public List<String> getBridges() {
		return bridges;
	}

	/**
	 * @return String of info about bridges
	 */
	public String printPropertiesBridges() {
		return String.join(";", bridges);
	}

	/**
	 * @return array to represent the arena
	 */
	public String printPropertiesArray() {
		String s = Arrays.deepToString(arena);
		s = s.replace("[[", "").replace("]]", "").replace("], [", ";").replace(" ", "");
		return s;
	}

	/**
	 * @return starting location for ants in box arena
	 */
	public int getStart() {
		return boxSize / 2;
	}

	/**
	 * Create frame and Arena panel in the frame.
	 * 
	 * @param s
	 */
	public static void main(String[] s) {
		// make arena array
		// TODO: run this multiple times to make diff layers of arrays
		
//		ArenaArray a = new ArenaArray(4, 4, 17, false);
//		a.addBoxBridge(0, 0, 0, 1, 'H', 10);
//		a.addBoxBridge(0, 0, 1, 0, 'V', 10);
//		a.addBoxBridge(0, 1, 0, 2, 'H', 10);
//		a.addBoxBridge(0, 2, 0, 3, 'H', 10);
//		a.addBoxBridge(0, 2, 1, 2, 'V', 10); // take out for linear connection
////		a.addBoxBridge(0, 3, 1, 3, 'V', 10);
//		a.addBoxBridge(1, 0, 2, 0, 'V', 10);
////		a.addBoxBridge(1, 2, 1, 3, 'H', 10);
//		a.addBoxBridge(0, 2, 1, 3, 'D', 10); // diagonal bridge
//		a.addBoxBridge(2, 0, 3, 0, 'V', 10);
//		a.addBoxBridge(2, 1, 3, 1, 'V', 10);
//		a.addBoxBridge(2, 1, 2, 2, 'H', 10); //taking out for testing
//		a.addBoxBridge(3, 0, 3, 1, 'H', 10);
//		a.addBoxBridge(2, 2, 3, 2, 'V', 10);
		
//		a.addBoxBridge(3, 2, 3, 3, 'H', 10); // Fall only

		
		//transposed for summer
		ArenaArray a = new ArenaArray(4, 4, 17, false);
		a.addBoxBridge(0, 0, 0, 1, 'H', 10);
		a.addBoxBridge(0, 1, 0, 2, 'H', 10);
		a.addBoxBridge(0, 2, 0, 3, 'H', 10);
//		a.addBoxBridge(0, 2, 1, 2, 'V', 10); // take out for linear connection
		a.addBoxBridge(0, 3, 1, 3, 'V', 10);
		a.addBoxBridge(1, 2, 2, 2, 'V', 10);
		a.addBoxBridge(1, 2, 1, 3, 'H', 10);
		a.addBoxBridge(2, 2, 2, 3, 'H', 10);

		a.addBoxBridge(0, 0, 1, 0, 'V', 10);
		
		a.addBoxBridge(1, 0, 2, 0, 'V', 10);
		a.addBoxBridge(2, 0, 3, 0, 'V', 10);
		a.addBoxBridge(2, 1, 3, 1, 'V', 10);
		a.addBoxBridge(2, 0, 2, 1, 'H', 10); //taking out for testing
		a.addBoxBridge(3, 0, 3, 1, 'H', 10);
		a.addBoxBridge(2, 0, 3, 1, 'D', 10); // diagonal bridge
		
		// // Summer R nests
		 a.addBoxNest(3, 0, "R1");
		 a.addBoxNest(2, 1, "R2");
		 a.addBoxNest(3, 1, "R3");
		
		 // Summer D nests
		 a.addBoxNest(0, 3, "D1");
		 a.addBoxNest(1, 2, "D2");
		 a.addBoxNest(2, 3, "D3");


		// Fall R nests
//		a.addBoxNest(0, 2, "R1");
//		a.addBoxNest(0, 3, "R2");
//		a.addBoxNest(1, 2, "R3");
//		a.addBoxNest(1, 3, "R4");
//
		// Fall D nests
//		a.addBoxNest(2, 0, "D1");
//		a.addBoxNest(3, 1, "D2");
//		a.addBoxNest(2, 2, "D3");
//		a.addBoxNest(3, 3, "D4");
//
		 // Summer R nests
//		 a.addBoxNest(0, 3, "R1");
//		 a.addBoxNest(1, 2, "R2");
//		 a.addBoxNest(1, 3, "R3");
		
		 // Summer D nests
//		 a.addBoxNest(3, 0, "D1");
//		 a.addBoxNest(2, 1, "D2");
//		 a.addBoxNest(3, 2, "D3");
//		 a.addBoxNest(2, 1, "D3");
//		 a.addBoxNest(3, 1, "D2");
		 
		// make fall arena with secions further away
//		ArenaArray a = new ArenaArray(5, 5, 17, false);
//		//R bridges
//		a.addBoxBridge(0, 0, 0, 1, 'H', 10);
//		a.addBoxBridge(0, 1, 0, 2, 'H', 10);
//		a.addBoxBridge(0, 2, 0, 3, 'H', 10);
//		a.addBoxBridge(0, 3, 0, 4, 'H', 10);
//		a.addBoxBridge(0, 3, 1, 3, 'V', 10);
//		a.addBoxBridge(0, 4, 1, 4, 'V', 10);
//		a.addBoxBridge(1, 3, 1, 4, 'H', 10);
//		a.addBoxBridge(0, 3, 1, 4, 'D', 10); // diagonal bridge
//		
//		//D bridges
//		a.addBoxBridge(0, 0, 1, 0, 'V', 10);
//		a.addBoxBridge(1, 0, 2, 0, 'V', 10);
//		a.addBoxBridge(2, 0, 3, 0, 'V', 10);
//		a.addBoxBridge(3, 0, 4, 0, 'V', 10);
//		a.addBoxBridge(4, 0, 4, 1, 'H', 10);
//		a.addBoxBridge(4, 2, 4, 3, 'H', 10);
//		a.addBoxBridge(3, 1, 3, 2, 'H', 10);
//		a.addBoxBridge(3, 1, 4, 1, 'V', 10);
//		a.addBoxBridge(3, 2, 4, 2, 'V', 10);
//		
//		// R nests
//		a.addBoxNest(0, 3, "R1");
//		a.addBoxNest(0, 4, "R2");
//		a.addBoxNest(1, 3, "R3");
//		a.addBoxNest(1, 4, "R4");
//
//		// D nests
//		a.addBoxNest(3, 0, "D1");
//		a.addBoxNest(4, 1, "D2");
//		a.addBoxNest(3, 2, "D3");
//		a.addBoxNest(4, 3, "D4");

		// make uninteresting arena: mirrored in both sections
		// a.addBoxNest(0, 2, "R1");
		// a.addBoxNest(0, 3, "R2");
		// a.addBoxNest(1, 2, "R3");
		// a.addBoxNest(1, 3, "R4");
		// a.addBoxNest(2, 0, "D1");
		// a.addBoxNest(3, 0, "D2");
		// a.addBoxNest(2, 1, "D3");
		// a.addBoxNest(3, 1, "D4");
		// a.addBoxBridge(2, 2, 3, 2, 'V', 10);
		// a.addBoxBridge(3, 0, 3, 1, 'H', 10);
		// a.addBoxBridge(2, 0, 2, 1, 'H', 10);
		// a.addBoxBridge(2, 0, 3, 1, 'D', 10);

		System.out.println(a.printPropertiesArray());
		System.out.println(a.printPropertiesBridges());

		System.out.println(a.getStart());

	}

}
