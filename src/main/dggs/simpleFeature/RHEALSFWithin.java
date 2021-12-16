package main.dggs.simpleFeature;

import main.dggs.cell.objects.CellCollection;

public class RHEALSFWithin {
	public static boolean within(String cells1, String cells2) {
		if (RHEALSFEquals.equals(cells1, cells2)) {
			return false;
		}
		else {
			String together = String.join(cells1, cells2);
			CellCollection testcc = new CellCollection(together);
			if (testcc.equals(new CellCollection(cells2))) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}

