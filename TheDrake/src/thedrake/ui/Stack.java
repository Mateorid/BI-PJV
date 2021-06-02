package thedrake.ui;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import thedrake.PlayingSide;
import thedrake.TroopFace;

import java.util.Queue;

public class Stack extends Pane {
    private Queue<ImageView> content;

    private final PlayingSide playingSide;

    public Stack(PlayingSide playingSide) {
        this.playingSide = playingSide;
        setPrefSize(100, 100);
        setOnMouseClicked(event -> onClick());

        content.add(new ImageView(new TroopImageSet("Drake").get(playingSide, TroopFace.AVERS)));
        content.add(new ImageView(new TroopImageSet("Clubman").get(playingSide, TroopFace.AVERS)));
        content.add(new ImageView(new TroopImageSet("Clubman").get(playingSide, TroopFace.AVERS)));
        content.add(new ImageView(new TroopImageSet("Monk").get(playingSide, TroopFace.AVERS)));
        content.add(new ImageView(new TroopImageSet("Spearman").get(playingSide, TroopFace.AVERS)));
        content.add(new ImageView(new TroopImageSet("Swordsman").get(playingSide, TroopFace.AVERS)));
        content.add(new ImageView(new TroopImageSet("Archer").get(playingSide, TroopFace.AVERS)));
    }

    private void onClick() {
        //todo
    }
}
