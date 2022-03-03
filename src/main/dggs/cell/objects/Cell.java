package main.dggs.cell.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Collections;

import org.paukov.combinatorics3.Generator;

public class Cell {
	
	public String suid;
	public Integer resolution;
	public RHEALPixParameterisation rhpp;

	public Cell(String suid, RHEALPixParameterisation...  rhpp_opt) {
		this.rhpp = rhpp_opt.length > 0 ? rhpp_opt[0] : new RHEALPixParameterisation("auspix");
		// Validate the suid input
		this.suid = suid;
		if (!this.rhpp.zero_cells.contains(suid.substring(0,1))) {
			throw new IllegalArgumentException("Cell suids must start with one of the zero cells");
		}
		if (suid.length() > 1) {
			if (!this.rhpp.suffixes.stream().anyMatch(suid.substring(1)::startsWith)) {
				throw new IllegalArgumentException("Cell suids must only contain integers in the range {TODO} after the first integer");
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
		if (this.resolution >= 1) {
			Cell parent_cell = new Cell(this.suid.substring(0, this.suid.length()-1));
			return parent_cell;
		}
		else {
			return null;
		}
	}
	
	// Cell children
	public CellCollection children(Integer... resolution_opt) {
		// resolution_delta is specified resolution - cell's resolution, else 1 less than cell's resolution 
		Integer resolution_delta = resolution_opt.length > 0 ? resolution_opt[0] - this.resolution: 1;
		List<String> children_suids = new ArrayList<String>();
		Generator.permutation(this.rhpp.suffixes)
	        .withRepetitions(resolution_delta)
	        .stream()
	        .forEach(perm -> children_suids.add(this.suid + String.join("", perm)));
		String children_suids_string = String.join(" ", children_suids);
		return new CellCollection(children_suids_string, false);
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
    
    // wrapper on neighbour_with_rotation to just return the neighbour
    public Cell neighbour(String direction) {
    	Map<Cell, Integer> map = this.neighbour_with_rotation(direction);
    	Optional<Cell> firstKey = map.keySet().stream().findFirst();
    	if (firstKey.isPresent()) {
    	    Cell key = firstKey.get();
    	    return key;
    	}
		return null;
    	
    }
       
    public Map<Cell, Integer> neighbour_with_rotation(String direction) {
    	HashMap<String, HashMap<String, String>> an = AtomicNeighbours.get_an();
    	List<String> zero_cells = this.rhpp.zero_cells;
//      an = self.atomic_neighbours()
//      suid = self.suids
//      zero_cells = parametrisations[self.crs]["zero_cells"]
//      neighbour_suid = []
    	List<String> neighbour_suid = new ArrayList<String>();
//      crossed_all_borders = False
    	boolean crossed_all_borders = false;
//      # Scan from the back to the front of suid.
//      for i in reversed(list(range(len(suid)))):
//          n = suid[i]
//          if crossed_all_borders:
//              neighbour_suid.append(n)
//          else:
//              neighbour_suid.append(an[n][direction])
//              if n not in border[direction]:
//                  crossed_all_borders = True
    	for (int i=this.suid.length()-1; i>=0; i--) {
    		String n = this.suid.substring(i,i+1);
    		if (crossed_all_borders) {
    			neighbour_suid.add(n);
    		}
    		else {
    			neighbour_suid.add(an.get(n).get(direction));
    			boolean contains = Arrays.stream(this.rhpp.borders.get(direction)).anyMatch(n::equals);
    			if (!contains) {
    				crossed_all_borders = true;
    			}
    		}
    	}
//      neighbour_suid.reverse()
    	Collections.reverse(neighbour_suid);
    	String neighbour_suid_str = String.join("", neighbour_suid);
//      neighbour = neighbour_suid
    	
//      # Second, rotate the neighbour if necessary.
//      # If self is a polar cell and neighbour is not, or vice versa,
//      # then rotate neighbour accordingly.
//      self0 = suid[0]
//      neighbour0 = neighbour_suid[0]
    	String suid_zero = this.suid.substring(0, 1);
    	String neighbour_zero = neighbour_suid_str.substring(0, 1);
//    	String test = zero_cells.get(5);
//      if (
//      (self0 == zero_cells[5] and neighbour0 == an[self0]["left"])
//      or (self0 == an[zero_cells[5]]["right"] and neighbour0 == zero_cells[5])
//      or (self0 == zero_cells[0] and neighbour0 == an[self0]["right"])
//      or (self0 == an[zero_cells[0]]["left"] and neighbour0 == zero_cells[0])
//  ):
//      neighbour = self.rotate(neighbour_suid, 1)
//  elif (
//      (self0 == zero_cells[5] and neighbour0 == an[self0]["down"])
//      or (self0 == an[zero_cells[5]]["down"] and neighbour0 == zero_cells[5])
//      or (self0 == zero_cells[0] and neighbour0 == an[self0]["up"])
//      or (self0 == an[zero_cells[0]]["up"] and neighbour0 == zero_cells[0])
//  ):
//      neighbour = self.rotate(neighbour_suid, 2)
//  elif (
//      (self0 == zero_cells[5] and neighbour0 == an[self0]["right"])
//      or (self0 == an[zero_cells[5]]["left"] and neighbour0 == zero_cells[5])
//      or (self0 == zero_cells[0] and neighbour0 == an[self0]["left"])
//      or (self0 == an[zero_cells[0]]["right"] and neighbour0 == zero_cells[0])
//  ):
//      neighbour = self.rotate(neighbour_suid, 3)
    	Integer rotation = 0;
    	if (
    			(suid_zero.equals(zero_cells.get(5)) && neighbour_zero.equals(an.get(suid_zero).get("left")))
    			|| (suid_zero.equals(an.get(zero_cells.get(5)).get("right")) && neighbour_zero.equals(zero_cells.get(5)))
    			|| (suid_zero.equals(zero_cells.get(0)) && neighbour_zero.equals(an.get(suid_zero).get("right")))
    			|| (suid_zero.equals(an.get(zero_cells.get(0)).get("left")) && neighbour_zero.equals(zero_cells.get(0)))
    			){
    		rotation = 1;
//    		neighbour_suid_str = rotate(neighbour_suid_str, 1);
    		}
    	else if (
    			(suid_zero.equals(zero_cells.get(5)) && neighbour_zero.equals(an.get(suid_zero).get("down")))
    			|| (suid_zero.equals(an.get(zero_cells.get(5)).get("down")) && neighbour_zero.equals(zero_cells.get(5)))
    			|| (suid_zero.equals(zero_cells.get(0)) && neighbour_zero.equals(an.get(suid_zero).get("up")))
    			|| (suid_zero.equals(an.get(zero_cells.get(0)).get("up")) && neighbour_zero.equals(zero_cells.get(0)))
    			){
    		rotation = 2;
//    		neighbour_suid_str = rotate(neighbour_suid_str, 2);
    		}
    	else if (
    			(suid_zero.equals(zero_cells.get(5)) && neighbour_zero.equals(an.get(suid_zero).get("right")))
    			|| (suid_zero.equals(an.get(zero_cells.get(5)).get("left")) && neighbour_zero.equals(zero_cells.get(5)))
    			|| (suid_zero.equals(zero_cells.get(0)) && neighbour_zero.equals(an.get(suid_zero).get("left")))
    			|| (suid_zero.equals(an.get(zero_cells.get(0)).get("right")) && neighbour_zero.equals(zero_cells.get(0)))
    			){
    		rotation = 3;
//    		neighbour_suid_str = rotate(neighbour_suid_str, 3);
    		}
		neighbour_suid_str = rotate(neighbour_suid_str, rotation);
		HashMap<Cell, Integer> return_map = new HashMap<>();
		return_map.put(new Cell(neighbour_suid_str), rotation);
		return return_map;
    }
    
    private String rotate(String suid, Integer quarter_turns) {
//            Return the suid of the cell that is the result of rotating this cell's
//            resolution 0 supercell by `quarter_turns` quarter turns anticlockwise.
//            Used in neighbour().
//            return [self.rotate_entry(x, quarter_turns) for x in suid]
    	List<String> rotated = new ArrayList<String>();
    	for (int i=0; i<suid.length(); i++) {
//    		System.out.println(i);
    		rotated.add(rotate_entry(suid.substring(i,i+1), quarter_turns));
    	}
    	return String.join("", rotated);
    }

    private String rotate_entry(String suid_substring, Integer quarter_turns) {
//        N = self.N
    	int N = this.rhpp.N;
//        # Original matrix of subcell numbers as drawn in the docstring.
    	HashMap<Integer, HashMap<Integer, Integer>> A = this.child_order();
//        # Function (written as a dictionary) describing action of rotating A
//        # one quarter turn anticlockwise.    	
//        f = dict()
    	HashMap<String, String> f = new HashMap<>();
//        for i in range(N):
//            for j in range(N):
//                n = A[(i, j)]
//                f[n] = A[(j, N - 1 - i)]
    	for (int i=0; i<N; i++) {
    		for (int j=0; j<N; j++) {
//    			int[] int_index = new int[]{i, j};
//    			int[] int_new_index = new int[]{j, N-1-i};
    			Integer n  = A.get(i).get(j);
    			Integer newVal = A.get(j).get(N-1-i);
//    			Integer newVal = A.get(int_new_index.toString());
    			f.put(n.toString(), newVal.toString());
    		}
    	}
//        # Level 0 cell names stay the same.
//        for c in parametrisations[self.crs]["zero_cells"]:
//            f[c] = c
    	for (String c: this.rhpp.zero_cells) {
    		f.put(c, c);
    	}
//        quarter_turns = quarter_turns % 4
    	quarter_turns = quarter_turns % 4;
//        if quarter_turns == 1:
//            return f[x]
    	if (quarter_turns.equals(1)) {
    		return f.get(suid_substring);
    	}
//        elif quarter_turns == 2:
//            return f[f[x]]
    	if (quarter_turns.equals(2)) {
    		return f.get(f.get(suid_substring));
    	}
//        elif quarter_turns == 3:
//            return f[f[f[x]]]
    	if (quarter_turns.equals(3)) {
    		return f.get(f.get(f.get(suid_substring)));
    	}
//        else:
//            return x
		else {
			return suid_substring;
		}
    }
    
//  def child_order(self):
//  # using code from https://github.com/manaakiwhenua/rhealpixdggs-py
//  child_order = {}
//  for (row, col) in product(list(range(self.N)), repeat=2):
//      order = row * self.N + col
//      # Handy to have both coordinates and order as dictionary keys.
//      child_order[(row, col)] = order
//  return child_order
    // use nested hashmaps as having int array as key in java is not worth the complication
    public HashMap<Integer, HashMap<Integer, Integer>> child_order()
    {
    	int N = this.rhpp.N;
        HashMap<Integer, HashMap<Integer, Integer>> outer_map = new HashMap<>();
    	for (Integer i=0; i<N; i++) {
            HashMap<Integer, Integer> inner_map = new HashMap<>();
    		for (Integer j=0; j<N; j++) {
                Integer order = i * N + j;
    			inner_map.put(j, order);
    		}
    		outer_map.put(i, inner_map);
    	}
        return outer_map;
    }
////    			int[] intarray = new int[]{i,j};
////    	        System.out.println(Arrays.toString(intarray));
//                outer_map.put(i, (order, order))
////                System.out.println(order.toString());
//                child_order_map.put(intarray.toString(), order);
    
    public CellCollection neighbours(Boolean...  include_diagonals_opt) {
    	    	
    	Boolean include_diagonals = include_diagonals_opt.length > 0 ? include_diagonals_opt[0] : true;
		List<String> neighbours_suids = new ArrayList<String>();
//	    ln, lr = self.neighbour("left", include_rotation=True)
//	    rn, rr = self.neighbour("right", include_rotation=True)
//	    un = self.neighbour("up")
//	    dn = self.neighbour("down")
    	Map<Cell, Integer> left = this.neighbour_with_rotation("left");
    	Map<Cell, Integer> right = this.neighbour_with_rotation("right");
    	neighbours_suids.add(this.neighbour("up").suid);
    	neighbours_suids.add(this.neighbour("down").suid);
    	neighbours_suids.add(this.neighbour("left").suid);
    	neighbours_suids.add(this.neighbour("right").suid);
		//  neighbours.extend([ln, rn, un, dn])
		//  if (
		//      include_diagonals and self.resolution > 0
		//  ):  # no diagonals at resolution 0 as it's a cube
		//      for cell, rotation in zip([ln, rn], [lr, rr]):
		//          if rotation in [1, 3]:
		//              neighbours.append(cell.neighbour("left"))
		//              neighbours.append(cell.neighbour("right"))
		//          else:
		//              neighbours.append(cell.neighbour("up"))
		//              neighbours.append(cell.neighbour("down"))
		//  return CellCollection(neighbours)
    	if (include_diagonals && this.resolution > 0) {
    		// left
	        for (Map.Entry<Cell, Integer> iterleft : left.entrySet()) {
	        	if (Stream.of(1,3).anyMatch(iterleft.getValue()::equals)) {
	        		neighbours_suids.add(iterleft.getKey().neighbour("left").suid);
	        		neighbours_suids.add(iterleft.getKey().neighbour("right").suid);
	        	}
	        	else {
	        		neighbours_suids.add(iterleft.getKey().neighbour("up").suid);
	        		neighbours_suids.add(iterleft.getKey().neighbour("down").suid);
	        		}
	        	};
	        }
	        // right
	        for (Map.Entry<Cell, Integer> iterright: right.entrySet()) {
	        	if (Stream.of(1,3).anyMatch(iterright.getValue()::equals)) {
	        		neighbours_suids.add(iterright.getKey().neighbour("left").suid);
	        		neighbours_suids.add(iterright.getKey().neighbour("right").suid);
	        	}
	        	else {
	        		neighbours_suids.add(iterright.getKey().neighbour("up").suid);
	        		neighbours_suids.add(iterright.getKey().neighbour("down").suid);
	        	}
	        }
	        return new CellCollection(String.join(" ", neighbours_suids));
    }
    
//  def border(self, resolution=None) -> Union[Cell, CellCollection]:
//  """
//  The set of cells that form the border of this cell, at a resolution at or higher than the cell's resolution.
//  NB a cells border *at* it's resolution *is* that cell
//  :return: Cell (for a border at the Cell's resolution) or CellCollection otherwise
//  """
//  if resolution == None:
//      return self
//  else:
//      resolution_delta = resolution - self.resolution
//      left_edge = product([0, 3, 6], repeat=resolution_delta)
//      right_edge = product([2, 5, 8], repeat=resolution_delta)
//      top_edge = product([0, 1, 2], repeat=resolution_delta)
//      bottom_edge = product([6, 7, 8], repeat=resolution_delta)
//      all_edges = list(
//          set(
//              chain.from_iterable(
//                  zip(left_edge, right_edge, top_edge, bottom_edge)
//              )
//          )
//      )
//  all_cells = CellCollection(
//      [self.__str__() + "".join([str(j) for j in i]) for i in all_edges]
//  )
//  return all_cells
    public CellCollection border(Integer... resolution_opt) {
		// resolution_delta is specified resolution - cell's resolution, else 1 less than cell's resolution 
		Integer resolution_delta = resolution_opt.length > 0 ? resolution_opt[0] - this.resolution: 0;
		List<String> border_suids = new ArrayList<String>();
		for (Map.Entry<String, String[]> iter: this.rhpp.borders.entrySet()) {
			Generator.permutation(iter.getValue())
	        .withRepetitions(resolution_delta)
	        .stream()
	        .forEach(perm -> border_suids.add(this.suid + String.join("", perm)));	
		}
		List<String> border_deduped = new ArrayList<>(new HashSet<>(border_suids));
        return new CellCollection(String.join(" ", border_deduped));
    }
 
}