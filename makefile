# Makefile for Sudoku Java Project

# Variables
SRC_DIR = src
BIN_DIR = bin
MAIN_CLASS = SudokuGUI
JAR_NAME = sudoku.jar

# Java compiler
JAVAC = javac
# Java command
JAVA = java

# Source files
SOURCES = $(wildcard $(SRC_DIR)/*.java)
# Class files
CLASSES = $(patsubst $(SRC_DIR)/%.java,$(BIN_DIR)/%.class,$(SOURCES))

# Targets
all: clean compile jar

compile: $(CLASSES)

$(BIN_DIR)/%.class: $(SRC_DIR)/%.java $(CLASSES)
	@mkdir -p $(BIN_DIR)
	$(JAVAC) -d $(BIN_DIR) $(SOURCES)

jar: $(CLASSES)
	@mkdir -p $(BIN_DIR)
	jar cfe $(JAR_NAME) $(MAIN_CLASS) -C $(BIN_DIR) .

run: jar
	$(JAVA) -cp $(JAR_NAME) $(MAIN_CLASS)

clean:
	rm -rf $(BIN_DIR)/*.class $(JAR_NAME)

.PHONY: all compile jar run clean
