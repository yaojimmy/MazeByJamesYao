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
		// pick initial position (x,y) at some random position on the maze
		int x = random.nextIntWithinInterval(0, width-1);
		int y = random.nextIntWithinInterval(0, height-1);
		// create an initial list of all wallboards that could be removed
		// those wallboards lead to adjacent cells that are not part of the spanning tree yet.
		final ArrayList<Wallboard> candidates = new ArrayList<Wallboard>();
		updateListOfWallboards(x, y, candidates);
		
		Wallboard curWallboard;
		// we need to consider each candidate wallboard and consider it only once
		while(!candidates.isEmpty()){
			// in order to have a randomized algorithm,
			// we randomly select and extract a wallboard from our candidate set
			// this also reduces the set to make sure we terminate the loop
			curWallboard = extractWallboardFromCandidateSetRandomly(candidates);
			// check if wallboard leads to a new cell that is not connected to the spanning tree yet
			if (floorplan.canTearDown(curWallboard))
			{
				// delete wallboard from maze, note that this takes place from both directions
				floorplan.deleteWallboard(curWallboard);
				// update current position
				x = curWallboard.getNeighborX();
				y = curWallboard.getNeighborY();
				
				floorplan.setCellAsVisited(x, y); // the flag is never reset, so this ensure we never go to (x,y) again
				updateListOfWallboards(x, y, candidates); // checks to see if it has wallboards to new cells, if it does it adds them to the list
				// note that each wallboard can get added at most once. This is important for termination and efficiency
			}
		}
	}
	/**
	 * Pick a random position in the list of candidates, remove the candidate from the list and return it
	 * @param candidates
	 * @return candidate from the list, randomly chosen
	 */
	private Wallboard extractWallboardFromCandidateSetRandomly(final ArrayList<Wallboard> candidates) {
		return candidates.remove(random.nextIntWithinInterval(0, candidates.size()-1)); 
	}
	

	/**
	 * Updates a list of all wallboards that could be removed from the maze based on wallboards towards new cells
	 * @param x
	 * @param y
	 */
	private void updateListOfWallboards(int x, int y, ArrayList<Wallboard> wallboards) {
		Wallboard wallboard = new Wallboard(x, y, CardinalDirection.East) ;
		for (CardinalDirection cd : CardinalDirection.values()) {
			wallboard.setLocationDirection(x, y, cd);
			if (floorplan.canTearDown(wallboard)) // 
			{
				wallboards.add(new Wallboard(x, y, cd));
			}
		}
	}

}