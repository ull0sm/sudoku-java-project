import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.Stack;

public class SudokuGUI {
    private final SudokuBoard board;
    private final SudokuCell[][] cells;
    private static final int SIZE = 9; // Size of the Sudoku board
    private static final int SUBGRID_SIZE = 3; // Size of each sub-grid
    private final Stack<CellState> undoStack = new Stack<>();
    private final Stack<CellState> redoStack = new Stack<>();

    public SudokuGUI() {
        board = new SudokuBoard();
        cells = new SudokuCell[SIZE][SIZE];
        createAndShowGUI();
    }

    @SuppressWarnings("unused")
    private void createAndShowGUI() {
        JFrame frame = createFrame();
        JPanel gridPanel = createGridPanel();

        // Reset button
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());

        // Undo button
        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(e -> undoAction());

        // Redo button
        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(e -> redoAction());

        // Control panel for buttons
        JPanel controlPanel = new JPanel();
        controlPanel.add(undoButton);
        controlPanel.add(redoButton);
        controlPanel.add(resetButton);

        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH); // Add control panel with buttons
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private JPanel createGridPanel() {
        JPanel gridPanel = new JPanel(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE, 2, 2));

        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                JPanel subPanel = createSubPanel(i, j);
                gridPanel.add(subPanel);
            }
        }
        return gridPanel;
    }

    private JPanel createSubPanel(int gridRow, int gridCol) {
        JPanel subPanel = new JPanel(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));
        subPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        for (int row = 0; row < SUBGRID_SIZE; row++) {
            for (int col = 0; col < SUBGRID_SIZE; col++) {
                int boardRow = gridRow * SUBGRID_SIZE + row;
                int boardCol = gridCol * SUBGRID_SIZE + col;

                SudokuCell cell = new SudokuCell(board, boardRow, boardCol);
                cells[boardRow][boardCol] = cell;

                // Add DocumentListener to track changes for undo/redo
                cell.getDocument().addDocumentListener(new DocumentListener() {
                    private String lastValue = cell.getText(); // Store last valid value

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        handleChange();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        handleChange();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        handleChange();
                    }

                    private void handleChange() {
                        String newValue = cell.getText();
                        if (!lastValue.equals(newValue)) {
                            undoStack.push(new CellState(cell, lastValue)); // Save previous state
                            redoStack.clear(); // Clear redo stack on new input
                            lastValue = newValue; // Update last value
                        }
                    }
                });

                subPanel.add(cell);
            }
        }
        return subPanel;
    }

    private void undoAction() {
        if (!undoStack.isEmpty()) {
            CellState lastState = undoStack.pop();
            SudokuCell cell = lastState.cell;
            String oldValue = lastState.value;

            redoStack.push(new CellState(cell, cell.getText())); // Save current state for redo
            cell.setText(oldValue); // Revert to previous state
        }
    }

    private void redoAction() {
        if (!redoStack.isEmpty()) {
            CellState lastState = redoStack.pop();
            SudokuCell cell = lastState.cell;
            String newValue = lastState.value;
    
            // Save the current state to undo stack
            undoStack.push(new CellState(cell, cell.getText()));
    
            // Reapply the previously undone value
            cell.setText(newValue);
            if (newValue.isEmpty()) {
                cell.setBackground(Color.WHITE); // Reset background for cleared cells
            } else {
                cell.setBackground(Color.PINK); // Highlight previous error state, if any
            }
        }
    }
    

    private void resetGame() {
        board.resetBoard(); // Generate a new Sudoku puzzle
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                cells[i][j].setupInitialValue(); // Reset cells to match new puzzle
            }
        }
        undoStack.clear();
        redoStack.clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}

// Helper class to store cell states for undo/redo functionality
class CellState {
    SudokuCell cell;
    String value;

    CellState(SudokuCell cell, String value) {
        this.cell = cell;
        this.value = value;
    }
}
