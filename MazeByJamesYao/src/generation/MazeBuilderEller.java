package generation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class MazeBuilderEller extends MazeBuilder implements Runnable {
	
	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
	}
	
	private class Cel{
		int x;
		int y;
		public Cel(int a, int b) {
			x = a;
			y = b;
		}
		@Override
		public boolean equals(Object other) {
			// trivial special cases
			if (this == other)
				return true ;
			if (null == other)
				return false ;
			if (getClass() != other.getClass())
				return false ;
			// general case
			final Cel o = (Cel)other;
			if (x != o.x && y != o.y) {
				return false;
			}
			return true;
		}
		@Override
		public int hashCode() {
			  assert false : "hashCode not designed";
			  return 42; // any arbitrary constant will do
		}
	}

	/**
	 * This method creates the Maze one row at a time, where once a row has been generated, the algorithm 
	 * no longer looks at it. Each cell in a row is contained in a set, where two cells are in the same 
	 * set if there is a path between them through the part of the Maze made so far.
	 * Creating row consists of two parts: randomly connecting adjacent cells within a row, and randomly 
	 * connecting cells between the current and next row.
	 * Horizontal: don't connect cells in same set. When connecting, Union sets.
	 * Vertical: must create a cell if it's a set of size one. When not connecting, make put in set by itself.
	 */
	// not implemented yet, just created Eller description from copy of MazeBuilderPrim for now
	@Override
	protected void generatePathways() {
		// stores sets for cells
		Set[][] sets = new Set[width][height];
		// loop through the rows
		for(int y = 0; y < height-1; y++) {
			// join cells not members of a set to their own set
			for(int i = 0; i < width; i++) {
				if (sets[i][y].size() == 0) {
					sets[i][y] = new HashSet<Cel>();
					sets[i][y].add(new Cel(i,y));
				}
			}
			// randomly join adjacent cells not in same set
			int numtimesx = random.nextIntWithinInterval(0, width-1);
			for (int i = 0; i < numtimesx; i++) {
				int x = random.nextIntWithinInterval(0, width-1);
				// checks if already a member of a set
				Cel c = new Cel(i, y);
				if (sets[i+1][y].contains(c)) {
					// merge cells of both sets in a single set, delete Wallboard
					mergeRight(sets, x, y);
				}
			}
			// randomly create vertical connections with every remaining set in row
			int numtimesy = random.nextIntWithinInterval(0, width-1);
			for (int i = 0; i < numtimesy; i++) {
				//merge cells of both sets in a single set, delete Wallboard
				int x = random.nextIntWithinInterval(0, width-1);
				mergeDown(sets, x, y);
			}
		}
		// join cells not members of a set to their own set
		for (int i = 0; i < width; i++) {
		// checks if already a member of a set
			if (sets[i][height-1].size() == 0) {
				sets[i][height-1] = new HashSet<Cel>();
				sets[i][height-1].add(new Cel(i,height-1));
			}
		}
		// connect all adjacent and disjoint cells in last row
		for (int i = 0; i < width-1; i++) {
			Cel c = new Cel(i, height-1);
			if (!sets[i+1][height-1].contains(c)) {
				mergeRight(sets, i, height-1);
			}
		}
	}
	
	// merges cells horizontally
	public void mergeRight(Set[][] s, int x, int y) {
		s[x][y].addAll(s[x+1][y]);
		s[x+1][y].addAll(s[x][y]);
		Wallboard wallboard = new Wallboard(x, y, CardinalDirection.East);
		if(floorplan.canTearDown(wallboard)) {
			floorplan.deleteWallboard(wallboard);
		}
	}
	
	// merges cells vertically
	public void mergeDown(Set[][] s, int x, int y) {
		s[x][y].addAll(s[x][y+1]);
		s[x][y+1].addAll(s[x][y]);
		Wallboard wallboard = new Wallboard(x, y, CardinalDirection.South);
		if(floorplan.canTearDown(wallboard)) {
			floorplan.deleteWallboard(wallboard);
		}
	}

}