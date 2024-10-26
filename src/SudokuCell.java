import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SudokuCell extends JTextField {
    private final SudokuBoard board;
    private final int row;
    private final int col;

    public SudokuCell(SudokuBoard board, int row, int col) {
        this.board = board;
        this.row = row;
        this.col = col;
        setHorizontalAlignment(CENTER);
        setupInitialValue();
        setupInputValidation();
    }

    private void setupInitialValue() {
        int value = board.getBoard()[row][col];
        if (value != 0) {
            setText(String.valueOf(value));
            setBackground(Color.LIGHT_GRAY);
            setEditable(false);
        } else {
            setEditable(true);
            setBackground(Color.WHITE); // Set editable cell background to white
        }
    }

    private void setupInputValidation() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || getText().length() >= 1) {
                    e.consume();
                }
            }
        });

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateInput();
            }
        });
    }

    private void validateInput() {
        if (!isEditable()) {
            return; // Skip validation if cell is not editable
        }

        try {
            String input = getText();
            if (!input.isEmpty()) {
                int number = Integer.parseInt(input);
                if (number < 1 || number > 9) {
                    JOptionPane.showMessageDialog(null, "Please enter a number between 1 and 9.");
                    setText("");
                    return;
                }
                if (board.isValidMove(row, col, number)) {
                    board.makeMove(row, col, number);
                    setBackground(Color.WHITE);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid move! Try again.");
                    setText("");
                    setBackground(Color.PINK); // Highlight invalid input
                }
            } else {
                setText(""); // Clear if input is empty
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            setText("");
        }
    }
}
