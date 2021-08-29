package cork;

import java.util.Scanner;

class Tasks {
    private static int getInt(Scanner scan) {
        while (!scan.hasNextInt()) {scan.next();}
        return scan.nextInt();
    }

    static int[] getMove(Board chess, Scanner scan) {
        int[] move = new int[6];
        System.out.print("\n\nChoose piece rank: ");
        move[0] = Tasks.getInt(scan);
        System.out.print("Choose piece file: ");
        move[1] = Tasks.getInt(scan);
        System.out.print("Choose new rank: ");
        move[2] = Tasks.getInt(scan);
        System.out.print("Choose new file: ");
        move[3] = Tasks.getInt(scan);

        // Gets the id of the current piece and the new positions, respectively
        move[4] = chess.getID(move[1], move[0]);
        move[5] = chess.getID(move[3], move[2]);

        return move;
    }

    // All of the data on the move is assumed to be in bounds of the board
    // NOTE: The 404 test is to see if the either id has been set to the out-of-bounds error code "404"
    static boolean checkMove(int oldY, int oldX, int newY, int newX, int id, int newId, int[][] board) {
        return id != 404 && newId != 404 &&
                pieceWasMoved(oldY, oldX, newY, newX, id)
                && validMovement(oldY, oldX, newY, newX, id)
                && clearPath(oldY, oldX, newY, newX, id, newId, board);
    }

    // Checks to see if the piece moves the way a piece of that type should. AKA, it follows the rules
    private static boolean validMovement(int oldY, int oldX, int newY, int newX, int id) {
        // Checks to see if a move is diagonal, vertical, horizontal, or within one square, respectively
        boolean diagonal = Math.abs((double)(newY - oldY) / (double)(newX - oldX)) == 1.0;
        boolean vertical = Math.abs(newX - oldX) == 0;
        boolean horizontal = Math.abs(newY - oldY) == 0;
        boolean withinOne = Math.abs(newY - oldY) <= 1 && Math.abs(newX - oldX) <= 1;

        // PYMOVE SECTION: Checks to see if the knight moves properly using the Pythagorean theorem
        // If the a = 2 and b = 1 or the b = 2 and a = 1, then the c will have to equal 5 and will only
        // equal 5 under those two scenarios.
        double a = Math.abs(newY - oldY);
        double b = Math.abs(newX - oldX);
        double c = (a * a) + (b * b);
        boolean pyMove = c == 5;

        // If the piece is a pawn, then check to make sure that it is either moving 1 or 2 places forward (relative
        // to its respective side) or just 1 place off its current non-starting square.
        if (Math.abs(id) == 1 && (vertical || (diagonal))) {
            int distance = newY - oldY;
            if (id == 1) {
                if (oldY == 6) {return distance == -2;}
                else {return distance == -1;}
            }
            else if (id == -1) {
                if (oldY == 1) {return distance == 2 || distance == 1;}
                else {return distance == 1;}
            }
        }

        // Since the following pieces are simpler than the pawn, their tests can be broken down to simple directional
        // tests. The tests are written to show what directional movement they are allowed to make and if they are
        // it or not. What piece is being tested is shown on the right of the test. The knight is an exception. For an
        // explanation, check the pyMove test in the test portion of this Class
        else if (Math.abs(id) == 3) {return diagonal;} // BISHOP
        else if (Math.abs(id) == 5) {return horizontal || vertical;} // ROOK
        else if (Math.abs(id) == 9) {return horizontal || vertical || diagonal;} // QUEEN
        else if (Math.abs(id) == 99) {return (horizontal || vertical || diagonal) && withinOne;} // KING
        else if (Math.abs(id) == 2) {return pyMove;} // KNIGHT

        return false;
    }

    // Checks if there was a move made or not. A simple test that could have been put in elsewhere but works on its own
    private static boolean pieceWasMoved(int oldY, int oldX, int newY, int newX, int oldId)
        {return (newY - oldY != 0 || newX - oldX != 0) && oldId != 0;}

    // Checks if the piece is blocked by another piece or the destination is one of its own pieces
    private static boolean clearPath(int oldY, int oldX, int newY, int newX, int id, int newId, int[][] board) {
        boolean allClear = true;

        // Check to see if the piece is a bishop, rook, or queen. If yes, then, assuming there is at least 1 square of
        // space, scans between the start and end point using a while loop and determines if the path from the start to
        // the end is clear. Ignores the destination square because that is a separate test.
        // Returns: True = no blockage. False = blockage
        if (Math.abs(id) == 1 || Math.abs(id) == 3 || Math.abs(id) == 5 || Math.abs(id) == 9) {
            int tempX = newX;
            int tempY = newY;
            while(((oldX != tempX) || (oldY != tempY))
                    && (Math.abs(oldX - tempX) > 1 || Math.abs(oldY - tempY) > 1)) {
                if (tempX < oldX) {tempX++;}
                else if (tempX > oldX){tempX--;}
                if (tempY < oldY) {tempY++;}
                else if (tempY > oldY) {tempY--;}
                if (board[tempY][tempX] != 0) {
                    allClear = false;
                    break;
                }
            }
        }

        // Final test to see if the specific troublesome pieces (pieces whose moves go through other pieces) gotten the
        // all clear to go and then, for all pieces, tests if they are both opposite signs, aka a black piece taking a
        // white piece of vise versa
        return (allClear && id * newId <= 0);
    }
}
