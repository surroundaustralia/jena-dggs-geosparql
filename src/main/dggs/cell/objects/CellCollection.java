package main.dggs.cell.objects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;
import java.util.stream.Collectors;

public class CellCollection {

	public Cell[] cells;
	public String suids;
	public Integer max_resolution;
	public Integer min_resolution;

	public CellCollection(String suids, Boolean... compress_opt) {
		// default compress to true
		Boolean compress = compress_opt.length > 0 ? compress_opt[0] : true;
		
		if (suids.isEmpty()) {
			this.cells = null;
			this.suids = null;
		}
		else {
			String[] string_array = suids.split(" ");
			List<Cell> cellList = new ArrayList<Cell>();
			for (String s : string_array) {
				cellList.add(new Cell(s));
			}
			this.cells = cellList.toArray(new Cell[cellList.size()]);

			// Deduplicate e.g. {R1, R1} -> {R1}
			Set<String> cellsSet = new HashSet<String>();
			for (Cell c : this.cells)
				if (!cellsSet.add(c.suid))
					cellList.remove(c);
			this.cells = cellList.toArray(new Cell[cellList.size()]);

			// Absorb children in to parents e.g. {R1, R11} -> {R1}
			List<Cell> absorbRemoveList = new ArrayList<Cell>();
			for (int cell = 0; cell < this.cells.length; cell++) {
				int max_ancestor_len = this.cells[cell].suid.length() - 1;
				for (int i = 0; i < max_ancestor_len; i++) {
					Cell ancestor_cell = new Cell(this.cells[cell].suid.substring(0, i + 1));
					if (Arrays.asList(this.cells).contains(ancestor_cell)) {
						absorbRemoveList.add(this.cells[cell]);
					}
				}
			}
			for (Cell cellToRemove: absorbRemoveList) {
				cellList.remove(cellToRemove);
			}
			this.cells = cellList.toArray(new Cell[cellList.size()]);

			// compress
			if (compress) {
				List<Cell> cellListWithoutZeroCells = new ArrayList<Cell>();
				for (Cell c : cellList) {
					if (c.resolution > 1) {
						cellListWithoutZeroCells.add(c);
					} 
				}
				Map<String, List<Cell>> cellListGrouped = cellListWithoutZeroCells.stream()
						.collect(Collectors.groupingBy(w -> w.parent().suid));
				cellListGrouped.forEach((k, v) -> {
					if (v.size() == 9) {
						cellList.removeAll(v);
						cellList.add(new Cell(k));
					}
				});
				this.cells = cellList.toArray(new Cell[cellList.size()]);
			}

			// order
			Arrays.sort(this.cells, (a, b) -> a.suid.compareTo(b.suid));

			// regenerate suids based on cells
			final String[] sarray = Arrays.stream(this.cells).map(Object::toString).toArray(String[]::new);
			this.suids = String.join(" ", sarray);
			
			// set min and max resolution
			List<Integer> resolutions = new ArrayList<Integer>();
			for (Cell c : this.cells) {
				resolutions.add(c.resolution);
			}
			this.min_resolution = Collections.min(resolutions);
			this.max_resolution = Collections.max(resolutions);
		}
	}


	// overloading for no input - not sure if used
	public CellCollection() {
		this.cells = null;
		this.suids = null;
		this.min_resolution = null;
		this.max_resolution = null;
	}
	// Equals must override the generic Java equals function (then check for type Cell),
	// otherwise Cells in Arrays that have operations will not know to utilise this method
	// Equals requires implementing hashcode
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final CellCollection other = (CellCollection) obj;
        if ((this.suids == null) ? (other.suids != null) : !this.suids.equals(other.suids)) {
            return false;
        }

        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.suids != null ? this.suids.hashCode() : 0);
        return hash;
    }

	// CellCollection Addition
	public CellCollection add(CellCollection otherCC) {
		String together = String.join(this.suids, otherCC.suids);
		return new CellCollection(together);
	}
	
	// CellCollection Children
	public CellCollection children() {
		List<String> childrenList = new ArrayList<String>();
		for (Cell cell : this.cells) {
			CellCollection thiscellchildren = cell.children();
			childrenList.add(thiscellchildren.suids);
		}
		String children_suid_string = String.join(" ", childrenList);
		return new CellCollection(children_suid_string);
	}

	// CellCollection overlap Cell - reuse Cell method
	public boolean overlaps(Cell otherCell) {
		return otherCell.overlaps(this);
	}

	// CellCollection overlap CellCollection - reuse CellCollection method
	public boolean overlaps(CellCollection otherCells) {
		for (Cell cell : otherCells.cells) {
			if (this.overlaps(cell)) {
				return true;
			}
		}
		return false;
	}
	
	private void innerSubtract(
			List<String> removeList,
			List<String> retainList,
			CellCollection cc1,
			CellCollection cc2
			) {
		for (Cell cell1 : cc1.cells) {
			for (Cell cell2 : cc2.cells) {
				if (cell1.overlaps(cell2)) {
					if (cell1.resolution >= cell2.resolution) {
							removeList.add(cell1.suid);
						} 
					else {
						CellCollection thiscellchildren = cell1.children();
						thiscellchildren.innerSubtract(
								removeList,
								retainList,
								thiscellchildren,
								new CellCollection(cell2.suid));
						}
					}
				else {
				retainList.add(cell1.suid);
				}
			}
		}
	}

	// CellCollection Subtraction
	public CellCollection subtract(CellCollection otherCC) {
		List<String> removeList = new ArrayList<String>();
		List<String> retainList = new ArrayList<String>();
		innerSubtract(removeList, retainList, this, otherCC);
		retainList.removeAll(removeList);
		String retain_suids = String.join(" ", retainList);
		CellCollection retainCC = new CellCollection(retain_suids);
		return retainCC;
	}
}