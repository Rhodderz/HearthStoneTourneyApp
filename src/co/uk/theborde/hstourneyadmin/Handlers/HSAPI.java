package co.uk.theborde.hstourneyadmin.Handlers;

import co.uk.theborde.hstourneyadmin.Objects.Card;
import co.uk.theborde.hstourneyadmin.Objects.CardBack;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
            card = JSONToObj.JSONObjToCard(cardObj);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return card;
    }

    public static ObservableList<Card> getAllCards(){
        //This was a mistake lol
        //Will have this as all cards in one.
        ObservableList<Card> cards = FXCollections.observableArrayList(new ArrayList<Card>());

        try{
            HttpResponse<JsonNode> response = Unirest.get("https://omgvamp-hearthstone-v1.p.mashape.com/cards")
                    .header("X-Mashape-Key", "hzhJddPhgxmshn5MdqLhuLmI5LIRp1A22LFjsnDiq8WaEuvWXa")
                    .asJson();

            for(JSONArray expansion : expansions(response)){
                for(Object jsonObj : expansion){
                    if(((JSONObject)jsonObj).has("img")) {
                        if(!((JSONObject) jsonObj).getString("type").equalsIgnoreCase("Hero Power")
                                && !((JSONObject) jsonObj).getString("type").equalsIgnoreCase("Hero")) {
                            Card card = JSONToObj.JSONObjToCard((JSONObject) jsonObj);
                            cards.add(card);
                        }
                    }
                }
            }
        }catch (UnirestException une){
            une.printStackTrace();
        }

        return cards;
    }

    public static ObservableList<Card> getAllCardsByType(String type){
        ObservableList<Card> cards = FXCollections.observableArrayList(new ArrayList<Card>());

        try{
            HttpResponse<JsonNode> response = Unirest.get("https://omgvamp-hearthstone-v1.p.mashape.com/cards/types/"+type)
                    .header("X-Mashape-Key", "hzhJddPhgxmshn5MdqLhuLmI5LIRp1A22LFjsnDiq8WaEuvWXa")
                    .asJson();

            for(Object object : response.getBody().getArray()){
                Card card = JSONToObj.JSONObjToCard((JSONObject)object);
                cards.add(card);
            }
        }catch (UnirestException une){
            une.printStackTrace();
        }

        return cards;
    }

    public static ArrayList<CardBack> getAllCardBacks(){
        ArrayList<CardBack>cardBacks = new ArrayList<CardBack>();

        try{
            HttpResponse<JsonNode> response = Unirest.get("https://omgvamp-hearthstone-v1.p.mashape.com/cardbacks")
                    .header("X-Mashape-Key", "hzhJddPhgxmshn5MdqLhuLmI5LIRp1A22LFjsnDiq8WaEuvWXa")
                    .asJson();

            JSONArray cbJArray = response.getBody().getArray();

            for(int i = 0; i < cbJArray.length(); i++){
                JSONObject cardBackObj = cbJArray.getJSONObject(i);
                CardBack cardBack = new CardBack();
                cardBack.setID(cardBackObj.getInt("cardBackId"));
                cardBack.setName(cardBackObj.getString("name"));
                cardBack.setDescription(cardBackObj.getString("description"));
                cardBack.setSource(cardBackObj.getString("source"));
                //cardBack.setSourceDescription(cardBackObj.getString("sourceDescription")); //doesnt exist o.O
                cardBack.setEnabled(cardBackObj.getBoolean("enabled"));
                cardBack.setImage(cardBackObj.getString("img"));
                cardBack.setImageAnimated(cardBackObj.getString("imgAnimated"));
                cardBack.setSortCatagory(cardBackObj.getString("sortCategory"));
                cardBack.setSortOrder(cardBackObj.getString("sortOrder"));
                cardBack.setLocale(cardBackObj.getString("locale"));

                cardBacks.add(cardBack);
            }
        }catch (UnirestException ue){
            ue.printStackTrace();
        }

        return cardBacks;
    }
    public static CardBack getSingleCardback(int ID){
        CardBack result = new CardBack();
        ArrayList<CardBack>tmp = HSAPI.getAllCardBacks();
        for(CardBack cb : tmp){
            if(cb.getID() == ID){
                result = cb;
            }
        }
        return result;
    }

    //Helpers
    private static ArrayList<JSONArray> expansions(HttpResponse<JsonNode> response){
        ArrayList<JSONArray> result = new ArrayList<>();

        try{
            result.add(response.getBody().getObject().getJSONArray("Basic"));
            result.add(response.getBody().getObject().getJSONArray("Whispers of the Old Gods"));
            result.add(response.getBody().getObject().getJSONArray("The Grand Tournament"));
            result.add(response.getBody().getObject().getJSONArray("Classic"));
            result.add(response.getBody().getObject().getJSONArray("Blackrock Mountain"));
            result.add(response.getBody().getObject().getJSONArray("Naxxramas"));
            result.add(response.getBody().getObject().getJSONArray("Karazhan"));
            result.add(response.getBody().getObject().getJSONArray("Tavern Brawl"));
            result.add(response.getBody().getObject().getJSONArray("Promo"));
            result.add(response.getBody().getObject().getJSONArray("Reward"));
            result.add(response.getBody().getObject().getJSONArray("The League of Explorers"));
            result.add(response.getBody().getObject().getJSONArray("Mean Streets of Gadgetzan"));
            result.add(response.getBody().getObject().getJSONArray("Goblins vs Gnomes"));
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }

    /*
    FOR single expansions
    JSONArray basicCards = response.getBody().getObject().getJSONArray("Basic");
            JSONArray wotogCards = response.getBody().getObject().getJSONArray("Whispers of the Old Gods");
            JSONArray tgt = response.getBody().getObject().getJSONArray("The Grand Tournament");
            JSONArray classicCards = response.getBody().getObject().getJSONArray("Classic");
            JSONArray bmCards = response.getBody().getObject().getJSONArray("Blackrock Mountain");
            JSONArray naxCards = response.getBody().getObject().getJSONArray("Naxxramas");
            JSONArray karCards = response.getBody().getObject().getJSONArray("Karazhan");
            JSONArray tavernCards = response.getBody().getObject().getJSONArray("Tavern Brawl");
            JSONArray promoCards = response.getBody().getObject().getJSONArray("Promo");
            JSONArray rewardCards = response.getBody().getObject().getJSONArray("Reward");
            JSONArray leoCards = response.getBody().getObject().getJSONArray("The League of Explorers");
            JSONArray msgCards = response.getBody().getObject().getJSONArray("Mean Streets of Gadgetzan");
            JSONArray gvgCards = response.getBody().getObject().getJSONArray("Goblins vs Gnomes");
     */
}
