/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator.daot;

import java.sql.*;
import java.util.List;

/**
 *
 * @author antlammi
 */
public class MatchupsDao {

    private Database database;

    public MatchupsDao(Database database) {
        this.database = database;
    }

    public Double findOne(Integer key1, Integer key2) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("Select * From Matchups WHERE deck1_id = ? AND deck2_id = ?");
        stmt.setInt(1, key1);
        stmt.setInt(2, key2);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Double d = rs.getDouble("winrate");

        stmt.close();
        rs.close();

        conn.close();

        return d;
    }

    public List<Double> findAll() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Double saveOrUpdate(Integer key1, Integer key2, Double wr) throws SQLException {
        Double d = findOne(key1, key2);
        if (d == null) {
            try (Connection conn = database.getConnection()) {
                PreparedStatement stmt = conn.prepareStatement("INSERT into Matchups(deck1_id, deck2_id, winrate) Values(?, ?, ?)");
                stmt.setInt(1, key1);
                stmt.setInt(2, key2);
                stmt.setDouble(3, wr);
                
                stmt.executeUpdate();
                
                return findOne(key1, key2);
            } 
        } else {
            try (Connection conn = database.getConnection()){
                PreparedStatement stmt = conn.prepareStatement("UPDATE Matchups SET winrate = ? WHERE deck1_id = ? AND deck2_id = ?");
                stmt.setDouble(1, wr);
                stmt.setInt(2, key1);
                stmt.setInt(3, key2);
                
                stmt.executeUpdate();
                
                return findOne(key1, key2);
            }
        }
        
    }

    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
