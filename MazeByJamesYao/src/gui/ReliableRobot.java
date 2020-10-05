package gui;

import generation.CardinalDirection;
import gui.Constants.UserInput;

public class ReliableRobot implements Robot {
	private Controller c;
	private DistanceSensor rsensor;
	private DistanceSensor lsensor;
	private DistanceSensor fsensor;
	private DistanceSensor bsensor;
	private float[] battery_level = new float[1];
	private int odometer_num;
	private float distance_sense = 1;
	private float quarter_turn = 3;
	private float step_forward = 6;
	private float jump_wall = 40;

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
		bsensor.setSensorDirection(Direction.BACKWARD);
		
		// set sensor mazes
		rsensor.setMaze(c.getMazeConfiguration());
		lsensor.setMaze(c.getMazeConfiguration());
		fsensor.setMaze(c.getMazeConfiguration());
		bsensor.setMaze(c.getMazeConfiguration());
		
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
		battery_level[0] = level;

	}

	@Override
	public float getEnergyForFullRotation() {
		return quarter_turn * 4;
	}

	@Override
	public float getEnergyForStepForward() {
		if (distanceToObstacle(Direction.FORWARD) == 0) {
			return jump_wall;
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
			battery_level[0] = battery_level[0] - distance_sense;
			try {
				return fsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level);
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		else if (direction == Direction.BACKWARD) {
			battery_level[0] = battery_level[0] - distance_sense;
			try {
				return bsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level);
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		else if (direction == Direction.LEFT) {
			try {
				battery_level[0] = battery_level[0] - distance_sense;
				return lsensor.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level);
			} catch (Exception e) {
				return Integer.MAX_VALUE;
			}
		}
		else if (direction == Direction.RIGHT) {
			try {
				battery_level[0] = battery_level[0] - distance_sense;
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
