package mac.chess;

import javafx.scene.image.Image;
import mac.chess.pieces.King;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static mac.chess.Main.*;

public class ChessPiece {
    String type;
    Image icon;
    public int row;
    public int column;
    public boolean white;

    public ChessPiece(int inputRow, int inputColumn, boolean inputWhite) {
        row = inputRow;
        column = inputColumn;
        white = inputWhite;
    }

    public void updatePosition(Point newPoint, ChessPiece[][] board, boolean copy) {
        System.out.println("Attempting...");
        System.out.println("Attempting: " + this.getPoint());
        System.out.println(Arrays.deepToString(board));
        if(board[this.row][this.column] == null) System.out.println("NULL");
        ChessPiece piece = copy ? (ChessPiece) board[this.row][this.column].clone() : board[this.row][this.column];
        System.out.println(piece.toString());

        // Make old position be null
        board[row][column] = null;
        System.out.println("%");
        // Add piece to new position, will overwrite if something is already there
        board[newPoint.row][newPoint.column] = piece;
        System.out.println("%2");

        // Updating the variables
        piece.setRow(newPoint.row);
        System.out.println("%3");
        piece.setColumn(newPoint.column);
        System.out.println("%4");
    }

    // Checking if the move is actually valid
    public boolean isValid(Point newPoint, ChessPiece[][] board) {
        ArrayList<Point> possibleMoves = this.moveList(board);
        if(!possibleMoves.contains(newPoint)) {
            System.out.println("#1");
            System.out.println(possibleMoves);
            return false;
        }

        // define a temporary chessBoard that's a copy of the current with the users input
        ChessPiece[][] boardTemp = new ChessPiece[board.length][];
        for (int i = 0; i < board.length; i++) {
            boardTemp[i] = Arrays.copyOf(board[i], board[i].length);
        }
        updatePosition(newPoint, boardTemp, true);

        System.out.println("Reset");

        // Check if after the move player is in check
        ArrayList<Point> enemyMoves = allMoves(boardTemp, !this.white);
        ChessPiece king = findKing(boardTemp, white);
        if(enemyMoves.contains(king.getPoint())) {
            System.out.println("Failed! This move puts you into check");
            return false;
        }
        return true;
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

    public Image getIcon() throws IOException {
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
            System.out.println("Trying...");
            System.out.println(this.toString());
            return this.getClass().getConstructors()[0].newInstance(this.row, this.column, this.white);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed");
            return null;
        }
    }
    public void setRow(int inputRow) {
        this.row = inputRow;
    }
    public void setColumn(int inputColumn) {
        this.column = inputColumn;
    }
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
