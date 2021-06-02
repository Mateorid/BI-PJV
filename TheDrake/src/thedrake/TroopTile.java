package thedrake;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static thedrake.TroopFace.AVERS;
import static thedrake.TroopFace.REVERS;

public final class TroopTile implements Tile {

    private final Troop troop;
    private final PlayingSide playingSide;
    private final TroopFace troopFace;

    // Konstruktor
    public TroopTile(Troop troop, PlayingSide playingSide, TroopFace troopFace) {
        this.troop = troop;
        this.playingSide = playingSide;
        this.troopFace = troopFace;
    }

    // Vrací barvu, za kterou hraje jednotka na této dlaždici
    public PlayingSide side() {
        return playingSide;
    }

    // Vrací stranu, na kterou je jednotka otočena
    public TroopFace face() {
        return troopFace;
    }

    // Jednotka, která stojí na této dlaždici
    public Troop troop() {
        return troop;
    }

    // Vrací False, protože na dlaždici s jednotkou se nedá vstoupit
    public boolean canStepOn() {
        return false;
    }

    // Vrací True
    public boolean hasTroop() {
        return true;
    }

    @Override
    public List<Move> movesFrom(BoardPos pos, GameState state) {
        List<Move> result = new ArrayList<>();
        for (TroopAction it : troop.actions(troopFace)) {
            result.addAll(it.movesFrom(pos, playingSide, state));
        }
        return result;
    }

    // Vytvoří novou dlaždici, s jednotkou otočenou na opačnou stranu
    // (z rubu na líc nebo z líce na rub)
    public TroopTile flipped() {
        return new TroopTile(troop, playingSide, troopFace == AVERS ? REVERS : AVERS);
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{");
        writer.print("\"troop\":");
        troop.toJSON(writer);
        writer.print(",\"side\":");
        playingSide.toJSON(writer);
        writer.print(",\"face\":");
        troopFace.toJSON(writer);
        writer.print("}");
    }
}
