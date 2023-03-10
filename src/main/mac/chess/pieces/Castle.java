package mac.chess.pieces;

import mac.chess.ChessPiece;
import mac.chess.Point;

import java.util.ArrayList;

public class Castle extends ChessPiece {
    public Castle(int inputRow, int inputColumn, boolean inputWhite) {
        super(inputRow, inputColumn, inputWhite);
    }

    public ArrayList<Point> moveList(ChessPiece[][] board) {
        ArrayList<Point> moveList = new ArrayList<>();


        // All possible moves. Formatted as {row difference, column difference}
        int[] rowDelta = {0, 0, 1, -1};
        int[] columnDelta = {1, -1, 0, 0};
        // {Right, Left, Down, Up}

        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 9; j++) {
                // Increase or decrease the row by 1 every iteration
                int x = row + rowDelta[i] * j;

                // Increase or decrease the column by 1 every iteration
                int y = column + columnDelta[i] * j;

                // Makes sure the provided coordinates are within the bounds
                if (x >= 1 && x <= 8 && y >= 1 && y <= 8) {

                    // If the point provided has a same player piece, stop looking past it.
                    if (isTeam(board[x][y])) {
                        break;
                    }
                    // If the point provided has an enemy piece, add it to the move list, then stop looking past it
                    else if (isEnemy(board[x][y])) {
                        moveList.add(new Point(x, y));
                        break;
                    } else {

                        moveList.add(new Point(x, y));
                    }
                }
            }
        }
        return moveList;
    }
}
