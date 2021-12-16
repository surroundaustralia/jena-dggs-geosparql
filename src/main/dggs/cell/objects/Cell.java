package main.dggs.cell.objects;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Cell {
	
	public String suid;
	public Integer resolution;

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
		this.resolution = suid.length()-1;
	}
	// overload for empty suid
	public Cell()
	{
		this.suid = null;
		this.resolution = null;
	}

	
	// Cell addition
	public CellCollection add(Cell otherCell) {
		String suids= this.suid +" "+ otherCell.suid;
	    return new CellCollection(suids);
	}
	
	// Cell parent
	public Cell parent() {
		if (this.resolution > 1) {
			Cell parent_cell = new Cell(this.suid.substring(0, this.suid.length()-1));
			return parent_cell;
		}
		else {
			return null;
		}
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

    // Cell overlap Cell
    public boolean overlaps(Cell otherCell) {
    	int min_length = Math.min(this.suid.length(), otherCell.suid.length());
    	if (this.suid.substring(0, min_length).equals(otherCell.suid.substring(0, min_length))) 
    		{return true;}
    	else return false;
    }
    
    // Cell overlap CellCollection
    public boolean overlaps(CellCollection otherCells) {
    	for (Cell cell: otherCells.cells)
	    	{
    		int min_length = Math.min(this.suid.length(), cell.suid.length());
	    	if (this.suid.substring(0, min_length).equals(cell.suid.substring(0, min_length))) 
	    		{return true;}
	    	}
    	return false;
    }
    
    public static Map<String, Map> atomic_neighbours() {
//        # north_square = south_square = 0
    	int north_square = 0;
    	int south_square = 0;
//        # zero_cells = parametrisations[self.crs]["zero_cells"]
    	List<String> zerocells = new ArrayList<String>();
    	zerocells.add("N");
    	zerocells.add("O");
    	zerocells.add("P");
    	zerocells.add("Q");
    	zerocells.add("R");
    	zerocells.add("S");
//    	String zerocells = "NOPQRS";
//        #
    	Integer N = 3;	
//        # # Taken from the rHEALPix DGGS repository
//        #
//        # # Initialize atomic neighbour relationships among suid.
//        # # Dictionary of up, right, down, and left neighbours of
//        # # resolution 0 suid and their subcells 0--(N_side**2 -1),
//        # # aka the atomic neighbours.
//        # # Based on the layouts
//        # #
//        # #   0
//        # #   1 2 3 4   (but folded into a cube) and
//        # #   5
//        # #
//        # #   0 1 2
//        # #   3 4 5
//        # #   6 7 8   (example for N_side=3).
//        # #
//        # an = {}
    	Map<String, Map> an = new HashMap<String, Map>();
//      # # neighbours of zero_cells[1], ..., zero_cells[4]
//      # an[zero_cells[1]] = {
//      #     "left": zero_cells[4],
//      #     "right": zero_cells[2],
//      #     "down": zero_cells[5],
//      #     "up": zero_cells[0],
//      #     }
    	Map<String,String> inner1 = new HashMap<String, String>();
    	inner1.put("left", zerocells.get(4));
    	inner1.put("right", zerocells.get(2));
    	inner1.put("down", zerocells.get(5));
    	inner1.put("up", zerocells.get(0));
    	an.put(zerocells.get(1), inner1);
//        # an[zero_cells[2]] = {
//        #     "left": zero_cells[1],
//        #     "right": zero_cells[3],
//        #     "down": zero_cells[5],
//        #     "up": zero_cells[0],
//        #     }
    	Map<String,String> inner2 = new HashMap<String, String>();
    	inner2.put("left", zerocells.get(1));
    	inner2.put("right", zerocells.get(3));
    	inner2.put("down", zerocells.get(5));
    	inner2.put("up", zerocells.get(0));
    	an.put(zerocells.get(2), inner2);
//        # an[zero_cells[3]] = {
//        #     "left": zero_cells[2],
//        #     "right": zero_cells[4],
//        #     "down": zero_cells[5],
//        #     "up": zero_cells[0],
//        #     }
    	Map<String,String> inner3 = new HashMap<String, String>();
    	inner3.put("left", zerocells.get(2));
    	inner3.put("right", zerocells.get(4));
    	inner3.put("down", zerocells.get(5));
    	inner3.put("up", zerocells.get(0));
    	an.put(zerocells.get(3), inner3);
//        # an[zero_cells[4]] = {
//        #     "left": zero_cells[3],
//        #     "right": zero_cells[1],
//        #     "down": zero_cells[5],
//        #     "up": zero_cells[0],
//        #     }
    	Map<String,String> inner4 = new HashMap<String, String>();
    	inner4.put("left", zerocells.get(3));
    	inner4.put("right", zerocells.get(1));
    	inner4.put("down", zerocells.get(5));
    	inner4.put("up", zerocells.get(0));
    	an.put(zerocells.get(4), inner4);
//        # # neighbours of zero_cells[0] and zero_cells[5] depend on
//        # # volues of north_square and south_square, respectively.
//        # nn = north_square
//        # an[zero_cells[0]] = {
//        #     "down": zero_cells[(nn + 0) % 4 + 1],
//        #     "right": zero_cells[(nn + 1) % 4 + 1],
//        #     "up": zero_cells[(nn + 2) % 4 + 1],
//        #     "left": zero_cells[(nn + 3) % 4 + 1],
//        #     }
    	Map<String,String> inner0 = new HashMap<String, String>();
    	inner0.put("down", zerocells.get((north_square + 0) % 4 + 1));
    	inner0.put("right", zerocells.get((north_square + 1) % 4 + 1));
    	inner0.put("up", zerocells.get((north_square + 2) % 4 + 1));
    	inner0.put("left", zerocells.get((north_square + 3) % 4 + 1));
    	an.put(zerocells.get(0), inner0);
//        # ss = south_square
//        # an[zero_cells[5]] = {
//        #     "up": zero_cells[(ss + 0) % 4 + 1],
//        #     "right": zero_cells[(ss + 1) % 4 + 1],
//        #     "down": zero_cells[(ss + 2) % 4 + 1],
//        #     "left": zero_cells[(ss + 3) % 4 + 1],
//        #     }
//        #
    	Map<String,String> inner5 = new HashMap<String, String>();
    	inner5.put("up", zerocells.get((south_square + 0) % 4 + 1));
    	inner5.put("right", zerocells.get((south_square + 1) % 4 + 1));
    	inner5.put("down", zerocells.get((south_square + 2) % 4 + 1));
    	inner5.put("left", zerocells.get((south_square + 3) % 4 + 1));
    	an.put(zerocells.get(5), inner5);
//        # # neighbours of 0, 1, ..., N**2 - 1.
//        # for i in range(N ** 2):
//        #     an[i] = {
//        #         "left": i - 1,
//        #         "right": i + 1,
//        #         "up": (i - N) % N ** 2,
//        #         "down": (i + N) % N ** 2,
//        #         }
    	for (Integer i = 0; i < Math.pow(N, 2); i++) {
        	Map<String,String> innerloop = new HashMap<String, String>();
        	// left
        	Integer leftint = i-1;
        	if (i % N == 0) {
        		leftint = leftint + N;
        	} 
        	innerloop.put("left", leftint.toString());
        	// right
        	Integer rightint = i+1;
        	innerloop.put("right", rightint.toString());
        	if (i % N == N - 1) {
        		rightint = rightint - N;
        	} 
        	// down
        	// java mod (a % b + b) % b
        	Integer downint_a = (int) (i + N);
        	Integer b = (int) Math.pow(N, 2);
        	Integer downint = (int) (downint_a % b + b) % b;
        	innerloop.put("down", downint.toString());
        	// up
        	Integer upint_a = (int) (i - N);
        	Integer upint = (int) (upint_a % b + b) % b;
        	innerloop.put("up", upint.toString());
    		an.put(i.toString(), innerloop);
    	}
    	// the below has been included above 'inline'
//        # # Adjust left and right edge cases.
//        # for i in range(0, N ** 2, N):
//        #     an[i]["left"] = an[i]["left"] + N
//        # for i in range(N - 1, N ** 2, N):
//        #     an[i]["right"] = an[i]["right"] - N
		return an;
    }


    

        
}