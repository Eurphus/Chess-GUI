package me.mac.chess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ChessBoard extends Application {

    // Temporary, need to add better way to launch
    public void main() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: #fff;");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle square = new Rectangle(100, 100);
                square.setFill((i + j) % 2 == 0 ? Color.WHITE : Color.GRAY);
                root.add(square, i, j);
            }
        }
        Scene scene = new Scene(root, 1000, 1000);
        primaryStage.setTitle("Chess Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}