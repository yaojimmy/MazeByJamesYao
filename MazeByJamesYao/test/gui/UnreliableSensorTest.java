package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import generation.Maze;
import generation.MazeContainer;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;
import gui.Robot.Direction;

/**
 * Tests methods in UnreliableSensor
 * 
 * @author jamesyao
 *
 */

public class UnreliableSensorTest {

	/**
	 * tests setMaze method
	 */
	@Test
	void testSetMaze() {
		UnreliableSensor s = new UnreliableSensor();
		
		// set up getting maze
		StubOrder sorder = new StubOrder();
		MazeFactory mfactory = new MazeFactory();
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		Maze curmaze = (MazeContainer)sorder.getMaze();
		
		s.setMaze(curmaze);
		assertEquals(curmaze, s.getMaze());
	}
	
	/**
	 * tests setSensorDirection method
	 */
	@Test
	void testSetSensorDirection() {
		UnreliableSensor s = new UnreliableSensor();
		s.setSensorDirection(Direction.FORWARD);
		assertEquals(Direction.FORWARD, s.getSensorDirection());
	}
	
	/**
	 * tests getEnergyConsumptionForSensing method
	 */
	@Test
	void testGetEnergyConsumptionForSensing() {
		UnreliableSensor s = new UnreliableSensor();
		assertEquals(1, s.getEnergyConsumptionForSensing());
	}
	
	/**
	 * tests that operational is true by default
	 */
	@Test
	void testIsOperational() {
		UnreliableSensor s = new UnreliableSensor();
		assertEquals(s.isOperational(), true);
	}
	/**
	 * tests setOperational
	 */
	@Test
	void testSetOperational() {
		UnreliableSensor s = new UnreliableSensor();
		s.setOperational(false);
		assertEquals(s.isOperational(), false);
	}
	
	/**
	 * tests distanceToObstacle method
	 */
	@Test
	void testDistanceToObstacle() {
		// set up getting maze
		StubOrder sorder = new StubOrder();
		MazeFactory mfactory = new MazeFactory();
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		Maze curmaze = (MazeContainer)sorder.getMaze();
		
		UnreliableRobot r = new UnreliableRobot();
		UnreliableSensor s = new UnreliableSensor();
		Controller c = new Controller();
		WallFollower w = new WallFollower();
		c.setRobotAndDriver(r, w);
		r.setFSensor(s);
		c.switchFromGeneratingToPlaying(curmaze);
		float[] battery_level = {0};
		battery_level[0] = r.getBatteryLevel();
		s.setMaze(curmaze);
		try {
			assertTrue(s.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level) >= 0);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	/**
	 * tests distanceToObstacle method when it throws an exception when it's not operational
	 */
	@Test
	void testDistanceToObstacleNotOperational() {
		// set up getting maze
		StubOrder sorder = new StubOrder();
		MazeFactory mfactory = new MazeFactory();
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		Maze curmaze = (MazeContainer)sorder.getMaze();
		
		UnreliableRobot r = new UnreliableRobot();
		UnreliableSensor s = new UnreliableSensor();
		Controller c = new Controller();
		WallFollower w = new WallFollower();
		c.setRobotAndDriver(r, w);
		s.setOperational(false);
		s.setMaze(curmaze);
		r.setFSensor(s);
		float[] battery_level = {0};
		battery_level[0] = r.getBatteryLevel();
		try {
			// fails if it senses a distance to obstacle
			assertFalse(s.distanceToObstacle(c.getCurrentPosition(), c.getCurrentDirection(), battery_level) >= 0);
		} catch (Exception e) {
			// passes if exception is thrown
			assertTrue(true);
		}
	}
	
	/**
	 * tests startFailureAndRepairProcess method
	 */
	@Test
	void testStartFailureAndRepairProcess() {
		
	}
	
	/**
	 * tests stopFailureAndRepairProcess method
	 */
	@Test
	void testStopFailureAndRepairProcess() {
		
	}
	
}
