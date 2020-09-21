package generation;

import java.util.ArrayList;


public class MazeBuilderEller extends MazeBuilder implements Runnable {
	
	public MazeBuilderEller() {
		super();
		System.out.println("MazeBuilderEller uses Eller's algorithm to generate maze.");
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
		// initialize cells of first row to exist in their own sets
		// loop through the rows
		for(int y = 0; y < height; y++) {
			// randomly join adjacent cells not in same set
			// merge cells of both sets in a single set
			// randomly create vertical connections with every remaining set in row
			// put remaining cells in their own sets
		}
		// connect all adjacent and disjoint cells in last row
	}

}