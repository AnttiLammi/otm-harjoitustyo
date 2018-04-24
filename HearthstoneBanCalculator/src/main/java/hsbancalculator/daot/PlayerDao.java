/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.daot;

import hsbancalculator.sovelluslogiikka.Deck;
import hsbancalculator.sovelluslogiikka.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author antlammi
 */
public class PlayerDao implements Dao<Player, Integer> {

    private Database database;

    public PlayerDao(Database database) {
        this.database = database;
    }

    @Override
    public Player findOne(Integer key) throws SQLException, ClassNotFoundException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("Select * From Player WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String name = rs.getString("name");
        Integer deck1id = rs.getInt("deck1_id");
        Integer deck2id = rs.getInt("deck2_id");
        Integer deck3id = rs.getInt("deck3_id");
        Integer deck4id = rs.getInt("deck4_id");

        stmt.close();
        rs.close();

        conn.close();
        DeckDao dDao = new DeckDao(database);
        Deck d1 = dDao.findOne(deck1id);
        Deck d2 = dDao.findOne(deck2id);
        Deck d3 = dDao.findOne(deck3id);
        Deck d4 = dDao.findOne(deck4id);

        Player p = new Player(d1, d2, d3, d4);
        p.setID(id);
        p.setName(name);

        return p;
    }

    @Override
    public List<Player> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player saveOrUpdate(Player player) throws SQLException, ClassNotFoundException {
        DeckDao dDao = new DeckDao(database);
        ArrayList<Deck> line = player.lineup;
        
        ArrayList lineIDList = new ArrayList<>();
        for (int i=0; i<line.size(); i++){
            dDao.saveOrUpdate(line.get(i));
            Integer id = dDao.findIDByName(line.get(i).name);
            lineIDList.add(id);
        }
        
         try (Connection conn = database.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("Insert into Player (name, deck1_id, deck2_id, deck3_id, deck4_id) Values(?, ?, ?, ?, ?)");
            stmt.setString(1, player.name);
            stmt.setInt(2, (int)lineIDList.get(0));
            stmt.setInt(3, (int)lineIDList.get(1));
            stmt.setInt(4, (int)lineIDList.get(2));
            stmt.setInt(5, (int)lineIDList.get(3));
            
            stmt.executeUpdate();
        }
         
         return findOne(findByName(player.name));
     
    }
   public Integer findByName(String name) throws SQLException, ClassNotFoundException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Player WHERE name = ?");
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

               Integer id = rs.getInt("id");

                stmt.close();
                rs.close();

                conn.close();
                return id;
            }
        }

    }
    @Override
    public void delete(Integer key) throws SQLException, ClassNotFoundException {
        Connection conn = database.getConnection();
        
        PreparedStatement stmt = conn.prepareStatement("DELETE From Player where id = ?");
        stmt.setInt(0, key);
        stmt.executeUpdate();
    }

}
 