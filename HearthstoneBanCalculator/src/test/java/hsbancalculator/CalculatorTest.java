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
        d1.setWinrate(d2, 0.6);
        d1.setWinrate(d3, 0.55);
        d1.setWinrate(d4, 0.55);

        d2.setWinrate(d1, 0.4);
        d2.setWinrate(d2, 0.5);
        d2.setWinrate(d3, 0.5);
        d2.setWinrate(d4, 0.4);

        d3.setWinrate(d1, 0.45);
        d3.setWinrate(d2, 0.5);
        d3.setWinrate(d3, 0.5);
        d3.setWinrate(d4, 0.55);

        d4.setWinrate(d1, 0.45);
        d4.setWinrate(d2, 0.6);
        d4.setWinrate(d3, 0.45);
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
    public void constructorToimii() {
        Player pelaaja1 = new Player(new Deck("1"), new Deck("2"), new Deck("3"), new Deck("4"));
        Player pelaaja2 = new Player(new Deck("5"), new Deck("6"), new Deck("7"), new Deck("8"));
        Calculator calculator = new Calculator(pelaaja1, pelaaja2);
        assertEquals(pelaaja1, calculator.pelaaja1);
        assertEquals(pelaaja2, calculator.pelaaja2);
    }

    @Test
    public void testSimuloiConquest() {
       
        Player pelaaja1 = this.calculator.pelaaja1;
        Deck p1bannedclass = this.calculator.pelaaja1.lineup.get(0);
        Player pelaaja2 = this.calculator.pelaaja2;
        Deck p2bannedclass = this.calculator.pelaaja2.lineup.get(0);
        Calculator instance = this.calculator;
        Player expResult = this.calculator.pelaaja1;
        Player expResult2 = this.calculator.pelaaja2;
        Player result = instance.simuloiConquest(pelaaja1, p1bannedclass, pelaaja2, p2bannedclass);
        if (result == expResult) {
            assertEquals(expResult, result);
        } else if (result == expResult2) {
            assertEquals(expResult2, result);
        } else {

            fail("Kumpikaan pelaajista ei voittanut");
        }
    }

    /**
     * Test of simuloiLHS method, of class Calculator.
     */
    @Test
    public void testSimuloiLHS() {
        
        Player pelaaja1 = this.calculator.pelaaja1;
        Deck p1bannedclass = this.calculator.pelaaja1.lineup.get(0);
        Player pelaaja2 = this.calculator.pelaaja2;
        Deck p2bannedclass = this.calculator.pelaaja2.lineup.get(0);
        Calculator instance = this.calculator;
        Player expResult = this.calculator.pelaaja1;
        Player expResult2 = this.calculator.pelaaja2;
        Player result = instance.simuloiLHS(pelaaja1, p1bannedclass, pelaaja2, p2bannedclass);
        if (result == expResult) {
            assertEquals(expResult, result);
        } else if (result == expResult2) {
            assertEquals(expResult2, result);
        } else {

            fail("Kumpikaan pelaajista ei voittanut");
        }
    }

}
