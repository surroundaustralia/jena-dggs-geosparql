package main.dggs.simpleFeature;


import main.dggs.cell.objects.CellCollection;

public class RHEALSFTouches {
	public static boolean touches(String cells1, String cells2) {
		// if equal then do not touch
		if (RHEALSFEquals.equals(cells1, cells2)) {
			return false;
		}
		CellCollection cc1 = new CellCollection(cells1);
		CellCollection cc2 = new CellCollection(cells2);
		// if regional overlap, then do not touch
		if (cc1.overlaps(cc2)) {
			return false;
		}
		// find the max resolution among the two cell collections
		int max_res = Math.max(cc1.max_resolution, cc2.max_resolution);
		// 
		CellCollection smaller_cc_neighbours = new CellCollection();
		CellCollection larger_cc_children = new CellCollection();
		if (cc1.suids.length() < cc2.suids.length()) {
			smaller_cc_neighbours = smaller_cc_neighbours.add(cc1.neighbours(max_res));
			larger_cc_children = larger_cc_children.add(cc2.children(max_res));
		}
		else {
			smaller_cc_neighbours = smaller_cc_neighbours.add(cc2.neighbours(max_res));
			larger_cc_children = larger_cc_children.add(cc1.children(max_res));		
		}
		if (smaller_cc_neighbours.overlaps(larger_cc_children)) {
			return true;
		}

		return false;
	}
}

//if cls.sfEquals(cells_one, cells_two):
//    return False
//# if the geometries have regional intersection, they do not touch, they have some other spatial relationship
//SF = cls(cells_one, cells_two)
//if region_region_intersection(SF.coll_1.suids, SF.coll_2.suids):
//    return False
//# find the max resolution (smallest cells) among the two geometries
//if SF.coll_1.max_resolution > SF.coll_2.max_resolution:
//    max_resolution = SF.coll_1.max_resolution
//else:
//    max_resolution = SF.coll_2.max_resolution
//# estimate which geometry is smaller based on their lengths
//if len(SF.coll_1) < len(SF.coll_2):
//    smaller_collection_neighbours = SF.coll_1.neighbours(max_resolution)
//    larger_collection = SF.coll_2.suids
//else:
//    smaller_collection_neighbours = SF.coll_2.neighbours(max_resolution)
//    larger_collection = SF.coll_1.suids
//# if the neighbouring cells of a geometry are common to the other geometry then they touch
//# with the caveat that we must use neighbours at the maximum (smallest cells) resolution
//# .. and we need to filter on geometries that overlap in the first place
//if common_cells(smaller_collection_neighbours.suids, larger_collection):
//    return True