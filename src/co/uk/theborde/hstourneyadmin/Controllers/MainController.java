package co.uk.theborde.hstourneyadmin.Controllers;

import co.uk.theborde.hstourneyadmin.Handlers.DatabaseHandler;
import co.uk.theborde.hstourneyadmin.Objects.Card;
import co.uk.theborde.hstourneyadmin.Objects.Deck;
import co.uk.theborde.hstourneyadmin.Objects.Players;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import java.util.Optional;

public class MainController {
    //View Items
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
    private ObservableList<Players>players;

    /*
    Part 1:
    Connect to DB
    Get List of Players - if null means no players
    Populate userList with player names.
    */
    public void OnLoad(){
        players = FXCollections.observableArrayList(dh.getPlayers());
        userList.setItems(players);
    }

    /*
    Part 2:
    When player is selected populate fields.
    If a field is null go with default.
    */

    /*
    Part 3:
    If plus button is pressed create blank player - display popup for name
    */
    public void onNewPlayer(){
        TextInputDialog dialog = new TextInputDialog("New Player");
        dialog.setTitle("New Player");
        dialog.setHeaderText("Insert the new players name.");
        dialog.setContentText("Player: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> createNewPlayer(result.get()));
        addListeners();
        tweakLists();
    }

    private void createNewPlayer(String name){
        Players player = new Players();
        player.setPlayerName(name);
        players.add(player);
    }

    private void tweakLists(){
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

    Part 6:
    If deck clicked display deck manager to add/remove cards from the deck - Max: 30, Max Same Card: 2
    When saved, if new deck add it to the deck table as an arrayblob (json) - get new deck id and add to player.
     */

    //Just some helpers
    private void addListeners(){
        userList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                Players player = players.get(t1.intValue());
                playerID_lbl.setText((""+player.getID()));
                playerName_lbl.setText(player.getPlayerName());
                playerStats_lbl.setText(
                        player.getTotalWins() + "/" +
                        player.getTotalGames() + "/" +
                        player.getTotalLosses()
                );
                //Check if card back is null, if null dont change (use default)
                if(player.getCardBack() != null && !player.getCardBack().isEmpty())
                    cardBack_img.imageProperty().set(new Image(player.getCardBack())); //Should Work
                //Do the same for avatar
                if(player.getAvatarURL() != null && !player.getAvatarURL().isEmpty())
                    avatar_img.imageProperty().set(new Image(player.getAvatarURL()));
                if(player.getDeck1() != null) {
                    deck1_lstV.setItems(player.getDeck1().getDeck());
                    deck1ID_lbl.setText(""+player.getDeck1().getID());
                    deck1Name_lbl.setText(player.getDeck1().getName());
                }
                if(player.getDeck2() != null) {
                    deck2_lstV.setItems(player.getDeck2().getDeck());
                    deck2ID_lbl.setText(""+player.getDeck2().getID());
                    deck2Name_lbl.setText(player.getDeck2().getName());
                }
                if(player.getDeck3() != null) {
                    deck3_lstV.setItems(player.getDeck3().getDeck());
                    deck3ID_lbl.setText(""+player.getDeck3().getID());
                    deck3Name_lbl.setText(player.getDeck3().getName());
                }
            }
        });
    }
}
