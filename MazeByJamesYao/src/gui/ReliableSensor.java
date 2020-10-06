package gui;

import generation.CardinalDirection;
import generation.Maze;
import generation.Wallboard;
import gui.Robot.Direction;

public class ReliableSensor implements DistanceSensor {
	private Maze m;
	private Direction d;
	private float energy_req = 1;

	public ReliableSensor() {
		// nothing needed
	}
	@Override
	public int distanceToObstacle(int[] currentPosition, CardinalDirection currentDirection, float[] powersupply)
			throws Exception {
		int curdist = 0;
		int x = currentPosition[0];
		int y = currentPosition[1];
		while (m.getFloorplan().hasNoWall(x, y, currentDirection)) {
			if (currentDirection == CardinalDirection.North) {
				if (y - 1 < 0)
					return Integer.MAX_VALUE;
				y = y - 1;
				curdist++;
			}
			else if (currentDirection == CardinalDirection.South) {
				if (y + 1 >= m.getHeight())
					return Integer.MAX_VALUE;
				y = y + 1;
				curdist++;
			}
			else if (currentDirection == CardinalDirection.East) {
				if (x + 1 >= m.getWidth())
					return Integer.MAX_VALUE;
				x = x + 1;
				curdist++;
			}
			else if (currentDirection == CardinalDirection.West) {
				if (x - 1 < 0)
					return Integer.MAX_VALUE;
				x = x - 1;
				curdist++;
			}
				
		}
		return curdist;
	}

	@Override
	public void setMaze(Maze maze) {
		m = maze;

	}

	public Maze getMaze() {
		return m;
	}
	
	@Override
	public void setSensorDirection(Direction mountedDirection) {
		d = mountedDirection;
	}
	
	public Direction getSensorDirection() {
		return d;
	}

	@Override
	public float getEnergyConsumptionForSensing() {
		return energy_req;
	}

	@Override
	public void startFailureAndRepairProcess(int meanTimeBetweenFailures, int meanTimeToRepair)
			throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stopFailureAndRepairProcess() throws UnsupportedOperationException {
		// TODO Auto-generated method stub

	}

}
