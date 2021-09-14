package dggs.cell.objects;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import dggs.cell.objects.Cell;

class CellTest {

	@Test
	void testValidZero() {
		new Cell("R1");
	}
	
	@Test
	void testInvalidZero() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new Cell("T1");
		});
		
	    String expectedMessage = "Cell suids must start with one of N, O, P, Q, R, or S";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
		
	}

	// validity
	@Test
	void testInvalidRemaining() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new Cell("R9");
		});
		
	    String expectedMessage = "Cell suids must only contain characters 0..8 after the first character";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
		
	}
	
	// addition - must define what equals means for this to work!
	@Test
	void testAddition() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R2");		
		Cell[] cells = {cell1, cell2};
		CellCollection test1 = new CellCollection("R1 R2");
		CellCollection test2 = cell1.add(cell2);
		assertTrue(test1.equals(test2));
	}
	
	// cell equality
	@Test
	void testEquality() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R1");
		assertTrue(cell1.equals(cell2));
	}
	
	// cell inequality
	@Test
	void testInEquality() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R2");
		assertFalse(cell1.equals(cell2));
	}
	
	// cell parent valid
	@Test
	void testParentPositive() {
		Cell cell1 = new Cell("R111");
		Cell cell2 = new Cell("R11");
		assertTrue(cell1.parent().equals(cell2));
	}
	
	// cell parent valid
	@Test
	void testParentNegative() {
		Cell cell1 = new Cell("R11");
		Cell cell2 = new Cell("R11");
		assertFalse(cell1.parent().equals(cell2));
	}
	
}
