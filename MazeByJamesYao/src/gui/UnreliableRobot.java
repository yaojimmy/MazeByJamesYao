package gui;

import gui.Robot.Direction;

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

	public UnreliableRobot() {
		super();
	}
	
	/**
	 * @param direction starts failure and repair process for sensor in designated direction
	 * @param meanTimeBetweenFailures time between failures
	 * @param meanTimeToRepair time before sensor is allowed to be operational again
	 */
	public void startFailureAndRepairProcess(Direction direction, int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		Thread failureRepair = new Thread(this);
		timeToRepair = meanTimeToRepair;
		timeBetweenFailures = meanTimeBetweenFailures;
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

	}

	/**
	 * runs until end of maze
	 */
	@Override
	public void run() {
		while(!super.isAtExit()) {
			for (Direction dir : Direction.values()) {
				if (dir == Direction.FORWARD) {
					this.getFSensor().startFailureAndRepairProcess(timeBetweenFailures, timeToRepair);
				}
				if (dir == Direction.LEFT) {
					this.getLSensor().startFailureAndRepairProcess(timeBetweenFailures, timeToRepair);
				}
				if (dir == Direction.RIGHT) {
					this.getRSensor().startFailureAndRepairProcess(timeBetweenFailures, timeToRepair);
				}
				if (dir == Direction.BACKWARD) {
					this.getBSensor().startFailureAndRepairProcess(timeBetweenFailures, timeToRepair);
				}
			}
		}
		
	}

}
