package gui;

/**
 * Extends all functionality of ReliableSensor, with an added failure and repair process
 * Only can use method if not in operational state
 * 
 * @author jamesyao
 *
 */
public class UnreliableSensor extends ReliableSensor implements DistanceSensor {
	private boolean operational;

	public UnreliableSensor() {
		// starts in operational state
		operational = true;
	}
	
	/**
	 * goes through failure and repair process
	 * @param time between failures
	 * @param time before operational again
	 */
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}
	
	/**
	 * ends failure and repair process
	 */
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

}
