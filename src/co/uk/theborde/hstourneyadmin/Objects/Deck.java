package co.uk.theborde.hstourneyadmin.Objects;

import co.uk.theborde.hstourneyadmin.Handlers.HSAPI;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

/**
 * Created by easyr on 21/12/2016.
 * Yes this only holds a deck at present but later will hold some more functions
 */
public class Deck {
    private ObservableList<Card> Cards;
    private String name;
    private int ID;

    public Deck(){
        //Empty
    }

    public Deck(String jsonString){
        //Place ints from json into array
        try {
            JSONParser parser = new JSONParser();
            JSONArray jarray = (JSONArray) parser.parse(jsonString);
            //First object will be ID
            //Second Object is the Deck Array
            ID = jarray.getInt(0);
            name = jarray.getString(1);
            JSONArray deckJArray = jarray.getJSONArray(2);

            for(int i = 0; i < deckJArray.length(); i++){
                JSONObject obj = (JSONObject)jarray.get(i);
                Card card = HSAPI.getCard((int)obj.get("id"));
                Cards.add(card);
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public String saveToJSON(){
        //Grab all the cards id and place them into JSON
        //Will Be one Array with 3 objects
        // [id,name,{array}]
        String json = "";
        return json;
    }

    public ObservableList<Card> getDeck() {
        return Cards;
    }
    public void setDeck(ObservableList<Card> card) {
        Cards = card;
    }
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
