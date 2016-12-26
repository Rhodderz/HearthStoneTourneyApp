package co.uk.theborde.hstourneyadmin.Handlers;

import co.uk.theborde.hstourneyadmin.Objects.Deck;
import co.uk.theborde.hstourneyadmin.Objects.Players;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by easyr on 21/12/2016.
 */
public class DatabaseHandler {
    private String server = "jdbc:mysql://82.145.42.9:44806/HSTourney?useSSL=false";
    private String username = "hstourney";
    private String password = "6!~WvY,\\T!cEq_R)";

    public ArrayList<Players> getPlayers(){
        ArrayList<Players> players = new ArrayList<Players>();
        try {
            Connection con = getConnection();
            //Go through iteration of players
            //Create new Player (and Appropriate decks etc).
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM HSTourney.Players;");

            while(rs.next()){
                //Create Player
                Players player = new Players();
                player.setID(rs.getInt(1));
                player.setPlayerName(rs.getString(2));
                player.setAvatarURL(rs.getString(3));
                player.setTotalWins(rs.getInt(7));
                player.setTotalGames(rs.getInt(8));
                player.setTotalLosses(rs.getInt(9));
                player.setCardBack(rs.getString(10));
                //Get/Create Decks - Gets JSON - Convert JSON into ID's(int)
                player.setDeck1(new Deck(rs.getString(4)));
                player.setDeck2(new Deck(rs.getString(5)));
                player.setDeck3(new Deck(rs.getString(6)));
                //Add Player to list
                players.add(player);
            }

        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        return players;
    }

    private Connection getConnection() throws SQLException{
        Connection con = DriverManager.getConnection(server,username,password);

        return con;
    }
}
