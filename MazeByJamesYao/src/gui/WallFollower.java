package gui;

import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;

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
		while(!rob.isAtExit()) {
			if(!drive1Step2Exit())
				return false;
		}
		// moving into the exit
		if (rob.canSeeThroughTheExitIntoEternity(Direction.FORWARD)) {
			rob.move(1);
		}
		if (rob.canSeeThroughTheExitIntoEternity(Direction.LEFT)) {
			rob.rotate(Turn.LEFT);
			rob.move(1);
		}
		if (rob.canSeeThroughTheExitIntoEternity(Direction.RIGHT)) {
			rob.rotate(Turn.RIGHT);
			rob.move(1);
		}
		return true;
	}

	@Override
	public boolean drive1Step2Exit() throws Exception {
		return wallFollowerAlg();
	}
	
	/**
	 * algorithm for following left wall
	 * if no left wall, turn left
	 * if left wall, move forward
	 * if wall ahead, move right
	 * if wall right, turn around and move forward
	 * @return true if moves, false if doesn't
	 */
	private boolean wallFollowerAlg() throws Exception {
		// if there is a wall left, check for other walls
		if (rob.distanceToObstacle(Direction.LEFT) == 0) {
			
			// if there is a wall forward, check for other walls
			if (rob.distanceToObstacle(Direction.FORWARD) == 0) {
				// if there is a wall right, rotate around and move forward
				if (rob.distanceToObstacle(Direction.RIGHT) == 0) {
					rob.rotate(Turn.AROUND);
					rob.move(1);
					return true;
				}
				// if no wall right, rotate right and move forward
				else {
					rob.rotate(Turn.RIGHT);
					rob.move(1);
					return true;
				}
			}
			// if no wall forward, move forward
			else {
				rob.move(1);
				return true;
			}
		}
		// if no wall left, rotate left and move forward
		else {
			rob.rotate(Turn.LEFT);
			rob.move(1);
			return true;
		}
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
