package ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Arena extends JPanel {

	// size of each cell
	public static final int CELLSIZE = 6; // was 10

	// array to represent the arena and the arena of cells
	private String[][][] arenaArray;
	private Cell[][][] theArena;

	// parameters
	private int maxPher;
	private int pherStrength;
	private int pherFactor;

	// number and list of ants
	private int numAnts;
	private List<Ant> ants = new ArrayList<Ant>();

	// if the simulation has started
	private boolean started = false;

	// list of nests and bridges in arena
	private Map<Cell, String> theNests = new HashMap<Cell, String>();
	private List<Bridge> bridges = new ArrayList<Bridge>();
	private int[][] bridgeArray; // array to represent bridges

	// list of activities (nest enters/bridge crosses) during simulation
	private List<Activity> activity = new ArrayList<Activity>();

	// list of nest occupation at end of simulation
	private List<NestOccupation> nestOccupation = new ArrayList<NestOccupation>();

	// keep track fo steps and simulations
	private int numSteps = 0;
	public int numSimulations = 0;
	private int totalSteps;
	private int totalSimulations;

	// initial location of ants
	private int startRow;
	private int startCol;

	private int boxSize;

	// where to store the data
	private String nestFile;
	private String activityFile;
	
	private double decayRate = 0.05;
	
	/**
	 * Constructor for the arena
	 * 
	 * Take in the different parameters and data files
	 */
	public Arena(String[][][] array, int[][] bridges, String nestFile, String activityFile, int totalSteps,
			int totalSimulations, int numAnts, int startRow, int startCol, int boxSize, int pherStrength, int maxPher,
			int pherFactor) {

		this.arenaArray = array;
		this.bridgeArray = bridges;

		this.nestFile = nestFile;
		this.activityFile = activityFile;

		this.pherFactor = pherFactor;
		this.pherStrength = pherStrength;
		this.maxPher = maxPher;

		this.totalSimulations = totalSimulations;
		this.totalSteps = totalSteps;
		this.numAnts = numAnts;

		this.startRow = startRow;
		this.startCol = startCol;
		this.boxSize = boxSize;

	}

	/**
	 * Start the simulation. Make the arena and initialize it. Add ants inside.
	 */
	public void simulate() {

		// create the arena and initialize it
		theArena = new Cell[arenaArray.length][arenaArray[0].length][arenaArray[0][0].length];

		// theArena = new Cell[arenaArray.length][arenaArray[0].length];
		initArena();

		// add ants and start them
		for (int i = 0; i < numAnts; i++) {
			Ant a = new Ant(i, startRow, startCol, theArena, this, pherFactor, new Direction(), activity);
			ants.add(a);
			// a.start();
		}

		started = true;
		startSim();
	}

	/**
	 * Initialize the arena based on the arena array. Add cells, specifying their
	 * types.
	 */
	private void initArena() {

		// make the cells
		for (int layer = 0; layer < arenaArray.length; layer++) {
			for (int row = 0; row < arenaArray[0].length; row++) {
				for (int col = 0; col < arenaArray[0][0].length; col++) {

					if (arenaArray[layer][row][col].equals("O")) {
						theArena[layer][row][col] = new EmptyCell(layer, row, col, pherStrength, maxPher, decayRate);
					} else if (arenaArray[layer][row][col].equals("X")) {
						theArena[layer][row][col] = new WallCell(layer, row, col);

						// } else if (arenaArray[row][col].charAt(0) == 'B') {
						// // edit bridge cell info
						// // String str = arenaArray[row][col].substring(1);
						// // String[] strlist = str.replaceAll("[^?0-9]+", " ").trim().split(" ");
						// // char orientation = str.charAt(str.indexOf(strlist[0]) +
						// strlist[0].length());
						// // String name = str.substring(str.indexOf(orientation) + 1);

					} else if (arenaArray[layer][row][col].charAt(0) == 'N') {						
						theArena[layer][row][col] = new NestCell(layer, row, col, pherStrength, maxPher,
								arenaArray[layer][row][col].substring(1), activity, this, decayRate);
						theNests.put(theArena[layer][row][col], arenaArray[layer][row][col].substring(1));
					}
				}
			}
		}

		// keep track of all the bridges
		for (int i = 0; i < bridgeArray.length; i++) {

			// get in info from the bridge array
			int[] info = bridgeArray[i];

			// the two ends of the bridge
			Cell end1 = theArena[info[1]][info[2]][info[3]];
			Cell end2 = theArena[info[4]][info[5]][info[6]];

			Bridge b = new Bridge(info[0], end1, end2, pherStrength, maxPher, decayRate);
			bridges.add(b);

			// let the ends be able to access the bridge
			end1.addBridge(b);
			end2.addBridge(b);
		}

	}

	/**
	 * Start the simulations. Move each ant in each step. Repeat simulations based
	 * on totalSimulations.
	 */
	public void startSim() {

		while (numSimulations < totalSimulations) {

			// move each ant
			Collections.shuffle(ants);
			for (Ant ant : ants) {
//				ant.move();

				// repaint();
			}

			for (int layer = 0; layer < arenaArray.length; layer++) {
				for (int row = 0; row < arenaArray[0].length; row++) {
					for (int col = 0; col < arenaArray[0][0].length; col++) {
						if (theArena[layer][row][col].visited()) {
							theArena[layer][row][col].pherDecay();
						}
					}
				}
			}

			repaint();

			// keep going until you run out of steps
			if (numSteps >= totalSteps) {

				// for (Ant a : ants) {
				// a.stopAnt();
				// }

				// count the number of ants in each nest
				for (Cell nest : theNests.keySet()) {
					// int numAnts = 0;
					// for (Ant a : ants) {
					// int r = a.getRow();
					// int c = a.getCol();
					// int l = a.getLayer();
					// if (theArena[l][r][c] == nest) {
					// numAnts++;
					// }
					// }
					// nestOccupation.add(new NestOccupation(theNests.get(nest),
					// nest.getPheromone(), numAnts,
					// numSimulations, pherFactor));
					System.out.println(theNests.get(nest) + " " + nest.getAnts() + " " + nest.getPheromone());
					nestOccupation.add(new NestOccupation(theNests.get(nest), nest.getPheromone(), nest.getAnts(),
							numSimulations, pherFactor));
				}

				System.out.println("Simulation: " + numSimulations);
				numSimulations++;

				// reset if there are more simulations to do
				if (numSimulations <= totalSimulations - 1) {
					reset();
				} else {
					System.out.println("making files");
					makeCSVNests(nestFile);
					makeCSVActivity(activityFile);
				}
				numSteps = 0;
			}
			numSteps++;
		}
	}

	/**
	 * Paint the arena and ants on the screen after any move.
	 */
	public void paint(Graphics g) {

		// paint only if the simulation has started
		if (started) {

			super.paint(g);
			Graphics2D g2 = (Graphics2D) g;

			// TODO: have different graphics for the multiple layers

			for (int layer = 0; layer < arenaArray.length; layer++) {
				for (int row = 0; row < arenaArray[0].length; row++) {
					for (int col = 0; col < arenaArray[0][0].length; col++) {
						// graphics
						//// paint walls black
						if (theArena[layer][row][col].getType() == Cell.WALL) {
							Rectangle2D.Double c;
							c = new Rectangle2D.Double(col * CELLSIZE, row * CELLSIZE, CELLSIZE, CELLSIZE);
							g2.setPaint(Color.BLACK);
							g2.fill(c);
						}
						// paint nests blue
						else if (theArena[layer][row][col].getType() == Cell.NEST) {
							Rectangle2D.Double c;
							c = new Rectangle2D.Double(col * CELLSIZE, row * CELLSIZE, CELLSIZE, CELLSIZE);
							g2.setPaint(Color.BLUE);
							g2.fill(c);
						} else if (theArena[layer][row][col].hasBridge()) {
							Rectangle2D.Double c;
							c = new Rectangle2D.Double(col * CELLSIZE, row * CELLSIZE, CELLSIZE, CELLSIZE);
							g2.setPaint(Color.DARK_GRAY);
							g2.fill(c);
						}

//						synchronized (theArena[layer][row][col]) {
							if (theArena[layer][row][col].visited()) {
								// graphics
								Rectangle2D.Double c;
								c = new Rectangle2D.Double(col * CELLSIZE, row * CELLSIZE, CELLSIZE, CELLSIZE);

								if (theArena[layer][row][col].getType() == Cell.NEST) {
									g2.setPaint(Color.GREEN);
								} else {
									g2.setPaint(Color.PINK);
								}
								g2.fill(c);

//							}
						}
					}
				}
			}

			// try {
			// Thread.sleep(4);
			// } catch (InterruptedException e) {
			// System.out.println("hi");
			// }

			// decrease the phromoens of the bridges
			for (Bridge bridge : bridges) {
				bridge.pherDecay();
			}

			// graphics
			// paint ants red
			synchronized (ants) {
				for (Ant a : ants) {
					if (!a.onBridge()) {
						Rectangle2D.Double c;
						c = new Rectangle2D.Double(a.getCol() * CELLSIZE, a.getRow() * CELLSIZE, CELLSIZE, CELLSIZE);
						g2.setPaint(Color.RED);
						g2.fill(c);
					}
				}
			}
		}
	}

	/**
	 * @return number of steps
	 */
	synchronized public int getNumSteps() {
		return numSteps;
	}

	/**
	 * @return number of simulations
	 */
	synchronized public int getNumSimulations() {
		return numSimulations;
	}

	/**
	 * Reset arena for a new simulation
	 */
	public void reset() {
		ants = new ArrayList<Ant>();

		// draw the ants again
		// for (Ant a : ants) {
		// a.setCol(BOXSIZE / 2);
		// a.setRow(BOXSIZE / 2);
		// a.restart();
		// a.start();
		// }

		for (int i = 0; i < numAnts; i++) {
			Ant a = new Ant(i, startRow, startCol, theArena, this, pherFactor, new Direction(), activity);
			ants.add(a);
			// a.start();
		}

		// reset each cell and bridge
		for (int layer = 0; layer < arenaArray.length; layer++) {
			for (int row = 0; row < arenaArray[0].length; row++) {
				for (int col = 0; col < arenaArray[0][0].length; col++) {
					theArena[layer][row][col].reset();
				}
			}
		}
		for (Bridge bridge : bridges) {
			bridge.reset();
		}
		repaint();
	}

	/**
	 * Make the files to store the activity data for bridge crossings and nest
	 * enters/exits
	 * 
	 * @param filePath
	 *            where the file is
	 */
	public void makeCSVActivity(String filePath) {
		try {
			File f = new File(filePath);
			CSVPrinter csvPrinter;

			// add to file if file exists
			if (f.exists()) {
				FileWriter fw = new FileWriter(filePath, true);
				csvPrinter = new CSVPrinter(fw, CSVFormat.DEFAULT);
			} else {
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));

				csvPrinter = new CSVPrinter(writer,
						CSVFormat.DEFAULT.withHeader("Time", "AntID", "Activity", "Simulation", "FromLayer", "FromRow",
								"FromCol", "FromBoxRow", "FromBoxCol", "ToLayer", "ToRow", "ToCol", "ToBoxRow",
								"ToBoxCol", "Name", "PheromoneStrength", "MaxPheromone", "PherFactor"));
			}
			for (Activity a : activity) {
				int fromRow = a.getFromRow();
				int fromCol = a.getFromCol();
				int toRow = a.getToRow();
				int toCol = a.getToCol();

				csvPrinter.printRecord(a.getTime(), a.getID(), a.getActivity(), a.getSimulation(), a.getFromLayer(),
						fromRow, fromCol, (fromRow - fromRow / boxSize) / boxSize,
						(fromCol - fromCol / boxSize) / boxSize, a.getToLayer(), toRow, toCol,
						(toRow - toRow / boxSize) / boxSize, (toCol - toCol / boxSize) / boxSize, a.getName(),
						pherStrength, maxPher, pherFactor);
			}
			csvPrinter.flush();
			csvPrinter.close();

		} catch (Exception IOException) {
			System.out.println("Activity file not found");
		}
	}

	/**
	 * Make file to store nest occupation data
	 * 
	 * @param filePath
	 *            where the file is
	 */
	public void makeCSVNests(String filePath) {

		try {
			File f = new File(filePath);
			CSVPrinter csvPrinter;

			// add to file if file exists
			if (f.exists()) {
				FileWriter fw = new FileWriter(filePath, true);
				csvPrinter = new CSVPrinter(fw, CSVFormat.DEFAULT);
			} else {
				BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath));
				csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("Nest", "NumAnts", "NestType",
						"Simulation", "PheromoneStrength", "MaxPheromone", "PherFactor", "nestPher"));
			}
			for (NestOccupation n : nestOccupation) {
				csvPrinter.printRecord(n.getName(), n.getAnts(), "" + n.getName().charAt(0), n.getSimulation(),
						pherStrength, maxPher, pherFactor, n.getPher());
			}
			csvPrinter.flush();
			csvPrinter.close();

		} catch (Exception IOException) {
			System.out.println("Nest file not found");
		}
	}
}
