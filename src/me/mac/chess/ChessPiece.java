package me.mac.chess;

import javafx.scene.image.Image;

import java.util.ArrayList;

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
    public void updatePosition(int inputRow, int inputColumn) {
        row = inputRow;
        column = inputColumn;
    }

    // Checks if provided piece is the same team or not. True for if it is, false if it isn't or is null
    public boolean isTeam(ChessPiece checkPiece) {
        if(checkPiece != null) {
            return checkPiece.white == this.white;
        }
        return false;
    }

    // Checks if provided piece is the enemy team or not. True for if it is, false if it isn't or is null
    public boolean isEnemy(ChessPiece checkPiece) {
        if(checkPiece != null) {
            return checkPiece.white != this.white;
        }
        return false;
    }

    public ArrayList<String> moveList(ChessPiece[][] chessBoard) {
        return new ArrayList<>();
    }
}
