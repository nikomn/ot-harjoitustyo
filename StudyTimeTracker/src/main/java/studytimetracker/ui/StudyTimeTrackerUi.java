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
import studytimetracker.dao.CourseFileDao;
import studytimetracker.dao.DBWriter;
import studytimetracker.dao.UserFileDao;
import studytimetracker.domain.Course;
import studytimetracker.domain.StudyTimeService;
import studytimetracker.domain.User;
import studytimetracker.ui.BetaGui;

/**
 *
 * @author nikoniem
 */
public class StudyTimeTrackerUi {

    private List<User> users;
    private List<Course> courses;
    private StudyTimeService studytimeservice;
    private Scanner scanner;
    private DBWriter dbwriter;

    public StudyTimeTrackerUi() throws Exception {
        this.scanner = new Scanner(System.in);
        this.dbwriter = new DBWriter();
        this.courses = dbwriter.getCourses();
        this.users = dbwriter.getUsers();
        //this.users = new ArrayList<>();
        //UserFileDao userDao = new UserFileDao("userdb.txt");
        //CourseFileDao courseDao = new CourseFileDao("coursedb.txt");
        //this.users = userDao.getUsers();
        //this.courses = courseDao.getCourses();
        //this.studytimeservice = new StudyTimeService(userDao, courseDao);

    }

    public void startScreenCli() throws Exception {
        System.out.println("Welcome to StudyTimeTracker beta ui");
        System.out.println("This ui is ment for testing purposes only and "
                + "should be replaced with a gui soon...");

        System.out.println("\n1. Log in");
        System.out.println("2. Create new user account");
        System.out.println("3. Start gui (early beta - unstable!)");
        System.out.println("x. Close program");
        System.out.println("\nSelect function");
        System.out.println("> ");
        String selection = "";
        while (!"1".equals(selection) && !"2".equals(selection)
                && !"3".equals(selection) && !"x".equals(selection)) {
            selection = this.scanner.nextLine();
            if (selection.equals("3")) {
                launch(BetaGui.class);
            } else if (selection.equals("1")) {
                loginScreenCli();
            } else if (selection.equals("2")) {
                newUserScreenCli();
            } else if (selection.equals("x")) {
                System.exit(0);
                //break;
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
                addCourseScreenCli(user);
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
                //this.users.add(user);
                //this.studytimeservice.createUser(userName);
                this.dbwriter.addUser(user);
                userCreated = true;
                addCourseScreenCli(user);
            }
        }

    }

    public void menuScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public void addCourseScreenCli(User user) throws Exception {
        boolean programrunning = true;
        while (programrunning) {
            System.out.println("Welcome " + user.getName() + "!");
            System.out.println("Course name (use empty string to return to start screen): ");
            String courseName = this.scanner.nextLine();
            if (courseName.isEmpty()) {
                //System.exit(0);
                programrunning = false;
                startScreenCli();
            }
            Course course = new Course(courseName, user);
            //this.courses.indexOf(course);
            if (!this.courses.contains(course)) {
                //this.studytimeservice.createCourse(courseName, user);
                this.dbwriter.addCourse(course);
            }
            course = this.courses.get(this.courses.indexOf(course));

            //System.out.println("Tracking time on course " + course.getName());
            //course.startTimeTracking();
            this.dbwriter.trackCourse(course);
            //System.out.println("Stop tracking by pressing Enter...");
            //String testi = this.scanner.nextLine();
            //course.stopTimeTracking();
            System.out.println("Total tracked time for user " + user.getName()
                    + " on course " + course.getName());
            System.out.println(course.formatTime());
        }

    }

    public void selectedCourseScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public void summaryScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public static void main(String[] args) throws Exception {

        //UserFileDao userDao = new UserFileDao("userdb.txt");
        //userDao.create(new User("testaaja!"));
        //StudyTimeService sts = new StudyTimeService(userDao);
        //Scanner s = new Scanner(System.in);
        StudyTimeTrackerUi st = new StudyTimeTrackerUi();
        //st.users.add(new User("test"));
        st.startScreenCli();

        //tagi.addTime(120.0);
        System.exit(0);

    }

}
