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
		Wallboard w = new Wallboard(currentPosition[0], currentPosition[1], currentDirection);
		while (m.getFloorplan().hasNoWall(w.getX(), w.getY(), w.getDirection())) {
			if (w.getDirection() == CardinalDirection.North)
				w.setLocationDirection(w.getX(), w.getY()-1, w.getDirection());
			else if (w.getDirection() == CardinalDirection.South)
				w.setLocationDirection(w.getX(), w.getY()+1, w.getDirection());
			else if (w.getDirection() == CardinalDirection.East)
				w.setLocationDirection(w.getX()+1, w.getY(), w.getDirection());
			else if (w.getDirection() == CardinalDirection.West)
				w.setLocationDirection(w.getX()-1, w.getY(), w.getDirection());
			curdist++;
				
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
