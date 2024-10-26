import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}

class SudokuBoard {
    private final int[][] board;
    private final int SIZE = 9;

    public SudokuBoard() {
        board = new int[SIZE][SIZE];
        initializeBoard();
    }

    private void initializeBoard() {
        // A sample Sudoku puzzle
        board[0] = new int[]{5, 3, 0, 0, 7, 0, 0, 0, 0};
        board[1] = new int[]{6, 0, 0, 1, 9, 5, 0, 0, 0};
        board[2] = new int[]{0, 9, 8, 0, 0, 0, 0, 6, 0};
        board[3] = new int[]{8, 0, 0, 0, 6, 0, 0, 0, 3};
        board[4] = new int[]{4, 0, 0, 8, 0, 3, 0, 0, 1};
        board[5] = new int[]{7, 0, 0, 0, 2, 0, 0, 0, 6};
        board[6] = new int[]{0, 6, 0, 0, 0, 0, 2, 8, 0};
        board[7] = new int[]{0, 0, 0, 4, 1, 9, 0, 0, 5};
        board[8] = new int[]{0, 0, 0, 0, 8, 0, 0, 7, 9};
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean isValidMove(int row, int col, int number) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == number || board[i][col] == number) {
                return false;
            }
        }
        int boxRowStart = row / 3 * 3;
        int boxColStart = col / 3 * 3;
        for (int r = boxRowStart; r < boxRowStart + 3; r++) {
            for (int c = boxColStart; c < boxColStart + 3; c++) {
                if (board[r][c] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    public void makeMove(int row, int col, int number) throws InvalidInputException {
        if (!isValidMove(row, col, number)) {
            throw new InvalidInputException("Invalid move! Try again.");
        }
        board[row][col] = number;
    }

    public boolean isSolved() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}

public class SudokuGUI {
    private final SudokuBoard board;
    private final JTextField[][] textFields;

    public SudokuGUI() {
        board = new SudokuBoard();
        textFields = new JTextField[9][9];
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    
        JPanel gridPanel = new JPanel(new GridLayout(9, 9)); // Panel for the Sudoku grid
    
        // Create text fields for the Sudoku grid
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                textFields[row][col] = new JTextField();
                textFields[row][col].setHorizontalAlignment(JTextField.CENTER);
                if (board.getBoard()[row][col] != 0) {
                    textFields[row][col].setText(String.valueOf(board.getBoard()[row][col]));
                    textFields[row][col].setEditable(false); // Make preset values uneditable
                }
                gridPanel.add(textFields[row][col]);
            }
        }
    
        frame.add(gridPanel, BorderLayout.CENTER);
    
        // Create a button panel for the Submit Move button
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit Move");
        submitButton.addActionListener(new SubmitMoveListener());
        buttonPanel.add(submitButton);
    
        frame.add(buttonPanel, BorderLayout.SOUTH);
    
        frame.setSize(600, 600);
        frame.setVisible(true);
    }
    

    private class SubmitMoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                // Get row, column, and number from user input
                String rowInput = JOptionPane.showInputDialog("Enter row (0-8):");
                String colInput = JOptionPane.showInputDialog("Enter column (0-8):");
                String numInput = JOptionPane.showInputDialog("Enter number (1-9):");

                int row = Integer.parseInt(rowInput);
                int col = Integer.parseInt(colInput);
                int number = Integer.parseInt(numInput);

                if (board.isValidMove(row, col, number)) {
                    board.makeMove(row, col, number);
                    textFields[row][col].setText(String.valueOf(number));

                    if (board.isSolved()) {
                        JOptionPane.showMessageDialog(null, "Congratulations! You've solved the Sudoku!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid move! Try again.");
                }
            } catch (InvalidInputException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}