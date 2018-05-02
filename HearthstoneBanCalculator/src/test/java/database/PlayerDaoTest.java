/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import hsbancalculator.daot.Database;
import hsbancalculator.daot.DeckDao;
import hsbancalculator.daot.MatchupsDao;
import hsbancalculator.daot.PlayerDao;
import hsbancalculator.sovelluslogiikka.Deck;
import hsbancalculator.sovelluslogiikka.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author antlammi
 */
public class PlayerDaoTest {

    private Database db;
    private DeckDao dDao;
    private PlayerDao pDao;

    public PlayerDaoTest() throws ClassNotFoundException, SQLException {
        this.db = new Database("jdbc:sqlite:src/test/java/database/test.db");
        this.dDao = new DeckDao(db);
        this.dDao.saveOrUpdate(new Deck("Cube Warlock"));
        this.dDao.saveOrUpdate(new Deck("Quest Rogue"));
        this.dDao.saveOrUpdate(new Deck("Even Paladin"));
        this.dDao.saveOrUpdate(new Deck("Control Priest"));
        this.pDao = new PlayerDao(db);

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
    public void tearDown() throws SQLException, ClassNotFoundException {
        Connection conn = db.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE From Deck where 1=1");
        stmt.executeUpdate();
        stmt.close();

        stmt = conn.prepareStatement("DELETE From Player where 1=1");
        stmt.executeUpdate();
        stmt.close();
        conn.close();
    }

    @Test
    public void pelaajanVoiLisataJaLoytaa() throws SQLException, ClassNotFoundException {
        List<Deck> d = dDao.findAll();
        Player p = new Player(d.get(0), d.get(1), d.get(2), d.get(3));
        p.setName("Testimies");
        pDao.saveOrUpdate(p);
        assertTrue(pDao.findOne(pDao.findByName(p.name)) != null);
    }

    @Test
    public void tietynPelaajanVoiLoytaa() throws SQLException, ClassNotFoundException {
        List<Deck> d = dDao.findAll();
        Player p = new Player(d.get(0), d.get(1), d.get(2), d.get(3));
        p.setName("Testimies");
        pDao.saveOrUpdate(p);

        Player p2 = pDao.findOne(pDao.findByName(p.name));
        assertEquals(p.name, p2.name);
    }

    @Test
    public void nullKunEiLoydy() throws SQLException, ClassNotFoundException {
        assertEquals(pDao.findOne(2), null);
    }

    @Test
    public void findAllToimii() throws SQLException, ClassNotFoundException {
        List<Deck> d = dDao.findAll();
        Player p = new Player(d.get(0), d.get(1), d.get(2), d.get(3));
        p.setName("Testimies");

        List<Deck> d2 = dDao.findAll();
        Player p2 = new Player(d.get(0), d.get(1), d.get(2), d.get(3));
        p2.setName("Testinainen");
        pDao.saveOrUpdate(p);
        pDao.saveOrUpdate(p2);
        List<Player> pelaajat = this.pDao.findAll();
        List<String> pelaajanimet = new ArrayList<>();
        pelaajat.forEach(s -> pelaajanimet.add(s.name));

        assertTrue(pelaajanimet.get(0).equals("Testimies"));
        assertTrue(pelaajanimet.get(1).equals("Testinainen"));
    }

    @Test
    public void poistaminenToimii() throws SQLException, ClassNotFoundException {
        List<Deck> d = dDao.findAll();
        Player p = new Player(d.get(0), d.get(1), d.get(2), d.get(3));
        p.setName("Testimies");
        pDao.saveOrUpdate(p);

        assertTrue(pDao.findAll().size() > 0);

        pDao.delete(1);

        assertTrue(pDao.findAll().isEmpty());
    }

    @Test
    public void findByNameToimii() throws SQLException, ClassNotFoundException {
        List<Deck> d = dDao.findAll();
        Player p = new Player(d.get(0), d.get(1), d.get(2), d.get(3));
        p.setName("Testimies");

        pDao.saveOrUpdate(p);
        Integer r = pDao.findByName("Testimies");

        assertEquals(pDao.findOne(r).name, "Testimies");
    }
}
