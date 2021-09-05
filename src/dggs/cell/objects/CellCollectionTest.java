package dggs.cell.objects;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import dggs.cell.objects.CellCollection;
import dggs.cell.objects.Cell;

class CellCollectionTest {

	@Test
	void testBasicCellCollection() {
		Cell[] cells = {new Cell("R1"), new Cell("R1")};
		new CellCollection(cells);
	}
	
	
}
