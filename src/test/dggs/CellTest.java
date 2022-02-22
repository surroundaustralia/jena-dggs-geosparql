package test.dggs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import main.dggs.cell.objects.Cell;
import main.dggs.cell.objects.CellCollection;

public class CellTest {

//	@Test
//	void test_child_order() {
//		HashMap<int[], Integer> test = new Cell("R1").child_order();
//	}
	
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
	
	// addition
	@Test
	void testAddition() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R2");		
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
	
	// cell parent zero cell
	@Test
	void testParentZeroCell() {
		Cell cell1 = new Cell("R");
		assertTrue(cell1.parent() == null);
	}
	
	// cell overlap positive 1
	@Test
	void testOverlapPositive1() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R11");
		assertTrue(cell1.overlaps(cell2));
	}
	
	// cell overlap positive 2
	@Test
	void testOverlapPositive2() {
		Cell cell1 = new Cell("R11");
		Cell cell2 = new Cell("R1");
		assertTrue(cell1.overlaps(cell2));
	}
	
	// cell overlap positive 3
	@Test
	void testOverlapPositive3() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R1");
		assertTrue(cell1.overlaps(cell2));
	}
	
	// cell overlap negative
	@Test
	void testOverlapNegative() {
		Cell cell1 = new Cell("R11");
		Cell cell2 = new Cell("R12");
		assertFalse(cell1.overlaps(cell2));
	}
	
	// cell children positive
	@Test
	void testChildrenPositive() {
		Cell cell1 = new Cell("R1");
		CellCollection cell2 = new CellCollection("R10 R11 R12 R13 R14 R15 R16 R17 R18", false);
		assertTrue(cell1.children().equals(cell2));
	}
	
	// cell overlap cell collection positive
	@Test
	void testOverlapCellCollectionPositive() {
		Cell cell1 = new Cell("R1");
		CellCollection cells2 = new CellCollection("R12 R21");
		assertTrue(cell1.overlaps(cells2));
	}
	
	// cell overlap cell collection negative
	@Test
	void testOverlapCellCollectionNegative() {
		Cell cell1 = new Cell("R11");
		CellCollection cells2 = new CellCollection("R21 R22");
		assertFalse(cell1.overlaps(cells2));
	}
	
	@Test
	void testResolution2() {
		Cell cell1 = new Cell("R11");
		assertTrue(cell1.resolution == 2);
	}
	
	@Test
	void testResolution0() {
		Cell cell1 = new Cell("R");
		assertTrue(cell1.resolution == 0);
	}
	
	@Test
	void testNeighbour() {
		Cell cell1 = new Cell("R1");
		Cell neighbour = cell1.neighbour("down");
		Cell cell2 = new Cell("R4");
		assertEquals(neighbour, cell2);
	}
	
	@Test
	void testNeighboursN() {
		Cell cell1 = new Cell("N");
		CellCollection neighbours = cell1.neighbours();
		CellCollection cell2 = new CellCollection("O P Q R");
		assertEquals(neighbours, cell2);
	}
	
	@Test
	void testNeighboursP() {
		Cell cell1 = new Cell("P");
		CellCollection neighbours = cell1.neighbours();
		CellCollection cell2 = new CellCollection("N S O Q");
		assertEquals(neighbours, cell2);
	}
	
	@Test
	void testNeighboursN8() {
		Cell cell1 = new Cell("N8");
		CellCollection neighbours = cell1.neighbours();
		CellCollection cell2 = new CellCollection("N5 N4 N7 O1 O2 P0 P1");
		assertEquals(neighbours, cell2);
	}
	
	@Test
	void testNeighboursP0() {
		Cell cell1 = new Cell("P0");
		CellCollection neighbours = cell1.neighbours();
		CellCollection cell2 = new CellCollection("N5 N8 O2 O5 P1 P3 P4");
		assertEquals(neighbours, cell2);
	}
	
}
