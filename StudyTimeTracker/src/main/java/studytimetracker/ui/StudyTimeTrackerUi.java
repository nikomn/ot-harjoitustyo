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
import studytimetracker.domain.Course;
import studytimetracker.domain.User;
import studytimetracker.ui.BetaGui;

/**
 *
 * @author nikoniem
 */
public class StudyTimeTrackerUi {

    private List<User> users;

    public StudyTimeTrackerUi() {
        this.users = new ArrayList<>();
    }

    public void startScreenCli(Scanner s) {
        System.out.println("Welcome to StudyTimeTracker beta ui");
        System.out.println("This ui is ment for testing purposes only and "
                + "should be replaced with a gui soon...");

        System.out.println("\n1. Log in");
        System.out.println("2. Create new user account");
        System.out.println("3. Start gui (early beta - unstable!)");
        System.out.println("\nSelect function");
        System.out.println("> ");
        String selection = "";
        while (!"1".equals(selection) && !"2".equals(selection)
                && !"3".equals(selection)) {
            selection = s.nextLine();
            if (selection.equals("3")) {
                launch(BetaGui.class);
            } else if (selection.equals("1")) {
                loginScreenCli(s);
            } else if (selection.equals("2")) {
                newUserScreenCli(s);
            }
        }

    }

    public void loginScreenCli(Scanner s) {
        boolean userExists = false;
        while (!userExists) {
            System.out.println("");
            System.out.println("Username: ");
            String userName = s.nextLine();
            User user = new User(userName);
            if (this.users.contains(user)) {
                userExists = true;
                addCourseScreenCli(user, s);
            } else {
                System.out.println("Sorry, no such user");

            }
        }

    }

    public void newUserScreenCli(Scanner s) {
        boolean userCreated = false;
        while (!userCreated) {
            System.out.println("Define new username: ");
            String userName = s.nextLine();
            User user = new User(userName);
            if (this.users.contains(user)) {
                System.out.println("Sorry, username " + userName
                        + " is already in use! Please select some other username.");
            } else {
                this.users.add(user);
                addCourseScreenCli(user, s);
            }
        }

    }

    public void menuScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public void addCourseScreenCli(User user, Scanner s) {
        System.out.println("Welcome " + user.getName() + "!");
        System.out.println("Course name: ");
        String courseName = s.nextLine();
        Course tagi = new Course(courseName, user);
        System.out.println("Tracking time on course " + tagi.getName());
        tagi.startTimeTracking();
        System.out.println("Stop tracking by pressing Enter...");
        String testi = s.nextLine();
        tagi.stopTimeTracking();
        System.out.println("Total tracked time for user " + user.getName()
                + " on course " + tagi.getName());
        System.out.println(tagi.formatTime());

    }

    public void selectedCourseScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public void summaryScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        StudyTimeTrackerUi st = new StudyTimeTrackerUi();
        st.users.add(new User("test"));
        st.startScreenCli(s);

        //tagi.addTime(120.0);
        System.exit(0);

    }

}
