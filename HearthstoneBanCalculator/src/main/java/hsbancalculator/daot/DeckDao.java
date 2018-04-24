/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.daot;

import hsbancalculator.sovelluslogiikka.Deck;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author antlammi
 */
public class DeckDao implements Dao<Deck, Integer> {

    private Database database;

    public DeckDao(Database database) {
        this.database = database;
    }

    @Override
    public Deck findOne(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("Select * From Deck WHERE id = ?");
        stmt.setInt(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Deck d = new Deck(rs.getInt("id"), rs.getString("name"));

        stmt.close();
        rs.close();

        conn.close();

        return d;
    }

    @Override
     public List<Deck> findAll() throws SQLException {
        List<Deck> decks = new ArrayList<>();

        try (Connection conn = database.getConnection();
            ResultSet result = conn.prepareStatement("SELECT id, name FROM Deck").executeQuery()) {

            while (result.next()) {
                decks.add(new Deck(result.getInt("id"), result.getString("name")));
            }
        }

        return decks;
    }

    
    @Override
    public Deck saveOrUpdate(Deck paramDeck) throws SQLException {
        Deck d = findByName(paramDeck.name);

        if (d != null) {
            return null; //tietojen päivitys tähän
        } 
        
        try (Connection conn = database.getConnection()){
            PreparedStatement stmt = conn.prepareStatement("Insert into Deck (name) Values(?)");
            stmt.setString(1, paramDeck.name);
            stmt.executeUpdate();
        }
        
        return findByName(paramDeck.name);
    }
    public Integer findIDByName(String name) throws SQLException{
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Deck WHERE name = ?");
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
    public Deck findByName(String name) throws SQLException {
        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT id, name FROM Deck WHERE name = ?");
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }

                Deck d = new Deck(rs.getInt("id"), rs.getString("name"));

                stmt.close();
                rs.close();

                conn.close();
                return d;
            }
        }

    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE From Deck WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }

}
