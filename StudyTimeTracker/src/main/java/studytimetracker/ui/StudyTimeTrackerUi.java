/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.ui;

import java.util.Scanner;
import static javafx.application.Application.launch;
import studytimetracker.domain.Tag;
import studytimetracker.domain.User;
import studytimetracker.ui.BetaGui;

/**
 *
 * @author nikoniem
 */
public class StudyTimeTrackerUi {

    public void startScreenCli(Scanner s) {
        System.out.println("Welcome to StudyTimeTracker beta ui");
        System.out.println("This ui is ment for testing purposes only and "
                + "should be replaced with a gui soon...");

        System.out.println("\n1. Log in");
        System.out.println("2. Create new user account");
        System.out.println("3. Start gui (early beta - unstable!)");
        System.out.println("\nSelect function");
        System.out.print("> ");
        String selection = "";
        while (!"1".equals(selection) && !"2".equals(selection) 
                && !"3".equals(selection)) {
            selection = s.nextLine();
            if (selection.equals("3")) {
                launch(BetaGui.class);
            } else if (selection.equals("1")) {
                loginScreenCli(s);
            } else if (selection.equals("2")) {
                newUserScreenCli();
            }
        }

    }

    public void loginScreenCli(Scanner s) {
        System.out.print("Username: ");
        String userName = s.nextLine();
        User user = new User(userName);
        addTagScreenCli(user, s);
    }

    public void newUserScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public void menuScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public void addTagScreenCli(User user, Scanner s) {

        System.out.print("Course name: ");
        String courseName = s.nextLine();
        Tag tagi = new Tag(courseName, user);
        System.out.println("Tracking time on course " + tagi.getName());
        tagi.startTimeTracking();
        System.out.println("Stop tracking by pressing Enter...");
        String testi = s.nextLine();
        tagi.stopTimeTracking();
        System.out.println("Total tracked time for user " + user.getName()
                + " on course " + tagi.getName());
        System.out.println(tagi.formatTime());

    }

    public void selectedTagScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public void summaryScreenCli() {
        System.out.println("Not implemented yet...");
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        StudyTimeTrackerUi st = new StudyTimeTrackerUi();
        st.startScreenCli(s);

        //tagi.addTime(120.0);
        System.exit(0);

    }

}
