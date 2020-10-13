package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import generation.Maze;
import generation.MazeContainer;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;
import gui.Robot.Direction;

public class UnreliableRobotTest {

	/**
	 * tests setting Controller
	 */
	@Test
	void testSetController() {
		UnreliableRobot r = new UnreliableRobot();
		Controller c = new Controller();
		r.setController(c);
		assertEquals(r.getController(), c);
	}
	
	/**
	 * tests setting sensors
	 */
	@Test
	void testSetSensor() {
		UnreliableRobot r = new UnreliableRobot();
		UnreliableSensor s = new UnreliableSensor();
		r.setFSensor(s);
		assertEquals(r.getFSensor(), s);
	}
	
	/**
	 * tests distanceToObstacle with sensor in tested direction being not operational
	 * Should get distance with a different sensor and return the same result
	 */
	/*
	@Test
	void testDistanceToObstacleNotOperationalSensor() {
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
		
		// set up robot, controller, robotdriver, and sensors
		Controller c = new Controller();
		WallFollower w = new WallFollower();
		UnreliableRobot r = new UnreliableRobot();
		w.setMaze(curmaze);
		c.states[2].setMazeConfiguration(curmaze);
		w.setRobot(r);
		c.setRobotAndDriver(r, w);
		r.setController(c);
		
		// test using sensor
		int firstdistance = r.distanceToObstacle(Direction.FORWARD);
		UnreliableSensor fsensor = new UnreliableSensor();
		fsensor.setSensorDirection(Direction.FORWARD);
		fsensor.setMaze(curmaze);
		r.setFSensor(fsensor);
		fsensor.setOperational(false);
		assertEquals(firstdistance, r.distanceToObstacle(Direction.FORWARD));
	}
	*/
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
