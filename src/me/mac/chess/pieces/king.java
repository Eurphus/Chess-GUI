package me.mac.chess.pieces;

// Chess File Imports

import me.mac.chess.ChessPiece;

import java.util.ArrayList;

import static me.mac.chess.data.rowList;

public class king extends ChessPiece {

    // Constructor
    public king(int inputRow, int inputColumn, boolean inputWhite) {
        super(inputRow, inputColumn, inputWhite);
    }


    public ArrayList<String> moveList(ChessPiece[][] board) {
        ArrayList<String> moveList = new ArrayList<>();

        // All possible moves. Formatted as {row difference, column difference}
        int[][] combinations = {{0, 1}, {1, 0}, {-1, 0}, {0, -1}, {1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        int horizontal, vertical;

        // For each possible move (max 8 moves), goes through each one to see if it is possible
        for (int i = 0; i < 8; i++) {
            horizontal = row + combinations[i][0];
            vertical = column + combinations[i][1];

            // Checks if this specific row/column combination is possible - Not outside board or blocked by piece.
            if (horizontal >= 1 && horizontal <= 8 && vertical >= 1 && vertical <= 8) {

                // Check if surrounding tiles are enemy or empty
                if (!this.isTeam(board[vertical][horizontal])) {
                    moveList.add(rowList.get(horizontal) + vertical);
                }
            }
        }
        return moveList;
    }
}