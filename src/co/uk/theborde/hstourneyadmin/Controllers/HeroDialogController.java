package co.uk.theborde.hstourneyadmin.Controllers;

import co.uk.theborde.hstourneyadmin.Handlers.HSAPI;
import co.uk.theborde.hstourneyadmin.Objects.Card;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/**
 * Created by easyr on 30/12/2016.
 */
public class HeroDialogController {
    public ScrollPane rootScrollPane;

    private TilePane tilePane;

    public DeckManagerController deckManagerController;

    private ObservableList<Card> heros;

    @FXML public void initialize(){
        tilePane = new TilePane();
        rootScrollPane.setContent(tilePane);
        Platform.runLater(() -> loadHeros());
    }

    private void loadHeros(){
        heros = HSAPI.getAllCardsByType("Hero");

        for(Card hero : heros){
            try {
                ImageView imageView = new ImageView("/Images/Cards/" + hero.getCardID() + ".png");
                imageView.setId(hero.getCardID());
                imageView.setFitWidth(150);
                imageView.setFitHeight(200);
                imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ImageView iv = (ImageView) event.getSource();
                        String id = iv.getId();
                        deckManagerController.setHero(getHero(id));
                        ((Stage) ((ImageView) event.getSource()).getParent().getScene().getWindow()).close();
                        event.consume();
                    }

                });

                tilePane.getChildren().add(imageView);
            }catch (IllegalArgumentException iae){
                System.err.println("Hero Card Missing: " + hero.getName() +
                                    "\nCard ID: " + hero.getCardID());
            }
        }
    }

    private Card getHero(String id){
        for(Card card : heros)
            if(card.getCardID().equalsIgnoreCase(id))
                return card;
        return null;
    }
}