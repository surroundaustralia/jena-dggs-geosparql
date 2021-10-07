package dggs.simple_features;

import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase2;


public class SFequals extends FunctionBase2
{
    public NodeValue exec(NodeValue nv1, NodeValue nv2)
    {
        return equalsfunc(nv1, nv2) ;
    }
	private NodeValue equalsfunc(NodeValue nv1, NodeValue nv2) {
		NodeValue returnval = nv2;
		if (nv1.equals(nv2)) {
			returnval = nv1;
		}
		return returnval;
	}
}

//public class SFequals {
//	public Boolean equals(String s1, String s2) {
//		return s1 == s2;
//	}
//}


