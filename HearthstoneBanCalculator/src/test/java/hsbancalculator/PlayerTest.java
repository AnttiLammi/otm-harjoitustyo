/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator;

import hsbancalculator.sovelluslogiikka.Player;
import hsbancalculator.sovelluslogiikka.Deck;
import java.io.PrintStream;
import java.util.ArrayList;
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
public class PlayerTest {

    public PlayerTest() {
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
    public void tearDown() {
    }

    /**
     * Test of setWinrate method, of class Player.
     */
    @Test
    public void testSetWinrate() {

        Deck deck1 = new Deck("test1");
        Deck deck2 = new Deck("test2");
        Deck deck3 = new Deck("test3");
        Deck deck4 = new Deck("test4");
        Random rng = new Random();
        Double winrate = 0.05;
        Player instance = new Player(deck1, deck2, deck3, deck4);

        instance.setWinrate(deck1, deck2, winrate);
        Double asetus = deck1.getWinrate(deck2);

        assertEquals((Double) 0.05, asetus);

    }

    /**
     * Test of getWinrate method, of class Player.
     */
    @Test
    public void testGetWinrate() {
        System.out.println("getWinrate");
        Deck deck1 = new Deck("test1");
        Deck deck2 = new Deck("test2");
        Player instance = new Player(deck1, deck2, new Deck("test3"), new Deck("test4"));
        Random rng = new Random();
        Double wr = rng.nextDouble();
        deck1.setWinrate(deck2, wr);
        
        Double result = instance.getWinrate(deck1, deck2);
        assertEquals(wr, result);
        
        Double expectedResult = -100.0;
        Double result2 = instance.getWinrate(deck1, new Deck("not found"));
        
        assertEquals(expectedResult, result2);
    }

}
