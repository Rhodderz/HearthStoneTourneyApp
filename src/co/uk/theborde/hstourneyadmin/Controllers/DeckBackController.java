package co.uk.theborde.hstourneyadmin.Controllers;

import co.uk.theborde.hstourneyadmin.Handlers.HSAPI;
import co.uk.theborde.hstourneyadmin.Objects.CardBack;
import co.uk.theborde.hstourneyadmin.Objects.Deck;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Created by easyr on 27/12/2016.
 */
public class DeckBackController {
    @FXML public TilePane rootTilePane;
    @FXML public ScrollPane rootScrollPane;

    public ObservableList<CardBack> cardBacks;
    public MainController mainController;

    private Stage dialogStage;


    @FXML
    private void initialize() {
        //Display loading icon
        //Once for loop finished then load tile panes children.
        rootTilePane = new TilePane();
        rootScrollPane.setContent(rootTilePane);

        synchronized (loadBacks()){
            //wait
        }
    }

    private Object loadBacks(){
        //Get array of card back urls,
        //Create ObservableList of Images with the url loaded
        //Load images on the tile map.
        try {
            cardBacks = FXCollections.observableArrayList(HSAPI.getAllCardBacks());
            int index = 0;
            for (CardBack cardBack : cardBacks) {
                ImageView imageView = new ImageView(cardBack.getImage());
                imageView.setFitHeight(200);
                imageView.setFitWidth(150);
                imageView.setId(""+index);
                imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ImageView iv =(ImageView)event.getSource();
                        String id = iv.getId();
                        ObservableList<CardBack> cbs = getDeckBackController().cardBacks;
                        getDeckBackController().mainController.currentPlayer.setCardBack(cbs.get(Integer.parseInt(id)));
                        getDeckBackController().mainController.updateCardBack();
                        ((Stage)((ImageView) event.getSource()).getParent().getScene().getWindow()).close();
                        event.consume();
                    }
                });
                rootTilePane.getChildren().add(imageView);
                index++;
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public DeckBackController getDeckBackController(){
        return this;
    }
}
