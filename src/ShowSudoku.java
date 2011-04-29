import javax.swing.*;

public class ShowSudoku {
	public static void main(String[] arg){
		ISudokuSolver s = new SudokuSolver();
		s.setup(3);

		SudokuGUI g = new SudokuGUI(s);

		// Setup of the frame containing the puzzle
		JFrame f = new JFrame();
		f.setSize(800,650);
		f.setTitle("Sudoku Solver");
		f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(g);    
		f.setVisible(true);
	}
}

