
package sudoku;

import java.util.*;

//Models a sudoku grid
public class Grid {

	private int[][] values;

	// See TestGridSupplier for examples of input.
	// Dots in input strings become 0s in values[][].
	// Constructs a grid with an array of rows
	public Grid(String[] rows) {
		values = new int[9][9];
		for (int j = 0; j < 9; j++) {
			String row = rows[j];
			char[] charray = row.toCharArray();
			for (int i = 0; i < 9; i++) {
				char ch = charray[i];
				if (ch != '.')
					values[j][i] = ch - '0';
			}
		}
	}

	// Copies grid into a new grid
	public Grid(Grid src) {
		values = new int[src.values.length][src.values[0].length];
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
				values[i][j] = src.values[i][j];
			}
		}
	}

	// a method to change an array to a string
	public String toString() {
		String s = "";
		for (int j = 0; j < 9; j++) {
			for (int i = 0; i < 9; i++) {
				int n = values[j][i];
				if (n == 0)
					s += '.';
				else
					s += (char) ('0' + n);
			}
			s += "\n";
		}
		return s;
	}

	// Gets the next empty cell in grid
	private int[] getIndicesOfNextEmptyCell() {
		int[] yx = new int[2];

		for (yx[0] = 0; yx[0] < values.length; yx[0]++)
			for (yx[1] = 0; yx[1] < values.length; yx[1]++)
				if (values[yx[0]][yx[1]] == 0)
					return yx;

		return null;
	}

	// Finds an empty member of values[][]. Returns an array list of 9 grids
	// that look like the current grid,
	// except the empty member contains 1, 2, 3 .... 9. Returns null if the
	// current grid is full.
	//
	// Example: if this grid = 1........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	// .........
	//
	// Then the returned array list would contain:
	//
	// 11....... 12....... 13....... 14....... and so on 19.......
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// ......... ......... ......... ......... .........
	// Generates the next 9 grids with digits 1-9
	public ArrayList<Grid> next9Grids() {
		int[] indicesOfNext = getIndicesOfNextEmptyCell();
		ArrayList<Grid> nextGrids = new ArrayList<>();

		for (int n = 1; n <= values.length; n++) {
			Grid grid = new Grid(this);
			grid.values[indicesOfNext[0]][indicesOfNext[1]] = n;
			nextGrids.add(grid);
		}

		return nextGrids;
	}

	// Returns true if the input list contains a repeated value that isn't zero.
	private boolean containsNonZeroRepeats(ArrayList<Integer> ints) {
		// remove 0
		ArrayList<Integer> remove = new ArrayList<Integer>();
		remove.add(0);
		ints.removeAll(remove);

		HashSet<Integer> noDuplicates = new HashSet<Integer>(ints);
		// If sizes don't differ than there we no non zero repeats
		if (ints.size() == noDuplicates.size()) {
			return true;
		}
		return false;
	}

	// Returns true if this grid is legal. A grid is legal if no row, column, or
	// zone contains
	// a repeated 1, 2, 3, 4, 5, 6, 7, 8, or 9.
	//
	public boolean isLegal() {

		// checks the rows
		for (int i = 0; i < values.length; i++) {
			ArrayList<Integer> row = new ArrayList<Integer>();
			for (int j = 0; j < values[0].length; j++) {
				row.add(values[i][j]);
				if (row.size() == values[0].length) {
					if (!containsNonZeroRepeats(row)) {
						return false;
					}
				}
			}
		}

		// Checks the columns
		for (int j = 0; j < values[0].length; j++) {
			ArrayList<Integer> column = new ArrayList<Integer>();
			for (int i = 0; i < values.length; i++) {
				column.add(values[i][j]);
				if (column.size() == values.length) {
					if (!containsNonZeroRepeats(column)) {
						return false;
					}
				}
			}
		}

		 //Checks zones
//		 for (int z = 0; z < 9; z++) {
//		 int r = 3 * (z / 3);
//		 int ro = r + 2;
//		 int c = 3 * (z % 3);
//		 int co = c + 2;
//		
//		 ArrayList<Integer> zones = new ArrayList<>();
//		 for (int j = c; j <= co; j++) {
//		 for (int i = r; i <= ro; i++) {
//		 zones.add(values[j][i]);
//		 }
//		 }
//		 if (!containsNonZeroRepeats(zones)) {
//		 // System.out.println("error in zones");
//		 return false;
//		 }
//		
//		 }
//		 return true;

		ArrayList<Integer> y = new ArrayList<Integer>();
		int m = 3;
		int k = 3;
		int j = 0;
		int a = 0;
		int b = 0;
		int count = 0;
		for (int i = a; i < m; i++) {
			if(i == 9)
			{
				break;
			}
			for (j = b; j < k; j++) {

				y.add(values[i][j]);
				count++;
			}
			ArrayList<Integer> mo;
			if (y.size() == 9) {
				mo = new ArrayList<>(y);
				if (!containsNonZeroRepeats(mo)) {
					return false;
				}
				y.clear();

				m = m + 3;

				count = 0;
				if (m > 9 && j == 3) {
					m = 3;
					b = 3;
					a = 0;
					k = k + 3;
					i=-1;
					
					count = 0;
				} else if (m > 9 && j == 6) {
					m = 3;
					b=6;
					a = 0;
					k = k + 3;
					
					count = 0;
					i = -1;
				}

			} else if (count == 9 && y.size() == 8) {
				return false;
			}
			//System.out.println(y);

		}
		return true;
	}

	// Returns true if every cell member of values[][] is a digit from 1-9.
	public boolean isFull() {
		for (int i = 0; i < values.length; i++) {
			for (int j = 0; j < values[0].length; j++) {
				if (values[i][j] < 1 || values[i][j] > 9) {
					return false;
				}
			}
		}
		return true;
	}

	// Returns true if x is a Grid and, for every (i,j)
	public boolean equals(Grid x) {
		Grid that = (Grid) x;

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (that.values[i][j] == this.values[i][j]) {
					return true;
				}
			}
		}

		return false;

	}
}
