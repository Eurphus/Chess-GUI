package mac.chess;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static mac.chess.Main.*;

public class ChessPiece {
    public int row;
    public int column;
    public final boolean white;

    public ChessPiece(int inputRow, int inputColumn, boolean inputWhite) {
        row = inputRow;
        column = inputColumn;
        white = inputWhite;
    }

    public void updatePosition(Point newPoint, ChessPiece[][] board, boolean copy) {
        // Define piece based off the original board, or of a copy
        ChessPiece piece = copy ? (ChessPiece) board[this.row][this.column].clone() : board[this.row][this.column];

        // Make old position be null
        board[row][column] = null;

        // Add piece to new position, will overwrite if something is already there
        board[newPoint.row][newPoint.column] = piece;

        // Updating the variables
        piece.row = newPoint.row;
        piece.column = newPoint.column;
    }

    // Checking if the move is actually valid
    public boolean isValid(Point newPoint, ChessPiece[][] board) {
        ArrayList<Point> possibleMoves = this.moveList(board);
        if(!possibleMoves.contains(newPoint)) return false;

        // define a temporary chessBoard that's a copy of the current with the users input
        ChessPiece[][] boardTemp = new ChessPiece[board.length][];
        for (int i = 0; i < board.length; i++) {
            boardTemp[i] = Arrays.copyOf(board[i], board[i].length);
        }
        updatePosition(newPoint, boardTemp, true);

        // Check if after the move player is in check
        ArrayList<Point> enemyMoves = allMoves(boardTemp, !this.white);
        ChessPiece king = findKing(boardTemp, white);
        return !enemyMoves.contains(king.getPoint());
    }

    // Checks if provided piece is the same team or not. True for if it is, false if it isn't or is null
    public boolean isTeam(ChessPiece checkPiece) {
        if(checkPiece != null) {
            return checkPiece.white == white;
        }
        return false;
    }

    // Checks if provided piece is the enemy team or not. True for if it is, false if it isn't or is null
    public boolean isEnemy(ChessPiece checkPiece) {
        if(checkPiece != null) {
            return checkPiece.white != white;
        }
        return false;
    }

    public ArrayList<Point> moveList(ChessPiece[][] board) {
        return new ArrayList<>();
    }

    public Image getIcon() {
        // Find resource name based off the current subClass and team
        String name = white ? "white" : "black";
        name += this.getClass().getSimpleName();

        return new Image(Objects.requireNonNull(getClass().getResource("/" + name + ".png")).toExternalForm());
    }

    public Point getPoint() {
        return new Point(this.row, this.column);
    }

    public Object clone() {
        try {
            return this.getClass().getConstructors()[0].newInstance(this.row, this.column, this.white);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String toString() {
        return this.getClass().getSimpleName();
    }
}