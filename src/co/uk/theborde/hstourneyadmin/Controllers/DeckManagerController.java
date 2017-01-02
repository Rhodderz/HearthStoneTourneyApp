package co.uk.theborde.hstourneyadmin.Controllers;

import co.uk.theborde.hstourneyadmin.Handlers.HSAPI;
import co.uk.theborde.hstourneyadmin.Main;
import co.uk.theborde.hstourneyadmin.Objects.Card;
import co.uk.theborde.hstourneyadmin.Objects.Deck;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.TilePane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;

/**
 * Created by easyr on 28/12/2016.
 */
public class DeckManagerController {
    public SplitPane rootSplitPane;
    public Button done_btn;
    public ListView deckListView;
    public TabPane cardTabsPane;
    public TextField deckName_txt;
    public ImageView hero_img;

    private Pagination NeutralPages;
    private TilePane DruidTilePane;
    private TilePane HunterTilePane;
    private TilePane MageTilePane;
    private TilePane PaladinTilePane;
    private TilePane PriestTilePane;
    private TilePane RogueTilePane;
    private TilePane ShamanTilePane;
    private TilePane WarlockTilePane;
    private TilePane WarriorTilePane;
    private TilePane DreamTilePane;

    public MainController mainController;
    public int num; //Deck number chosen

    private int neutralAmount = 0;
    private ObservableList<Card> CardList;
    private ArrayList<TilePane> neutralPagesList;
    private Deck deck;

    @FXML
    public void initialize() {
        CardList = HSAPI.getAllCards();
        setupTabs();
        setupImageViews();
        setupListCellFactory();
        setupDragAndDrop();
    }

    public void onDoneClick() {
        System.out.println("DEBUG");
        ((Stage)rootSplitPane.getScene().getWindow()).close();
    }

