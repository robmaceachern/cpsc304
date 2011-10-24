package ca.ubc.cpsc304.r3;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClerkTest {
	
	private Clerk clerk1 = new Clerk();

	@Test
	public void testFuckThePolice() {
		assertTrue(clerk1.fuckThePolice().toLowerCase().contains("fuck the police"));
	}

}
