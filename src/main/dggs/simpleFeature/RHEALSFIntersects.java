package main.dggs.simpleFeature;


public class RHEALSFIntersects {
	public static boolean intersects(String cells1, String cells2) {
		if ( 
				(RHEALSFDisjoint.disjoint(cells1, cells2)) || 
				(RHEALSFEquals.equals(cells1, cells2)) ||
				(RHEALSFContains.contains(cells1, cells2)) ||
				(RHEALSFWithin.within(cells1, cells2)) ||
				(RHEALSFTouches.touches(cells1, cells2))
				) {
			return true;
		}
		return false;
	}
}
