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
		// TODO Auto-generated method stub

	}
	
	/**
	 * @param direction ends failure and repair process for sensor in designated direction
	 */
	public void stopFailureAndRepairProcess(Direction direction) throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
