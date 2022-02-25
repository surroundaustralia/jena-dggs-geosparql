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
		
	    String expectedMessage = "Cell suids must start with one of the zero cells";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
		
	}

	// validity
	@Test
	void testInvalidRemaining() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			new Cell("R9");
		});
		
	    String expectedMessage = "Cell suids must only contain integers in the range {TODO} after the first integer";
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
	
	@Test
	void testChildrenAtRes1() {
		Cell cell1 = new Cell("P");
		CellCollection neighbours = cell1.children(1);
		CellCollection cell2 = new CellCollection("P0 P1 P2 P3 P4 P5 P6 P7 P8", false);
		assertEquals(neighbours, cell2);
	}
	
	@Test
	void testChildrenAtRes2() {
		Cell cell1 = new Cell("P");
		CellCollection neighbours = cell1.children(2);
		CellCollection cell2 = new CellCollection("P00 P01 P02 P03 P04 P05 P06 P07 P08 P10 P11 P12 P13 P14 P15 P16 P17 P18 P20 P21 P22 P23 P24 P25 P26 P27 P28 P30 P31 P32 P33 P34 P35 P36 P37 P38 P40 P41 P42 P43 P44 P45 P46 P47 P48 P50 P51 P52 P53 P54 P55 P56 P57 P58 P60 P61 P62 P63 P64 P65 P66 P67 P68 P70 P71 P72 P73 P74 P75 P76 P77 P78 P80 P81 P82 P83 P84 P85 P86 P87 P88", false);
		assertEquals(neighbours, cell2);
	}
	
	@Test
	void testBorderUnspecified() {
		Cell cell1 = new Cell("P");
		CellCollection border = cell1.border();
		CellCollection cell2 = new CellCollection("P");
		assertEquals(border, cell2);
	}
	
	@Test
	void testBorder1() {
		Cell cell1 = new Cell("P");
		CellCollection border = cell1.border(1);
		CellCollection cell2 = new CellCollection("P0 P1 P2 P3 P5 P6 P7 P8");
		assertEquals(border, cell2);
	}
	
	@Test
	void testBorder2() {
		Cell cell1 = new Cell("P");
		CellCollection border = cell1.border(2);
		CellCollection cell2 = new CellCollection("P00 P01 P02 P03 P06 P10 P11 P12 P20 P21 P22 P25 P28 P30 P33 P36 P52 P55 P58 P60 P63 P66 P67 P68 P76 P77 P78 P82 P85 P86 P87 P88");
		assertEquals(border, cell2);
	}
}
