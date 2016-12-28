package co.uk.theborde.hstourneyadmin.Handlers;

import co.uk.theborde.hstourneyadmin.Objects.Card;
import org.json.JSONObject;

/**
 * Created by easyr on 28/12/2016.
 */
public class JSONToObj {
    public static Card JSONObjToCard(JSONObject cardObj){
        Card card = new Card();

        card.setCardID(cardObj.getString("cardId"));
        card.setName(cardObj.getString("name"));
        card.setCardSet(cardObj.getString("cardSet"));
        card.setType(cardObj.getString("type"));
        card.setFaction(cardObj.getString("faction"));
        card.setRarity(cardObj.getString("rarity"));
        card.setCost(cardObj.getInt("cost"));
        card.setAttack(cardObj.getInt("attack"));
        card.setHealth(cardObj.getInt("health"));
        card.setText(cardObj.getString("text"));
        card.setFlavor(cardObj.getString("flavor"));
        card.setArtist(cardObj.getString("artist"));
        card.setCollectible(cardObj.getBoolean("collectible"));
        card.setElite(cardObj.getBoolean("elite"));
        card.setRace(cardObj.getString("race"));
        card.setImage(cardObj.getString("img"));
        card.setImageGold(cardObj.getString("imgGold"));
        card.setLocale(cardObj.getString("locale"));

        return card;
    }
}
