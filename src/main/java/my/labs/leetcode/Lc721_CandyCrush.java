package my.labs.leetcode;

public class Lc721_CandyCrush {

    /**
     * The 1st step is to replace 3 consecutive same numbers with 0
     * horizontally and vertically
     * Time Complexity O (m x n)
     *
     *
     *
     * @param board
     * @return
     */
    public static int[][] crush_step1(int[][] board) {
        int rows = board.length;
        int columns = board[0].length;
        boolean[][] toCrush = new boolean[rows][columns];
        // Row by row scanning
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 2; j++) {
                int val = board[i][j];
                if (val != 0 && val == board[i][j + 1] && val == board[i][j + 2]) {
                    toCrush[i][j] = true;
                    toCrush[i][j + 1] = true;
                    toCrush[i][j + 2] = true;
                }
            }
        }

        // Column by colum scanning
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows - 2; j++) {
                int val = board[j][i];
                if (val != 0 && val == board[j + 1][i] && val == board[j + 2][i]) {
                    toCrush[j][i] = true;
                    toCrush[j + 1][i] = true;
                    toCrush[j + 2][i] = true;
                }
            }
        }

        // replaced with 0
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (toCrush[i][j]) {
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }

    public static int[][] crush_step1_in_place_replace(int[][] board) {
        // scan by row and column respectively, if see numbers to crush, set it to a corresponding negative number (eg.
        // 5 to -5)
        // always compare with absolute value.
        // re-scan the replace the negative number to 0
        int rows = board.length;
        int columns = board[0].length;
        // scan row by row
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns - 2; j++) {
                int value = Math.abs(board[i][j]);
                if (value == board[i][j + 1] && value == board[i][j + 2]) {
                    board[i][j] = -board[i][j];
                    board[i][j + 1] = -board[i][j + 1];
                    board[i][j + 2] = -board[i][j + 2];
                }
            }
        }

        // scan by column
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows - 2; j++) {
                int value = Math.abs(board[j][i]);
                if (value == board[j + 1][i] && value == board[j + 2][i]) {
                    board[j][i] = -board[j][i];
                    board[j + 1][i] = -board[j + 1][i];
                    board[j + 2][i] = -board[j + 2][i];
                }
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int value = board[i][j];
                if (value < 0) {
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }

    /**
     * Performs a "fall down" operation on a 2D board after marked cells have been set to 0.
     *
     * <p>
     * The algorithm processes each column independently from bottom to top:
     * <ol>
     *   <li>Initialize a pointer `write` at the bottom of the column.</li>
     *   <li>Iterate from the bottom to the top of the column:
     *       <ul>
     *           <li>If the current cell is non-zero, copy its value to the position pointed by `write` and move `write` one step upward.</li>
     *       </ul>
     *   </li>
     *   <li>After all non-zero values are moved down, fill the remaining cells above `write` with 0.</li>
     * </ol>
     * </p>
     *
     * <p>
     * Example (column from top to bottom):
     * <pre>
     * Input:  [1, 0, 2, 0]
     * After fall down: [0, 0, 1, 2]
     * </pre>
     * </p>
     *
     * <p>
     * Time Complexity: O(m * n) — each cell is visited once.
     * Space Complexity: O(1) — in-place operation with no extra storage.
     * </p>
     *
     * @param board The 2D integer array representing the board, where 0 indicates empty cells.
     */
    public static int[][] crush_step2(int [][] board) {
        int rows = board.length;
        int columns = board[0].length;
        // scan column by column
        for (int j = 0; j < columns; j++){
            int pointer = rows - 1;
            for (int i = rows -1; i >= 0; i--) {
                if (board[i][j] != 0 ) {
                    board[pointer][j] = board[i][j];
                    pointer --;
                }
            }
            for (int k = pointer; k >= 0; k--){
                board[k][j] = 0;
            }
        }
        return board;
    }

    public static void printBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] board = new int[][] {{1, 2, 3, 4, 5}, {6, 3, 3, 3, 7}, {8, 1, 2, 2, 2}, {1, 4, 5, 6, 7}};
        printBoard(board);
        System.out.println("----------------");
        int[][] replaced = crush_step1_in_place_replace(board);
        printBoard(replaced);
        System.out.println("----------------");
        printBoard(crush_step2(replaced));

    }
}
