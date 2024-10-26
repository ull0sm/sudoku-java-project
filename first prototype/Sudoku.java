public class Sudoku {
    private Board board;

    public Sudoku() {
        board = new Board();
    }

    // Method to start the game
    public void start() {
        System.out.println("Welcome to Sudoku!");
        board.display();
    }
}
