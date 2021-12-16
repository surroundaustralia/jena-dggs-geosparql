package main.dggs.simpleFeature;

import main.dggs.cell.objects.CellCollection;

public class RHEALSFContains {
	public static boolean contains(String cells1, String cells2) {
		if (RHEALSFEquals.equals(cells1, cells2)) {
			return false;
		}
		else {
			String together = String.join(cells1, cells2);
			CellCollection testcc = new CellCollection(together);
			if (testcc.equals(new CellCollection(cells1))) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}

