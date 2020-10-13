package gui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import generation.Maze;
import generation.MazeContainer;
import generation.MazeFactory;
import generation.StubOrder;
import gui.Robot.Turn;
import generation.Order.Builder;

/**
 * Tests individual WallFollower methods
 * 
 * @author jamesyao
 *
 */

public class WallFollowerTest {

	/**
	 * tests setRobot method
	 */
	@Test
	void testSetRobot() {
		Robot r = new ReliableRobot();
		WallFollower w = new WallFollower();
		w.setRobot(r);
		assertEquals(w.getRobot(), r);
	}
	
	/**
	 * tests setMaze method
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
	 * tests getEnergyConsumption method when energy has been consumed
	 */
	@Test
	void testGetEnergyConsumptionMove() {
		// setup
		StubOrder sorder = new StubOrder();
		MazeFactory mfactory = new MazeFactory();
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		Maze curmaze = (MazeContainer)sorder.getMaze();
		
		Robot r = new ReliableRobot();
		WallFollower w = new WallFollower();
		Controller c = new Controller();
		c.setBuilder(Builder.Eller);
		c.setRobotAndDriver(r, w);
		w.setRobot(r);
		c.currentState.setMazeConfiguration(curmaze);
		w.setMaze(curmaze);
		r.setController(c);
		c.switchFromGeneratingToPlaying(curmaze);
		try {
			w.drive1Step2Exit();
		} catch (Exception e) {}
		assertTrue(w.getEnergyConsumption() > 0);
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

}
