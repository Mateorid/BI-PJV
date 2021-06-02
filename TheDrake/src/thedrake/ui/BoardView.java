package thedrake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import thedrake.BoardPos;
import thedrake.GameState;
import thedrake.Move;
import thedrake.PositionFactory;

import java.util.List;

public class BoardView extends GridPane implements TileViewContext {

    private GameState gameState;

    private ValidMoves validMoves;

    private TileView selectedTile = null;

    public BoardView(GameState gameState) {
        this.gameState = gameState;
        this.validMoves = new ValidMoves(gameState);

        PositionFactory positionFactory = gameState.board().positionFactory();

        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                BoardPos boardPos = positionFactory.pos(x, 3 - y);
                add(new TileView(boardPos, gameState.tileAt(boardPos), this), x, y);
            }
        }
        setHgap(5);
        setVgap(5);
        setPadding(new Insets(15));
        setAlignment(Pos.CENTER);
    }


    @Override
    public void tileViewSelected(TileView tileView) {
        if (selectedTile != null && selectedTile != tileView)
            selectedTile.unselect();

        selectedTile = tileView;

        clearMoves();

        showMoves(validMoves.boardMoves(tileView.getBoardPos()));
    }

    @Override
    public void executeMove(Move move) {
        selectedTile.unselect();
        selectedTile = null;
        clearMoves();
        gameState = move.execute(gameState);
        validMoves = new ValidMoves(gameState);
        updateTiles();
    }

    private void showMoves(List<Move> moves) {
        for (Move move : moves) {
            tileViewOn(move.target()).setMove(move);
        }
    }

    private TileView tileViewOn(BoardPos pos) {
        int index = (3 - pos.j()) * 4 + pos.i();
        return (TileView) getChildren().get(index);
    }

    private void updateTiles() {
        for (Node node : getChildren()) {
            TileView tileView = (TileView) node;
            tileView.setTile(gameState.tileAt(tileView.getBoardPos()));
        }
    }

    private void clearMoves() {
        for (Node node : getChildren()) {
            ((TileView) node).clearMove();
        }
    }
}
