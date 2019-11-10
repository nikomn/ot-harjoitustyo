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
 * @author nikoniem
 */
public class KassapaateTest {
    
    Kassapaate kassapaate;
    
    @Before
    public void setUp() {
        kassapaate = new Kassapaate();
    }
    
    @Test
    public void kassanSummaOikeinAlusa() {
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void myytyjenEdullistenMaaraOikeinAlusa() {
        assertEquals(0, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void myytyjenMaukkaidenMaaraOikeinAlusa() {
        assertEquals(0, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void syoEdullisestiMaksuKirjautuuOikein() {
        kassapaate.syoEdullisesti(240);
        assertEquals(100240, kassapaate.kassassaRahaa());
    }
    
    
    @Test
    public void syoMaukkaastiMaksuKirjautuuOikein() {
        kassapaate.syoMaukkaasti(400);
        assertEquals(100400, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void syoEdullisestiVaihtorahaOikein() {
        assertEquals(10, kassapaate.syoEdullisesti(250));
    }
    
    @Test
    public void syoMaukkaastiVaihtorahaOikein() {
        assertEquals(10, kassapaate.syoMaukkaasti(410));
    }
    
    @Test
    public void syoEdullisestiEiMyyntiaJosEiRiittavastiRahaa() {
        assertEquals(10, kassapaate.syoEdullisesti(10));
    }
    
    @Test
    public void syoMaukkaastiEiMyyntiaJosEiRiittavastiRahaa() {
        assertEquals(10, kassapaate.syoMaukkaasti(10));
    }
    
    @Test
    public void syoEdullisestiKorttimaksuPaluuarvoOikeinKunSaldoa() {
        Maksukortti kortti = new Maksukortti(250);
        assertEquals(true, kassapaate.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoEdullisestiKorttimaksuPaluuarvoOikeinKunEiSaldoa() {
        Maksukortti kortti = new Maksukortti(200);
        assertEquals(false, kassapaate.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoMaukkaastiKorttimaksuPaluuarvoOikeinKunSaldoa() {
        Maksukortti kortti = new Maksukortti(480);
        assertEquals(true, kassapaate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void syoMaukkaastiKorttimaksuPaluuarvoOikeinKunEiSaldoa() {
        Maksukortti kortti = new Maksukortti(200);
        assertEquals(false, kassapaate.syoMaukkaasti(kortti));
    }
    
    @Test
    public void myytyjenEdullistenMaaraOikein() {
        Maksukortti kortti = new Maksukortti(250);
        kassapaate.syoEdullisesti(kortti);
        kassapaate.syoEdullisesti(kortti);
        kassapaate.syoEdullisesti(240);
        kassapaate.syoEdullisesti(10);
        assertEquals(2, kassapaate.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void myytyjenMaukkaidenMaaraOikein() {
        Maksukortti kortti = new Maksukortti(500);
        kassapaate.syoMaukkaasti(kortti);
        kassapaate.syoMaukkaasti(kortti);
        kassapaate.syoMaukkaasti(400);
        kassapaate.syoMaukkaasti(10);
        assertEquals(2, kassapaate.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kortinSaldoKasvaaLadatessa() {
        Maksukortti kortti = new Maksukortti(500);
        kassapaate.lataaRahaaKortille(kortti, 100);
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void kassanSaldoKasvaaLadatessaKortille() {
        Maksukortti kortti = new Maksukortti(500);
        kassapaate.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, kassapaate.kassassaRahaa());
    }
    
    @Test
    public void negatiivistaSummaEiLisataKortille() {
        Maksukortti kortti = new Maksukortti(0);
        kassapaate.lataaRahaaKortille(kortti, -1);
        assertEquals(100000, kassapaate.kassassaRahaa());
    }
    
    

        
    
}
