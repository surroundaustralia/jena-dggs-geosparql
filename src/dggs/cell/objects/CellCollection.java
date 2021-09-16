package dggs.cell.objects;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import java.util.stream.Collectors;


public class CellCollection {
	
	public Cell[] cells;
	public String suids;
	
//	public CellCollection(Cell[] cells)
	public CellCollection(String suids, Boolean... compress_opt)
	{	
		//default compress to true
		Boolean compress = compress_opt.length > 0 ? compress_opt[0] : true;
		
		String[] string_array = suids.split(" ");
		List<Cell> cellList = new ArrayList<Cell>();
		for(String s: string_array) {
			cellList.add(new Cell(s));
		}
		this.cells = cellList.toArray(new Cell[cellList.size()]);

		// Deduplicate e.g. {R1, R1} -> {R1}
		Set<String> cellsSet = new HashSet<String>();
		for(Cell c: this.cells) if(!cellsSet.add(c.suid)) cellList.remove(c);
		this.cells = cellList.toArray(new Cell[cellList.size()]);
		
		// Absorb children in to parents e.g. {R1, R11} -> {R1}
		for (int cell=0; cell < this.cells.length; cell++) {

			int max_ancestor_len = this.cells[cell].suid.length() - 1;
			for (int i=0; i < max_ancestor_len; i++) {
				Cell ancestor_cell = new Cell(this.cells[cell].suid.substring(0, i+1));
				if (Arrays.asList(this.cells).contains(ancestor_cell)) {
					cellList.remove(cell);
				}
			}
		}
		this.cells = cellList.toArray(new Cell[cellList.size()]);
		
		// compress
		if (compress) {
			Map<String, List<Cell>> cellListGrouped =
					cellList.stream().collect(Collectors.groupingBy(w -> w.parent().suid));
			cellListGrouped.forEach((k,v) -> {if (v.size()==9) {
				cellList.removeAll(v);
				cellList.add(new Cell(k));
				}
			});
			this.cells = cellList.toArray(new Cell[cellList.size()]);
		}
			
		// order
		Arrays.sort(this.cells, (a,b) -> a.suid.compareTo(b.suid));
		
		// regenerate suids based on cells
		final String[] sarray = Arrays.stream(this.cells).map(Object::toString).
                toArray(String[]::new);
		this.suids = String.join(" ", sarray);
	}
		
	// equals
	public Boolean equals(CellCollection otherCC) {
		if (this.suids.equals(otherCC.suids)) {
			return true; 
			}
		return false;
		}
	
	// CellCollection Addition
	public CellCollection add(CellCollection otherCC) {
		String together = String.join(this.suids, otherCC.suids);
	    return new CellCollection(together);
	}
	
}