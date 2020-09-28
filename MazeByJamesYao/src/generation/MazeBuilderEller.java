package generation;

import java.util.HashSet;
import java.util.Set;


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
		// stores set number at coordinate
		Integer[][] set_nums = new Integer[width][height];
		int cur_num = 0;
		
		// loop through the rows (except last row)
		for(int y = 0; y < height-1; y++) {
			
			// set cells not members of a set to their own set
			for(int i = 0; i < width; i++) {
				if (set_nums[i][y] == null) {
					// if adjacent cells have no walls, merge
					if (this.floorplan.hasNoWall(i, y, CardinalDirection.West)) {
						set_nums[i][y] = cur_num;
						mergeRight(set_nums, i-1, y);
					}
					else {
						set_nums[i][y] = cur_num;
						cur_num++;
					}
				}
			}
			
			// randomly join adjacent cells not in same set
			// printMaze();
			int numtimesx = random.nextIntWithinInterval(0, width-1);
			for (int i = 0; i < numtimesx; i++) {
				int x = random.nextIntWithinInterval(0, width-2);
				
				// if not in same set, merge
				if (set_nums[x][y] != set_nums[x+1][y]) {
					// merge cells of both sets in a single set, delete Wallboard
					mergeRight(set_nums, x, y);
					// printMaze();
				}
			}
			// printMaze();
			
			// randomly create vertical connections with every set
			int numtimesv = random.nextIntWithinInterval(0, width-1);
			Set<Integer> used = new HashSet<Integer>();
			
			for (int j = 0; j < numtimesv; j++) {
				int x = random.nextIntWithinInterval(0, width-2);
				used.add(set_nums[x][y]);
				mergeDown(set_nums, x, y);
			}
			// makes sure every set has a vertical connection
			for (int j = 0; j < width; j++) {
				if (!used.contains(set_nums[j][y])) {
					used.add(set_nums[j][y]);
					mergeDown(set_nums, j, y);
				}
				// if no wallboard between, then has a vertical connection
				// accounting for rooms
				if (this.floorplan.hasNoWall(j, y, CardinalDirection.South))
					mergeDown(set_nums, j, y);
			}
			// printMaze();
		}
		// join cells not members of a set to their own set
		// printMaze();
		for (int i = 0; i < width; i++) {
		// if not already part of set, make it
			if (set_nums[i][height-1] == null) {
				if (this.floorplan.hasNoWall(i, height-1, CardinalDirection.West)) {
					set_nums[i][height-1] = cur_num;
					mergeRight(set_nums, i-1, height-1);
				}
				else {
					set_nums[i][height-1] = cur_num;
					cur_num++;
				}
			}
		}
		// printMaze();
		// connect all adjacent and disjoint cells in last row
		for (int i = 0; i < width-1; i++) {
			if (set_nums[i+1][height-1] != set_nums[i][height-1]) {
				mergeRight(set_nums, i, height-1);
			}
		}
		printMaze();
	}
	
	// merges cells horizontally
	private void mergeRight(Integer[][] s_nums, int x, int y) {
		// change set number of all values in old set into merged one
		int old_set_num = s_nums[x+1][y];
		int merged_set_num = s_nums[x][y];
		// change all old set numbers in row to merged set number
		for (int i = 0; i < width; i++) {
			if (s_nums[i][y] != null && s_nums[i][y] == old_set_num) {
				s_nums[i][y] = merged_set_num;
			}
		}
		// tear down wallboard between them
		Wallboard wallboard = new Wallboard(x, y, CardinalDirection.East);
		if(floorplan.canTearDown(wallboard)) {
			floorplan.deleteWallboard(wallboard);
		}
	}
	
	// merges cells vertically
	private void mergeDown(Integer[][] s_nums, int x, int y) {
		// set below number equal to current number
		s_nums[x][y+1] = s_nums[x][y];
		// tear down wallboard between them
		Wallboard wallboard = new Wallboard(x, y, CardinalDirection.South);
		if(floorplan.canTearDown(wallboard)) {
			floorplan.deleteWallboard(wallboard);
		}
	}
	
	// prints maze
	private void printMaze() {
		for (int y = this.height-1; y >= 0; y--) {
			for (int x = 0; x < this.width; x++) {
				System.out.print(x + "," + y + ":");
				if (this.floorplan.hasWall(x, y, CardinalDirection.North))
					System.out.print("S");
				if (this.floorplan.hasWall(x, y, CardinalDirection.East))
					System.out.print("E");
				if (this.floorplan.hasWall(x, y, CardinalDirection.South))
					System.out.print("N");
				if (this.floorplan.hasWall(x, y, CardinalDirection.West))
					System.out.print("W");
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}

}