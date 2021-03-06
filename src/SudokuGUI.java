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

	/**
	 * 
	 */
	private static final long serialVersionUID = 8167068105887269512L;
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
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		//String path = classLoader.getResource("/images/logo.jpg").getPath();
		
		background = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/background.png").getPath());	
		border_left = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/border_left.png").getPath());
		border_right = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/border_right.png").getPath());
		border_top = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/border_top.png").getPath());
		border_bottom = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/border_bottom.png").getPath());
		corner_left_top = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/corner_left_top.png").getPath());
		corner_left_bottom = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/corner_left_bottom.png").getPath());
		corner_right_top = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/corner_right_top.png").getPath());
		corner_right_bottom = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/corner_right_bottom.png").getPath());
		solve = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/solve.png").getPath());
		active_cell = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/active_background.png").getPath());
		notSolvable = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/notSolved.png").getPath());

		one = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/one.png").getPath());
		two = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/two.png").getPath());
		three = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/three.png").getPath());
		four = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/four.png").getPath());
		five = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/five.png").getPath());
		six = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/six.png").getPath());
		seven = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/seven.png").getPath());
		eight = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/eight.png").getPath());
		nine = Toolkit.getDefaultToolkit().getImage(classLoader.getResource("imgs/nine.png").getPath());

		this.solver = solver;
		this.addMouseListener(this);
		typingArea = new JTextField(0);
        typingArea.addKeyListener(this);
        this.add(typingArea, BorderLayout.PAGE_START);
        ins = 20;
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
	   this.add(typingArea, BorderLayout.PAGE_START);
	   this.setLayout(new FlowLayout());
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
