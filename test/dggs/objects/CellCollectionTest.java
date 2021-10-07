package dggs.cell.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dggs.cell.objects.CellCollection;
import dggs.cell.objects.Cell;

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
	
}
