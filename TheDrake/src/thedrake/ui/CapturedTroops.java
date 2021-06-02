package thedrake.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import thedrake.PlayingSide;
import thedrake.Troop;
import thedrake.TroopFace;

public class CapturedTroops extends HBox {

    private final PlayingSide playingSide;

    public CapturedTroops(PlayingSide playingSide) {
        this.playingSide = playingSide;
        setAlignment(Pos.CENTER);
        setPadding(new Insets(3, 3, 3, 3));
    }

    public void addTroop(Troop troop) {
        this.getChildren().add(new ImageView(new TroopImageSet(troop.name()).get(playingSide, TroopFace.AVERS)));
    }
}
