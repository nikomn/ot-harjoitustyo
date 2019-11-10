package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoAlussaOikein() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void saldoKasvaaOikein() {
        kortti.lataaRahaa(50);
        assertEquals("saldo: 0.60", kortti.toString());
    }
    
    @Test
    public void saldoVaheneeOikeinKunRahaaRiittavasti() {
        kortti.lataaRahaa(50); // Lisätty, koska alkuperäisessä koodissa esim. 10 senttiä - 5 senttiä -> saldo: 0.5!?
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.55", kortti.toString());
    }
    
    @Test
    public void saldoEiVaheneJosEiRahaa() {
        kortti.otaRahaa(15);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void metodinPaluuarvoOikeinKunRahaaRiittavasti() {
        assertEquals(true, kortti.otaRahaa(5));
    }
    
    @Test
    public void metodinPaluuarvoOikeinKunRahaaEiRiittavasti() {
        assertEquals(false, kortti.otaRahaa(15));
    }
    
    @Test
    public void saldoNaytetaanOikein() {
        assertEquals(10, kortti.saldo());
    }
}
