package test.dggs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.dggs.cell.objects.CellCollection;
import main.dggs.cell.objects.Cell;

public class CellCollectionTest {

	// instantiate basic cell collection
	
	@Test
	void testBasicCellCollection() {
		CellCollection testcellcoll = new CellCollection("R1 R2");
	}
	
    // equality
	@Test
	void testequality() {
		CellCollection test1 = new CellCollection("R1 R2");
		CellCollection test2 = new CellCollection("R1 R2");
		assertTrue(test1.equals(test2));
	}
	
	// deduplication
	@Test
	void testDecuplicate() {
		CellCollection test1 = new CellCollection("R1 R1");
		CellCollection test2 = new CellCollection("R1");
		assertTrue(test1.equals(test2));
	}
	
	// order
	@Test
	void testOrder() {
		CellCollection test1 = new CellCollection("R1 R2");
		CellCollection test2 = new CellCollection("R2 R1");
		assertTrue(test1.equals(test2));
	}	
	
	// absorb
	@Test
	void testAbsorb() {
		CellCollection test1 = new CellCollection("R1 R11");
		CellCollection test2 = new CellCollection("R1");
		assertTrue(test1.equals(test2));
	}
	
	// compress
	@Test
	void testCompressPositive() {
		CellCollection test1 = new CellCollection("R10 R11 R12 R13 R14 R15 R16 R17 R18");
		CellCollection test2 = new CellCollection("R1");
		assertTrue(test1.equals(test2));
	}
	
	@Test
	void testCompressNegative() {
		CellCollection test1 = new CellCollection("R100 R11 R12 R13 R14 R15 R16 R17 R18");
		CellCollection test2 = new CellCollection("R1");
		assertFalse(test1.equals(test2));
	}
	
	@Test
	void testAdditionPositive() {
		CellCollection test1 = new CellCollection("R1 R2");
		CellCollection test2 = new CellCollection("R3 R4");
		CellCollection test3 = new CellCollection("R1 R2 R3 R4");
		CellCollection test4 = test1.add(test2);
		assertFalse(test3.equals(test4));
	}
	
	// cell overlap cell collection positive
	@Test
	void testOverlapCellCollectionPositive() {
		Cell cell1 = new Cell("R1");
		CellCollection cells2 = new CellCollection("R12 R21");
		assertTrue(cells2.overlaps(cell1));
	}
	
	// cell overlap cell collection negative
	@Test
	void testOverlapCellCollectionNegative() {
		Cell cell1 = new Cell("R11");
		CellCollection cells2 = new CellCollection("R21 R22");
		assertFalse(cells2.overlaps(cell1));
	}
	
	// cell overlap cell collection positive
	@Test
	void testCCoverlapsCCPositive() {
		CellCollection cells1 = new CellCollection("R12 R13");
		CellCollection cells2 = new CellCollection("R13 R14");
		assertTrue(cells1.overlaps(cells2));
	}
	
	// cell overlap cell collection negative
	@Test
	void testCCoverlapsCCNegative() {
		CellCollection cells1 = new CellCollection("R12 R13");
		CellCollection cells2 = new CellCollection("R14 R15");
		assertFalse(cells1.overlaps(cells2));
	}
	
	// cell overlap cell collection negative
	@Test
	void testSubtraction() {
		CellCollection cells1 = new CellCollection("R");
		CellCollection cells2 = new CellCollection("R1");
		CellCollection cells3 = new CellCollection("R0 R2 R3 R4 R5 R6 R7 R8");
		CellCollection test = cells1.subtract(cells2);
		assertEquals(test, cells3);
	}
	
	// cell subtraction
	@Test
	void testSubtractionNothingLeft() {
		CellCollection cells1 = new CellCollection("R");
		CellCollection cells2 = new CellCollection("R");
		CellCollection test = cells1.subtract(cells2);
		assertEquals(test.cells, null);
		assertEquals(test.suids, null);
	}
	
	// cell subtraction
	@Test
	void testSubtractionDisjoint() {
		CellCollection cells1 = new CellCollection("R");
		CellCollection cells2 = new CellCollection("P");
		CellCollection test = cells1.subtract(cells2);
		CellCollection expected = new CellCollection("R");
		assertEquals(test, expected);
	}
	
	// cell collection subtraction
	@Test
	void testSubtractionSimple() {
		CellCollection cells1 = new CellCollection("R");
		CellCollection cells2 = new CellCollection("R1 R3 R5 R7");
		CellCollection test = cells1.subtract(cells2);
		CellCollection expected = new CellCollection("R0 R2 R4 R6 R8");
		assertEquals(test, expected);
	}
	
	// cell collection children
	@Test
	void testChildren() {
		CellCollection cells1 = new CellCollection("R P1").children();
		CellCollection expectedCells1Children = new CellCollection("P1 R0 R1 R2 R3 R4 R5 R6 R7 R8", false);
		assertEquals(cells1, expectedCells1Children);
	}
	
	// cell collection max resolution
	@Test
	void testMaxRes() {
		CellCollection cells1 = new CellCollection("R P1 R23 R345 Q2345");
		assert(cells1.max_resolution == 4);
	}
	
	// cell collection min resolution
	@Test
	void testMinRes() {
		CellCollection cells1 = new CellCollection("R P1 R23 R345 Q2345");
		assert(cells1.min_resolution == 0);
	}
	
	// cell collection min resolution
	@Test
	void testNeighbours() {
		CellCollection cells1 = new CellCollection("R4 R51").neighbours();
		CellCollection expectedNeighbours = new CellCollection("R08 R16 R17 R18 R26 R27 R28 R32 R35 R38 R50 R52 R53 R54 R55 R56 R62 R70 R71 R72 R80");
		assert(cells1.equals(expectedNeighbours));
	}
}
