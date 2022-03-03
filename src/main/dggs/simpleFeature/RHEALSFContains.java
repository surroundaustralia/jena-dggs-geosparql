package main.dggs.simpleFeature;

import main.dggs.cell.objects.CellCollection;

public class RHEALSFContains {
	public static boolean contains(String cells1, String cells2) {
		if (RHEALSFEquals.equals(cells1, cells2)) {
			return false;
		}
		else {
			CellCollection cc1 = new CellCollection(cells1);
			CellCollection cc2 = new CellCollection(cells2);
			CellCollection testcc = cc1.add(cc2);
			if (testcc.equals(new CellCollection(cells1))) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}

