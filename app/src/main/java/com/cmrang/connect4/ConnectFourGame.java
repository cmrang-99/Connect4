package com.cmrang.connect4;

public class ConnectFourGame {
    // Constants for the game
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42;

    // Member variable to store the game state
    private int[][] grid;
    private int currentPlayer;

    // Constructor to initialize the grid and start a new game
    public ConnectFourGame() {
        grid = new int[ROW][COL];
        resetGame();
    }

    // Reset the game state
    public void resetGame() {
        // Clear the board using Arrays.fill for more concise code
        for (int[] row : grid) {
            java.util.Arrays.fill(row, EMPTY);
        }
        currentPlayer = BLUE; // Blue starts the game
    }

    public int getDisc(int row, int col) {
        return grid[row][col];
    }

    // Check if the game is over
    public boolean isGameOver() {
        return checkForWin() || isBoardFull();
    }

    private boolean isBoardFull() {
        // Simplified the loop using anyMatch for early return on empty slot
        return java.util.stream.IntStream.range(0, ROW)
                .flatMap(r -> java.util.stream.IntStream.range(0, COL)
                        .map(c -> grid[r][c] == EMPTY ? 1 : 0))
                .noneMatch(cell -> cell == 1);
    }

    // Check if there's a winner
    private boolean checkForWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    private boolean checkHorizontal() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                if (isWinningCombo(row, col, 0, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVertical() {
        for (int col = 0; col < COL; col++) {
            for (int row = 0; row <= ROW - 4; row++) {
                if (isWinningCombo(row, col, 1, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonal() {
        // Check down-right diagonal
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 0; col <= COL - 4; col++) {
                if (isWinningCombo(row, col, 1, 1)) {
                    return true;
                }
            }
        }
        // Check up-right diagonal
        for (int row = 0; row <= ROW - 4; row++) {
            for (int col = 3; col < COL; col++) {
                if (isWinningCombo(row, col, 1, -1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isWinningCombo(int startRow, int startCol, int rowInc, int colInc) {
        // Check if there is a 4-disc combination starting from (startRow, startCol)
        for (int i = 0; i < 4; i++) {
            if (grid[startRow + i * rowInc][startCol + i * colInc] != currentPlayer) {
                return false;
            }
        }
        return true;
    }

    public void dropDisc(int col) {
        // Find the lowest available row in the selected column
        for (int row = ROW - 1; row >= 0; row--) {
            if (grid[row][col] == EMPTY) {
                grid[row][col] = currentPlayer;
                currentPlayer = (currentPlayer == BLUE) ? RED : BLUE;
                break;
            }
        }
    }

    public void loadGameState(String gameState) {
        int index = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                grid[row][col] = Character.getNumericValue(gameState.charAt(index++));
            }
        }
    }

    public String getGameState() {
        StringBuilder state = new StringBuilder();
        for (int[] row : grid) {
            for (int cell : row) {
                state.append(cell);
            }
        }
        return state.toString();
    }

    public void startGame() {
        resetGame(); // Reset the game to start fresh
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }
}
