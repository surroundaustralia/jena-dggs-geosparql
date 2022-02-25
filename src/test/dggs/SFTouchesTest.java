package test.dggs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.dggs.simpleFeature.RHEALSFTouches;

public class SFTouchesTest {

	// positive test
	@Test
	void testCCTouchesPositive1() {
		assertTrue(RHEALSFTouches.touches("R1", "R2"));
	}
	
	// positive test
	@Test
	void testCCTouchesPositive2() {
		assertTrue(RHEALSFTouches.touches("R0", "R4"));
	}
	
	// negative test
	@Test
	void testCCTouchesNegative() {
		assertFalse(RHEALSFTouches.touches("R1", "R7"));
	}
	
}
