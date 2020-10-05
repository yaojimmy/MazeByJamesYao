package gui;

import generation.CardinalDirection;
import gui.Constants.UserInput;

public class ReliableRobot implements Robot {
	Controller c;
	DistanceSensor rsensor;
	DistanceSensor lsensor;
	DistanceSensor fsensor;
	DistanceSensor bsensor;
	float[] battery_level;
	int odometer_num;
	float distance_sense = 1;
	float quarter_turn = 3;
	float step_forward = 6;
	float jump_wall = 40;

	public ReliableRobot() {
		
		// instantiate sensors
		rsensor = new ReliableSensor();
		lsensor = new ReliableSensor();
		fsensor = new ReliableSensor();
		bsensor = new ReliableSensor();
		
		// set sensor directions
		rsensor.setSensorDirection(Direction.RIGHT);
		lsensor.setSensorDirection(Direction.LEFT);
		fsensor.setSensorDirection(Direction.FORWARD);
		rsensor.setSensorDirection(Direction.BACKWARD);
		
		// set battery level to max
		setBatteryLevel(3500);
		
		// set odometer number
		resetOdometer();
	}
	
	@Override
	public void setController(Controller controller) {
		c = controller;

	}

	@Override
	public int[] getCurrentPosition() throws Exception {
		return c.getCurrentPosition();
	}

	@Override
	public CardinalDirection getCurrentDirection() {
		return c.getCurrentDirection();
	}

	@Override
	public float getBatteryLevel() {
		return battery_level[0];
	}

	@Override
	public void setBatteryLevel(float level) {
		battery_level = new float[1];
		battery_level[0] = level;

	}

	@Override
	public float getEnergyForFullRotation() {
		return quarter_turn * 4;
	}

	@Override
	public float getEnergyForStepForward() {
		try {
			if (0 == fsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level)) {
				return jump_wall;
			}
		} catch (Exception e) {
			return step_forward;
		}
		return step_forward;
	}

	@Override
	public int getOdometerReading() {
		return odometer_num;
	}

	@Override
	public void resetOdometer() {
		odometer_num = 0;

	}

	@Override
	public void rotate(Turn turn) {
		if (turn == Turn.LEFT) {
			c.keyDown(UserInput.Left, 0);
			battery_level[0] = battery_level[0] - quarter_turn;
		}
		else if (turn == Turn.RIGHT) {
			c.keyDown(UserInput.Right, 0);
			battery_level[0] = battery_level[0] - quarter_turn;
		}
		else if (turn == Turn.AROUND) {
			c.keyDown(UserInput.Down, 0);
			battery_level[0] = battery_level[0] - (2 *quarter_turn);
		}
		
	}

	@Override
	public void move(int distance) {
		for (int i = 0; i < distance; i++) {
			if (hasStopped() == true)
				break;
			else {
				c.keyDown(UserInput.Up, 0);
				battery_level[0] = battery_level[0] - step_forward;
			}
		}

	}

	@Override
	public void jump() {
		// jumps over wall
		// energy already consumed with move
		c.keyDown(UserInput.Jump, 0);

	}

	@Override
	public boolean isAtExit() {
		try {
			return c.getMazeConfiguration().getFloorplan().isExitPosition(getCurrentPosition()[0], getCurrentPosition()[1]);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean isInsideRoom() {
		try {
			return c.getMazeConfiguration().getFloorplan().isInRoom(getCurrentPosition()[0], getCurrentPosition()[1]);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean hasStopped() {
		// checks if battery is depleted or there is an obstacle in front
		if (battery_level[0] <= 0)
			return true;
		try {
			if (fsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level) == 0)
			return true;
		} catch (Exception e) {
			return true;
		}
		return false;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		if (direction == Direction.FORWARD) {
			try {
				return fsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level);
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		if (direction == Direction.BACKWARD) {
			try {
				return bsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level);
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		if (direction == Direction.LEFT) {
			try {
				return lsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level);
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		if (direction == Direction.RIGHT) {
			try {
				return rsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level);
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		return 0;
	}

	@Override
	public boolean canSeeThroughTheExitIntoEternity(Direction direction) throws UnsupportedOperationException {
		if (distanceToObstacle(direction) == Integer.MAX_VALUE) {
			return true;
		}
		return false;
	}

	@Override
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

}
