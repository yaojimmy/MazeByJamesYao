package gui;

import org.junit.jupiter.api.Test;

import generation.CardinalDirection;
import gui.Robot.Direction;

import static org.junit.jupiter.api.Assertions.*;

class ReliableSensorTest {
	ReliableSensor s;
	Controller c;
	
	@Test
	void testSetMaze() {
		c = new Controller();
		s = new ReliableSensor();
		s.setMaze(c.getMazeConfiguration());
		assertEquals(s.getMaze(), c.getMazeConfiguration());
	}
	
	@Test
	void testSensorDirection() {
		c = new Controller();
		s = new ReliableSensor();
		s.setSensorDirection(Direction.FORWARD);
		assertEquals(s.getSensorDirection(), Direction.FORWARD);
	}
	
	@Test
	void testGetEnergyConsumptionForSensing() {
		c = new Controller();
		s = new ReliableSensor();
		assertEquals(s.getEnergyConsumptionForSensing(), (float)1);
	}
	
	@Test
	void testDistanceToObstacle() {
		c = new Controller();
		s = new ReliableSensor();
		s.setMaze(c.getMazeConfiguration());
		s.setSensorDirection(Direction.FORWARD);
		int[] position = {0, 0};
		float[] powersupply = {3500};
		try {
			assertEquals(0, s.distanceToObstacle(position, CardinalDirection.West, powersupply));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
