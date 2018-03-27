package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    @Test
    public void kortinSaldoAlussaOikein(){
        assertEquals("saldo: 10.0", kortti.toString());
    }
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein(){
        kortti.lataaRahaa(200);
        assertEquals("saldo: 12.0", kortti.toString());
    }
    
    @Test
    public void saldoPieneneeOttaessaOikein(){
        kortti.otaRahaa(200);
        assertEquals("saldo: 8.0", kortti.toString());
    }
    @Test
    public void saldoEiPieneneJosRahaEiRiitä(){
        kortti.otaRahaa(1100);
        assertEquals("saldo: 10.0", kortti.toString());
    }
    @Test
    public void ottaminenPalauttaaFalse(){
        assertEquals(false, kortti.otaRahaa(1100));
    }
    @Test
    public void ottaminenPalauttaaTrue(){
        assertEquals(true, kortti.otaRahaa(900));
    }
    @Test
    public void saldoOikein(){
        assertEquals(1000, kortti.saldo());
    }
}
