package test.dggs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.dggs.simpleFeature.RHEALSFWithin;

public class SFWithinTest {

	// positive test
	@Test
	void testCCEqualsPositive() {
		assertTrue(RHEALSFWithin.within("R10", "R1"));
	}
	
	// negative test
	@Test
	void testCCEqualsNegative() {
		assertFalse(RHEALSFWithin.within("R1", "R10"));
	}
	
	// negative complex
	@Test
	void testCCEqualsNegativeComplex() {
		assertFalse(RHEALSFWithin.within("R1", "R10 R2"));
	}
}
