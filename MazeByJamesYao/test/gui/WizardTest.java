package gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WizardTest {

	@Test
	void testSetRobot() {
		Robot r = new ReliableRobot();
		Wizard w = new Wizard();
		w.setRobot(r);
		assertEquals(w.getRobot(), r);
	}
}
