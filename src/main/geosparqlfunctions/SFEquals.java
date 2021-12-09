package main.geosparqlfunctions;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;

import main.dggs.cell.objects.CellCollection;

public class SFEquals extends FunctionBase2{

	public SFEquals() { super() ; }

	@Override
	public NodeValue exec(NodeValue geom_lit1, NodeValue geom_lit2) {
		CellCollection cc1 = new CellCollection(geom_lit1.asString());
		CellCollection cc2 = new CellCollection(geom_lit2.asString());
		if (cc1.equals(cc2))
			{return NodeValue.TRUE ;}
		return NodeValue.FALSE ;
	}
}
