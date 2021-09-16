package dggs.cell.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class Cell {
	
	public String suid;

	public Cell(String suid)
	{
		// Validate the suid input
		this.suid = suid;
		if (!Stream.of("N","O","P","Q","R","S").anyMatch(suid::startsWith)) {
			throw new IllegalArgumentException("Cell suids must start with one of N, O, P, Q, R, or S");
		}
		if (suid.length() > 1) {
			if (!Stream.of("0","1","2","3","4","5","6","7","8").anyMatch(suid.substring(1)::startsWith)) {
				throw new IllegalArgumentException("Cell suids must only contain characters 0..8 after the first character");
			}
		}
	}
	
	// Cell adition
	public CellCollection add(Cell otherCell) {
		String suids= this.suid +" "+ otherCell.suid;
	    return new CellCollection(suids);
	}
	
	// Cell parent
	public Cell parent() {
		Cell parent_cell = new Cell(this.suid.substring(0, this.suid.length()-1));
	    return parent_cell;
	}
	
	// Cell children
	public CellCollection children() {
		String[] suffixes = {"0","1","2","3","4","5","6","7","8"};
		List<String> suids = new ArrayList<String>();
		for (String suffix: suffixes) 
			{suids.add(new String(this.suid + suffix));}
		String joined_suids = String.join(" ", suids);
		return new CellCollection(joined_suids, false);
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

        final Cell other = (Cell) obj;
        if ((this.suid == null) ? (other.suid != null) : !this.suid.equals(other.suid)) {
            return false;
        }

        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.suid != null ? this.suid.hashCode() : 0);
        return hash;
    }
    
    // toString method to support working with an array of Cells - used to 'regenerate' the suids for a CellCollection
    // this may not be required if there's a way to reference the suid attribute in the 'regenerate' code
    public String toString() {
        return this.suid;
    }

    // 
    public boolean overlaps(Cell otherCell) {
    	int min_length = Math.min(this.suid.length(), otherCell.suid.length());
    	if (this.suid.substring(0, min_length).equals(otherCell.suid.substring(0, min_length))) 
    		{return true;}
    	else return false;
    }
    
}