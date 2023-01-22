package mac.chess;

// Java Imports

import mac.chess.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    // True for white, false for black
    public static boolean currentTeam = true;
    private static ChessMenu chess;

    // chessBoard Array. Keep in mind the array has padding for row 0 and column 0
    public static final ChessPiece[][] chessBoard = {
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


    public static void main(String... args) {

        // Create new ChessBoard
        chess = new ChessMenu();

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
                if (piece == null) continue;
                if (piece.white != inputWhite) continue;
                allMoves.addAll(piece.moveList(tempBoard));
            }
        }
        return allMoves;
    }

    public static boolean checkMateDetector(ChessPiece[][] board, boolean inputWhite) {
        ChessPiece[][] boardTemp;
        List<Point> moveList;

        ChessPiece king = findKing(board, inputWhite);

        ChessPiece tempPiece;

        // loops the array
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {

                // Skip if not the player piece
                tempPiece = board[x][y];
                if (tempPiece == null) continue;
                if (tempPiece.white != inputWhite) continue;

                // Make a temporary copy of the current given board
                boardTemp = new ChessPiece[board.length][];
                for (int i = 0; i < board.length; i++) {
                    boardTemp[i] = Arrays.copyOf(board[i], board[i].length);
                }

                // set moveList to the current piece moveList
                moveList = tempPiece.moveList(boardTemp);

                // For the current piece, simulate every move it can make it the temporary board
                // If any of the moves stop the check, then return false.
                for (Point p : moveList) {
                    boardTemp[x][y].updatePosition(p, boardTemp, true);
                    ArrayList<Point> enemyMoves = allMoves(boardTemp, !inputWhite);

                    // If the temp piece is the king, see if the enemy move list contains any of the
                    if (tempPiece instanceof King) {
                        if (!enemyMoves.contains(p)) {
                            return false;
                        }
                    }
                    // Check if after the updated position, the enemyMoves still contain the king.
                    else if (!enemyMoves.contains(king.getPoint())) {
                        return false;
                    }
                    // If the move does not successfully prevent chess, reset the board and retry.
                    boardTemp = new ChessPiece[board.length][];
                    for (int i = 0; i < board.length; i++) {
                        boardTemp[i] = Arrays.copyOf(board[i], board[i].length);
                    }
                }
            }
        }

        // Return that checkMate has been detected unless proven otherwise with return false
        return true;
    }

    public static boolean staleMateDetector(ChessPiece[][] board, boolean inputWhite) {
        List<Point> moveList;

        ChessPiece tempPiece;

        // loops the array
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {

                // Skip if not the player piece
                tempPiece = board[x][y];
                if (tempPiece == null) continue;
                if (tempPiece.white != inputWhite) continue;

                // set moveList to the current piece moveList
                moveList = tempPiece.moveList(board);

                // If a move is possible without putting the king in check, then declare no stalemate
                for (Point p : moveList) {
                    if(tempPiece.isValid(p, board)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static ChessPiece findKing(ChessPiece[][] board, boolean inputWhite) {
        ChessPiece king = null;

        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {
                if (board[x][y] instanceof King && board[x][y].white == inputWhite) king = board[x][y];
            }
        }
        return king;
    }

    // Detect if the game is over
    // This is before the player moves
    // If the game is over, the board will stay visible but lock up since no move is valid. This is intentional.
    public static void gameOver() {
        ChessPiece kingPiece = findKing(chessBoard, currentTeam);

        //
        // Stalemate detector
        //
        boolean stalemate = true;

        // Check if the king is able to move
        for (Point p : kingPiece.moveList(chessBoard)) {
            if (kingPiece.isValid(p, chessBoard)) {
                stalemate = false;
            }
        }

        // If the king is unable to move, check if the rest of the pieces are able to move
        if(stalemate) {
            if(staleMateDetector(chessBoard, currentTeam)) {
                chess.showWinner("No one", "Stalemate");
            }
        }

        //
        // Checkmate Detecting
        //

        ArrayList<Point> enemyMoves = allMoves(chessBoard, !currentTeam);

        // Only run if the King is in check
        if (enemyMoves.contains(kingPiece.getPoint())) {

            // Check if king is unable to move due to check
            boolean possible = false;
            for (Point p : kingPiece.moveList(chessBoard)) {
                if (kingPiece.isValid(p, chessBoard)) {
                    possible = true;
                }
            }

            // If the King is unable to move, then check for checkMate
            if (!possible) {
                if(checkMateDetector(chessBoard, currentTeam)) {
                    if(currentTeam) {
                        chess.showWinner("Black", "Checkmate");
                    } else {
                        chess.showWinner("White", "Checkmate");
                    }
                }
            }
        }
    }
}