package cork;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Board chess = new Board();
        initGame(chess);
        play(chess, scan);
    }

    private static void initGame(Board chess){
        chess.initBoard();
        System.out.println("Game Start!\n");
        chess.printBoard();
    }

    // Gets the user's choice and moves the piece
    private static void play(Board chess, Scanner scan) {
        boolean whiteCanCastle = true;
        boolean blackCanCastle = true;

        // Get's user's choice
        // Split into the <old x position>, <old y position>, <new x position>, <new y position>
        for (int i = 0; i < 100; i++) {
            // Gets the user's requested move, moves the pieces (with move verification), and prints the final board
            int[] move = Tasks.getMove(chess, scan);
            boolean valid = chess.move(move[1], move[0], move[3], move[2], move[4], move[5]);
            if (move[4] == 99 && valid) {whiteCanCastle = false;}
            else if (move[4] == -99 && valid) {blackCanCastle = false;}
            chess.printBoard();
        }
        // System.out.println("50 Move Rule Exceeded!\nGame Over!");
    }
}