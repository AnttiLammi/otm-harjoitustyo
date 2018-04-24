/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.daot;

import java.sql.*;

/**
 *
 * @author antlammi
 */
public class Database {

    private String databaseAddress;

    public Database(String databaseAddress) throws ClassNotFoundException, SQLException {

        this.databaseAddress = databaseAddress;

        this.createNewDatabase();

    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        // load the sqlite-JDBC driver using the current class loader
        Class.forName("org.sqlite.JDBC");
        return DriverManager.getConnection(databaseAddress);
    }

    public void createNewDatabase() throws SQLException, ClassNotFoundException {

        Connection conn = this.getConnection();
        PreparedStatement stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Deck(id integer PRIMARY KEY, name varchar(200));");
        stmt.execute();

        stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Player(id integer PRIMARY KEY, name varchar(200),"
                + " deck1_id integer, deck2_id integer, deck3_id integer, deck4_id integer, FOREIGN KEY(deck1_id) REFERENCES Deck(id),"
                + " FOREIGN KEY(deck2_id) REFERENCES Deck(id), FOREIGN KEY(deck3_id) REFERENCES Deck(id), FOREIGN KEY (deck4_id) REFERENCES Deck(id));");

        stmt.execute();

        stmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Matchups (deck1_id integer, deck2_id integer, winrate double,"
                + " FOREIGN KEY(deck1_id) REFERENCES Deck(id),"
                + " FOREIGN KEY(deck2_id) REFERENCES Deck(id),"
                + " PRIMARY KEY (deck1_id, deck2_id));");
        stmt.execute();
        stmt.close();
        conn.close();

    }
}
