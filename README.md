# Sudoku Game

A Java-based Sudoku game with a graphical user interface (GUI). This project allows users to play Sudoku on a 9x9 grid with randomly generated puzzles.

## Project Structure

- **SudokuBoard.java**: Manages the Sudoku grid and generates puzzles.
- **SudokuCell.java**: Handles individual cells within the Sudoku grid, including user input validation.
- **SudokuGUI.java**: Sets up the GUI and displays the Sudoku board.

## Features

- Randomly generated Sudoku puzzles.
- User-friendly GUI with interactive cell inputs.
- Real-time validation of player moves.
- Reset functionality to restart the game.

## Requirements

- Java JDK 8 or higher
- Java Swing (included with JDK)

## Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/ull0sm/sudoku-java-project
cd sudoku-game
```

### 2. Build the Project (Linux Users)
On Linux-based systems, you can use the `make` command:
```bash
make
```

For users on other operating systems (e.g., Windows or macOS), you can manually compile the project using `javac`:
```bash
javac -d bin src/*.java
```

### 3. Run the Game
On Linux:
```bash
make run
```

On other systems:
```bash
java -cp bin SudokuGUI
```

### 4. Clean the Build
On Linux:
```bash
make clean
```

Manually (if not using `make`):
```bash
rm -rf bin/*.class
```

## Usage

- **Start Game**: Enter numbers 1-9 in the editable cells.
- **Reset Game**: Press the "Reset" button to clear the board.

## Contributing

This project was developed as a collaborative mini project. Contributions are welcome. To contribute:
- Fork the repository
- Create a new branch (`git checkout -b feature-branch`)
- Commit your changes
- Push to the branch (`git push origin feature-branch`)
- Open a pull request
---
