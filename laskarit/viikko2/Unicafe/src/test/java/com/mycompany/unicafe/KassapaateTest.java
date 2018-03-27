/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

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
public class KassapaateTest {

    Kassapaate kassa;

    public KassapaateTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void kassaAlussaOikein(){
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiOikeinKunRahaaRiittavasti(){
        int vaihtoraha = kassa.syoEdullisesti(500);
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(260, vaihtoraha);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        
        vaihtoraha = kassa.syoMaukkaasti(400);
        assertEquals(0, vaihtoraha);
        assertEquals(100640, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
       
    }
    
    @Test
    public void kateisostoToimiiOikeinKunRahaaEiRiittavasti(){
           int vaihtoraha = kassa.syoEdullisesti(100);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(100, vaihtoraha);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        
        vaihtoraha = kassa.syoMaukkaasti(240);
        assertEquals(240, vaihtoraha);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiOstoKunRiittavastiRahaa(){
        Maksukortti kortti = new Maksukortti(1000);
        
        assertEquals(true, kassa.syoEdullisesti(kortti));
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
        
        assertEquals(true, kassa.syoMaukkaasti(kortti));
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void korttiOstoKunRahaEiRiita(){
        Maksukortti kortti = new Maksukortti(200);
        
        assertEquals(false, kassa.syoEdullisesti(kortti));
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
        
        assertEquals(false, kassa.syoMaukkaasti(kortti));
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void kortinLataaminenToimiiOikein(){
        Maksukortti kortti = new Maksukortti(1000);
        kassa.lataaRahaaKortille(kortti, 1000);
        
        assertEquals(2000, kortti.saldo());
        assertEquals(101000, kassa.kassassaRahaa());
        
        kassa.lataaRahaaKortille(kortti, -1000);
        
        assertEquals(2000, kortti.saldo());
        assertEquals(101000, kassa.kassassaRahaa());
    }
}
