package gui;

import generation.CardinalDirection;

/**
 * Extends all functionality of ReliableSensor, with an added failure and repair process
 * Only can use method if not in operational state
 * 
 * @author jamesyao
 *
 */
public class UnreliableSensor extends ReliableSensor implements Runnable, DistanceSensor {
	private boolean operational;
	private int timeToRepair;
	private int timeBetweenFailures;
	Thread failureRepair;

	public UnreliableSensor() {
		// starts in operational state
		super();
		operational = true;
	}
	
	/**
	 * 
	 * @param isOperational sets operational state
	 */
	public void setOperational(boolean isOperational) {
		operational = isOperational;
	}
	
	/**
	 * 
	 * @return returns operational state
	 */
	public boolean isOperational() {
		return operational;
	}
	
	/**
	 * @return returns distance to obstacle if sensor has not failed
	 */
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		if (operational) {
			return super.distanceToObstacle(currentPosition, currentDirection, powersupply);
		}
		else {
			throw new Exception("sensorfailure");
		}
	}
	/**
	 * goes through failure and repair process
	 * @param meanTimeBetweenFailures time between failures
	 * @param meanTimeToRepair time before sensor becomes operational again
	 */
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		if (failureRepair == null) {
			failureRepair = new Thread(this);
			operational = true;
			timeToRepair = meanTimeToRepair;
			timeBetweenFailures = meanTimeBetweenFailures;
			failureRepair.start();
		}
	}
	
	/**
	 * ends failure and repair process
	 */
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		operational = true;
		failureRepair.interrupt();
	}

	@Override
	public void run() {
		while (true) {
			try {
				operational = false;
				Thread.sleep(timeToRepair * 1000);
			} catch (InterruptedException e) {}
			try {
				operational = true;
				Thread.sleep(timeBetweenFailures * 1000);
			} catch (InterruptedException e) {}
		}
	}

}
