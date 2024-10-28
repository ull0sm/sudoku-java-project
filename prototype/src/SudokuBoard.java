
import java.util.Scanner;

public class SudokuBoard {
    public int[][] board;
    public final SudokuGenerator generator;
    public final SudokuValidator validator;

    public SudokuBoard() {
        this.generator = new SudokuGenerator();
        this.validator = new SudokuValidator();
        resetBoard();
    }

    public int[][] getBoard() {
        return board;
    }

    public void resetBoard() {
        board = generator.generateSudoku();
    }

    public boolean isValidMove(int row, int col, int number) {
        return validator.isValidPlacement(board, row, col, number);
    }

    public void makeMove(int row, int col, int number) {
        board[row][col] = number;
    }

    public void printBoard() {
        System.out.println("------+-------+------");
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0 && col != 0) {
                    System.out.print("| "); // Print vertical border
                }
                System.out.print(board[row][col] == 0 ? ". " : board[row][col] + " "); // Use '.' for empty cells
            }
            System.out.println();
            if ((row + 1) % 3 == 0) {
                System.out.println("------+-------+------"); // Print horizontal border
            }
        }
    }    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SudokuBoard sudoku = new SudokuBoard();
        int row=0, col=0, number=0;

        while (true) {
            try {
                System.out.println("Current Sudoku Board:");
                sudoku.printBoard();
                System.out.print("Enter row (0-8), column (0-8), and number (1-9) to place (or -1 to exit): ");
                row = scanner.nextInt();
                if (row == -1) break; // Exit condition
                col = scanner.nextInt();
                number = scanner.nextInt();
                
            } catch (Exception e) {
                System.out.println("invalid entry");
                System.exit(0);
            }

            if (sudoku.isValidMove(row, col, number)) {
                sudoku.makeMove(row, col, number);
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }

        System.out.println("Final Sudoku Board:");
        sudoku.printBoard();
        scanner.close();
    }
}