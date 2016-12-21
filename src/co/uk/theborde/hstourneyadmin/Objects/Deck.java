package co.uk.theborde.hstourneyadmin.Objects;

import co.uk.theborde.hstourneyadmin.Handlers.HSAPI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

/**
 * Created by easyr on 21/12/2016.
 * Yes this only holds a deck at present but later will hold some more functions
 */
public class Deck {
    private ArrayList<Card> Cards;

    public Deck(){
        //Empty
    }

    public Deck(String jsonString){
        //Place ints from json into array
        try {
            JSONParser parser = new JSONParser();
            JSONArray jarray = (JSONArray) parser.parse(jsonString);

            for(int i = 0; i < jarray.length(); i++){
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
        String json = "";
        return json;
    }

    public ArrayList<Card> getCards() {
        return Cards;
    }
    public void setCards(ArrayList<Card> card) {
        Cards = card;
    }
}
