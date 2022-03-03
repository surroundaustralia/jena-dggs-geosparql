package main.dggs.simpleFeature;


import main.dggs.cell.objects.CellCollection;

public class RHEALSFDisjoint {
	public static boolean disjoint(String cells1, String cells2) {
		CellCollection cc1 = new CellCollection(cells1);
		CellCollection cc2 = new CellCollection(cells2);
		if ( (!cc1.overlaps(cc2)) && (!RHEALSFTouches.touches(cells1, cells2)) ) {
			return true;
		}
		return false;
	}
}
