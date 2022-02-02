package main.dggs.cell.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.dggs.cell.objects.RHEALPixParameterisation;

public class AtomicNeighbours {
    public static HashMap<String, HashMap<String, String>> get_an() {
    // rhpp = rHEALPix parameterisation
    RHEALPixParameterisation rhpp = new RHEALPixParameterisation("auspix");
//      # rhpp.north_square = rhpp.south_square = 0
//  	int rhpp.north_square = 0;
//  	int rhpp.south_square = 0;
//  	Integer N = 3;	
//      # zero_cells = parametrisations[self.crs]["zero_cells"]
//  	String rhpp.zero_cells = "NOPQRS";
//      #
//      # # Taken from the rHEALPix DGGS repository
//      #
//      # # Initialize atomic neighbour relationships among suid.
//      # # Dictionary of up, right, down, and left neighbours of
//      # # resolution 0 suid and their subcells 0--(N_side**2 -1),
//      # # aka the atomic neighbours.
//      # # Based on the layouts
//      # #
//      # #   0
//      # #   1 2 3 4   (but folded into a cube) and
//      # #   5
//      # #
//      # #   0 1 2
//      # #   3 4 5
//      # #   6 7 8   (example for N_side=3).
//      # #
//      # an = {}
  	// HashMap<String, HashMap<Float,HashMap<Float, String>>> map = new HashMap<>();

  	HashMap<String, HashMap<String, String>> an = new HashMap<>();
//    # # neighbours of zero_cells[1], ..., zero_cells[4]
//    # an[zero_cells[1]] = {
//    #     "left": zero_cells[4],
//    #     "right": zero_cells[2],
//    #     "down": zero_cells[5],
//    #     "up": zero_cells[0],
//    #     }
  	HashMap<String,String> inner1 = new HashMap<String, String>();
  	inner1.put("left", rhpp.zero_cells.get(4));
  	inner1.put("right", rhpp.zero_cells.get(2));
  	inner1.put("down", rhpp.zero_cells.get(5));
  	inner1.put("up", rhpp.zero_cells.get(0));
  	an.put(rhpp.zero_cells.get(1), inner1);
//      # an[zero_cells[2]] = {
//      #     "left": zero_cells[1],
//      #     "right": zero_cells[3],
//      #     "down": zero_cells[5],
//      #     "up": zero_cells[0],
//      #     }
  	HashMap<String,String> inner2 = new HashMap<String, String>();
  	inner2.put("left", rhpp.zero_cells.get(1));
  	inner2.put("right", rhpp.zero_cells.get(3));
  	inner2.put("down", rhpp.zero_cells.get(5));
  	inner2.put("up", rhpp.zero_cells.get(0));
  	an.put(rhpp.zero_cells.get(2), inner2);
//      # an[zero_cells[3]] = {
//      #     "left": zero_cells[2],
//      #     "right": zero_cells[4],
//      #     "down": zero_cells[5],
//      #     "up": zero_cells[0],
//      #     }
  	HashMap<String,String> inner3 = new HashMap<String, String>();
  	inner3.put("left", rhpp.zero_cells.get(2));
  	inner3.put("right", rhpp.zero_cells.get(4));
  	inner3.put("down", rhpp.zero_cells.get(5));
  	inner3.put("up", rhpp.zero_cells.get(0));
  	an.put(rhpp.zero_cells.get(3), inner3);
//      # an[zero_cells[4]] = {
//      #     "left": zero_cells[3],
//      #     "right": zero_cells[1],
//      #     "down": zero_cells[5],
//      #     "up": zero_cells[0],
//      #     }
  	HashMap<String,String> inner4 = new HashMap<String, String>();
  	inner4.put("left", rhpp.zero_cells.get(3));
  	inner4.put("right", rhpp.zero_cells.get(1));
  	inner4.put("down", rhpp.zero_cells.get(5));
  	inner4.put("up", rhpp.zero_cells.get(0));
  	an.put(rhpp.zero_cells.get(4), inner4);
//      # # neighbours of zero_cells[0] and zero_cells[5] depend on
//      # # volues of rhpp.north_square and rhpp.south_square, respectively.
//      # nn = rhpp.north_square
//      # an[zero_cells[0]] = {
//      #     "down": zero_cells[(nn + 0) % 4 + 1],
//      #     "right": zero_cells[(nn + 1) % 4 + 1],
//      #     "up": zero_cells[(nn + 2) % 4 + 1],
//      #     "left": zero_cells[(nn + 3) % 4 + 1],
//      #     }
  	HashMap<String,String> inner0 = new HashMap<String, String>();
  	inner0.put("down", rhpp.zero_cells.get((rhpp.north_square + 0) % 4 + 1));
  	inner0.put("right", rhpp.zero_cells.get((rhpp.north_square + 1) % 4 + 1));
  	inner0.put("up", rhpp.zero_cells.get((rhpp.north_square + 2) % 4 + 1));
  	inner0.put("left", rhpp.zero_cells.get((rhpp.north_square + 3) % 4 + 1));
  	an.put(rhpp.zero_cells.get(0), inner0);
//      # ss = rhpp.south_square
//      # an[zero_cells[5]] = {
//      #     "up": zero_cells[(ss + 0) % 4 + 1],
//      #     "right": zero_cells[(ss + 1) % 4 + 1],
//      #     "down": zero_cells[(ss + 2) % 4 + 1],
//      #     "left": zero_cells[(ss + 3) % 4 + 1],
//      #     }
//      #
  	HashMap<String,String> inner5 = new HashMap<String, String>();
  	inner5.put("up", rhpp.zero_cells.get((rhpp.south_square + 0) % 4 + 1));
  	inner5.put("right", rhpp.zero_cells.get((rhpp.south_square + 1) % 4 + 1));
  	inner5.put("down", rhpp.zero_cells.get((rhpp.south_square + 2) % 4 + 1));
  	inner5.put("left", rhpp.zero_cells.get((rhpp.south_square + 3) % 4 + 1));
  	an.put(rhpp.zero_cells.get(5), inner5);
//      # # neighbours of 0, 1, ..., N**2 - 1.
//      # for i in range(N ** 2):
//      #     an[i] = {
//      #         "left": i - 1,
//      #         "right": i + 1,
//      #         "up": (i - N) % N ** 2,
//      #         "down": (i + N) % N ** 2,
//      #         }
  	for (Integer i = 0; i < Math.pow(rhpp.N, 2); i++) {
  		HashMap<String,String> innerloop = new HashMap<String, String>();
      	// left
      	Integer leftint = i-1;
      	if (i % rhpp.N == 0) {
      		leftint = leftint + rhpp.N;
      	} 
      	innerloop.put("left", leftint.toString());
      	// right
      	Integer rightint = i+1;
      	if (i % rhpp.N == rhpp.N - 1) {
      		rightint = rightint - rhpp.N;
      	} 
      	innerloop.put("right", rightint.toString());
      	// down
      	// java mod (a % b + b) % b
      	Integer downint_a = (int) (i + rhpp.N);
      	Integer b = (int) Math.pow(rhpp.N, 2);
      	Integer downint = (int) (downint_a % b + b) % b;
      	innerloop.put("down", downint.toString());
      	// up
      	Integer upint_a = (int) (i - rhpp.N);
      	Integer upint = (int) (upint_a % b + b) % b;
      	innerloop.put("up", upint.toString());
  		an.put(i.toString(), innerloop);
  	}
  	// the below has been included above 'inline'
//      # # Adjust left and right edge cases.
//      # for i in range(0, N ** 2, N):
//      #     an[i]["left"] = an[i]["left"] + N
//      # for i in range(N - 1, N ** 2, N):
//      #     an[i]["right"] = an[i]["right"] - N
		return an;
  }

}
