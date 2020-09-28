package generation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import generation.Order.Builder;

class MazeFactoryTest {

	@Test
	void setUp() {
		// initialize a MazeFactory object to create Maze and run tests on
		MazeFactory mfactory = new MazeFactory();
		// instantiate a StubOrder
		StubOrder sorder = new StubOrder();
		sorder.setBuilder(Builder.Eller);
		// order that StubOrder
		mfactory.order(sorder);
		// waitTillDelivered
		mfactory.waitTillDelivered();
	}
	
	@Test
	void testNoIsolations() {
		// tests if there are any cells that cannot be reached
	}
	
	@Test
	void testNoLoops() {
		// tests if there are any loops
	}
	
	void testSingleExit() {
		// tests if there is only one exit
	}
	
	void testReachableExit() {
		// tests if there is a reachable exit
	}

}
