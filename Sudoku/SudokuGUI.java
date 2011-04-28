import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * A component that displays the puzzle of a soduko game. You can click on a piece
 * of the puzzle and insert a number by typing it. An assigned value can be changed
 * by inserting another number or by deleting the value. To delete a value, you 
 * should press space after clicking on the particular place. 
 * To see the solution of the sudoku, if one exists, you should press the solve-button. 
 * In this version of the class, you can only insert the numbers from 1 to 9.
 * @author Mai Ajspur
 */
public class SudokuGUI extends JComponent implements MouseListener, KeyListener{

	private Image 			background, active_cell, solve, notSolvable;
	private Image 			border_left, border_right,border_top, border_bottom;
	private Image 			corner_left_top, corner_left_bottom, corner_right_top, corner_right_bottom;
	private Image			one, two, three, four, five, six, seven, eight, nine;
	private ISudokuSolver 	solver;				
	private int 			cCol;
	private int 			cRow;
	private boolean 		canBeSolved;
	private final int		ins;	
	private final int		imSize;
	private JTextField 		typingArea;
	
	public SudokuGUI(ISudokuSolver solver)
	{
		background = Toolkit.getDefaultToolkit().getImage("imgs/background.png");	
		border_left = Toolkit.getDefaultToolkit().getImage("imgs/border_left.png");
		border_right = Toolkit.getDefaultToolkit().getImage("imgs/border_right.png");
		border_top = Toolkit.getDefaultToolkit().getImage("imgs/border_top.png");
		border_bottom = Toolkit.getDefaultToolkit().getImage("imgs/border_bottom.png");
		corner_left_top = Toolkit.getDefaultToolkit().getImage("imgs/corner_left_top.png");
		corner_left_bottom = Toolkit.getDefaultToolkit().getImage("imgs/corner_left_bottom.png");
		corner_right_top = Toolkit.getDefaultToolkit().getImage("imgs/corner_right_top.png");
		corner_right_bottom = Toolkit.getDefaultToolkit().getImage("imgs/corner_right_bottom.png");
		solve = Toolkit.getDefaultToolkit().getImage("imgs/solve.png");
		active_cell = Toolkit.getDefaultToolkit().getImage("imgs/active_background.png");
		notSolvable = Toolkit.getDefaultToolkit().getImage("imgs/notSolved.png");

		one = Toolkit.getDefaultToolkit().getImage("imgs/one.png");
		two = Toolkit.getDefaultToolkit().getImage("imgs/two.png");
		three = Toolkit.getDefaultToolkit().getImage("imgs/three.png");
		four = Toolkit.getDefaultToolkit().getImage("imgs/four.png");
		five = Toolkit.getDefaultToolkit().getImage("imgs/five.png");
		six = Toolkit.getDefaultToolkit().getImage("imgs/six.png");
		seven = Toolkit.getDefaultToolkit().getImage("imgs/seven.png");
		eight = Toolkit.getDefaultToolkit().getImage("imgs/eight.png");
		nine = Toolkit.getDefaultToolkit().getImage("imgs/nine.png");

		this.solver = solver;
		this.addMouseListener(this);
		typingArea = new JTextField(0);
        typingArea.addKeyListener(this);
        add(typingArea, BorderLayout.PAGE_START);
        ins = 150;
        cRow = -1;
        cCol = -1;
        imSize = 60;
        canBeSolved = true;
	}

	public void paint(Graphics g){
		this.setDoubleBuffered(true);
		Insets in = getInsets();               
		g.translate(in.left, in.top);            

		int[][] puzzle = this.solver.getPuzzle();
		int size = puzzle.length;
			
		for (int c = 0; c < size; c++){
			for (int r = 0; r < size; r++){
				int value = puzzle[c][r];
				g.drawImage(background, imSize*c+ins, imSize*r+ins, this);
				if(cCol == c && cRow == r)
					g.drawImage(active_cell, imSize*c+ins, imSize*r+ins, this);
				if ( value == 1 ) // background
					g.drawImage(one, imSize*c+ins, imSize*r+ins, this);
				else if ( value == 2) 
					g.drawImage(two, imSize*c+ins, imSize*r+ins, this);
				else if ( value == 3)
					g.drawImage(three, imSize*c+ins, imSize*r+ins, this);
				else if ( value == 4)
					g.drawImage(four, imSize*c+ins, imSize*r+ins, this);
				else if ( value == 5)
					g.drawImage(five, imSize*c+ins, imSize*r+ins, this);
				else if ( value == 6)
					g.drawImage(six, imSize*c+ins, imSize*r+ins, this);
				else if ( value == 7)
					g.drawImage(seven, imSize*c+ins, imSize*r+ins, this);
				else if ( value == 8)
					g.drawImage(eight, imSize*c+ins, imSize*r+ins, this);
				else if ( value == 9)
					g.drawImage(nine, imSize*c+ins, imSize*r+ins, this);
				if(c == 0){
					g.drawImage(border_left, -imSize+ins, imSize*r+ins, this); 
					g.drawImage(border_right, size*imSize+ins, imSize*r+ins, this); 
				}
			}
			g.drawImage(border_top, imSize*c+ins, -imSize+ins, this);
			g.drawImage(border_bottom, imSize*c+ins, size*imSize+ins, this);
		}
		g.drawImage(corner_left_top, -imSize+ins, -imSize+ins, this);
		g.drawImage(corner_left_bottom, -imSize+ins, size*imSize+ins, this);
		g.drawImage(corner_right_top, imSize*size+ins, -imSize+ins, this);
		g.drawImage(corner_right_bottom, imSize*size+ins, size*imSize+ins, this);
		g.setColor(new Color(220,220,220));
		for (int i = 1; i < size; i++){
			g.drawLine(ins, imSize*i+ins, size*imSize+ins, imSize*i+ins);
			g.drawLine(imSize*i+ins, ins, imSize*i+ins, size*imSize+ins);	
		}
		int num = (int)Math.sqrt(size);
		g.setColor(Color.black);
		for (int i = 1; i < num; i++){
			g.drawLine(ins, num*imSize*i+ins, size*imSize+ins, num*imSize*i+ins);
			g.drawLine(num*imSize*i+ins, 0+ins, num*imSize*i+ins, size*imSize+ins);	
		}
		
		g.drawImage(solve, imSize*(size+1)+ins, imSize*(size/2)+ins, this);
		
		if (!canBeSolved)
			g.drawImage(notSolvable, 0+ins + (int)(imSize*((double)size/2)-125), (int)(imSize*((double)size/2)+ins-50), this);		
	}

	public void mouseClicked(MouseEvent e){
	    add(typingArea, BorderLayout.PAGE_START);
        typingArea.requestFocusInWindow();
      
        int row = (e.getY()-ins)/imSize;
		int col = (e.getX()-ins)/imSize;
		cCol = col;
		cRow = row;
		int l = solver.getPuzzle().length;
		if ( (cCol == l + 1 || cCol == l + 2) && 
				(cRow == l/2 || cRow == l/2+1)){
			canBeSolved = solver.solve();
		}
		repaint();
	}
		
	public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (c == ' ' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8' || c == '9'){
        	if ( c != ' '){
        		int value = Integer.parseInt(""+c);
        		solver.setValue(cCol, cRow, value);
        	}
        	else
        		solver.setValue(cCol, cRow, 0);
        }
        canBeSolved = true;
        this.remove(typingArea);
        cCol = -1; cRow = -1;
        repaint();		      
	}
	
//	 Not used methods from the interface of MouseListener and Keylistener 
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
}
