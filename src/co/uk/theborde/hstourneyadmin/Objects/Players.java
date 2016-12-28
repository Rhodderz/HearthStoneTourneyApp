package co.uk.theborde.hstourneyadmin.Objects;

/**
 * Created by easyr on 21/12/2016.
 */
public class Players {
    private int ID;
    private String PlayerName;
    private String AvatarURL;
    private Deck Deck1;
    private Deck Deck2;
    private Deck Deck3;
    private int TotalWins;
    private int TotalGames;
    private int TotalLosses;
    private CardBack CardBack;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public String getAvatarURL() {
        return AvatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        AvatarURL = avatarURL;
    }

    public Deck getDeck1() {
        return Deck1;
    }

    public void setDeck1(Deck deck1) {
        Deck1 = deck1;
    }

    public Deck getDeck2() {
        return Deck2;
    }

    public void setDeck2(Deck deck2) {
        Deck2 = deck2;
    }

    public Deck getDeck3() {
        return Deck3;
    }

    public void setDeck3(Deck deck3) {
        Deck3 = deck3;
    }

    public int getTotalWins() {
        return TotalWins;
    }

    public void setTotalWins(int totalWins) {
        TotalWins = totalWins;
    }

    public int getTotalGames() {
        return TotalGames;
    }

    public void setTotalGames(int totalGames) {
        TotalGames = totalGames;
    }

    public int getTotalLosses() {
        return TotalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        TotalLosses = totalLosses;
    }

    public CardBack getCardBack() {
        return CardBack;
    }

    public void setCardBack(CardBack cardBack) {
        CardBack = cardBack;
    }
}
