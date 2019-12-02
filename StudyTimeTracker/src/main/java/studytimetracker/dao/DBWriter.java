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
 *
 * @author nikoniem
 */
public class DBWriter {

    private List<Course> courses;
    private List<User> users;
    private DBReader dbreader;

    public DBWriter() throws Exception {
        this.dbreader = new DBReader();
        this.courses = this.dbreader.getCourses();
        this.users = this.dbreader.getUsers();
    }

    public void updateCourseDB() throws Exception {
        try {
            PrintWriter fwriter = new PrintWriter("coursedb.txt");

            for (Course course : this.courses) {
                System.out.println(course.getName());
                fwriter.println(course.getName() + "£" + course.getTime()
                        + "£" + course.getUser().getName());
            }
            fwriter.close();
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void updateUserDB() throws Exception {
        try {
            PrintWriter fwriter = new PrintWriter("userdb.txt");

            for (User user : this.users) {
                System.out.println(user.getName());
                fwriter.println(user.getName());
            }
            fwriter.close();
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }
    }

    public void addCourse(Course course) throws Exception {
        if (!this.courses.contains(course)) {
            this.courses.add(course);
            updateCourseDB();
        }

    }
    
    public void addUser(User user) throws Exception {
        if (!this.users.contains(user)) {
            this.users.add(user);
            updateUserDB();
        }

    }
    
    public List<Course> getCourses() {
        return this.courses;
    }
    
    public List<User> getUsers() {
        return this.users;
    }
    
    
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
}
