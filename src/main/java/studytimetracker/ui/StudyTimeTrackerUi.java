/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static javafx.application.Application.launch;
import studytimetracker.dao.DBWriter;
import studytimetracker.domain.Course;
import studytimetracker.domain.User;
import studytimetracker.ui.BetaGui;

/**
 *
 * @author nikoniem
 */
public class StudyTimeTrackerUi {

    private List<User> users;
    private List<Course> courses;
    private Scanner scanner;
    private DBWriter dbwriter;

    public StudyTimeTrackerUi() throws Exception {
        this.scanner = new Scanner(System.in);
        this.dbwriter = new DBWriter("userdb.txt", "coursedb.txt");
        this.courses = dbwriter.getCourses();
        this.users = dbwriter.getUsers();

    }

    public void startScreenCli() throws Exception {
        while (true) {
            System.out.println("Welcome to StudyTimeTracker beta ui");
            System.out.println("This ui is ment for testing purposes only and "
                    + "should be replaced with a gui soon...");
            System.out.println("\n1. Log in");
            System.out.println("2. Create new user account");
            System.out.println("3. Start gui (early beta - unstable!)");
            System.out.println("x. Close program");
            System.out.println("\nSelect function");
            String i = this.scanner.nextLine();
            switch (i) {
                case "1":
                    loginScreenCli();
                case "2":
                    newUserScreenCli();
                case "x":
                    System.exit(0);
                default:
                    startScreenCli();
            }
        }

    }

    public void loginScreenCli() throws Exception {
        boolean userExists = false;
        while (!userExists) {
            System.out.println("");
            System.out.println("Username (use empty string to return to start screen): ");
            String userName = this.scanner.nextLine();
            if (userName.isEmpty()) {
                startScreenCli();
            }
            User user = new User(userName);
            if (this.users.contains(user)) {
                userExists = true;
                menuScreenCli(user);
            } else {
                System.out.println("Sorry, no such user");

            }
        }

    }

    public void newUserScreenCli() throws Exception {
        boolean userCreated = false;
        while (!userCreated) {
            System.out.println("Define new username (use empty string to return to start screen): ");
            String userName = this.scanner.nextLine();
            if (userName.isEmpty()) {
                startScreenCli();
            }
            User user = new User(userName);
            if (this.users.contains(user)) {
                System.out.println("Sorry, username " + userName
                        + " is already in use! Please select some other username.");
            } else {
                this.dbwriter.addUser(user);
                userCreated = true;
                //addCourseScreenCli(user);
                menuScreenCli(user);
            }
        }

    }

    public void menuScreenCli(User user) throws Exception {
        while (true) {
            //Runtime.getRuntime().exec("clear");
            //System.out.print("\033[H\033[2J");
            //System.out.flush();
            System.out.println("Welcome " + user.getName() + "!");
            System.out.println("Select function...");
            System.out.println("1. Select course");
            System.out.println("2. Add new course");
            System.out.println("3. Print totals");
            System.out.println("x. Log out");
            String i = this.scanner.nextLine();
            switch (i) {
                case "1":
                    selectCourseMenuCli(user);
                case "2":
                    addCourseScreenCli(user);
                case "3":
                    summaryScreenCli(user);
                case "x":
                    startScreenCli();
                default:
                    break;
            }
        }

    }

    public void addCourseScreenCli(User user) throws Exception {
        while (true) {
            System.out.println("Course name (use empty string to return to menu screen): ");
            String courseName = this.scanner.nextLine();
            if (courseName.isEmpty()) {
                //System.exit(0);
                menuScreenCli(user);
            }
            Course course = new Course(courseName, user);
            //this.courses.indexOf(course);
            if (!this.courses.contains(course)) {
                this.dbwriter.addCourse(course);
                System.out.println("Course added successfully!");
                menuScreenCli(user);
            } else {
                System.out.println("Course already exist in database!");
            }
        }

    }

