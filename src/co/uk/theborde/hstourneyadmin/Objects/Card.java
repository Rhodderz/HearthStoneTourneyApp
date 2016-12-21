package co.uk.theborde.hstourneyadmin.Objects;

/**
 * Created by easyr on 21/12/2016.
 */
public class Card {
    private String CardID;
    private String Name;
    private String CardSet;
    private String Type;
    private String Faction;
    private String Rarity;
    private int Cost;
    private int Attack;
    private int Health;
    private String text;
    private String flavor;
    private String artist;
    private boolean collectible;
    private boolean elite;
    private String race;
    private String image;
    private String imageGold;
    private String locale;

    public String getCardID() {
        return CardID;
    }

    public void setCardID(String cardID) {
        CardID = cardID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCardSet() {
        return CardSet;
    }

    public void setCardSet(String cardSet) {
        CardSet = cardSet;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getFaction() {
        return Faction;
    }

    public void setFaction(String faction) {
        Faction = faction;
    }

    public String getRarity() {
        return Rarity;
    }

    public void setRarity(String rarity) {
        Rarity = rarity;
    }

    public int getCost() {
        return Cost;
    }

    public void setCost(int cost) {
        Cost = cost;
    }

    public int getAttack() {
        return Attack;
    }

    public void setAttack(int attack) {
        Attack = attack;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        Health = health;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageGold() {
        return imageGold;
    }

    public void setImageGold(String imageGold) {
        this.imageGold = imageGold;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
