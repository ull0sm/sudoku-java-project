class Board {
    private static final int SIZE = 9; // Size of the Sudoku board
    private int[][] board;

    public Board(int[][] board) {
        this.board = board;
    }

    // Method to display the board
    public void display() {
        for (int row = 0; row < SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("- - - - - - - - - - -");
            }
            for (int col = 0; col < SIZE; col++) {
                if (col % 3 == 0 && col != 0) {
                    System.out.print("| ");
                }
                System.out.print(board[row][col] + " ");
            }
            System.out.println();
        }
    }
}