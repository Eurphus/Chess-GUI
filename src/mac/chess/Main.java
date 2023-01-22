package mac.chess;

// Java Imports

import mac.chess.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    // True for white, false for black
    public static boolean chosenTeam;
    public static boolean currentTeam = true;
    public static boolean chosenWhite;

    public int difficulty;

    // chessBoard Array. Keep in mind the array has padding for row 0 and column 0
    public static ChessPiece[][] chessBoard = {
            {null, null, null, null, null, null, null, null, null, null},
            {null, new Castle(1, 1, true), new Horse(1, 2, true), new Bishop(1, 3, true), new Queen(1, 4, true), new King(1, 5, true), new Bishop(1, 6, true), new Horse(1, 7, true), new Castle(1, 8, true), null},
            {null, new Pawn(2, 1, true), new Pawn(2, 2, true), new Pawn(2, 3, true), new Pawn(2, 4, true), new Pawn(2, 5, true), new Pawn(2, 6, true), new Pawn(2, 7, true), new Pawn(2, 8, true), null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, new Pawn(7, 1, false), new Pawn(7, 2, false), new Pawn(7, 3, false), new Pawn(7, 4, false), new Pawn(7, 5, false), new Pawn(7, 6, false), new Pawn(7, 7, false), new Pawn(7, 8, false), null},
            {null, new Castle(8, 1, false), new Horse(8, 2, false), new Bishop(8, 3, false), new Queen(8, 4, false), new King(8, 5, false), new Bishop(8, 6, false), new Horse(8, 7, false), new Castle(8, 8, false), null}
    };
    /*public static ChessPiece[][] chessBoard = {
            {null, null, null, null, null, null, null, null, null, null},
            {null, new King(1, 1, true), null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null, null, null},
            {null, null, null, null, new Queen(8, 4, false), new King(8, 5, false), null, null, null}
    };*/


    public static void main(String... args) {

        Scanner scan = new Scanner(System.in);
        int teamInt = -1;


        // Create new ChessBoard
        ChessMenu chess = new ChessMenu();

        // Call the main method in class
        chess.main();
    }

    // Find all possible moves from all pieces within a given chessBoard & player
    public static ArrayList<Point> allMoves(ChessPiece[][] tempBoard, boolean inputWhite) {
        ArrayList<Point> allMoves = new ArrayList<>();
        ChessPiece piece;
        // Goes through every single position on the board finding white pieces, then adding their available moves to the total moveList
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                piece = tempBoard[x][y];
                if(piece == null) continue;
                if (piece.white != inputWhite) continue;
                allMoves.addAll(piece.moveList(tempBoard));
                System.out.println(piece.getClass().getSimpleName() + ": " + piece.moveList(tempBoard));
            }
        }
        System.out.println("Submitting");
        return allMoves;
    }

    public static boolean checkMateDetector(ChessPiece[][] board, boolean inputWhite) {
        ChessPiece[][] boardTemp;
        List<Point> moveList = null;

        ChessPiece king = findKing(board, inputWhite);

        ChessPiece tempPiece;
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                tempPiece = board[x][y];
                if (tempPiece == null) continue;
                if (tempPiece.white != inputWhite) continue;
                moveList = tempPiece.moveList(board);

                // Make a temporary copy of the current given board
                boardTemp = Arrays.stream(board).map(ChessPiece[]::clone).toArray(ChessPiece[][]::new);

                // Check if each move in the given moveList of the piece would change the check
                for (Point p : moveList) {
                    boardTemp[p.row][p.column] = tempPiece;
                    boardTemp[x][y] = null;

                    // If the tempPiece is the king, make the kingPosition update with every possible move for the king
                    if (tempPiece.getClass().getSimpleName().equals("King")) {
                        if (!allMoves(boardTemp, !inputWhite).contains(new Point(p.row, p.column))) {
                            return false;
                        }

                        // If the move successfully prevents chess, return that checkMake has not been detected, and reset the temporary board.
                        if (!allMoves(boardTemp, !inputWhite).contains(new Point(king.row, king.column))) {
                            return false;
                        } else {
                            // If the move does not successfully prevent chess, reset the board and retry.
                            boardTemp = Arrays.stream(board).map(ChessPiece[]::clone).toArray(ChessPiece[][]::new);
                        }
                    }
                }
            }
        }
        // Return that checkMate has been detected unless proven otherwise with return false
        return true;
    }

    public static ChessPiece findKing(ChessPiece[][] board, boolean inputWhite) {
        ChessPiece king = null;
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                if(board[x][y] instanceof King && board[x][y].white == inputWhite) king = board[x][y];
            }
        }
        return king;
    }

    public static ChessPiece[][] getCopy(ChessPiece[][] board) {
        ChessPiece[][] newBoard = java.util.Arrays.stream(board).map(el -> el.clone()).toArray($ -> board.clone());

        return newBoard;
    }



    public static void gameOver() {
        System.out.println("Game over");
    }
}