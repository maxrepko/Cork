package cork;

class Board {
    private final int[][] board = new int[8][8];
    private final int[] defaultRow = {5, 2, 3, 9, 99, 3, 2, 5};

    // Initializes the "board" to a default chess s et up
    void initBoard() {
        for (int i = 0; i < 8; i++) {board[0][i] = (-1 * defaultRow[i]); board[1][i] = -1; board[6][i] = 1; board[7][i] = defaultRow[i];
        }
    }

    void printBoard() {
        System.out.println("Current Position:\n-----------------\n");
        for (int x = 0; x < 8; x++) {
            System.out.print((x) + " ");
            for (int y = 0; y < 8; y++) {
                System.out.printf("[%3d]", board[x][y]);
            }
            System.out.println("");
        }
        System.out.print("  ");
        for (int i = 0; i < 8; i++) {
            System.out.printf("%3d  ", i);
        }
    }

    // Checks if the move is valid, and if it is, moves the piece.
    // Else, notify the player their move was invalid
    boolean move(int oldY, int oldX, int newY, int newX, int id, int newId) {
        boolean valid = Tasks.checkMove(oldY, oldX, newY, newX, id, newId, board);
        if(valid) {
            board[newY][newX] = board[oldY][oldX];
            board[oldY][oldX] = 0;
        }
        return valid;
    }

    // Tests to see if the requested ID is in the bounds of the board, if it isn't, it will return a fail code
    int getID(int y, int x) {
        if (y >= 0 && y <= 7 && x >= 0 && x <= 7) {return board[y][x];}
        else {
            System.out.println("Request is out of bounds!");
            return 404;
        }
    }
}
