package gui;

/**
 * Extends all functionality of ReliableSensor, with an added failure and repair process
 * Only can use method if not in operational state
 * 
 * @author jamesyao
 *
 */
public class UnreliableSensor extends ReliableSensor implements Runnable {
	private boolean operational;

	public UnreliableSensor() {
		// starts in operational state
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
	public boolean getOperational() {
		return operational;
	}
	
	/**
	 * goes through failure and repair process
	 * @param meanTimeBetweenFailures time between failures
	 * @param meanTimeToRepair time before sensor becomes operational again
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
