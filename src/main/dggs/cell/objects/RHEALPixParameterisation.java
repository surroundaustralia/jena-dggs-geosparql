package main.dggs.cell.objects;
import java.util.List;
import java.util.stream.IntStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class RHEALPixParameterisation {
	public String name;
	public Integer N;
	public Integer north_square;
	public Integer south_square;
	public List<String> zero_cells;
	public List<String> suffixes;
	public HashMap<String, String[]> borders;
	
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
			IntStream raw_suffixes = IntStream.range(0, (int) Math.pow(this.N, 2));
			this.suffixes = raw_suffixes.boxed().map(Object::toString).collect(Collectors.toList());
			this.borders = this.get_border(this.N);
		}
	}
	
	public HashMap<String, String[]> get_border(Integer N){
    	int[] up_border = new int[N];
    	int[] down_border = new int[N];
    	int[] left_border = new int[N];
    	int[] right_border = new int[N];
    	IntStream.range(0, N).forEachOrdered(i -> {
    		up_border[i] = i;
    		down_border[i] = (N - 1) * N + i;
    		left_border[i] = i * N;
    		right_border[i] = (i + 1) * N - 1;
    	});
        String[] up_border_string = Arrays.stream(up_border).mapToObj(String::valueOf).toArray(String[]::new);
        String[] down_border_string = Arrays.stream(down_border).mapToObj(String::valueOf).toArray(String[]::new);
    	String[] left_border_string = Arrays.stream(left_border).mapToObj(String::valueOf).toArray(String[]::new);
    	String[] right_border_string = Arrays.stream(right_border).mapToObj(String::valueOf).toArray(String[]::new);
      	HashMap<String,String[]> border_string = new HashMap<String, String[]>();
      	border_string.put("left", left_border_string);
      	border_string.put("right", right_border_string);
      	border_string.put("up", up_border_string);
      	border_string.put("down", down_border_string);
		return border_string;
	}
}
