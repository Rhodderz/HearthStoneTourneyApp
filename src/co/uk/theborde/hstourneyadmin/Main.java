package co.uk.theborde.hstourneyadmin;

import co.uk.theborde.hstourneyadmin.Controllers.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
/**
API @ https://market.mashape.com/omgvamp/hearthstone#
 */

public class Main extends Application {
    private MainController mc;
    private AnchorPane root;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainView.fxml"));
        root = loader.load();
        mc = loader.getController();
        primaryStage.setTitle("Hearth Stone Tourney");
        primaryStage.setScene(new Scene(root));

        primaryStage.show();

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/Images/hsicon.png")));
        Platform.runLater(() -> mc.OnLoad());
    }


    public static void main(String[] args) {
        launch(args);
    }
}
