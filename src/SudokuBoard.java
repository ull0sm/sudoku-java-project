import java.util.Random;

public class SudokuBoard {
    private int[][] board;
    private final Random random;

    public SudokuBoard() {
        board = new int[9][9];
        random = new Random();
        generateSudoku();
    }

    public int[][] getBoard() {
        return board;
    }

    private void generateSudoku() {
        fillBoard();
        removeNumbers();
    }

    private boolean fillBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValidPlacement(row, col, num)) {
                            board[row][col] = num;
                            if (fillBoard()) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidPlacement(int row, int col, int num) {
        for (int x = 0; x < 9; x++) {
            if (board[row][x] == num || board[x][col] == num ||
                board[row - row % 3 + x / 3][col - col % 3 + x % 3] == num) {
                return false;
            }
        }
        return true;
    }

    private void removeNumbers() {
        int count = 40;
        while (count > 0) {
            int i = random.nextInt(9);
            int j = random.nextInt(9);
            if (board[i][j] != 0) {
                board[i][j] = 0;
                count--;
            }
        }
    }

    public boolean isValidMove(int row, int col, int number) {
        return isValidPlacement(row, col, number);
    }

    public void makeMove(int row, int col, int number) {
        board[row][col] = number;
    }
    public void resetBoard() {
        board = new int[9][9]; // Reset the board to a new instance
        generateSudoku(); // Regenerate the Sudoku board
    }
    
}