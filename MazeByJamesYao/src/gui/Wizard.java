package gui;

import generation.Maze;

public class Wizard implements RobotDriver {
	Robot rob;
	Maze m;
	float energy_consumed;
	int pathlength;

	// sets robot
	@Override
	public void setRobot(Robot r) {
		rob = r;
		energy_consumed = 0;
		pathlength = 0;
	}

	// sets maze
	@Override
	public void setMaze(Maze maze) {
		m = maze;

	}

	/*
	 * drives towards exit
	 * if makes it to exit, returns true
	 * if stops for whatever reason, returns false
	 */
	@Override
	public boolean drive2Exit() throws Exception {
		while(!rob.isAtExit()) {
			if(!drive1Step2Exit())
				return false;
		}
		return true;
	}

	// moves robot one step towards the exit according to algorithm
	// returns true if it moves, false if it doesn't
	@Override
	public boolean drive1Step2Exit() throws Exception {
		return wizard_alg();
	}
	
	/*
	 * wizard algorithm
	 * use Maze information to find neighbor close to exit
	 * can use sensors to determine where walls are
	 */
	private boolean wizard_alg() {
		return false;
	}

	// returns total energy consumed
	@Override
	public float getEnergyConsumption() {
		return energy_consumed;
	}

	// returns total number of cells traversed
	@Override
	public int getPathLength() {
		return pathlength;
	}

}
