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
	
}
