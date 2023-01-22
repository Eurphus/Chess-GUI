package mac.chess.pieces;

import mac.chess.ChessPiece;
import mac.chess.Point;

import java.util.ArrayList;

public class Pawn extends ChessPiece {
    public Pawn(int inputRow, int inputColumn, boolean inputWhite) {
        super(inputRow, inputColumn, inputWhite);
    }

    // Return all possible moves for a specific Pawn
    public ArrayList<Point> moveList(ChessPiece[][] board) {

        // Define initial moveList
        ArrayList<Point> moves = new ArrayList<>();
        // White Moves
        if (white) {
            // Normal moving mechanism
            if (board[row+1][column] == null) {
                moves.add(new Point(row + 1, column));
            }

            // Move down 2 rows when on row 2
            if (row == 2 && board[row + 2][column] == null) {
                moves.add(new Point(4, column));
            }
            // Go down and take a piece diagonally
            if (isEnemy(board[row + 1][column + 1])) {
                moves.add(new Point(row+1, column+1));
            }

            // Go down and take a piece diagonally
            if (isEnemy(board[row + 1][column - 1])) {
                moves.add(new Point(row+1, column-1));
            }
            // Black Moves
        } else {
            // Normal moving mechanism
            if (board[row - 1][column] == null) {
                moves.add(new Point(row - 1, column));
            }

            // Move up 2 rows when on row 2
            if (row == 7 && board[row - 2][column] == null) {
                moves.add(new Point(5, column));
            }

            // Go up and take a piece diagonally
            if (isEnemy(board[row - 1][column + 1])) {
                moves.add(new Point(row - 1, column + 1));
            }

            // Go up and take a piece diagonally
            if (isEnemy(board[row - 1][column - 1])) {
                moves.add(new Point(row - 1, column - 1));
            }
        }
        return moves;
    }
}