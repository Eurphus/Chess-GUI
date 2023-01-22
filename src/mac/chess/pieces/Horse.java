package mac.chess.pieces;

import javafx.scene.image.Image;
import mac.chess.ChessPiece;
import mac.chess.Point;

import java.util.ArrayList;

public class Horse extends ChessPiece {

    // Constructor
    public Horse(int inputRow, int inputColumn, boolean inputWhite) {
        super(inputRow, inputColumn, inputWhite);
    }

    public ArrayList<Point> moveList(ChessPiece[][] board) {
        ArrayList<Point> moves = new ArrayList<>();

        // All possible moves. Formatted as {row difference, column difference}
        int[][] combinations = {{1, 2}, {2, 1}, {2, -1}, {1, -2}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}};
        int horizontal, vertical;

        // For each possible move (max 8 moves), goes through each one to see if it is possible
        for (int i = 0; i < 8; i++) {
            horizontal = row + combinations[i][0];
            vertical = column + combinations[i][1];

            // Checks if this specific row/column combination is possible - Not outside board or blocked by piece.
            if (horizontal >= 1 && horizontal <= 8 && vertical >= 1 && vertical <= 8) {

                // Check if surrounding tiles are enemy or empty
                if (!isTeam(board[horizontal][vertical])) {
                    moves.add(new Point(horizontal, vertical));
                }
            }
        }
        return moves;
    }
}