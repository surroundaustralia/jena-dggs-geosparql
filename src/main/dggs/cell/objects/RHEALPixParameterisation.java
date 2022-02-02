package main.dggs.cell.objects;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class RHEALPixParameterisation {
	public String name;
	public Integer N;
	public Integer north_square;
	public Integer south_square;
	public List<String> zero_cells;
	
	public RHEALPixParameterisation(String name) {
		if (name == "auspix") {
			this.name = name;
			this.N = 3;
			this.north_square = 0;
			this.south_square = 0;
			this.zero_cells = new ArrayList<String>();
			this.zero_cells.add("N");
			this.zero_cells.add("O");
			this.zero_cells.add("P");
			this.zero_cells.add("Q");
			this.zero_cells.add("R");
			this.zero_cells.add("S"); 			
		}
	}
//	public void instantiate(HashMap parameterisation) {
//		this.name = (String) parameterisation.get("name");
//		this.N = (Integer) parameterisation.get("N");
//		this.north_square = (Integer) parameterisation.get("north_square");
//		this.south_square = (Integer) parameterisation.get("south_square");
//		this.zero_cells = (List<String>) parameterisation.get("zero_cells");
//	}
}
