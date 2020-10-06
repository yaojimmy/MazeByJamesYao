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
		
		// set battery level to max
		setBatteryLevel(3500);
		
		// set odometer number
		resetOdometer();
	}
	
	@Override
	public void setController(Controller controller) {
		c = controller;
		
		// set sensor mazes
		rsensor.setMaze(controller.getMazeConfiguration());
		lsensor.setMaze(controller.getMazeConfiguration());
		fsensor.setMaze(controller.getMazeConfiguration());
		bsensor.setMaze(controller.getMazeConfiguration());

	}
	
	public Controller getController() {
		return c;
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
				battery_level[0] = battery_level[0] - step_forward;
				if (hasStopped() == true)
					break;
				c.keyDown(UserInput.Up, 0);
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
			return false;
		}
		return false;
	}

	@Override
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		CardinalDirection thisdar = c.getCurrentDirection();
		if (direction == Direction.FORWARD) {
			battery_level[0] = battery_level[0] - distance_sense;
			try {
				return fsensor.distanceToObstacle(c.getCurrentPosition(), thisdar, battery_level);
			} catch (Exception e) {
				return 0;
			}
		}
		else if (direction == Direction.BACKWARD) {
			battery_level[0] = battery_level[0] - distance_sense;
			try {
				thisdar = thisdar.rotateClockwise();
				thisdar = thisdar.rotateClockwise();
				return bsensor.distanceToObstacle(c.getCurrentPosition(), thisdar, battery_level);
			} catch (Exception e) {
				return 0;
			}
		}
		else if (direction == Direction.LEFT) {
			battery_level[0] = battery_level[0] - distance_sense;
			try {
				thisdar = thisdar.rotateClockwise();
				return lsensor.distanceToObstacle(c.getCurrentPosition(), thisdar, battery_level);
			} catch (Exception e) {
				return 0;
			}
		}
		else if (direction == Direction.RIGHT) {
			battery_level[0] = battery_level[0] - distance_sense;
			try {
				thisdar = thisdar.rotateClockwise();
				thisdar = thisdar.rotateClockwise();
				thisdar = thisdar.rotateClockwise();
				return rsensor.distanceToObstacle(c.getCurrentPosition(), thisdar, battery_level);
			} catch (Exception e) {
				return 0;
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
