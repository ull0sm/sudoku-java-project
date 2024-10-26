import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                textFields[row][col].setEditable(false); // Make fields non-editable

                // Optionally, disable fields with pre-filled numbers
                if (board.getBoard()[row][col] != 0) {
                    textFields[row][col].setText(String.valueOf(board.getBoard()[row][col]));
                    // You can also use this to visually indicate pre-filled cells
                    textFields[row][col].setBackground(Color.LIGHT_GRAY); // Optional
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

                // Allow moves only if the cell is empty (0)
                if (board.getBoard()[row][col] == 0 && board.isValidMove(row, col, number)) {
                    board.makeMove(row, col, number);
                    textFields[row][col].setText(String.valueOf(number));
                    textFields[row][col].setBackground(Color.WHITE); // Reset background color

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