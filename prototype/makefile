# Makefile for Sudoku project

# Directories
SRC_DIR = src
BIN_DIR = bin

# Source files
SOURCES = $(SRC_DIR)/SudokuBoard.java $(SRC_DIR)/SudokuGenerator.java $(SRC_DIR)/SudokuValidator.java

# Class files
CLASS_FILES = $(patsubst $(SRC_DIR)/%.java,$(BIN_DIR)/%.class,$(SOURCES))

# Java compiler
JC = javac

# Flags
JC_FLAGS = -d $(BIN_DIR)

# Default target
all: $(CLASS_FILES)

# Rule to compile Java files
$(BIN_DIR)/%.class: $(SRC_DIR)/%.java
	$(JC) $(JC_FLAGS) $<

# Clean target
clean:
	rm -f $(BIN_DIR)/*.class

.PHONY: all clean