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
        ObservableList<Card> cards = FXCollections.observableArrayList(new ArrayList<Card>());

        try{
            HttpResponse<JsonNode> response = Unirest.get("https://omgvamp-hearthstone-v1.p.mashape.com/cards")
                    .header("X-Mashape-Key", "hzhJddPhgxmshn5MdqLhuLmI5LIRp1A22LFjsnDiq8WaEuvWXa")
                    .asJson();

            JSONArray cardJArray = response.getBody().getArray();
            for(int i = 0; i < cardJArray.length(); i++){
                cards.add(JSONToObj.JSONObjToCard(cardJArray.getJSONObject(i)));
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
}
