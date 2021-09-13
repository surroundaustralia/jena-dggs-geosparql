package dggs.cell.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dggs.cell.objects.CellCollection;
import dggs.cell.objects.Cell;

class CellCollectionTest {

	// instantiate basic cell collection
	@Test
	void testBasicCellCollection() {
		Cell[] cells = {new Cell("R1"), new Cell("R2")};
		new CellCollection(cells);
	}
	
	// equality
	@Test
	void testequality() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R2");		
		Cell[] cells = {cell1, cell2};
		CellCollection test1 = new CellCollection(cells);
		CellCollection test2 = new CellCollection(cells);
		assertTrue(test1.equals(test2));
	}
	
	// deduplication
	@Test
	void testDecuplicate() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R1");
		Cell cell3 = new Cell("R1");
		Cell[] cells = {cell1, cell2};
		Cell[] single_cell = {cell3};
		CellCollection test1 = new CellCollection(cells);
		CellCollection test2 = new CellCollection(single_cell);
		assertTrue(test1.equals(test2));
	}
	
	
	// absorb
	@Test
	void testAbsorb() {
		Cell cell1 = new Cell("R1");
		Cell cell2 = new Cell("R11");
		Cell cell3 = new Cell("R1");
		Cell[] cells = {cell1, cell2};
		Cell[] single_cell = {cell3};
		CellCollection test1 = new CellCollection(cells);
		CellCollection test2 = new CellCollection(single_cell);
		assertTrue(test1.equals(test2));
	}
	
	
	// compress
	@Test
	void testCompressPositive() {
		Cell cell1 = new Cell("R10");
		Cell cell2 = new Cell("R11");
		Cell cell3 = new Cell("R12");
		Cell cell4 = new Cell("R13");
		Cell cell5 = new Cell("R14");
		Cell cell6 = new Cell("R15");
		Cell cell7 = new Cell("R16");
		Cell cell8 = new Cell("R17");
		Cell cell9 = new Cell("R18");
		Cell cell10 = new Cell("R1");
		Cell[] cells = {cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9};
		Cell[] single_cell = {cell10};;
		CellCollection test1 = new CellCollection(cells);
		CellCollection test2 = new CellCollection(single_cell);
		assertTrue(test1.equals(test2));
	}
	
	@Test
	void testCompressNegative() {
		Cell cell1 = new Cell("R100");
		Cell cell2 = new Cell("R11");
		Cell cell3 = new Cell("R12");
		Cell cell4 = new Cell("R13");
		Cell cell5 = new Cell("R14");
		Cell cell6 = new Cell("R15");
		Cell cell7 = new Cell("R16");
		Cell cell8 = new Cell("R17");
		Cell cell9 = new Cell("R18");
		Cell cell10 = new Cell("R1");
		Cell[] cells = {cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8, cell9};
		Cell[] single_cell = {cell10};;
		CellCollection test1 = new CellCollection(cells);
		CellCollection test2 = new CellCollection(single_cell);
		assertFalse(test1.equals(test2));
	}
	
}
