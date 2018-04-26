/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import hsbancalculator.daot.*;
import hsbancalculator.sovelluslogiikka.Deck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author antlammi
 */
public class MatchupsDaoTest {

    private Database db;
    private DeckDao dDao;
    private MatchupsDao mDao;
    private Random rng;

    public MatchupsDaoTest() throws ClassNotFoundException, SQLException {
        this.db = new Database("jdbc:sqlite:src/test/java/database/test.db");

        this.dDao = new DeckDao(db);
        this.dDao.saveOrUpdate(new Deck("Cube Warlock"));

        this.dDao.saveOrUpdate(new Deck("Even Paladin"));

        this.mDao = new MatchupsDao(db);
        this.rng = new Random();

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() throws SQLException,  ClassNotFoundException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE From Deck where 1=1");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("DELETE From Matchups where 1=1");
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void saveAndFindOneTest() throws SQLException, ClassNotFoundException {
        Deck d1 = dDao.findOne(1);
        Deck d2 = dDao.findOne(2);

        this.mDao.saveOrUpdate(d1.id, d2.id, 0.60);

        assertEquals((Double) 0.60, mDao.findOne(d1.id, d2.id));
    }

    @Test
    public void updateTest() throws SQLException, ClassNotFoundException {
        Deck d1 = dDao.findOne(1);
        Deck d2 = dDao.findOne(2);

        this.mDao.saveOrUpdate(d1.id, d2.id, 0.60);

        assertEquals((Double) 0.60, mDao.findOne(d1.id, d2.id));
        
        this.mDao.saveOrUpdate(d1.id, d2.id, 0.58);
        assertEquals((Double) 0.58, mDao.findOne(d1.id, d2.id));
    }
    
    @Test
    public void findOneNull() throws SQLException, ClassNotFoundException{
       assertTrue(mDao.findOne(1, 2) == null);
    }
   
}
