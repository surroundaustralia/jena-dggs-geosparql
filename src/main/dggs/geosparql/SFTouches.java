package main.dggs.geosparql;
import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;
import main.dggs.simpleFeature.RHEALSFTouches;

public class SFTouches extends FunctionBase2{

	public SFTouches() { super() ; }

	@Override
	public NodeValue exec(NodeValue geom_lit1, NodeValue geom_lit2) {
		if (RHEALSFTouches.touches(geom_lit1.asString(), geom_lit2.asString()))
			{return NodeValue.TRUE ;}
		else {
			return NodeValue.FALSE ;
		}
	}
}