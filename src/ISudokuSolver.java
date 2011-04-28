
public interface ISudokuSolver {

	/**
	 * Gets the content of the puzzle as an 2 dimensional array.
	 * @return the soduko puzzle as an 2D array of integers. If there is inserted
	 * a value in the puzzle, then the corresponding entry in the array should
	 * have that value. If there is not inserted any value in the puzzle, the 
	 * corresponding entry of the array should be 0. The puzzle has entry (0,0)
	 * in the upper left corner.
	 */
	public int[][] getPuzzle();
	
	/**
	 * Inserts the specified value in the specified position in the puzzle. 
	 * Should check if the specified position in the puzzle is legal (ie. exists)
	 * and if the specified value is a legal value, and only insert the value
	 * if the parameters are legal.
	 * @param col the column that the value should be inserted in (starts with
	 * 0 in the leftmost column).
	 * @param row the row that the value should be inserted in (starting with
	 * 0 in the topmost row).
	 * @param value the value to insert. Note that this can be 0 to errase a 
	 * value from the real puzzle.
	 */
	public void setValue(int col, int row, int value);
	
	/**
	 * Initializes an empty puzzle of the specified size.
	 * @param size the size measured in blocks in a row, ie. a normal soduko
	 * will have a size of 3 (and NOT 9).
	 */
	public void setup(int size);
	
	/**
	 * Reads in a puzzle given as an array. It should be checked if the specified
	 * array has the right dimensions (ie. is "square" witht he right size) and 
	 * that the values are legal.
	 * @param p the puzzle that should be read in.
	 */
	public void readInPuzzle(int[][] p);
	
	/**
	 * Checks if the puzzle with the current content can be solved. If so, it
	 * solves the puzzle and remembers the "result", ie. when getPuzzle is called
	 * subsequently, it will return the content of the full solution. 
	 * @return true, if there exist a solution with the current content of the
	 * puzzle, false otherwise.
	 */
	public boolean solve();
}
