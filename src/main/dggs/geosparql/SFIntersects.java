package main.dggs.geosparql;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;
import main.dggs.simpleFeature.RHEALSFIntersects;

public class SFIntersects extends FunctionBase2{

	public SFIntersects() { super() ; }

	@Override
	public NodeValue exec(NodeValue geom_lit1, NodeValue geom_lit2) {
		if (RHEALSFIntersects.intersects(geom_lit1.asString(), geom_lit2.asString()))
			{return NodeValue.TRUE ;}
		else {
			return NodeValue.FALSE ;
		}
	}
}