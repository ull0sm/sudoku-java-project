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

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3, 2, 2)); // Create a grid of 3x3 panels

        // Create 3x3 sub-panels
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JPanel subPanel = new JPanel(new GridLayout(3, 3));
                subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Border for each 3x3 block

                // Create text fields for the 3x3 block
                for (int row = 0; row < 3; row++) {
                    for (int col = 0; col < 3; col++) {
                        int boardRow = i * 3 + row;
                        int boardCol = j * 3 + col;

                        textFields[boardRow][boardCol] = new JTextField();
                        textFields[boardRow][boardCol].setHorizontalAlignment(JTextField.CENTER);
                        textFields[boardRow][boardCol].setEditable(false);

                        if (board.getBoard()[boardRow][boardCol] != 0) {
                            textFields[boardRow][boardCol].setText(String.valueOf(board.getBoard()[boardRow][boardCol]));
                            textFields[boardRow][boardCol].setBackground(Color.LIGHT_GRAY);
                        }

                        subPanel.add(textFields[boardRow][boardCol]);
                    }
                }

                gridPanel.add(subPanel);
            }
        }

        frame.add(gridPanel, BorderLayout.CENTER);

        // Create a button panel for the Submit Move button
        JPanel buttonPanel = new JPanel();
        JButton submitButton = new JButton("Submit Move");
        submitButton.addActionListener(new SubmitMoveListener());
        buttonPanel.add(submitButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(700, 700);
        frame.setVisible(true);
    }

    private class SubmitMoveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String rowInput = JOptionPane.showInputDialog("Enter row (0-8):");
                String colInput = JOptionPane.showInputDialog("Enter column (0-8):");
                String numInput = JOptionPane.showInputDialog("Enter number (1-9):");

                int row = Integer.parseInt(rowInput);
                int col = Integer.parseInt(colInput);
                int number = Integer.parseInt(numInput);

                if (board.getBoard()[row][col] == 0 && board.isValidMove(row, col, number)) {
                    board.makeMove(row, col, number);
                    textFields[row][col].setText(String.valueOf(number));
                    textFields[row][col].setBackground(Color.WHITE);

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
