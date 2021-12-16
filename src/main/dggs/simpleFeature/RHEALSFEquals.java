package main.dggs.simpleFeature;

import main.dggs.cell.objects.CellCollection;

public class RHEALSFEquals {
	public static boolean equals(String cells1, String cells2) {
		CellCollection cc1 = new CellCollection(cells1);
		CellCollection cc2 = new CellCollection(cells2);
		if (cc1.equals(cc2))
			{return true ;}
		else {
			return false ;
		}
	}
}