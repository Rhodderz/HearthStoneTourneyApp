package co.uk.theborde.hstourneyadmin.Controllers;

import co.uk.theborde.hstourneyadmin.Handlers.DatabaseHandler;
import co.uk.theborde.hstourneyadmin.Handlers.HSAPI;
import co.uk.theborde.hstourneyadmin.Main;
import co.uk.theborde.hstourneyadmin.Objects.Card;
import co.uk.theborde.hstourneyadmin.Objects.Deck;
import co.uk.theborde.hstourneyadmin.Objects.Players;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Optional;

public class MainController {
    //View Items
    public AnchorPane rootAnchorPane;
    public Button newPlayer_btn;
    public Button save_btn;
    public ListView<Players> userList;
    public Label playerID_lbl;
    public Label playerName_lbl;
    public Label playerStats_lbl;
    public ImageView cardBack_img;
    public ImageView avatar_img;
    public ListView<Card> deck1_lstV;
    public ListView<Card> deck2_lstV;
    public ListView<Card> deck3_lstV;
    public Label deck1ID_lbl;
    public Label deck2ID_lbl;
    public Label deck3ID_lbl;
    public Label deck1Name_lbl;
    public Label deck2Name_lbl;
    public Label deck3Name_lbl;

    private DatabaseHandler dh = new DatabaseHandler();
    private ObservableList<Players> players;

    public Players currentPlayer;

    public void OnLoad() {
        players = FXCollections.observableArrayList(dh.getPlayers());
        userList.setItems(players);
        tweakLists();
    }

    public void onNewPlayer() {
        TextInputDialog dialog = new TextInputDialog("New Player");
        dialog.setTitle("New Player");
        dialog.setHeaderText("Insert the new players name.");
        dialog.setContentText("Player: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> createNewPlayer(result.get()));
        addListeners();
    }

    private void createNewPlayer(String name) {
        Players player = new Players();
        player.setPlayerName(name);
        players.add(player);
    }

    private void tweakLists() {
        userList.setCellFactory(new Callback<ListView<Players>, ListCell<Players>>() {
            public ListCell<Players> call(ListView<Players> param) {
                final ListCell<Players> cell = new ListCell<Players>() {
                    @Override
                    public void updateItem(Players item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getPlayerName());
                        }
                    }
                };
                return cell;
            }
        });

        Callback<ListView<Card>, ListCell<Card>> deckCallback = new Callback<ListView<Card>, ListCell<Card>>() {
            public ListCell<Card> call(ListView<Card> param) {
                final ListCell<Card> cell = new ListCell<Card>() {
                    @Override
                    public void updateItem(Card item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText("Name: " + item.getName() +
                                    " Cost: " + item.getCost());
                        }
                    }
                };
                return cell;
            }
        };

        deck1_lstV.setCellFactory(deckCallback);

        deck2_lstV.setCellFactory(deckCallback);

        deck3_lstV.setCellFactory(deckCallback);
    }

    /*
    Part 4:
    If avatar clicked show popup for new avatar
     - Avatar then uploaded to hstourney.theborde.co.uk/avatars/player_name.png

    Part 5:
    If Card back is clicked open popup and select from list of backs and update player
    */

    public void deckBackClicked() {
        //Create Custom Dialog
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/Views/DeckBackDialog.fxml"));
            ScrollPane rootScrollPane = (ScrollPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Select Deck Backing");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootAnchorPane.getScene().getWindow());
            Scene scene = new Scene(rootScrollPane);
            dialogStage.setScene(scene);

            DeckBackController deckBackController = loader.getController();
            deckBackController.setDialogStage(dialogStage);
            deckBackController.mainController = this;

            dialogStage.showAndWait();

            //Set current card back to one selected
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
    Part 6:
    If deck clicked display deck manager to add/remove cards from the deck - Max: 30, Max Same Card: 2
    When saved, if new deck add it to the deck table as an arrayblob (json) - get new deck id and add to player.
     */

    public void deckListClicked(MouseEvent event) {
        String clickedId = ((ListView) event.getSource()).getId();
        switch (clickedId) {
            case "deck1_lstV":
                if (currentPlayer != null)
                    openDeckManager(currentPlayer.getDeck1(), 1);
                break;
            case "deck2_lstV":
                if (currentPlayer != null)
                    openDeckManager(currentPlayer.getDeck2(), 2);
                break;
            case "deck3_lstV":
                if (currentPlayer != null)
                    openDeckManager(currentPlayer.getDeck3(), 3);
                break;
        }
    }

    private void openDeckManager(Deck deck, int num) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/Views/DeckManager.fxml"));
            SplitPane rootSplitPane = (SplitPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Player Deck Manager");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootAnchorPane.getScene().getWindow());
            Scene scene = new Scene(rootSplitPane);
            dialogStage.setScene(scene);

            DeckManagerController deckManagerController = loader.getController();
            deckManagerController.mainController = this;
            deckManagerController.setDeck(deck);
            deckManagerController.num = num;
            dialogStage.addEventFilter(WindowEvent.WINDOW_SHOWN, event -> deckManagerController.deckListView.setItems(deck.getDeck()));

            dialogStage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Just some helpers
    private void addListeners() {
        userList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                currentPlayer = players.get(t1.intValue());
                playerID_lbl.setText(("" + currentPlayer.getID()));
                playerName_lbl.setText(currentPlayer.getPlayerName());
                playerStats_lbl.setText(
                        currentPlayer.getTotalWins() + "/" +
                                currentPlayer.getTotalGames() + "/" +
                                currentPlayer.getTotalLosses()
                );
                //Check if card back is null, if null dont change (use default)
                if (currentPlayer.getCardBack() != null && !currentPlayer.getCardBack().getImage().isEmpty()) {
                    updateCardBack(); //Should Work
                } else {
                    cardBack_img.setImage(new Image("/Images/DeckBacks/Classic.png"));
                }
                //Do the same for avatar
                if (currentPlayer.getAvatarURL() != null && !currentPlayer.getAvatarURL().isEmpty())
                    avatar_img.imageProperty().set(new Image(currentPlayer.getAvatarURL()));

                if (currentPlayer.getDeck1() == null)
                    currentPlayer.setDeck1(new Deck());
                if (currentPlayer.getDeck2() == null)
                    currentPlayer.setDeck2(new Deck());
                if (currentPlayer.getDeck3() == null)
                    currentPlayer.setDeck3(new Deck());
                deck1_lstV.setItems(currentPlayer.getDeck1().getDeck());
                deck1ID_lbl.setText("" + currentPlayer.getDeck1().getID());
                deck1Name_lbl.setText(currentPlayer.getDeck1().getName());
                deck2_lstV.setItems(currentPlayer.getDeck2().getDeck());
                deck2ID_lbl.setText("" + currentPlayer.getDeck2().getID());
                deck2Name_lbl.setText(currentPlayer.getDeck2().getName());
                deck3_lstV.setItems(currentPlayer.getDeck3().getDeck());
                deck3ID_lbl.setText("" + currentPlayer.getDeck3().getID());
                deck3Name_lbl.setText(currentPlayer.getDeck3().getName());
            }
        });
    }

    public void updateCardBack() {
        cardBack_img.setImage(new Image("/Images/DeckBacks/" + currentPlayer.getCardBack().getName() + ".png"));
    }
}
