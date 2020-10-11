package gui;

import generation.Maze;

/**
 * Solves maze by following the left wall
 * 
 * @author jamesyao
 *
 */

public class WallFollower implements RobotDriver {
	Robot rob;
	Maze m;
	float energy_consumed;
	int pathlength;

	public WallFollower() {
		// nothing necessary
	}

	@Override
	public void setRobot(Robot r) {
		rob = r;

	}
	
	public Robot getRobot() {
		return rob;
	}

	@Override
	public void setMaze(Maze maze) {
		m = maze;

	}
	
	public Maze getMaze() {
		return m;
	}

	@Override
	public boolean drive2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * algorithm for following left wall
	 * @return true if moves, false if doesn't
	 */
	private boolean wallFollowerAlg() {
		// TODO Implement algorithm
		return false;
	}

	@Override
	public float getEnergyConsumption() {
		return energy_consumed;
	}

	@Override
	public int getPathLength() {
		// TODO Auto-generated method stub
		return pathlength;
	}

}
