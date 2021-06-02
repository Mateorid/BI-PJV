package thedrake;

import javafx.scene.layout.BorderPane;
import thedrake.ui.BoardView;
import thedrake.ui.Stack;

public class GameView extends BorderPane {

    private final BoardView boardView;
    private final Stack orangeStack;
    private final Stack blueStack;

    GameView(BoardView boardView, Stack orangeStack, Stack blueStack) {
        this.blueStack = blueStack;
        this.orangeStack = orangeStack;
        this.boardView = boardView;
    }
}
