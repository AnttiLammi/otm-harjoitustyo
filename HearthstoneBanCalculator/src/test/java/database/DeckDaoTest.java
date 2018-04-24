/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import hsbancalculator.daot.*;
import hsbancalculator.sovelluslogiikka.Deck;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import static org.junit.Assert.*;

/**
 *
 * @author antlammi
 */
public class DeckDaoTest {

    private Database db;
    private DeckDao dDao;

    public DeckDaoTest() throws SQLException, ClassNotFoundException {
        this.db = new Database("jdbc:sqlite:src/test/java/database/test.db");

        this.dDao = new DeckDao(db);
        this.dDao.saveOrUpdate(new Deck("Cube Warlock"));
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
    public void tearDown() throws SQLException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE From Deck where 1=1");
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testFindAll() throws SQLException {

        List<Deck> list = dDao.findAll();
        assertTrue(list.size() >= 1);

    }

    @Test
    public void testFindByName() throws SQLException {
        Deck test = dDao.findByName("Cube Warlock");
        assertTrue(test != null);

    }

    @Test
    public void testFindByNameNull() throws SQLException {

        assertTrue(dDao.findByName("Even Shaman") == null);
    }

    @Test
    public void testFindIDByName() throws SQLException {
        Integer id = dDao.findIDByName("Cube Warlock");
        assertEquals((int) 1, (int) id);

    }

    @Test
    public void testFindIDByNameNull() throws SQLException {
        assertTrue(dDao.findIDByName("Even Shaman") == null);
    }
    @Test
    public void testSaveAndDelete() throws SQLException {
        Deck test = new Deck("Even Paladin");
        List<Deck> testlist = dDao.findAll();
        Boolean found = false;
        for (int i = 0; i < testlist.size(); i++) {
            if (testlist.get(i).name.equals(test.name)) {
                found = true;
            }
        }
        assertFalse(found);

        dDao.saveOrUpdate(test);

        testlist = dDao.findAll();
        found = false;
        for (int i = 0; i < testlist.size(); i++) {
            if (testlist.get(i).name.equals(test.name)) {
                found = true;
            }
        }
        assertTrue(found);

        Integer id = dDao.findIDByName("Even Paladin");
        dDao.delete(id);
        testlist = dDao.findAll();
        found = false;
        for (int i = 0; i < testlist.size(); i++) {
            if (testlist.get(i).name.equals(test.name)) {
                found = true;
            }
        }
        assertFalse(found);

    }

    @Test
    public void testSaveWhenNull() throws SQLException {
        Deck test = new Deck("Cube Warlock");
        assertTrue(dDao.saveOrUpdate(test) == null);
    }
}
