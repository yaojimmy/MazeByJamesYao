package gui;

import generation.CardinalDirection;
import generation.Maze;
import gui.Robot.Direction;
import gui.Robot.Turn;

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
		try {
			int[] position = m.getNeighborCloserToExit(rob.getCurrentPosition()[0], rob.getCurrentPosition()[1]);
			// find direction of position
			CardinalDirection dir;
			if (position[0] == rob.getCurrentPosition()[0]+1) {
				dir = CardinalDirection.East;
			}
			else if (position[0] == rob.getCurrentPosition()[0]-1) {
				dir = CardinalDirection.West;
			}
			else if (position[1] == rob.getCurrentPosition()[1]+1) {
				dir = CardinalDirection.South;
			}
			else {
				dir = CardinalDirection.North;
			}
			
			while (dir != rob.getCurrentDirection()) {
				rob.rotate(Turn.LEFT);
			}
			if (rob.distanceToObstacle(Direction.FORWARD) == 0) {
				rob.jump();
			}
			else {
				rob.move(1);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
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
