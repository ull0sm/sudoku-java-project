import javax.swing.*;
import java.awt.*;

public class SudokuGUI {
    private final SudokuBoard board;
    private final SudokuCell[][] cells;
    private static final int SIZE = 9; // Size of the Sudoku board
    private static final int SUBGRID_SIZE = 3; // Size of each sub-grid

    public SudokuGUI() {
        board = new SudokuBoard();
        cells = new SudokuCell[SIZE][SIZE];
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = createFrame();
        JPanel gridPanel = createGridPanel();
        
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetGame());
        
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(resetButton, BorderLayout.SOUTH); // Add reset button to the bottom
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

                cells[boardRow][boardCol] = new SudokuCell(board, boardRow, boardCol);
                subPanel.add(cells[boardRow][boardCol]);
            }
        }
        return subPanel;
    }

    private void resetGame() {
        for (SudokuCell[] cellRow : cells) {
            for (SudokuCell cell : cellRow) {
                // Reset user entries
                if (cell.isEditable()) {
                    cell.setText(""); // Clear the input field
                    cell.setBackground(Color.WHITE); // Reset the background
                }
            }
        }
    }    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SudokuGUI::new);
    }
}
