package thedrake.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TheDrakeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("theDrake.fxml"));
        Parent root = loader.load();
        MenuController menuController = loader.getController();
        menuController.setStage(stage);

        Scene menuScene = new Scene(root, 800, 500);
        menuScene.getStylesheets().add(getClass().getResource("theDrake.css").toExternalForm());

        stage.setTitle("The Drake");
        stage.setScene(menuScene);
        stage.setOnCloseRequest(eh -> menuController.exitAction(null));
        stage.show();
    }
}
