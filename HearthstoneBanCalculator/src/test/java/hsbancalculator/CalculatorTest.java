/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsbancalculator;

import hsbancalculator.sovelluslogiikka.Player;
import hsbancalculator.sovelluslogiikka.Deck;
import hsbancalculator.sovelluslogiikka.Calculator;
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
public class CalculatorTest {

    Calculator calculator;

    public CalculatorTest() {
        Deck d1 = new Deck("Cube Warlock");
        Deck d2 = new Deck("Tempo Rogue");
        Deck d3 = new Deck("Control Priest");
        Deck d4 = new Deck("Even Paladin");

        d1.setWinrate(d1, 0.5);
        d1.setWinrate(d2, 0.5);
        d1.setWinrate(d3, 0.5);
        d1.setWinrate(d4, 0.5);

        d2.setWinrate(d1, 0.5);
        d2.setWinrate(d2, 0.5);
        d2.setWinrate(d3, 0.5);
        d2.setWinrate(d4, 0.5);

        d3.setWinrate(d1, 0.5);
        d3.setWinrate(d2, 0.5);
        d3.setWinrate(d3, 0.5);
        d3.setWinrate(d4, 0.5);

        d4.setWinrate(d1, 0.5);
        d4.setWinrate(d2, 0.5);
        d4.setWinrate(d3, 0.5);
        d4.setWinrate(d4, 0.5);

        Player p1 = new Player(d1, d2, d3, d4);
        Player p2 = new Player(d4, d3, d2, d1);
        calculator = new Calculator(p1, p2);
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

    

 

    @Test
    public void simulateConquestTest() {
        Player p1 = this.calculator.player1;
        p1.setName("p1");
        Deck p1bannedclass = p1.lineup.get(0);

        Player p2 = this.calculator.player2;
        p2.setName("p2");
        Deck p2bannedclass = p2.lineup.get(0);

        for (int j = 0; j < p2.lineup.size(); j++) {
            p1.lineup.get(1).setWinrate(p2.lineup.get(j), 55.0);
        }
        Calculator calc = new Calculator(p1, p2);
        Random rng = new Random();
        rng.setSeed(1);
        calc.setRandom(rng);
        Player expResult = p1;

        Player result = calc.simulateConquest(p1bannedclass, p2bannedclass);
        assertEquals(expResult.name, result.name);

    }

  

    @Test
    public void simulateLHSTest() {
        Player p1 = this.calculator.player1;
        p1.setName("p1");
        Deck p1bannedclass = p1.lineup.get(0);

        Player p2 = this.calculator.player2;
        p2.setName("p2");
        Deck p2bannedclass = p2.lineup.get(0);

        for (int j = 0; j < p2.lineup.size(); j++) {
            p1.lineup.get(1).setWinrate(p2.lineup.get(j), 75.0);
        }
        Calculator calc = this.calculator;
        Random rng = new Random();
        rng.setSeed(1);
        calc.setRandom(rng);
        Player expResult = p1;

        Player result = calc.simulateLHS(p1bannedclass, p2bannedclass);
        assertEquals(expResult.name, result.name);

    }
    

    @Test
    public void calculateBanLHS() {

        Player p1 = this.calculator.player1;
        Player p2 = this.calculator.player2;
        Deck bb = null;
        for (int i = 0; i < p1.lineup.size(); i++) {
            p1.setWinrate(p1.lineup.get(i), p2.lineup.get(0), 0.40);
            p2.setWinrate(p2.lineup.get(0), p1.lineup.get(i), 0.60);
            bb = p2.lineup.get(0);
        }

        Random random = new Random();
        random.setSeed(1);
        calculator.setRandom(random);
        Boolean conquest = false;

        ArrayList<Deck> vBan = new ArrayList<Deck>();
        HashMap<Deck, Double> dd = calculator.calculateBan(vBan, conquest);
        ArrayList<Deck> p2decks = new ArrayList<>();
        dd.keySet().forEach(s -> p2decks.add(s));
        for (int i = 0; i < p2decks.size(); i++) {
            if (p2decks.get(i).name.equals(bb.name)) {
                bb = p2decks.get(i);
            }
        }
        Double max = dd.get(bb);
        for (int i = 0; i < 4; i++) {
            if (dd.get(p2.lineup.get(i)) > max) {
                fail("paras ban ei ollut odotettu");
            }
        }
    }
    @Test
    public void calculateBanRestricted() {

        Player p1 = this.calculator.player1;
        Player p2 = this.calculator.player2;
        Deck bb = null;
        for (int i = 0; i < p1.lineup.size(); i++) {
            p1.setWinrate(p1.lineup.get(i), p2.lineup.get(0), 0.40);
            p2.setWinrate(p2.lineup.get(0), p1.lineup.get(i), 0.60);
            bb = p2.lineup.get(0);
        }

        Random random = new Random();
        random.setSeed(1);
        calculator.setRandom(random);
        Boolean conquest = false;

        ArrayList<Deck> vBan = new ArrayList<Deck>();
        vBan.add(p1.lineup.get(1));
        vBan.add(p1.lineup.get(2));
        HashMap<Deck, Double> dd = calculator.calculateBan(vBan, conquest);
        ArrayList<Deck> p2decks = new ArrayList<>();
        dd.keySet().forEach(s -> p2decks.add(s));
        for (int i = 0; i < p2decks.size(); i++) {
            if (p2decks.get(i).name.equals(bb.name)) {
                bb = p2decks.get(i);
            }
        }
        Double max = dd.get(bb);
        for (int i = 0; i < 4; i++) {
            if (dd.get(p2.lineup.get(i)) > max) {
                fail("paras ban ei ollut odotettu");
            }
        }
    }
    @Test
    public void calculateBanConquest() {
        Player p1 = this.calculator.player1;
        Player p2 = this.calculator.player2;
        Deck bb = null;
        for (int i = 0; i < p1.lineup.size(); i++) {
            p1.setWinrate(p1.lineup.get(i), p2.lineup.get(0), 0.40);
            p2.setWinrate(p2.lineup.get(0), p1.lineup.get(i), 0.60);
            bb = p2.lineup.get(0);
        }

        Random random = new Random();
        random.setSeed(1);
        calculator.setRandom(random);
        
        Boolean conquest = true;
        ArrayList<Deck> vBan = new ArrayList<Deck>();
        HashMap<Deck, Double> dd = calculator.calculateBan(vBan, conquest);
        ArrayList<Deck> p2decks = new ArrayList<>();
        dd.keySet().forEach(s -> p2decks.add(s));
        for (int i = 0; i < p2decks.size(); i++) {
            if (p2decks.get(i).name.equals(bb.name)) {
                bb = p2decks.get(i);
            }
        }
        Double max = dd.get(bb);
        for (int i = 0; i < 4; i++) {
            if (dd.get(p2.lineup.get(i)) > max) {
                fail("paras ban ei ollut odotettu");
            }
        }

    }
}
