package thedrake.ui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import thedrake.*;


public class MenuController {

    private Stage stage;

    @FXML
    private Button exitBtn;
    @FXML
    private Button onlineMPBtn;
    @FXML
    private Button localMPBtn;
    @FXML
    private Button localSPBtn;

    public void startGameAction(ActionEvent actionEvent) {
        BoardView boardView = new BoardView(createSampleGS());
        stage.setScene(new Scene(boardView,800,500));
        stage.show();
    }

    public void exitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private static GameState createSampleGS() {
        //todo change
        Board board = new Board(4);
        PositionFactory positionFactory = board.positionFactory();
        board = board.withTiles(new Board.TileAt(positionFactory.pos(1, 1), BoardTile.MOUNTAIN));
        return new StandardDrakeSetup().startState(board)
                .placeFromStack(positionFactory.pos(0, 0))
                .placeFromStack(positionFactory.pos(3, 3))
                .placeFromStack(positionFactory.pos(0, 1))
                .placeFromStack(positionFactory.pos(3, 2))
                .placeFromStack(positionFactory.pos(1, 0))
                .placeFromStack(positionFactory.pos(2, 3));
    }

    //-------------------------------------------Hover glow stuff-------------------------------------------
    private final Glow glow = new Glow(1);

    public void mouseEnteredHighlight(MouseEvent event) {
        localSPBtn.setEffect(glow);
    }

    public void mouseExitedHighlight(MouseEvent event) {
        localSPBtn.setEffect(null);
    }

    public void exitOn(MouseEvent event) {
        exitBtn.setEffect(glow);
    }

    public void exitOff(MouseEvent event) {
        exitBtn.setEffect(null);
    }

    public void onlineMPOn(MouseEvent event) {
        onlineMPBtn.setEffect(glow);
    }

    public void onlineMPOff(MouseEvent event) {
        onlineMPBtn.setEffect(null);
    }

    public void localMPOn(MouseEvent event) {
        localMPBtn.setEffect(glow);
    }

    public void localMPOff(MouseEvent event) {
        localMPBtn.setEffect(null);
    }
}
