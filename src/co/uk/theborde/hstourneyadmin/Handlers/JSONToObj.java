package co.uk.theborde.hstourneyadmin.Handlers;

import co.uk.theborde.hstourneyadmin.Objects.Card;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by easyr on 28/12/2016.
 */
public class JSONToObj {
    public static Card JSONObjToCard(JSONObject cardObj) {
        Card card = new Card();

        card.setRawJSON(cardObj);
        card.setCardID(cardObj.getString("cardId"));
        card.setName(cardObj.getString("name"));
        card.setCardSet(cardObj.getString("cardSet"));
        card.setType(cardObj.getString("type"));
        if (cardObj.has("playerClass"))
            card.setPlayerClass(cardObj.getString("playerClass"));
        if (cardObj.has("faction"))
            card.setFaction(cardObj.getString("faction"));
        if (cardObj.has("rarity"))
            card.setRarity(cardObj.getString("rarity"));
        if (cardObj.has("cost"))
            card.setCost(cardObj.getInt("cost"));
        if (cardObj.has("attack"))
            card.setAttack(cardObj.getInt("attack"));
        if (cardObj.has("health"))
            card.setHealth(cardObj.getInt("health"));
        if (cardObj.has("text"))
            card.setText(cardObj.getString("text"));
        if (cardObj.has("flavor"))
            card.setFlavor(cardObj.getString("flavor"));
        if (cardObj.has("artist"))
            card.setArtist(cardObj.getString("artist"));
        if (cardObj.has("collectible"))
            card.setCollectible(cardObj.getBoolean("collectible"));
        if (cardObj.has("elite"))
            card.setElite(cardObj.getBoolean("elite"));
        if (cardObj.has("race"))
            card.setRace(cardObj.getString("race"));
        if (cardObj.has("img"))
            card.setImage(cardObj.getString("img"));
        if (cardObj.has("imgGold"))
            card.setImageGold(cardObj.getString("imgGold"));
        card.setLocale(cardObj.getString("locale"));

        //TODO: Need to get the mechanics array and dop that as well

        return card;
    }
}
