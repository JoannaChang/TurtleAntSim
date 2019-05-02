package ants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.JFrame;

/**
 * This is the controller for turtle ant simulations. It reads in parameters
 * from a configuration/properties file and starts the simulations according to
 * those parameters.
 * 
 * @author Joanna Chang
 */
//need summer 10a
public class SimController {

	public SimController() {
		Properties prop = new Properties();
		InputStream input = null;

		try {
			// load a properties file
			input = new FileInputStream("SummerSim7c.properties");
			prop.load(input);

			// get arena info
			String[] layers = prop.getProperty("arena").split(":"); //layers split by ":"
			String[] ar = layers[0].split(";"); //rows split by ":"
			int col = ar[0].split(",").length; //cells split by ","
			
			int row = ar.length;

			String[][][] arenaArray = new String[layers.length][row][col];
			for (int i = 0; i < layers.length; i++) {
				String[] layer = layers[i].split(";");
				String[][] layerArray = new String[row][col];
				for (int j = 0; j < row; j++) {
					layerArray[j] = layer[j].split(",");
				}
				arenaArray[i] = layerArray;
			}

			// get bridge info
			String[] b = prop.getProperty("bridges").split(";");
			int[][] bridges = new int[b.length][7];
			for (int i = 0; i < b.length; i++) {
				bridges[i] = Arrays.stream(b[i].split(",")).mapToInt(Integer::parseInt).toArray();
			}

			String nestFile = prop.getProperty("nestFile");
			String activityFile = prop.getProperty("activityFile");

			int totalSteps = Integer.parseInt(prop.getProperty("totalSteps"));
			int totalSimulations = Integer.parseInt(prop.getProperty("totalSimulations"));
			int numAnts = Integer.parseInt(prop.getProperty("numAnts"));

			int startRow = Integer.parseInt(prop.getProperty("startRow"));
			int startCol = Integer.parseInt(prop.getProperty("startCol"));
			int boxSize = Integer.parseInt(prop.getProperty("boxSize"));

			int[] psValues = Arrays.stream(prop.getProperty("pherStrength").split(",")).mapToInt(Integer::parseInt)
					.toArray();
			int[] mpValues = Arrays.stream(prop.getProperty("maxPher").split(",")).mapToInt(Integer::parseInt)
					.toArray();
			int[] pfValues = Arrays.stream(prop.getProperty("pherFactor").split(",")).mapToInt(Integer::parseInt)
					.toArray();

			//run simulations
			for (int pf = pfValues[0]; pf <= pfValues[1]; pf = pf + pfValues[2]) {
				for (int ps = psValues[0]; ps <= psValues[1]; ps = ps + psValues[2]) {
					for (int mp = mpValues[0]; mp <= mpValues[1]; mp = mp + mpValues[2]) {
						JFrame f = new JFrame("Ant Simulation");
						Arena a = new Arena(arenaArray, bridges, nestFile, activityFile, totalSteps, totalSimulations,
								numAnts, startRow, startCol, boxSize, ps, mp, pf);
						f.add(a);
						f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						f.setSize(row * Arena.CELLSIZE + 350, col * Arena.CELLSIZE + 350);
						f.setVisible(true);
						a.simulate();
					}
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Run SimController
	 */
	public static void main(String[] s) {
		SimController sc = new SimController();
	}
}