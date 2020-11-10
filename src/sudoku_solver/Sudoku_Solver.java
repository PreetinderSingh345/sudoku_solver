package sudoku_solver;

import java.util.Scanner;

class Pair{
	
//	this Pair class is to get the top left corner point of the corresponding square wrt a point
	
	int x;
	int y;
	
	Pair(int x, int y){
		this.x=x;
		this.y=y;
	}
	
}

public class Sudoku_Solver{
	public static void print(int board[][]) {
		
		for(int i=0;i<9;i++) {//printing the board
			for(int j=0;j<9;j++) {
				System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	public static boolean isValid(int board[][]) {
		
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				int k=board[i][j];
				
				for(int k1=0;k1<9;k1++) {
					if(board[k1][j]==k && k1!=i) {//if any point in the same column has same value as the current point 
//						excluding the current point then the rule of game is violated and is not valid
						return false;
					}
				}
				
				for(int k2=0;k2<9;k2++) {
					if(board[i][k2]==k && k2!=j) {//if any point in the same row has same value as the current point 
//						excluding the current point then the rule of game is violated and is not valid
						return false;
					}
				}
				
				Pair p=getPair(i, j);//this is the top left corner point of the corresponding square
				
				int a=p.x;
				int b=p.y;
				
				for(int k1=a;k1<(a+3);k1++) {
					for(int k2=b;k2<(b+3);k2++) {
						if(board[k1][k2]==k && k1!=i && k2!=j) {//if any point in the corresponding square except the current
//							point has similar value then the rule of game is violated and is not valid
							return false;
						}
					}
				}
			}
		}
		
		return true;
		
	}
	public static boolean isFull(int board[][]) {
		
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(board[i][j]==0) {//if any point is empty then the board is not full
					return false;
				}
			}
		}
		
		return true;
		
	}
	public static Pair getPair(int x, int y) {
		
		int a=0, b=0;
		
		if(x>=0 && x<=2) {
			a=0;//in this case the x coordinate of the top left corner of the square is 0
		}
		else if(x>=3 && x<=5) {
			a=3;//in this case the x coordinate of the top left corner of the square is 3
		}
		else {
			a=6;//in this case the x coordinate of the top left corner of the square is 6
		}
			
		if(y>=0 && y<=2) {//similarly as above for y coordinate
			b=0;
		}
		else if(y>=3 && y<=5) {
			b=3;
		}
		else {
			b=6;
		}
		
		Pair p=new Pair(a, b);//pair which contains the top left corner of the square
		return p;//returning the point
		
	}
	
	public static boolean canSolve(int board[][]) {
		
		boolean filled=true;//assuming board is already filled
		int x=0, y=0;//initializing the first encountered unfilled point and assuming it is 0, 0
		
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				if(board[i][j]==0) {
					filled=false;//we try to fill the first unfilled point we encounter and we initialize the point and we 
//					break as the board is not filled
					x=i;
					y=j;
					break;
				}
			}
		}
		
		if(filled) {
			return true;//we return true if the board is filled
		}
		
		int k=1;
		while(k<=9) {//try to place numbers from 1 to 9 at the unfilled point and see which combination works
			boolean canPlace=true;//assuming that we can place current k value at the point x, y in the board
			
			for(int i=0;i<9;i++) {//cannot place k at x, y if column contains another k valued point
				if(board[i][y]==k) {
					canPlace=false;
					break;
				}
			}
			
			if(canPlace) {//we proceed only if the column condition is satisfied
				for(int j=0;j<9;j++) {//cannot place k at x, y if row contains another k valued point
					if(board[x][j]==k) {
						canPlace=false;
						break;				
					}
				}
			}
			
			if(canPlace) {//we proceed only if the row and column conditions are satisfied
				Pair p=getPair(x, y);
				
				int a=p.x;//obtain the top left corner of the square corresponding to the point x, y
				int b=p.y;
				
				for(int k1=a;k1<(a+3);k1++) {
					for(int k2=b;k2<(b+3);k2++) {
						if(board[k1][k2]==k) {//we cannot place k value at x, y point if the square contains any other k 
//							valued point
							canPlace=false;
							break;
						}
					}
				}
			}
			
			if(canPlace) {//we proceed only if the row, column and square conditions are satisfied
				board[x][y]=k;//place the k value at x, y point if we can do so
				
				boolean solved=canSolve(board);//see if the board gets solved by this combination
				
				if(solved) {//return true if this combination works
					return true;
				}
				else {//we backtrack to the previous arrangement
					board[x][y]=0;
				}
			}
			
			k++;//try for other values of k if the current value does not work
		}
		
		return false;//the board cannot be solved if any number from 1 to 9 cannot be placed at the point x, y
		
	}	
	public static void main(String[] args) {
	
		Scanner s=new Scanner(System.in);
		
		System.out.println("enter the elements of the 9x9 sudoku board row wise : ");
		
		int board[][]=new int[9][9];
		for(int i=0;i<9;i++) {
			for(int j=0;j<9;j++) {
				board[i][j]=s.nextInt();//taking input of sudoku board
			}
		}
		
		boolean output;//this output holds whether the board can be solved or not
		
		boolean full=isFull(board);
		
		if(full) {//if the board given is already full
			if(isValid(board)) {//to check if it satisfies the rules or not
				output=true;				
				System.out.println("the board is already solved");
				return ;
			}
			else {
				output=false;
			}
		}
		else {
			output=canSolve(board);//checking if the board can be solved or not
		}
		
		if(output) {
			System.out.println("the board can be solved and the solution is : ");
			print(board);//printing the solution in case the board can be solved
		}
		else {
			System.out.println("the board cannot be solved");
		}
		
	}
}