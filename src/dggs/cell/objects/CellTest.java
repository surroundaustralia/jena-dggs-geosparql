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
		CellCollection test1 = new CellCollection(cells);
		CellCollection test2 = cell1.add(cell2);
		assertTrue(test1.equals(test2));
	}
	
}
