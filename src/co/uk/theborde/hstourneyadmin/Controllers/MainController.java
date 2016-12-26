package co.uk.theborde.hstourneyadmin.Controllers;

import co.uk.theborde.hstourneyadmin.Handlers.DatabaseHandler;
import co.uk.theborde.hstourneyadmin.Objects.Deck;
import co.uk.theborde.hstourneyadmin.Objects.Players;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import java.util.Optional;

public class MainController {
    //View Items
    public Button newPlayer_btn;
    public ListView<Players> userList;
    public Label playerID_lbl;
    public Label playerName_lbl;
    public Label playerStats_lbl;
    public ImageView cardBack_img;
    public ListView<Deck> deck1_lstV;

    private DatabaseHandler dh = new DatabaseHandler();
    private ObservableList<Players>players;

    /*
    Part 1:
    Connect to DB
    Get List of Players - if null meens no players
    Populate userList with player names.
    */
    public void OnLoad(){
        players = FXCollections.observableArrayList(dh.getPlayers());
        userList.setItems(players);
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
    }

    private void createNewPlayer(String name){
        Players player = new Players();
        player.setPlayerName(name);
        players.add(player);
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
            }
        });
    }
}
