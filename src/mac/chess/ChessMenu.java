package mac.chess;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import static mac.chess.Main.*;


public class ChessMenu extends Application {

    // Variable for saving images when they are clicked
    private ImageView selectedPiece;

    // Temporary, need to add better way to launch
    public void main() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) {

        // Create board and values
        GridPane board = new GridPane();
        board.setMinSize(0, 0);
        board.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        board.minWidthProperty().bind(board.heightProperty());
        board.minHeightProperty().bind(board.widthProperty());
        board.maxWidthProperty().bind(board.heightProperty());
        board.maxHeightProperty().bind(board.widthProperty());
        board.setStyle("-fx-background-color: #fff;");

        // Create and add row and column labels
        for (int i = 0; i < 8; i++) {
            // Column 0
            addLabeledSquare(board, 0, i + 1, Integer.toString(i + 1));

            // Column 9
            addLabeledSquare(board, 9, i + 1, Integer.toString(i + 1));

            // Row 0
            addLabeledSquare(board, i + 1, 0, Character.toString((char) ('A' + i)));

            // Row 9
            addLabeledSquare(board, i + 1, 9, Character.toString((char) ('A' + i)));
        }

        // Create chessboard GUI
        for (int x = 1; x < 9; x++) {
            for (int y = 1; y < 9; y++) {

                // Create the square
                Rectangle tileSquare = new Rectangle();
                tileSquare.widthProperty().bind(board.widthProperty().divide(10));
                tileSquare.heightProperty().bind(tileSquare.widthProperty());

                // Determine colour of square
                tileSquare.setFill((y + x) % 2 == 0 ? Color.GRAY : Color.WHITE);

                StackPane tile = new StackPane();

                tile.getChildren().add(tileSquare);

                // If there is a piece at this location, add it to the board
                if (chessBoard[x][y] != null) {

                    // Create imageview using the getIcon() method from getting whatever piece is at the defined x and y
                    ImageView piece = new ImageView(chessBoard[x][y].getIcon());

                    // Make it a little smaller than the tile itself
                    piece.fitWidthProperty().bind(tileSquare.widthProperty().divide(1.1));
                    piece.fitHeightProperty().bind(tileSquare.heightProperty().divide(1.1));

                    tile.getChildren().add(piece);

                    // When a piece is clicked on and about to be dragged, make selectedPiece the clicked on piece
                    piece.setOnMousePressed(event -> {
                        if(chessBoard[GridPane.getRowIndex(piece.getParent())][GridPane.getColumnIndex(piece.getParent())].white == currentTeam) {
                            selectedPiece = piece;
                        }
                    });


                    // Helps enable dragging and helping it look a little nicer + verify
                    piece.setOnDragOver(event -> {
                        event.acceptTransferModes(TransferMode.MOVE);
                        event.consume();
                    });
                    piece.setOnDragDetected(event -> {
                        Dragboard db = piece.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putImage(piece.getImage());
                        db.setContent(content);
                        db.setDragView(piece.getImage(), event.getX(), event.getY());
                        event.consume();
                    });
                }

                // Add the stackPane tile to the board
                // Remember it is .add(object, column, row)
                board.add(tile, y, x);

                // When something is dragged over the tile, accept transfer mode
                tile.setOnDragOver(event -> {
                    event.acceptTransferModes(TransferMode.MOVE);
                    event.consume();
                });

                // When the tile has something dropped on it
                tile.setOnDragDropped(event -> {
                    Dragboard db = event.getDragboard();

                    // If an image is currently stored in the dB, indicating an existing event
                    if (db.hasImage() && selectedPiece != null) {
                        // Find the original square
                        StackPane sourceSquare = (StackPane) selectedPiece.getParent();

                        // Get current and new coordinates
                        int originalX = GridPane.getRowIndex(sourceSquare);
                        int originalY = GridPane.getColumnIndex(sourceSquare);

                        // Put the new coordinates into a point object
                        Point newPoint = new Point(GridPane.getRowIndex(tile), GridPane.getColumnIndex(tile));

                        // If the move is valid, then go ahead with it
                        if (chessBoard[originalX][originalY].isValid(newPoint, chessBoard)) {
                            // Remove the imageView from old tile
                            sourceSquare.getChildren().remove(selectedPiece);

                            // If the tile is already occupied, remove other piece
                            if(tile.getChildren().size() == 2) {
                                tile.getChildren().remove(1);
                            }

                            // Add imageView to new tile
                            tile.getChildren().add(selectedPiece);

                            // Tell the chessPiece to update its position
                            chessBoard[originalX][originalY].updatePosition(newPoint, chessBoard, false);

                            // Change Team
                            currentTeam = !currentTeam;

                            // Check if the game is over
                            gameOver();

                            // Reset the currently selected piece
                            selectedPiece = null;
                            event.setDropCompleted(true);
                        } else {
                            // Reset the currently selected piece since didn't go through
                            selectedPiece = null;
                            event.setDropCompleted(false);
                        }
                    } else {
                        // Reset the currently selected piece
                        selectedPiece = null;
                        event.setDropCompleted(false);
                    }
                    // Reset the currently selected piece
                    selectedPiece = null;
                    event.consume();
                });
            }
        }

        // Make new scene and define default size
        Scene scene = new Scene(board, 600, 600);

        // Set min and max values of the screen
        primaryStage.setMinHeight(100);
        primaryStage.setMinWidth(100);
        primaryStage.setMaxHeight(1000);
        primaryStage.setMaxWidth(1000);

        // Set title and show scene
        primaryStage.setTitle("Chess Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Add a labelled square to the array
    private void addLabeledSquare(GridPane board, int column, int row, String label) {

        // Create stackPane
        StackPane tile = new StackPane();

        // Create white square
        Rectangle square = new Rectangle();
        square.widthProperty().bind(board.widthProperty().divide(10));
        square.heightProperty().bind(square.widthProperty());
        square.setFill(Color.WHITE);

        // Add both label and square to the stackPane
        tile.getChildren().add(square);
        tile.getChildren().add(new Label(label));

        // Add the tile to the board
        board.add(tile, column, row);
    }

    // Make a pop-up when a winner is decided,
    public void showWinner(String winner, String text) {
        Stage winnerStage = new Stage();

        // Create label indicating winner
        Label winnerLabel = new Label(winner + " wins!");
        winnerLabel.setStyle("-fx-font-size: 50px;");

        // Create label for how they won
        Label messageLabel = new Label(text);
        messageLabel.setStyle("-fx-font-size: 30px;");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> winnerStage.close());

        // Create a VBox layout to hold the labels on button
        VBox vbox = new VBox(winnerLabel, messageLabel, closeButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);


        // Display the VBox
        Scene popUp = new Scene(vbox, 600, 600);
        winnerStage.setScene(popUp);
        winnerStage.show();
    }
}