    public void selectCourseMenuCli(User user) throws Exception {
        boolean coursesFound = false;
        int index = 0;
        while (index < this.courses.size()) {
            if (this.courses.get(index).getUser().equals(user)) {
                coursesFound = true;
                System.out.println(index + ": " + this.courses.get(index).getName());
            }
            index++;
        }
        if (coursesFound) {
            System.out.println("Select a course:");
            try {
                Integer courseNumber = Integer.parseInt(this.scanner.nextLine());
                Course course = this.courses.get(courseNumber);
                selectedCourseScreenCli(user, course);
            } catch (Exception e) {
                System.out.println("No such course!");
            }
        } else {
            System.out.println("No courses found! Please start by adding a course.");
            menuScreenCli(user);
        }

    }

    public void selectedCourseScreenCli(User user, Course course) throws Exception {
        while (true) {
            System.out.println("\n\n--------");
            System.out.println("Course: " + course.getName().toUpperCase());
            System.out.println("Totaltime: " + course.formatVisual());
            System.out.println("--------");
            System.out.println("\nSelect function");
            System.out.println("1. Track time in realtime");
            System.out.println("2. Add time manually");
            System.out.println("3. Edit tracked time");
            System.out.println("4. Select another course");
            System.out.println("x: Go to menu");
            String i = this.scanner.nextLine();
            switch (i) {
                case "1":
                    trackTimeScreenCli(user, course);
                case "2":
                    addTimeScreenCli(user, course);
                case "3":
                    editTotalTimeScreenCli(user, course);
                case "4":
                    selectCourseMenuCli(user);
                case "x":
                    menuScreenCli(user);
            }

        }

    }

    public void trackTimeScreenCli(User user, Course course) throws Exception {
        this.dbwriter.trackCourse(course);
        System.out.println("Total tracked time for user " + user.getName()
                + " on course " + course.getName());
        System.out.println(course.formatTime());
        selectedCourseScreenCli(user, course);
    }

    public void editTotalTimeScreenCli(User user, Course course) throws Exception {
        while (true) {
            System.out.println("Define totaltime (use format \"hh:mm:ss\") "
                    + "(empty string returns to menu)");
            String newTotalTime = this.scanner.nextLine();
            if (newTotalTime.equals("")) {
                selectedCourseScreenCli(user, course);
            }
            String[] timepieces = newTotalTime.split(":");
            try {
                Double h = Double.parseDouble(timepieces[0]);
                Double m = Double.parseDouble(timepieces[1]);
                Double s = Double.parseDouble(timepieces[2]);
                Double totalTime = (h * 60 * 60) + (m * 60) + s;
                //course.changeTime(totalTime);
                this.dbwriter.editTime(course, totalTime);
                selectedCourseScreenCli(user, course);
            } catch (Exception e) {
                System.out.println("Please try again!");
            }
        }
    }

    public void addTimeScreenCli(User user, Course course) throws Exception {
        while (true) {
            System.out.println("Time tracked so far: " + course.formatTime());
            System.out.println("Define time to be added (use format \"hh:mm:ss\") "
                    + "(empty string returns to menu)");
            String addedTime = this.scanner.nextLine();
            if (addedTime.equals("")) {
                selectedCourseScreenCli(user, course);
            }
            String[] timepieces = addedTime.split(":");
            try {
                Double h = Double.parseDouble(timepieces[0]);
                Double m = Double.parseDouble(timepieces[1]);
                Double s = Double.parseDouble(timepieces[2]);
                Double timeToAdd = (h * 60 * 60) + (m * 60) + s;
                //course.addTime(timeToAdd);
                this.dbwriter.addTime(course, timeToAdd);
                selectedCourseScreenCli(user, course);
            } catch (Exception e) {
                System.out.println("Please try again!");
            }
        }

    }

    public void summaryScreenCli(User user) throws Exception {
        boolean foundCourses = false;
        for (Course c : this.courses) {
            if (c.getUser().equals(user)) {
                foundCourses = true;
                System.out.println(c.getName());
                System.out.println("  " + c.formatVisual());
            }
        }
        if (!foundCourses) {
            System.out.println("No courses tracked!");
        }
        menuScreenCli(user);

    }

    public static void main(String[] args) throws Exception {

        StudyTimeTrackerUi st = new StudyTimeTrackerUi();
        st.startScreenCli();
        System.exit(0);

    }

}
