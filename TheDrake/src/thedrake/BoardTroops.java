package thedrake;

import java.io.PrintWriter;
import java.util.*;

import static thedrake.TroopFace.*;

public class BoardTroops implements JSONSerializable {
    private final PlayingSide playingSide;
    private final Map<BoardPos, TroopTile> troopMap;
    private final TilePos leaderPosition;
    private final int guards;
    private Throwable IllegalArgumentException;

    public BoardTroops(PlayingSide playingSide) {
        this.playingSide = playingSide;
        troopMap = Collections.emptyMap();
        leaderPosition = TilePos.OFF_BOARD;
        guards = 0;
    }

    public BoardTroops(PlayingSide playingSide, Map<BoardPos, TroopTile> troopMap, TilePos leaderPosition, int guards) {
        this.playingSide = playingSide;
        this.troopMap = troopMap;
        this.leaderPosition = leaderPosition;
        this.guards = guards;
    }

    public Optional<TroopTile> at(TilePos pos) {
        if (troopMap.get(pos) == null)
            return Optional.empty();
        return Optional.of(troopMap.get(pos));
    }

    public PlayingSide playingSide() {
        return playingSide;
    }

    public TilePos leaderPosition() {
        return leaderPosition;
    }

    public int guards() {
        int tmp = isLeaderPlaced() ? guards - 1 : guards;
        return Math.min(tmp, 2);
    }

    public boolean isLeaderPlaced() {
        return leaderPosition != TilePos.OFF_BOARD;
    }

    public boolean isPlacingGuards() {
        return isLeaderPlaced() && guards < 3;
    }

    public Set<BoardPos> troopPositions() {
        Set<BoardPos> set = new HashSet<BoardPos>();
        for (Map.Entry<BoardPos, TroopTile> entry : troopMap.entrySet()) {
            if (entry.getValue().hasTroop())
                set.add(entry.getKey());
        }
        return set;
    }

    public BoardTroops placeTroop(Troop troop, BoardPos target) {
        if (troopMap.get(target) != null)    ///policko je obsazene
            throw new IllegalArgumentException();
        ///Vytvoreni nove jednotky a mapy
        TroopTile tile = new TroopTile(troop, playingSide, AVERS);
        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);

        newTroops.put(target, tile);
        if (!isLeaderPlaced())  ///Prvni jednotka = leader
            return new BoardTroops(playingSide, newTroops, target, guards + 1);
        if (isPlacingGuards())
            return new BoardTroops(playingSide, newTroops, leaderPosition, guards + 1);
        return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
    }

    public BoardTroops troopStep(BoardPos origin, BoardPos target) {
        ///Spatny stav
        if (!isLeaderPlaced() || isPlacingGuards())
            throw new IllegalStateException();
        ///Spatne policka
        if (troopMap.get(origin) == null || troopMap.get(target) != null)
            throw new IllegalArgumentException();
        ///Vytvoreni nove jednotky a mapy
        TroopTile tile = new TroopTile(troopMap.get(origin).troop(), playingSide,
                troopMap.get(origin).face() == AVERS ? REVERS : AVERS);
        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);

        newTroops.remove(origin);
        newTroops.put(target, tile);

        if (origin.equals(leaderPosition))
            return new BoardTroops(playingSide, newTroops, target, guards);
        return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
    }

    public BoardTroops troopFlip(BoardPos origin) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        if (at(origin).isEmpty())
            throw new IllegalArgumentException();

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(origin, tile.flipped());

        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    public BoardTroops removeTroop(BoardPos target) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }
        if (at(target).isEmpty())
            throw new IllegalArgumentException();

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);

        newTroops.remove(target);
        if (target.equals(leaderPosition))
            return new BoardTroops(playingSide, newTroops, TilePos.OFF_BOARD, guards);
        return new BoardTroops(playingSide, newTroops, leaderPosition, guards);
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{");
        if (playingSide == PlayingSide.BLUE) {
            writer.print("\"side\":\"BLUE\",");
        } else {
            writer.print("\"side\":\"ORANGE\",");
        }
        writer.print("\"leaderPosition\":");
        leaderPosition.toJSON(writer);
        writer.print(",\"guards\":" + guards() + ",");
        //TroopMap
        writer.print("\"troopMap\":{");
        List<BoardPos> positions = new ArrayList<>(troopPositions());
        Collections.sort(positions);
        if (!positions.isEmpty()) {
            BoardPos last = positions.get(positions.size() - 1);
            for (BoardPos position : positions) {
                position.toJSON(writer);
                writer.print(":");
                if (at(position).isPresent())
                    at(position).get().toJSON(writer);
                if (position != last)
                    writer.print(",");
            }
        }
        writer.print("}}");
    }
}
