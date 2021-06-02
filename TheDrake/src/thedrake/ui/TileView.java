package thedrake.ui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import thedrake.BoardPos;
import thedrake.Move;
import thedrake.Tile;


public class TileView extends Pane {

    private final BoardPos boardPos;

    private Tile tile;

    private Move move = null;

    private final ImageView moveImage;

    private final TileViewContext tileViewContext;

    private final Border selectionBorder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    private final TileBackgrounds tileBackgrounds = new TileBackgrounds();

    public TileView(BoardPos boardPos, Tile tile, TileViewContext tileViewContext) {
        this.boardPos = boardPos;
        this.tile = tile;
        this.tileViewContext = tileViewContext;

        setPrefSize(100, 100);
        update();

        setOnMouseClicked(event -> onClick());

        moveImage = new ImageView(getClass().getResource("/assets/move.png").toString());
        moveImage.setVisible(false);
        getChildren().add(moveImage);
    }

    public void setMove(Move move) {
        this.move = move;
        moveImage.setVisible(true);
    }

    public void clearMove() {
        move = null;
        moveImage.setVisible(false);
    }

    private void onClick() {
        if (move != null)
            tileViewContext.executeMove(move);
        if (tile.hasTroop())
            select();
    }

    private void select() {
        setBorder(selectionBorder);
        tileViewContext.tileViewSelected(this);
    }

    public void unselect() {
        setBorder(null);
    }

    public void setTile(Tile tile) {
        this.tile = tile;
        update();
    }

    private void update() {
        setBackground(tileBackgrounds.get(tile));
    }

    public BoardPos getBoardPos() {
        return boardPos;
    }
}
