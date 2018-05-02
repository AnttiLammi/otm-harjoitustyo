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
 * Deck-taulun käsittelyoperaatiot.
 *
 * @author antlammi
 */
public class DeckDao implements Dao<Deck, Integer> {

    private Database database;

    public DeckDao(Database database) {
        this.database = database;
    }

    /**
     * Hakee tunnetun id:n avulla pakan taulusta.
     *
     * @param key
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public Deck findOne(Integer key) throws SQLException, ClassNotFoundException {
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

    /**
     * Hakee kaikki taulusta löytyvät pakat ja palauttaa ne listana.
     *
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public List<Deck> findAll() throws SQLException, ClassNotFoundException {
        List<Deck> decks = new ArrayList<>();

        try (Connection conn = database.getConnection();
                ResultSet result = conn.prepareStatement("SELECT id, name FROM Deck").executeQuery()) {

            while (result.next()) {
                decks.add(new Deck(result.getInt("id"), result.getString("name")));
            }
        }

        return decks;
    }

    /**
     * Lisää uuden pakan tauluun tai palauttaa null, mikäli se löytyy jo.
     *
     * @param paramDeck
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public Deck saveOrUpdate(Deck paramDeck) throws SQLException, ClassNotFoundException {
        Deck d = findByName(paramDeck.name);

        if (d != null) {
            return null; //tietojen päivitys tähän
        }

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("Insert into Deck (name) Values(?)");
            stmt.setString(1, paramDeck.name);
            stmt.executeUpdate();
        }

        return findByName(paramDeck.name);
    }

    /**
     * Hakee tunnetun nimen perusteella pakan id:n.
     *
     * @param name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Integer findIDByName(String name) throws SQLException, ClassNotFoundException {
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

    /**
     * Hakee tunnetun nimen avulla tietyn pakan taulusta.
     *
     * @param name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Deck findByName(String name) throws SQLException, ClassNotFoundException {
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

    /**
     * Poisto-operaatio pakan id:tä hyödyntäen, samalla poistaa tähän
     * sidonnaiset arvot Player ja Matchups tauluista.
     *
     * @param key
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    @Override
    public void delete(Integer key) throws SQLException, ClassNotFoundException {

        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE From Deck WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();

        stmt = conn.prepareStatement("Delete FROM Player WHERE deck1_id = ? OR deck2_id = ? OR deck3_id = ? OR deck4_id = ?");
        stmt.setInt(1, key);
        stmt.setInt(2, key);
        stmt.setInt(3, key);
        stmt.setInt(4, key);

        stmt.executeUpdate();

        stmt = conn.prepareStatement("Delete FROM Matchups WHERE deck1_id = ? OR deck2_id = ?");
        stmt.setInt(1, key);
        stmt.setInt(2, key);

        stmt.executeUpdate();
        conn.close();
    }

}