    public void onHeroClick() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/Views/HeroDialog.fxml"));
            ScrollPane rootScrollPane = (ScrollPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Select Deck Hero");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(rootSplitPane.getScene().getWindow());
            Scene scene = new Scene(rootScrollPane);
            dialogStage.setScene(scene);

            HeroDialogController heroDialogController = loader.getController();
            heroDialogController.deckManagerController = this;

            dialogStage.showAndWait();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupDragAndDrop() {
        //SOURCE done at Images

        //Setup Target
        deckListView.setOnDragOver(event -> {
            if (event.getGestureSource() != deckListView)// && event.getDragboard().hasString())
                event.acceptTransferModes(TransferMode.COPY);
            event.consume();
        });

        deckListView.setOnDragEntered(event -> {
            if (event.getGestureSource() != deckListView)// && event.getDragboard().hasString())
                if (deck.getDeck().size() < 30)
                    if (checkCardAmount(whereCard(event.getDragboard().getString())))
                        deckListView.setBlendMode(BlendMode.GREEN);
            if (deck.getDeck().size() > 30)
                deckListView.setBlendMode(BlendMode.RED);
            if (!checkCardAmount(whereCard(event.getDragboard().getString())))
                deckListView.setBlendMode(BlendMode.RED);

            event.consume();
        });

        deckListView.setOnDragExited(event -> {
            deckListView.setBlendMode(null);
            event.consume();
        });

        deckListView.setOnDragDropped(event -> {
            if (event.getTransferMode() == TransferMode.COPY)
                if (!(deck.getDeck().size() > 29))
                    if (checkCardAmount(whereCard(event.getDragboard().getString())))
                        deck.getDeck().add(whereCard(event.getDragboard().getString()));
            event.consume();
        });
    }

    private boolean checkCardAmount(Card card) {
        int amount = 0;
        for (Card deckCard : deck.getDeck())
            if (deckCard.getCardID().equalsIgnoreCase(card.getCardID()))
                amount++;
        if (amount <= 1) //1 less or it will add a third
            return true;
        else
            return false;
    }

    private void setupListCellFactory() {
        deckListView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
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
        });

    }

    private void setupTabs() {
        Tab neutralTab = new Tab();
        neutralTab.setText("Neutral");
        createNeutralPages();
        neutralTab.setContent(NeutralPages);

        Tab druidTab = new Tab();
        druidTab.setText("Druid");
        DruidTilePane = new TilePane();
        ScrollPane druidScrollPane = new ScrollPane();
        druidScrollPane.setContent(DruidTilePane);
        druidTab.setContent(druidScrollPane);

        Tab hunterTab = new Tab();
        hunterTab.setText("Hunter");
        HunterTilePane = new TilePane();
        ScrollPane hunterScrollPane = new ScrollPane();
        hunterScrollPane.setContent(HunterTilePane);
        hunterTab.setContent(hunterScrollPane);

        Tab mageTab = new Tab();
        mageTab.setText("Mage");
        MageTilePane = new TilePane();
        ScrollPane mageScrollPane = new ScrollPane();
        mageScrollPane.setContent(MageTilePane);
        mageTab.setContent(mageScrollPane);

        Tab paladinTab = new Tab();
        paladinTab.setText("Paladin");
        PaladinTilePane = new TilePane();
        ScrollPane paladinScrollPane = new ScrollPane();
        paladinScrollPane.setContent(PaladinTilePane);
        paladinTab.setContent(paladinScrollPane);

        Tab priestTab = new Tab();
        priestTab.setText("Priest");
        PriestTilePane = new TilePane();
        ScrollPane priestScrollPane = new ScrollPane();
        priestScrollPane.setContent(PriestTilePane);
        priestTab.setContent(priestScrollPane);

        Tab rougeTab = new Tab();
        rougeTab.setText("Rouge");
        RogueTilePane = new TilePane();
        ScrollPane rougeScrollPane = new ScrollPane();
        rougeScrollPane.setContent(RogueTilePane);
        rougeTab.setContent(rougeScrollPane);

        Tab shamanTab = new Tab();
        shamanTab.setText("Shaman");
        ShamanTilePane = new TilePane();
        ScrollPane shamanScrollPane = new ScrollPane();
        shamanScrollPane.setContent(ShamanTilePane);
        shamanTab.setContent(shamanScrollPane);

        Tab warlockTab = new Tab();
        warlockTab.setText("Warlock");
        WarlockTilePane = new TilePane();
        ScrollPane warlockScrollPane = new ScrollPane();
        warlockScrollPane.setContent(WarlockTilePane);
        warlockTab.setContent(warlockScrollPane);

        Tab warriorTab = new Tab();
        warriorTab.setText("Warrior");
        WarriorTilePane = new TilePane();
        ScrollPane warriorScrollPane = new ScrollPane();
        warriorScrollPane.setContent(WarriorTilePane);
        warriorTab.setContent(warriorScrollPane);

        Tab dreamTab = new Tab();
        dreamTab.setText("Dream");
        DreamTilePane = new TilePane();
        ScrollPane dreamScrollPane = new ScrollPane();
        dreamScrollPane.setContent(DreamTilePane);
        dreamTab.setContent(dreamScrollPane);

        cardTabsPane.getTabs().add(neutralTab);
        cardTabsPane.getTabs().add(druidTab);
        cardTabsPane.getTabs().add(hunterTab);
        cardTabsPane.getTabs().add(mageTab);
        cardTabsPane.getTabs().add(paladinTab);
        cardTabsPane.getTabs().add(priestTab);
        cardTabsPane.getTabs().add(rougeTab);
        cardTabsPane.getTabs().add(shamanTab);
        cardTabsPane.getTabs().add(warlockTab);
        cardTabsPane.getTabs().add(warriorTab);
        cardTabsPane.getTabs().add(dreamTab);
    }

    private void createNeutralPages() {
        getNumberOfNeutral();
        createTilePanesForPages();
        NeutralPages = new Pagination(neutralAmount / 100, 0);
        NeutralPages.setPageFactory((Integer pageIndex) -> newScrollPanePage(pageIndex));
    }

    private void createTilePanesForPages() {
        neutralPagesList = new ArrayList<TilePane>();
        for (int i = 0; i < neutralAmount / 100; i++) {
            TilePane tilePane = new TilePane();
            tilePane.setId(String.valueOf(i));
            neutralPagesList.add(tilePane);
        }
    }

    private void setupImageViews() {
        int count = 0;
        int pageIndex = 0;

        for (Card card : CardList) {
            try {
                ImageView imageView = new ImageView();
                imageView.setFitHeight(200);
                imageView.setFitWidth(150);
                imageView.setId(card.getCardID());
                imageView.setOnDragDetected(event -> {
                    System.out.println("Draging: " + card.getName());
                    Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(card.getCardID());
                    dragboard.setContent(content);
                    event.consume();
                });

                if (card.getImage() != null) {
                    imageView.setImage(new Image("/Images/Cards/" + card.getCardID() + ".png"));
                }

                imageView.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println(card.getName() + "\n" + card.getRawJSON());
                        event.consume();
                    }

                });

                if (card.getPlayerClass() != null) {
                    switch (card.getPlayerClass()) {
                        case "Neutral":
                            if (count > 100) {
                                count = 0;
                                pageIndex++;
                            }
                            neutralPagesList.get(pageIndex).getChildren().add(imageView);
                            count++;
                            break;
                        case "Druid":
                            DruidTilePane.getChildren().add(imageView);
                            break;
                        case "Hunter":
                            HunterTilePane.getChildren().add(imageView);
                            break;
                        case "Mage":
                            MageTilePane.getChildren().add(imageView);
                            break;
                        case "Paladin":
                            PaladinTilePane.getChildren().add(imageView);
                            break;
                        case "Priest":
                            PriestTilePane.getChildren().add(imageView);
                            break;
                        case "Rogue":
                            RogueTilePane.getChildren().add(imageView);
                            break;
                        case "Shaman":
                            ShamanTilePane.getChildren().add(imageView);
                            break;
                        case "Warlock":
                            WarlockTilePane.getChildren().add(imageView);
                            break;
                        case "Warrior":
                            WarriorTilePane.getChildren().add(imageView);
                            break;
                        case "Dream":
                            DreamTilePane.getChildren().add(imageView);
                            break;
                    }
                } else {
                    System.out.println("Card with no class: " + card.getName());
                }
            } catch (IllegalArgumentException iae) {
                //
            }
        }
    }

    private void getNumberOfNeutral() {
        CardList.stream().parallel().forEach((Card c) -> {
            if (c.getRawJSON().has("playerClass"))
                if (c.getPlayerClass().matches("Neutral"))
                    neutralAmount++;
        });
    }

    private ScrollPane newScrollPanePage(int pageIndex) {
        ScrollPane neutralScrollPane = new ScrollPane();
        neutralScrollPane.setContent(neutralPagesList.get(pageIndex));
        return neutralScrollPane;
    }

    private Card whereCard(String id) {
        for (Card card : CardList)
            if (card.getCardID().equalsIgnoreCase(id))
                return card;
        return null;

    }

    private void setUpNameListener(){
        ChangeListener<String>nameListener ;
        deckName_txt.textProperty().addListener((observable, oldValue, newValue) -> deck.setName(newValue));
    }

    public void setHero(Card hero) {
        deck.setHero(hero);
        hero_img.setImage(new Image("/Images/Cards/" + hero.getCardID() + ".png"));
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
