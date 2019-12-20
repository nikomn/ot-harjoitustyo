/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.domain;

;

import java.util.Objects;

/**
 * Kurssia kuvaava luokka
 */


public class Course {

    private String name;
    private double totaltime;  // Time is stored in seconds, double used in order to be able to store longer timeperiods
    private User user;
    private double startTime;

    /**
     * Laskee mm. hashmapin käyttämän hashCoden, joka toimii olion uniikkina
     * tunnisteena
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.user);
        return hash;
    }

    /**
     * Määrittelee arvon, jonka perusteella olio, erotetaan muista vastaavista,
     * esim. araylistin includesin käyttöön
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }
    

    /**
     * Konstruktori
     *
     * @param nimi, merkkijono
     * @param user, Courseen liittyvä User olio
     */
    public Course(String name, User user) {
        this.name = name;
        this.user = user;
        this.totaltime = 0.0;
    }

    /**
     * Paluuarvoton metodi, joka lisää course olioon aikaa
     *
     * @param elapsedtime sekunteina, double muodossa
     */
    public void addTime(double elapsedtime) {
        this.totaltime = this.totaltime + elapsedtime;
    }

    /**
     * Paluuarvoton metodi, joka muuttaa course olion aikakirjauksen kokonaan
     * uudeksi arvoksi
     *
     * @param time, sekuntteina, double arvona
     */
    public void changeTime(double time) {
        this.totaltime = time;
    }

    /**
     * Paluuarvoton metodi, joka asettaa ajanseurannan aloitusajan muuttujaan
     * starttime. Arvoksi kirjautuu nykyhetki.
     *
     */
    public void startTimeTracking() {
        System.out.println("Total time so far... " + this.totaltime);
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Paluuarvoton metodi, joka lisää aloitushetkestä kuluneen ajan totaltime
     * muuttujaan ja nollaa starttime muuttujan
     */
    public void stopTimeTracking() {
        double endtime = System.currentTimeMillis();
        System.out.println("Tracked time " + ((endtime - this.startTime) / 1000));
        this.totaltime = this.totaltime + ((endtime - this.startTime) / 1000);
        this.startTime = 0.0;
    }

    /**
     * Palauttaa Course olion nimen
     * @return String
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Palauttaa totaltimeen kirjatun arvon
     * @return double
     */

    public double getTime() {
        return this.totaltime;
    }
    
    /**
     * Palauttaa Courseen liittyvän User olion
     * @return User
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Palauttaa kokonaisajan muotoiltuna muotoon "hh:mm:ss"
     * @return String
     */
    public String formatTime() {
        Double minutes = Math.floor(this.totaltime / 60);
        Double hours = Math.floor(minutes / 60);
        minutes = minutes - (60 * hours);
        Double seconds = this.totaltime - (60 * minutes) - (60 * 60 * hours);

        // Add leading zeros if needed...
        String hoursFormat = hours.intValue() + "";
        if (hoursFormat.length() == 1) {
            hoursFormat = "0" + hoursFormat;
        }

        String minutesFormat = minutes.intValue() + "";
        if (minutesFormat.length() == 1) {
            minutesFormat = "0" + minutesFormat;
        }

        String secondsFormat = seconds.intValue() + "";
        if (secondsFormat.length() == 1) {
            secondsFormat = "0" + secondsFormat;
        }

        return hoursFormat + ":" + minutesFormat + ":" + secondsFormat;
    }

    /**
     * Tekstikäyttöliittymää varten luotu metodi, joka tulostaa ajan 
     * muunnettuna visuaaliseen muotoon, siten, että tunti == #
     * @return String
     */
    public String formatVisual() {
        Double minutes = Math.floor(this.totaltime / 60);
        Double hours = Math.floor(minutes / 60);

        String visualTime = "";

        if (hours < 1) {
            visualTime = "|";
        } else {
            for (int i = 0; i < hours.intValue(); i++) {
                visualTime = visualTime + "#";
            }
        }

        return visualTime + " (" + formatTime() + ")";
    }

}
