package dggs.cell.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;


public class CellCollection {
	
	public Cell[] cells;
	
	public CellCollection(Cell[] cells)
	{
		// deduplicate e.g. {R1, R1} -> {R1}
		Cell[] cc = cells;
		Set<String> cellsSet = new HashSet<String>();
		List<Cell> cellList = new ArrayList<Cell>();
		for(Cell c: cc) if(cellsSet.add(c.suid)) cellList.add(c);
		this.cells = cellList.toArray(new Cell[cellList.size()]);
		
		// "absorb" children in to parents e.g. {R1, R11} -> {R1}
		for (int cell=0; cell < this.cells.length; cell++) {
//			Set<String> ancestorCellsSet = new HashSet<String>();
//			List<Cell> remainingCells = new ArrayList<Cell>();
			int max_ancestor_len = this.cells[cell].suid.length() - 1;
			for (int i=0; i < max_ancestor_len; i++) {
				Cell ancestor_cell = new Cell(this.cells[cell].suid.substring(0, i+1));
				if (Arrays.asList(this.cells).contains(ancestor_cell)) {
					cellList.remove(cell);
				}
//					ancestorCellsSet.add(this.cells[cell].suid);
//					this.cells.delete
//				}
//				ancestorCells.add(new Cell(this.cells[cell].suid.substring(0, i)));
			}
		}
		this.cells = cellList.toArray(new Cell[cellList.size()]);
	}
		
	// equals
	public Boolean equals(CellCollection otherCC) {
		if (this.cells.length==otherCC.cells.length) {
			for (int i=0; i < this.cells.length; i++) {
				  if (!this.cells[i].equals(otherCC.cells[i])) {
					  return false;
				  }
			} return true;
		}
		else return false;
	}

}