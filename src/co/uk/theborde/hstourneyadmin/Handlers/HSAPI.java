package co.uk.theborde.hstourneyadmin.Handlers;

import co.uk.theborde.hstourneyadmin.Objects.Card;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import org.json.JSONObject;

/**
 * Created by easyr on 21/12/2016.
 */
public class HSAPI {

    public static Card getCard(int id){
        Card card = new Card();

        try {
            HttpResponse<JsonNode> response = Unirest.get("https://omgvamp-hearthstone-v1.p.mashape.com/cards/{name}")
                    .header("X-Mashape-Key", "hzhJddPhgxmshn5MdqLhuLmI5LIRp1A22LFjsnDiq8WaEuvWXa")
                    .header("Accept", "application/json")
                    .asJson();
            JSONObject cardObj = response.getBody().getObject();

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
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return card;
    }
}
