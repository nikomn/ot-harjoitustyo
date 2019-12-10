/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.dao;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;
import studytimetracker.domain.Course;
import studytimetracker.domain.User;

/**
 * Tietokannan hallinnoinnista vastaava luokka
 */
public class DBWriter {

    private List<Course> courses;
    private List<User> users;
    private DBReader dbreader;
    private String userfile;     // for example "userdb.txt"
    private String coursefile;   // for example "coursedb.txt"

    public DBWriter(String userfile, String coursefile) throws Exception {
        this.dbreader = new DBReader();
        this.coursefile = coursefile;
        this.userfile = userfile;
        this.courses = this.dbreader.getCourses(this.coursefile);
        this.users = this.dbreader.getUsers(this.userfile);

    }

    /**
     * Päivittää ohjelman suorituksen aikana tapahtuvat muutokset
     * kurssitietokantaan
     */
    public void updateCourseDB() throws Exception {
        try {
            PrintWriter fwriter = new PrintWriter(this.coursefile);

            for (Course course : this.courses) {
                // System.out.println(course.getName());
                fwriter.println(course.getName() + "£" + course.getTime()
                        + "£" + course.getUser().getName());
            }
            fwriter.close();
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    /**
     * Päivittää ohjelman suorituksen aikana tapahtuvat muutokset
     * käyttäjätietokantaan
     */
    public void updateUserDB() throws Exception {
        try {
            PrintWriter fwriter = new PrintWriter(this.userfile);

            for (User user : this.users) {
                // System.out.println(user.getName());
                fwriter.println(user.getName());
            }
            fwriter.close();
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    /**
     * Lisää kurssin sovellukseen ja tietokantaan
     */
    public void addCourse(Course course) throws Exception {
        if (!this.courses.contains(course)) {
            this.courses.add(course);
            updateCourseDB();
        }

    }

    /**
     * Lisää käyttäjän sovellukseen ja tietokantaan
     */
    public void addUser(User user) throws Exception {
        if (!this.users.contains(user)) {
            this.users.add(user);
            updateUserDB();
        }

    }

    /**
     * Palauttaa listauksen kursseista
     */
    public List<Course> getCourses() {
        return this.courses;
    }

    /**
     * Palauttaa listauksen käyttäjistä
     */
    public List<User> getUsers() {
        return this.users;
    }

    /**
     * Seuraa ajankäyttöä reaaliajassa. Odottaa käyttäjän painavan enter, joka
     * pysäyttää ajanseurannan.
     */
    public void trackCourse(Course course) throws Exception {
        if (this.courses.contains(course)) {
            System.out.println("Tracking time on course " + course.getName());
            course.startTimeTracking();
            System.out.println("Stop tracking by pressing Enter...");
            Scanner scanner = new Scanner(System.in);
            String testi = scanner.nextLine();
            course.stopTimeTracking();
            updateCourseDB();
        }

    }

    /**
     * Lisää aikaa manuaalisesti.
     *
     * @param timeToAdd lisättävä aika sekunteina
     */
    public void addTime(Course course, Double timeToAdd) throws Exception {
        if (this.courses.contains(course)) {
            course.addTime(timeToAdd);
            updateCourseDB();
        }
    }

    /**
     * Ylikirjoittaa tallennetun ajan käyttäjän syöttämällä ajalla.
     *
     * @param timeToAdd aika sekunteina
     */
    public void editTime(Course course, Double timeToAdd) throws Exception {
        if (this.courses.contains(course)) {
            course.changeTime(timeToAdd);
            updateCourseDB();
        }
    }
}
