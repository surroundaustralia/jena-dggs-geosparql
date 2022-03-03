package test.dggs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.dggs.simpleFeature.RHEALSFContains;

public class SFContainsTest {

	// positive test
	@Test
	void testCCEqualsPositive() {
		assertTrue(RHEALSFContains.contains("R1", "R10"));
	}
	
	// negative test
	@Test
	void testCCEqualsNegative() {
		assertFalse(RHEALSFContains.contains("R10", "R1"));
	}
	
	// negative complex
	@Test
	void testCCEqualsNegativeComplex() {
		assertFalse(RHEALSFContains.contains("R1", "R10 R2"));
	}
	
}
