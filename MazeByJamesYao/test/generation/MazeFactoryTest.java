package generation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import generation.Order.Builder;

class MazeFactoryTest {
	
	MazeFactory mfactory = new MazeFactory();
	StubOrder sorder = new StubOrder();

	@Test
	void setUp() {
		// my copy-paste template for setting up for tests
		// initialize a MazeFactory object to create Maze and run tests on
		// instantiate a StubOrder
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
		// setup
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(false);
		sorder.setSeed(2);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		
		Maze cur_maze = (MazeContainer)sorder.getMaze();
		// if there is an isolation, the distance to the exit will be infinity
		assertFalse(cur_maze.getMazedists().getMaxDistance() == Integer.MAX_VALUE);
	}
	
	@Test
	void testNoIsolationsRectangle() {
		// tests no isolations for rectangle maze
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(2);
		sorder.setSkillLevel(4);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		
		Maze cur_maze = (MazeContainer)sorder.getMaze();
		// if there is an isolation, the distance to the exit will be infinity
		assertFalse(cur_maze.getMazedists().getMaxDistance() == Integer.MAX_VALUE);
	}
	
	@Test
	void testIfPerfect() {
		// tests if maze is perfect
		// setup
		sorder.setBuilder(Builder.Eller);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(1);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		
		Maze cur_maze = (MazeContainer)sorder.getMaze();
		Floorplan f = cur_maze.getFloorplan();
		int numwalls = cur_maze.getWidth() + cur_maze.getHeight() - 1;
		for (int x = 0; x < cur_maze.getWidth(); x++) {
			for (int y = 0; y < cur_maze.getHeight(); y++) {
				if(f.hasWall(x, y, CardinalDirection.East))
					numwalls++;
				if(f.hasWall(x, y, CardinalDirection.South))
					numwalls++;
			}
		}
		assertTrue(numwalls == (cur_maze.getWidth() + 1) * (cur_maze.getHeight() + 1) - 1);
	}
	
	@Test
	void testIfPerfectforRectangle() {
		// tests if maze is perfect for rectangle maze
		// setup
		sorder.setBuilder(Builder.Prim);
		sorder.setPerfect(true);
		sorder.setSeed(0);
		sorder.setSkillLevel(4);
		mfactory.order(sorder);
		mfactory.waitTillDelivered();
		
		Maze cur_maze = (MazeContainer)sorder.getMaze();
		Floorplan f = cur_maze.getFloorplan();
		int numwalls = cur_maze.getWidth() + cur_maze.getHeight();
		for (int x = 0; x < cur_maze.getWidth(); x++) {
			for (int y = 0; y < cur_maze.getHeight(); y++) {
				if(f.hasWall(x, y, CardinalDirection.East))
					numwalls++;
				if(f.hasWall(x, y, CardinalDirection.South))
					numwalls++;
			}
		}
		assertTrue(numwalls == (cur_maze.getWidth() + 1) * (cur_maze.getHeight() + 1) - 1);
	}

}
