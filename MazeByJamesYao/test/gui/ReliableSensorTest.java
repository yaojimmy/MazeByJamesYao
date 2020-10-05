package gui;

import org.junit.jupiter.api.Test;

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
}
