package test.dggs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.dggs.simpleFeature.RHEALSFEquals;

public class SFEqualsTest {

	// positive test
	@Test
	void testCCEqualsPositive() {
		assertTrue(RHEALSFEquals.equals("R1 R2", "R1 R2"));
	}
	
	// negative test
	@Test
	void testCCEqualsNegative() {
		assertFalse(RHEALSFEquals.equals("R1 R2", "R1 R3"));
	}
	
	// constructive test
	@Test
	void testCCEqualsEquivalent() {
		assertTrue(RHEALSFEquals.equals("R1", "R10 R11 R12 R13 R14 R15 R16 R17 R18"));
	}

}
