package hsbancalculator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hsbancalculator.sovelluslogiikka.Deck;
import java.util.ArrayList;
import java.util.HashMap;
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
public class DeckTest {

    Deck deck1;
    Deck deck2; 
    Deck deck3; 
    Deck deck4;
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        deck1 = new Deck("Tempo Rogue");
        deck2 = new Deck("Secret Mage");
        deck3 = new Deck("Cube Warlock");
        deck4 = new Deck("Control Priest");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void setWinrateTest() {
        Deck deck = new Deck("Control Warlock");
        Deck vastassa = new Deck("Dude Paladin");

        Boolean asetus1 = deck.setWinrate(vastassa, 0.65);
        Boolean asetus2 = vastassa.setWinrate(deck,-1.0);

        assertEquals(true, asetus1);
        assertEquals(false, asetus2);
        
        HashMap<Deck, Double> map1 = deck.getAllWR();
        assertEquals((Double) 0.65, map1.get(vastassa));

       
    }

    @Test
    public void getWinrateTest(){
        deck1.setWinrate(deck2, 0.6);
        
        assertEquals((Double) 0.6, deck1.getWinrate(deck2));
        
        Double eR = -100.0;
        assertEquals(eR, deck1.getWinrate(deck3));
    }
    @Test 
    public void toStringTest(){
        assertEquals("Tempo Rogue", deck1.toString());
    }
    @Test
    public void getDeckWinratesTest(){
        Random rng = new Random();
        
        Double wr0 = rng.nextDouble();
        deck1.setWinrate(deck1, wr0);
        
        Double wr1 = rng.nextDouble();
        deck1.setWinrate(deck2, wr1);
        
        Double wr2 = rng.nextDouble();
        deck1.setWinrate(deck3, wr2);
        
        Double wr3 = rng.nextDouble();
        deck1.setWinrate(deck4, wr3);
     
        
        HashMap<Deck, Double> wrmap = deck1.getAllWR();
        assertEquals(wr0, wrmap.get(deck1));
        assertEquals(wr1, wrmap.get(deck2));
        assertEquals(wr2, wrmap.get(deck3));
        assertEquals(wr3, wrmap.get(deck4));
    }

}
