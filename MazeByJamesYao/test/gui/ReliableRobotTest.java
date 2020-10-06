package gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReliableRobotTest {

	@Test
	void testSetController() {
		// tests if Set Controller works
		ReliableRobot r = new ReliableRobot();
		Controller c = new Controller();
		r.setController(c);
		assertEquals(r.getController(), c);
	}
}
