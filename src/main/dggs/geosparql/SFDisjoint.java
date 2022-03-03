package main.dggs.geosparql;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;
import main.dggs.simpleFeature.RHEALSFDisjoint;

public class SFDisjoint extends FunctionBase2{

	public SFDisjoint() { super() ; }

	@Override
	public NodeValue exec(NodeValue geom_lit1, NodeValue geom_lit2) {
		if (RHEALSFDisjoint.disjoint(geom_lit1.asString(), geom_lit2.asString()))
			{return NodeValue.TRUE ;}
		else {
			return NodeValue.FALSE ;
		}
	}
}