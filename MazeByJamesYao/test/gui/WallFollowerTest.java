package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import generation.Maze;
import generation.MazeContainer;
import generation.MazeFactory;
import generation.StubOrder;
import generation.Order.Builder;

/**
 * Tests individual WallFollower methods
 * 
 * @author jamesyao
 *
 */

public class WallFollowerTest {

	/**
	 * tests setRobot method by seeing if getRobot returns same Robot
	 */
	@Test
	void testSetRobot() {
		Robot r = new ReliableRobot();
		WallFollower w = new WallFollower();
		w.setRobot(r);
		assertEquals(w.getRobot(), r);
	}
	
	/**
	 * tests setMaze method by seeing if getMaze returns same Maze
	 */
	@Test
	void testSetMaze() {
		StubOrder sorder = new StubOrder();
		MazeFactory mfactory = new MazeFactory();
		WallFollower w = new WallFollower();
		
		// setup
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		
		// maze being compared
		Maze curmaze = (MazeContainer)sorder.getMaze();
		w.setMaze(curmaze);
		assertEquals(w.getMaze(), curmaze);
	}
	
	/**
	 * tests getEnergyConsumption method when energy hasn't been consumed
	 */
	@Test
	void testGetEnergyConsumptionNone() {
		Robot r = new ReliableRobot();
		WallFollower w = new WallFollower();
		w.setRobot(r);
		assertEquals(w.getEnergyConsumption(), 0);
	}
	
	/**
	 * tests getPathLength method when not moved
	 */
	@Test
	void testGetPathLengthNone() {
		Robot r = new ReliableRobot();
		WallFollower w = new WallFollower();
		w.setRobot(r);
		assertEquals(w.getPathLength(), 0);
	}
	
	/**
	 * tests drive1Step2Exit moves
	 */
	@Test
	void test1Step2Exit() {
		// setup
		StubOrder sorder = new StubOrder();
		MazeFactory mfactory = new MazeFactory();
		
		// get maze
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		Maze curmaze = (MazeContainer)sorder.getMaze();
		
		// setup controller, driver, and robot
		Controller c = new Controller();
		WallFollower w = new WallFollower();
		ReliableRobot r = new ReliableRobot();
		w.setMaze(curmaze);
		c.states[2].setMazeConfiguration(curmaze);
		w.setRobot(r);
		c.setRobotAndDriver(r, w);
		r.setController(c);
		try {
			assertTrue(w.drive1Step2Exit());
		} catch (Exception e) {
		}
	}
	
	/**
	 * tests drive2Exit moves
	 */
	@Test
	void testDrive2Exit() {
		// setup
		StubOrder sorder = new StubOrder();
		MazeFactory mfactory = new MazeFactory();
		
		// get maze
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		Maze curmaze = (MazeContainer)sorder.getMaze();
		
		// setup controller, driver, and robot
		Controller c = new Controller();
		WallFollower w = new WallFollower();
		ReliableRobot r = new ReliableRobot();
		w.setMaze(curmaze);
		c.states[2].setMazeConfiguration(curmaze);
		w.setRobot(r);
		c.setRobotAndDriver(r, w);
		r.setController(c);
		try {
			assertTrue(w.drive2Exit());
		} catch (Exception e) {
		}
	}

}
