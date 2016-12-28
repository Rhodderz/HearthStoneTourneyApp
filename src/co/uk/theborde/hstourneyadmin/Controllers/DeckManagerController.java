package co.uk.theborde.hstourneyadmin.Controllers;

import co.uk.theborde.hstourneyadmin.Objects.Card;
import co.uk.theborde.hstourneyadmin.Objects.Deck;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;

/**
 * Created by easyr on 28/12/2016.
 */
public class DeckManagerController {
    public SplitPane rootSplitPane;
    public Button done_btn;
    public ListView deckListView;
    public TabPane cardTabsPane;

    private ObservableList<Card>CardList;
    private Deck deck;

    @FXML
    public void initialize(){
        //Get all the cards
        //Organise them between Neitral and Classes
        //Create Tabs for classes
        //Add Tile Pane to said tabs
        //Populate Tile Panes
        //Set ImagesViews ID to CardID
        //Populate List view with Deck if one already exists
        //Set up custom items for ListView (Most likley overide)
        //Setup Click Event Handler
        //Set up Drag and drop
    }
}
