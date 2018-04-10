/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.HashMap;
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

    @Test
    public void winratenAsetusToimii() {
        Deck deck = new Deck("Control Warlock");
        Deck vastassa = new Deck("Dude Paladin");

        deck.setWinrate(vastassa, 0.65);
        vastassa.setWinrate(deck, 0.35);

        HashMap<Deck, Double> map1 = deck.getAllWR();
       assertEquals((Double) 0.65, map1.get(vastassa));

        HashMap<Deck, Double> map2 = vastassa.getAllWR();
        assertEquals((Double) 0.35, map2.get(deck));
    }

}
