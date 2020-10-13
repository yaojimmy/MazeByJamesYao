package gui;

/**
 * Extends all functionality of ReliableRobot, with some sensors being unreliable
 * Uses ReliableRobot's setSensor methods to set unreliable sensors
 * 
 * @author jamesyao
 *
 */

public class UnreliableRobot extends ReliableRobot implements Robot, Runnable {
	private int timeBetweenFailures;
	private int timeToRepair;
	private Direction failingSensorDir;
	private Thread failureRepair;

	public UnreliableRobot() {
		super();
	}
	
	/**
	 * if the sensor in direction throws exception, identify a working sensor
	 * if no working sensor, wait
	 * else: rotate to sensor, store distance to obstacle, then rotate back to original position
	 * @return stored distance to obstacle
	 */
	public int distanceToObstacle(Direction direction) throws UnsupportedOperationException {
		
		// stores distance since there is no need to sense multiple times
		int distance = super.distanceToObstacle(direction);
		
		// if sensor did not work, identify a working sensor
		if (distance == Integer.MAX_VALUE) {
			Direction dir = identifyWorkingSensor();
			// calculate number of left rotations needed to move sensor to sensing direction
			int numRotations = relateDirections(direction, dir);
			
			// sense, rotate back to original position, then return recorded distance
			if (numRotations == 0) {
				return distance;
			}
			else if (numRotations == 1) {
				super.rotate(Turn.LEFT);
				distance = super.distanceToObstacle(dir);
				super.rotate(Turn.RIGHT);
				return distance;
			}
			else if (numRotations == 2) {
				super.rotate(Turn.AROUND);
				distance = super.distanceToObstacle(dir);
				super.rotate(Turn.AROUND);
				return distance;
			}
			else {
				super.rotate(Turn.RIGHT);
				distance = super.distanceToObstacle(dir);
				super.rotate(Turn.LEFT);
				return distance;
			}
			
			
			// else rotate so sensor is facing the direction that needs to be sensed
			// sense, then rotate back
		}
		// if sensor does work, return working value
		else {
			return distance;
		}
	}
	
	/**
	 * 
	 * @param sensingDir direction that needs to be sensed
	 * @param sensorDir direction of working sensor
	 * @return number of times robot need to rotate left to get sensorDir to sensingDir
	 */
	private int relateDirections(Direction sensingDir, Direction sensorDir) {
		if (sensorDir == Direction.FORWARD) {
			if (sensingDir == Direction.FORWARD)
				return 0;
			else if (sensingDir == Direction.LEFT)
				return 1;
			else if (sensingDir == Direction.BACKWARD)
				return 2;
			else
				return 3;
		}
		else if (sensorDir == Direction.LEFT) {
			if (sensingDir == Direction.LEFT)
				return 0;
			else if (sensingDir == Direction.BACKWARD)
				return 1;
			else if (sensingDir == Direction.RIGHT)
				return 2;
			else
				return 3;
		}
		else if (sensorDir == Direction.BACKWARD) {
			if (sensingDir == Direction.BACKWARD)
				return 0;
			else if (sensingDir == Direction.RIGHT)
				return 1;
			else if (sensingDir == Direction.FORWARD)
				return 2;
			else
				return 3;
		}
		else {
			if (sensingDir == Direction.RIGHT)
				return 0;
			else if (sensingDir == Direction.FORWARD)
				return 1;
			else if (sensingDir == Direction.LEFT)
				return 2;
			else
				return 3;
		}
	}
	
	/**
	 * goes through directions
	 * if sensor in direction is reliable or operational, return direction
	 * @return direction of working sensor
	 */
	private Direction identifyWorkingSensor() {
		for (Direction dir: Direction.values()) {
			if (super.distanceToObstacle(dir) != Integer.MAX_VALUE)
				return dir;
		}
		try {
			// Strategy B: wait until a working sensor becomes available
			Thread.sleep(300);
		} catch (InterruptedException e) {}
		return identifyWorkingSensor();
	}
	
	/**
	 * @param direction starts failure and repair process for sensor in designated direction
	 * @param meanTimeBetweenFailures time between failures
	 * @param meanTimeToRepair time before sensor is allowed to be operational again
	 */
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		failureRepair = new Thread(this);
		timeToRepair = meanTimeToRepair;
		timeBetweenFailures = meanTimeBetweenFailures;
		failingSensorDir = direction;
		failureRepair.start();

	}
	
	/**
	 * @param direction ends failure and repair process for sensor in designated direction
	 */
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		if (direction == Direction.FORWARD) {
			this.getFSensor().stopFailureAndRepairProcess();
		}
		if (direction == Direction.LEFT) {
			this.getFSensor().stopFailureAndRepairProcess();
		}
		if (direction == Direction.RIGHT) {
			this.getFSensor().stopFailureAndRepairProcess();
		}
		if (direction == Direction.BACKWARD) {
			this.getFSensor().stopFailureAndRepairProcess();
		}
		failureRepair.interrupt();

	}

	/**
	 * runs until end of maze
	 */
	@Override
	public void run() {
		while(!super.isAtExit()) {
			if (failingSensorDir == Direction.FORWARD) {
				this.getFSensor().startFailureAndRepairProcess(timeBetweenFailures, timeToRepair);
			}
			if (failingSensorDir == Direction.LEFT) {
				this.getLSensor().startFailureAndRepairProcess(timeBetweenFailures, timeToRepair);
			}
			if (failingSensorDir == Direction.RIGHT) {
				this.getRSensor().startFailureAndRepairProcess(timeBetweenFailures, timeToRepair);
			}
			if (failingSensorDir == Direction.BACKWARD) {
				this.getBSensor().startFailureAndRepairProcess(timeBetweenFailures, timeToRepair);
			}
		}
		
	}

}
