/*Jennifer Nguyen Do Ha*/
/*Edited version, with char[][] grid declared as a class variable*/
import java.util.*;
import java.io.*;

public class Game{
	/*CONSTANTS*/
	private static final char DEFAULT_CHAR = '.';
	private static final char PATH_CHAR = '*';
	private static final char CURRENT_PLACE = '@';
	private static final char WRONG_PLACE = '?';
	private static final String NEXTMOVE_PROMPT = "Please enter your next move (wsz): ";
	private static final String INSTRUCTION = "Move to the next column with 'w' for UP, 's' for STRAIGHT; 'z' for DOWN.";
	
	/*TWO CLASS VARIABLES*/
	private static Random rand = new Random();
	private static final Scanner console = new Scanner(System.in);
	private static char[][] grid; 
	
	/*CLASS METHODS*/
	private static int[] getDimensions(){
		System.out.println("Please enter two positive numbers for the grid dimensions: ");
		int[] row_col = new int[2];
		System.out.print("#rows #columns ");
		for (int i=0; i<2; i++){
			row_col[i] = console.nextInt();
		}
		return row_col;
	}
	
	
	public static char[][] createGrid(int[] dimensions){
		int height = dimensions[0];
		int width = dimensions[1];

		grid = new char[height][width]; //initiate 2d array according to given dimensions
		for (int row=0; row<height; row++){
			for (int col=0; col<width; col++){
				grid[row][col]=DEFAULT_CHAR;
			}
		}
		return grid;
	}
	
	
	public static char[][] createPath(){
		/*find and report each dimension*/
		int height = grid.length;
		int width = grid[0].length;
		System.out.println("There is the starting grid of dimensions "+height+" rows and "+width+" columns with a connected path");
		
		/*randomize an asterisk in the first column*/
		int row = rand.nextInt(height-1);
		grid[row][0] = PATH_CHAR;
		
		/*randomize an asterisk per column to create connected path*/
		for (int col=1; col<width; col++){
			int[] all_paths = {-1,0,1};
			int[] no_down = {-1,0};
			int[] no_up = {0,1};
			if (row == height-1){ 
				row += no_down[rand.nextInt(2)];}
			else if (row == 0){
				row += no_up[rand.nextInt(2)];}
			else{
				row += all_paths[rand.nextInt(3)];}
			grid[row][col] = PATH_CHAR;
		}
		return grid;
	}
	
	
	public static int updatePosition(int row, String move){
		if (move.equals("w"))
			row -=1;
		else if (move.equals("z"))
			row +=1;
		else
			row +=0;
		return row;
	}
	
	
	public static void updateGrid (int next_row, int next_col){
		if (grid[next_row][next_col] == PATH_CHAR){
			grid[next_row][next_col] = CURRENT_PLACE;
		}
		else{
			grid[next_row][next_col] = WRONG_PLACE;
		}
	}
	
	
	public static void printGrid(){
		for (int row=0; row<grid.length; row++){
			for (int col=0; col<grid[row].length; col++){
				System.out.print(grid[row][col]+" ");
			}
			System.out.println();	
		}
		System.out.println();
	}
	
	
	public static String getMove(){
		System.out.println(INSTRUCTION);
		System.out.print(NEXTMOVE_PROMPT);
		String move = console.next();
		if (!(move.equals("w") || move.equals("s") || move.equals("z"))){
			System.out.println("Invalid move... "+move+" We will assume you meant STRAIGHT");
			move = "s";
		}
		return move;
	}	
	
	
	public static void main(String[] args){
		/*set up grid*/
		int[] dimensions = getDimensions();
	    grid = createGrid(dimensions);
	    createPath();
		printGrid();
		
		/*initiate current position and start at first asterisk*/
		int row = 0;
		int col = 0;
		for (int i=0; i<grid.length; i++){
			if (grid[i][0]==PATH_CHAR){
				row += i;
				grid[row][0]= CURRENT_PLACE;
			}
		}
		System.out.println("You are at @ and need to follow the * path");
		printGrid();
		
		/*for loop that breaks when player enters a wrong move*/
		int correct_moves = 0;
		for (int column = 0; column <grid[0].length-1; column ++){
			if (grid[row][col] != CURRENT_PLACE){
				System.out.println("Wrong move.... You LOST");
				break;
			}
			String move = getMove(); //get user input for next move
			row = updatePosition(row, move);//update new position
			col += 1;	
			correct_moves +=1;
			updateGrid(row, col); //update and print new grid
			printGrid();
			}
		if (correct_moves == grid[0].length-1) //because the first '@' does not count as a move
			System.out.println("You WON");
	}                                                                                                            
}
	
