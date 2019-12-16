/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.dao;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import studytimetracker.domain.Course;
import studytimetracker.domain.User;

/**
 * 
 * Tietokannan lukemisesta vastaama luokka
 */
public class DBReader {
    
    /**
     * Metodi, joka lukee tietokannasta olemassa olevat kurssit
     * @param dbfile
     * @return Lista kursseista
     * @throws Exception, esim. jos tietokantaa ei voida käyttöoikeuksista johtuen lukea
     */
    public List<Course> getCourses(String dbfile) throws Exception {
        List<Course> courses = new ArrayList<>();
        try {
            Scanner freader = new Scanner(new File(dbfile));
            while (freader.hasNextLine()) {
                String line = freader.nextLine();
                String[] splitline = line.split("£");
                User u = new User(splitline[2]);
                Course c = new Course(splitline[0], u);
                Double t = Double.parseDouble(splitline[1]);
                c.changeTime(t);
                courses.add(c);

            }
            freader.close();
        } catch (Exception e) {
            FileWriter fwriter = new FileWriter(new File(dbfile));
            fwriter.close();
        }
        return courses;
    }
    /**
     * Metodi, joka lukee tietokannasta olemassaolevat käyttäjät
     * @param dbfile
     * @return Lista käyttäjistä
     * @throws Exception, esim. jos tietokantatiedostoa ei ole olemassa
     */
    public List<User> getUsers(String dbfile) throws Exception {
        List<User> users = new ArrayList<>();
        try {
            Scanner freader = new Scanner(new File(dbfile));
            while (freader.hasNextLine()) {
                String line = freader.nextLine();
                User u = new User(line);
                users.add(u);
                
            }
            freader.close();
        } catch (Exception e) {
            FileWriter fwriter = new FileWriter(new File(dbfile));
            fwriter.close();
        }
        return users;
    }
    
}
