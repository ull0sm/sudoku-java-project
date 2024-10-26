import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

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

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3, 2, 2));

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel subPanel = new JPanel(new GridLayout(3, 3));
                subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        int boardRow = i * 3 + row;
                        int boardCol = j * 3 + col;

                        textFields[boardRow][boardCol] = new JTextField();
                        textFields[boardRow][boardCol].setHorizontalAlignment(JTextField.CENTER);

                        // Set initial value and style
                        if (board.getBoard()[boardRow][boardCol] != 0) {
                            textFields[boardRow][boardCol].setText(String.valueOf(board.getBoard()[boardRow][boardCol]));
                            textFields[boardRow][boardCol].setBackground(Color.LIGHT_GRAY);
                            textFields[boardRow][boardCol].setEditable(false);
                        } else {
                            textFields[boardRow][boardCol].setEditable(true);
                            setupInputValidation(textFields[boardRow][boardCol], boardRow, boardCol);
                        }

                        subPanel.add(textFields[boardRow][boardCol]);
                    }
                }

                gridPanel.add(subPanel);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.setSize(700, 700);
        frame.setVisible(true);
    }

    private void setupInputValidation(JTextField textField, int row, int col) {
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || textField.getText().length() >= 1) {
                    e.consume(); // Ignore if not a digit or already has one character
                }
            }
        });

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    String input = textField.getText();
                    if (!input.isEmpty()) {
                        int number = Integer.parseInt(input);
                        if (board.isValidMove(row, col, number)) {
                            board.makeMove(row, col, number);
                            textField.setBackground(Color.WHITE);
                        } else {
                            textField.setText("");
                            JOptionPane.showMessageDialog(null, "Invalid move! Try again.");
                        }
                    }
                } catch (NumberFormatException ex) {
                    textField.setText(""); // Clear invalid input
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}

class SudokuBoard {
    private int[][] board;
    private Random random;

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
        removeNumbers(); // Optionally remove numbers to create the puzzle
    }

    private boolean fillBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValidPlacement(row, col, num)) {
                            board[row][col] = num; // Place the number
                            if (fillBoard()) { // Recursively try to fill the next cells
                                return true;
                            }
                            board[row][col] = 0; // Backtrack
                        }
                    }
                    return false; // No valid number found, trigger backtracking
                }
            }
        }
        return true; // Board is filled
    }

    private boolean isValidPlacement(int row, int col, int num) {
        for (int x = 0; x < 9; x++) {
            if (board[row][x] == num || board[x][col] == num || board[row - row % 3 + x / 3][col - col % 3 + x % 3] == num) {
                return false;
            }
        }
        return true;
    }

    private void removeNumbers() {
        int count = 40; // Number of cells to remove
        while (count > 0) {
            int i = random.nextInt(9);
            int j = random.nextInt(9);
            if (board[i][j] != 0) {
                board[i][j] = 0; // Remove number
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
}
