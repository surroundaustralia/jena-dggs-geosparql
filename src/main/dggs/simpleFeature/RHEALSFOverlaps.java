package main.dggs.simpleFeature;


public class RHEALSFOverlaps {
	public static boolean overlaps(String cells1, String cells2) {
		if ( 
				(!RHEALSFDisjoint.disjoint(cells1, cells2)) && 
				(!RHEALSFEquals.equals(cells1, cells2)) &&
				(!RHEALSFContains.contains(cells1, cells2)) &&
				(!RHEALSFWithin.within(cells1, cells2)) &&
				(!RHEALSFTouches.touches(cells1, cells2))
				) {
			return true;
		}
		return false;
	}
}
