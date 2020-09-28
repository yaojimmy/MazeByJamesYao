package generation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import generation.Order.Builder;

class MazeFactoryTest {
	
	MazeFactory mfactory;
	StubOrder sorder;

	@Test
	void setUp() {
		// template for setting up for tests
		// initialize a MazeFactory object to create Maze and run tests on
		mfactory = new MazeFactory();
		// instantiate a StubOrder
		sorder = new StubOrder();
		// set StubOrder values
		sorder.setBuilder(Builder.Prim);
		sorder.setPerfect(false);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		// order it
		mfactory.order(sorder);
		// waittildelivered
		mfactory.waitTillDelivered();
	}
	
	@Test
	void testNoIsolations() {
		// tests there are no isolations in maze
	}
	
	@Test
	void testExitReachableEverywhere() {
		// finite distance to exit from all positions
	}
	
	@Test
	void testAddRooms() {
		// tests adding a room
	}
	
	@Test
	void testNoLoops() {
		// tests if there are any loops in a "perfect" maze
	}
	
	@Test
	void testSingleExit() {
		// tests if there is only one exit
	}

}
