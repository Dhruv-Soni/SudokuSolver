package sudoku;

import java.util.*;

// solves a sudoku to get solutions
public class Solver {
	private Grid problem;
	private ArrayList<Grid> solutions;

	public Solver(Grid problem) {
		this.problem = problem;
	}

	// recurse the incomplete solutions
	public void solve() {
		solutions = new ArrayList<>();
		solveRecurse(problem);
	}

	// Standard backtracking recursive solver.
	private void solveRecurse(Grid grid) {
		Evaluation eval = evaluate(grid);

		// abandon if illegal
		if (eval == Evaluation.ABANDON) {
			return;
			// accept if solution is complete and legal
		} else if (eval == Evaluation.ACCEPT) {
			solutions.add(grid);

			// continue to recurse, if incomplete but legal
		} else {
			ArrayList<Grid> nextBoards = grid.next9Grids();
			for (Grid nextBoard : nextBoards) {
				solveRecurse(nextBoard);
			}
		}

	}

	// Returns Evaluation.ABANDON if the grid is illegal.
	// Returns ACCEPT if the grid is legal and complete.
	// Returns CONTINUE if the grid is legal and incomplete.
	//
	public Evaluation evaluate(Grid grid) {

		if (!grid.isLegal())
			return Evaluation.ABANDON;
		else if (grid.isLegal() && grid.isFull()) {
			return Evaluation.ACCEPT;
		} else {
			return Evaluation.CONTINUE;
		}
	}

	// returns the solutions for the sudoku
	public ArrayList<Grid> getSolutions() {
		return solutions;
	}

	public static void main(String[] args) {
		Grid g = TestGridSupplier.getInkala(); // or any other puzzle
		Solver solver = new Solver(g);
		System.out.println("Start");
		solver.solve();
		System.out.println("Start");
		System.out.println(solver.getSolutions());

		// Print out your solution, or test if it equals() the solution in
		// TestGridSupplier.
		//System.out.println(solver.getSolutions().get(0).equals(TestGridSupplier.getSolution2()));

	}
}