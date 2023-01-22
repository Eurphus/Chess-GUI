package me.mac.chess;

// Java Imports

import me.mac.chess.pieces.bishop;
import me.mac.chess.pieces.king;

public class Main {
    public static void main(String... args) {

        // ChessPiece Array
        ChessPiece[][] chessBoard = {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, new bishop(1, 4, true), new king(1, 5, true), new king(1, 6, true), new bishop(1, 7, true), null, null},
                {null, null, null, null, null, new king(2, 5, false), null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, new bishop(5, 3, true), null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
        };

        // Temporary testing
        for(int x = 0; x < chessBoard.length; x++) {
            for(int y = 0; y < chessBoard[x].length; y++) {
                if(chessBoard[x][y] != null) {
                    System.out.println(chessBoard[x][y].row);
                    System.out.println(chessBoard[x][y].column);
                    System.out.println(chessBoard[x][y].moveList(chessBoard));
                }
            }
        }

        // Create new ChessBoard
        ChessBoard chess = new ChessBoard();

        // Call the main method in class
        chess.main();
    }
}