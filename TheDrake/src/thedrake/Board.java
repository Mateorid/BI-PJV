package thedrake;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.IntStream;

public final class Board implements JSONSerializable {

    private final PositionFactory positionFactory;
    private final BoardTile[][] boardTiles;

    // Konstruktor. Vytvoří čtvercovou hrací desku zadaného rozměru, kde všechny dlaždice jsou prázdné, tedy BoardTile.EMPTY
    public Board(int dimension) {
        this.positionFactory = new PositionFactory(dimension);
        boardTiles = new BoardTile[dimension][dimension];

        //Initializes the 2D array with BoardTile.EMPTY
        IntStream.range(0, boardTiles.length).forEach(i -> Arrays.fill(boardTiles[i], BoardTile.EMPTY));
    }

    // Rozměr hrací desky
    public int dimension() {
        return positionFactory.dimension();
    }

    // Vrací dlaždici na zvolené pozici.
    public BoardTile at(TilePos pos) {
        return boardTiles[pos.i()][pos.j()];
    }

    // Vytváří novou hrací desku s novými dlaždicemi. Všechny ostatní dlaždice zůstávají stejné
    public Board withTiles(TileAt... ats) {
        Board newBoard = new Board(dimension());
        //Clones the old board into the new one
        for (int i = 0; i < boardTiles.length; i++) {
            newBoard.boardTiles[i] = boardTiles[i].clone();
        }
        //Inserts new tiles at desired location
        int i, j;
        for (TileAt at : ats) {
            i = at.pos.i();
            j = at.pos.j();
            newBoard.boardTiles[i][j] = at.tile;
        }
        return newBoard;
    }

    // Vytvoří instanci PositionFactory pro výrobu pozic na tomto hracím plánu
    public PositionFactory positionFactory() {
        return positionFactory;
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{\"dimension\":" + dimension() + ",");
        writer.print("\"tiles\":[");
        for (int col = 0; col < dimension(); col++) {
            for (int row = 0; row < dimension(); row++) {
                at(positionFactory.pos(row, col)).toJSON(writer);
                if (!(col == dimension() - 1 && row == dimension() - 1))
                    writer.print(",");
            }
        }
        writer.print("]}");
    }

    public static class TileAt {
        public final BoardPos pos;
        public final BoardTile tile;

        public TileAt(BoardPos pos, BoardTile tile) {
            this.pos = pos;
            this.tile = tile;
        }
    }
}

