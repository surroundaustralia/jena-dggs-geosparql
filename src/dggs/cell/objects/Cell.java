package dggs.cell.objects;

import java.util.stream.Stream;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.expr.nodevalue.NodeValueNode;
import com.google.common.base.Objects;



public class Cell {
	
	public String suid;

	public Cell(String suid)
	{
		// validate the suid input
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
	
	public CellCollection add(Cell otherCell) {
		Cell[] cells = {this, otherCell};
	    return new CellCollection(cells);
	}
	
    @Override
    public int hashCode() {
        return this.suid.hashCode();
    }
	
	public Boolean equals(Cell otherCell) {
		if (this.suid.equals(otherCell.suid)) {
			return true;
		}
		else return false;
	}

}