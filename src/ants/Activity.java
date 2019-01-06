package ants;

/**
 * Stores the data for an activity (bridge crossing or nest enter/exit).
 * 
 * @author Joanna
 *
 */
public class Activity {

	// ID of ant that performed the activity
	private int antID;

	// when the activity occurred: time step and simulation
	private int time;
	private int simulation;

	// where the activity occurred
	private int fromLayer, fromRow, fromCol, toLayer, toRow, toCol;

	// type of activity and name of bridge/nest
	private String activity, name;

	public Activity(int simulation, int time, int fromLayer, int fromRow, int fromCol, int toLayer, int toRow,
			int toCol, int antID, String activity, String name) {
		this.simulation = simulation;
		this.time = time;
		this.antID = antID;
		this.activity = activity;
		this.name = name;
		this.fromLayer = fromLayer;
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toLayer = toLayer;
		this.toRow = toRow;
		this.toCol = toCol;
	}

	public int getID() {
		return antID;
	}

	public int getSimulation() {
		return simulation;
	}

	public int getToLayer() {
		return toLayer;
	}

	public int getToRow() {
		return toRow;
	}

	public int getToCol() {
		return toCol;
	}

	public int getFromLayer() {
		return fromLayer;
	}

	public int getFromRow() {
		return fromRow;
	}

	public int getFromCol() {
		return fromCol;
	}

	public int getTime() {
		return time;
	}

	public String getActivity() {
		return activity;
	}

	public String getName() {
		return name;
	}

}
