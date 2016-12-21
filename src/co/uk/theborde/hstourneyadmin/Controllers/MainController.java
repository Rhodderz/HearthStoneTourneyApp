package co.uk.theborde.hstourneyadmin.Controllers;

import co.uk.theborde.hstourneyadmin.Handlers.DatabaseHandler;
import co.uk.theborde.hstourneyadmin.Objects.Players;

import java.util.ArrayList;

public class MainController {
    /*
    Part 1:
    Connect to DB
    Get List of Players - if null meens no players
    Populate userList with player names.
    */
    private DatabaseHandler dh = new DatabaseHandler();
    private ArrayList<Players>players;

    public void OnLoad(){
        players = dh.getPlayers();
    }

    /*
    Part 2:
    When player is selected populate fields.
    If a field is null go with default.

    Part 3:
    If plus button is pressed create blank player - display popup for name

    Part 4:
    If avatar clicked show popup for new avatar
     - Avatar then uploaded to hstourney.theborde.co.uk/avatars/player_name.png

    Part 5:
    If Card back is clicked open popup and select from list of backs and update player

    Part 6:
    If deck clicked display deck manager to add/remove cards from the deck - Max: 30, Max Same Card: 2
    When saved, if new deck add it to the deck table as an arrayblob (json) - get new deck id and add to player.
     */
}
