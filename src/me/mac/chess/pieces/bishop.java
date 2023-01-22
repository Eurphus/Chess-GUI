package me.mac.chess.pieces;

import me.mac.chess.ChessPiece;

import java.util.ArrayList;

import static me.mac.chess.data.rowList;

public class bishop extends ChessPiece {

    public bishop(int inputRow, int inputColumn, boolean inputWhite) {
        super(inputRow, inputColumn, inputWhite);
    }

    public ArrayList<String> moveList(ChessPiece[][] board) {
        ArrayList<String> moveList = new ArrayList<>();

        // Bishop Diagonal Up Right
        for (int i = column + 1, q = row + 1; (i <= 8 && q <= 8); i++, q++) {
            System.out.println(column + " " + row);
            if (super.isTeam(board[i][q])) {
                System.out.println("BREAKING");
                break;
            } else if (super.isEnemy(board[i][q])) {
                moveList.add(rowList.get(q) + i);
                System.out.println("ADDING AND BREAKING");
                break;
            }
            System.out.println("ADDING");
            moveList.add(rowList.get(q) + i);
        }
//
//        // Bishop Diagonal Down Left
//        for (int i = column - 1, q = row - 1; (i >= 1 && q >= 1); i--, q--) {
//            if (super.isTeam(board[i][q])) {
//                System.out.println("BREAKING");
//                break;
//            } else if (super.isEnemy(board[i][q])) {
//                moveList.add(rowList.get(q) + i);
//                System.out.println("ADDING AND BREAKING");
//                break;
//            }
//            System.out.println("ADDING");
//            moveList.add(rowList.get(q) + i);
//        }
//
//        // Bishop Diagonal Down Right
//        for (int i = column - 1, q = row + 1; (i >= 1 && q >= 8); i--, q++) {
//            if (super.isTeam(board[i][q])) {
//                break;
//            } else if (super.isEnemy(board[i][q])) {
//                moveList.add(rowList.get(q) + i);
//                break;
//            }
//            moveList.add(rowList.get(q) + i);
//        }
//
//        // Bishop Diagonal Up Left
//        for (int i = column + 1, q = row - 1; (i <= 8 && q >= 1); i++, q--) {
//            if (super.isTeam(board[i][q])) {
//                break;
//            } else if (super.isEnemy(board[i][q])) {
//                moveList.add(rowList.get(q) + i);
//                break;
//            }
//            moveList.add(rowList.get(q) + i);
//        }
        return moveList;
    }
}
