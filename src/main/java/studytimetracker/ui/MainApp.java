/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.ui;

import com.sun.java.swing.plaf.windows.resources.windows;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import studytimetracker.dao.DBWriter;
import studytimetracker.domain.Course;
import studytimetracker.domain.User;

/**
 *
 * @author nikoniem
 */
public class MainApp extends Application {

    private List<User> users;
    private List<Course> courses;
    private DBWriter dbwriter;
    private String timerStatus;
    private User loggedUser;
    private Course selectedCourse;
    private boolean courseIsSelected;
    private Stage window;
    private Timeline timeline;

    // Gui Components
    private Scene baseScene;
    private GridPane basePane;
    private Scene courseListScene, mainMenuScene;
    private Label userNameLabel, stopTimerLabel, loginErrorLabel, newUserNameLabel, newUserErrorLabel;
    private TextField usernameField, newUserNameField;
    private Button addCourseButton, showOverviewButton, changeTimeSceneButton, addTimeSceneButton, trackTimeSceneButton, loginSceneButton, loginButton, startTimerButton, stopTimerButton, pauseTimerButton, newUserSceneButton, createUserButton, backToLoginButton, selectCourseButton, selectedCourseButton, mainMenuButton;

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        this.dbwriter = new DBWriter("userdb.txt", "coursedb.txt");
        this.courses = dbwriter.getCourses();
        this.users = dbwriter.getUsers();
        this.mainMenuButton = new Button("Back to menu!");
        this.trackTimeSceneButton = new Button("Track time");
        this.addTimeSceneButton = new Button("Add time manually");
        this.changeTimeSceneButton = new Button("Change tracked time");
        this.showOverviewButton = new Button("Show overwiev");
        this.addCourseButton = new Button("Add new course");
        this.selectCourseButton = new Button("Select course");

        // Create base scene
        this.basePane = new GridPane();
        this.basePane.setPrefSize(500, 320);
        this.basePane.setAlignment(Pos.CENTER);
        this.basePane.setVgap(10);
        this.basePane.setHgap(10);
        this.basePane.setPadding(new Insets(20, 20, 20, 20));
        this.baseScene = new Scene(this.basePane);

        Scene loginScene = buildLoginScene();
        Scene newUserScene = buildNewUserScene();
        this.mainMenuScene = buildMainMenuScene();
        //Scene testScene = buildCourseMenuScene();

        this.window.setTitle("StudyTimeTracker!");
        this.window.setScene(loginScene);
        //this.window.setScene(testScene);
        this.window.show();

        this.loginButton.setOnAction((event) -> {
            if (logUserIn(this.usernameField.getText())) {
                this.courseIsSelected = false;
                this.window.setScene(this.mainMenuScene);
            } else {
                this.loginErrorLabel.setText("Username " + this.usernameField.getText() + " not found!");
            }

        });

        this.newUserSceneButton.setOnAction((event) -> {
            this.window.setScene(newUserScene);
        });

        this.backToLoginButton.setOnAction((event) -> {
            this.window.setScene(loginScene);
        });

        this.createUserButton.setOnAction((event) -> {
            String errorCheck = createNewUser(this.newUserNameField.getText());
            //System.out.println(errorCheck);
            if (errorCheck == "") {
                this.window.setScene(this.mainMenuScene);
            } else {
                this.newUserErrorLabel.setText(errorCheck);
            }
        });

        this.selectCourseButton.setOnAction((event) -> {
            Scene courseListScene = buildCourseListScene();
            this.window.setScene(courseListScene);
        });

        this.mainMenuButton.setOnAction((event) -> {
            this.window.setScene(this.mainMenuScene);
        });

        this.trackTimeSceneButton.setOnAction((event) -> {
            buildTimeTrackerScene();
        });

        this.addTimeSceneButton.setOnAction((event) -> {
            buildTimeAddingScene();
        });

        this.changeTimeSceneButton.setOnAction((event) -> {
            buildTimeChangingScene();
        });
        
        this.showOverviewButton.setOnAction((event) -> {
            buildSummaryScene();
        });
        
        this.addCourseButton.setOnAction((event) -> {
            buildNewCourseScene();
        });

    }

    public Scene buildLoginScene() {
        GridPane loginPane = new GridPane();
        this.userNameLabel = new Label("Username");
        this.usernameField = new TextField();
        this.loginButton = new Button("Login");
        this.newUserSceneButton = new Button("Create new user");
        this.loginErrorLabel = new Label("");

        loginPane.add(this.userNameLabel, 0, 0);
        loginPane.add(this.usernameField, 0, 1, 2, 1);
        loginPane.add(this.loginButton, 0, 2);
        loginPane.add(this.newUserSceneButton, 1, 2);
        loginPane.add(this.loginErrorLabel, 0, 3, 2, 1);

        loginPane.setPrefSize(500, 320);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setVgap(10);
        loginPane.setHgap(10);
        loginPane.setPadding(new Insets(20, 20, 20, 20));

        Scene loginScene = new Scene(loginPane);
        return loginScene;
    }

    public Scene buildNewUserScene() {
        GridPane newUserPane = new GridPane();
        this.newUserNameLabel = new Label("Select a username");
        this.newUserNameField = new TextField();
        this.createUserButton = new Button("Create user");
        this.backToLoginButton = new Button("Back to login");
        this.newUserErrorLabel = new Label("");

        newUserPane.add(this.newUserNameLabel, 0, 0, 2, 1);
        newUserPane.add(this.newUserNameField, 0, 1, 2, 1);
        newUserPane.add(this.createUserButton, 0, 2);
        newUserPane.add(this.backToLoginButton, 1, 2);
        newUserPane.add(this.newUserErrorLabel, 0, 3, 2, 1);

        newUserPane.setPrefSize(500, 320);
        newUserPane.setAlignment(Pos.CENTER);
        newUserPane.setVgap(10);
        newUserPane.setHgap(10);
        newUserPane.setPadding(new Insets(20, 20, 20, 20));

        Scene newUserScene = new Scene(newUserPane);
        return newUserScene;
    }

    public Scene buildMainMenuScene() {
        GridPane mainMenuPane = new GridPane();
        //this.selectCourseButton = new Button("Select course");
        this.selectCourseButton.setMaxWidth(140);
        //Button addCourseButton = new Button("Add new course");
        this.addCourseButton.setMaxWidth(140);
        //Button showOverviewButton = new Button("Show overwiev");
        this.showOverviewButton.setMaxWidth(140);
        Button logOutButton = new Button("Log out");
        logOutButton.setMaxWidth(140);

        mainMenuPane.add(this.selectCourseButton, 0, 1);
        mainMenuPane.add(this.addCourseButton, 0, 2);
        mainMenuPane.add(this.showOverviewButton, 0, 3);
        mainMenuPane.add(logOutButton, 0, 4);

        mainMenuPane.setPrefSize(500, 320);
        mainMenuPane.setAlignment(Pos.CENTER);
        mainMenuPane.setVgap(10);
        mainMenuPane.setHgap(10);
        mainMenuPane.setPadding(new Insets(20, 20, 20, 20));

        Scene mainMenuScene = new Scene(mainMenuPane);
        return mainMenuScene;
    }

    public Scene buildCourseListScene() {
        ScrollPane courseListPaneScroll = new ScrollPane();
        GridPane courseListPane = new GridPane();
        Label courseLabel = new Label("COURSES:");
        courseLabel.setFont(new Font("Arial", 30));
        courseListPane.add(courseLabel, 0, 0);
        int index = 1;
        for (Course c : getUsersCourses(this.loggedUser)) {
            Button b = new Button(c.getName());
            b.wrapTextProperty().setValue(true);
            b.setMaxWidth(300);
            b.setMinWidth(300);
            b.setMinHeight(90);
            b.setMaxHeight(90);
            b.setOnAction((event) -> {
                //System.out.println("Nappia " + b.getText() + " painettu!");
                int i = this.courses.indexOf(new Course(b.getText(), this.loggedUser));
                this.selectedCourse = this.courses.get(i);
                // System.out.println(this.selectedCourse.formatTime());
                Scene courseMenuScene = buildCourseMenuScene();
                this.window.setScene(courseMenuScene);
            });
            courseListPane.add(b, 0, index);
            index++;
        }

        courseListPaneScroll.setPrefSize(500, 320);
        courseListPane.setPrefSize(450, 320);
        courseListPane.setAlignment(Pos.CENTER);
        courseListPane.setVgap(10);
        courseListPane.setHgap(10);
        courseListPane.setPadding(new Insets(20, 20, 20, 20));

        courseListPaneScroll.setContent(courseListPane);

        Scene courseListScene = new Scene(courseListPaneScroll);
        return courseListScene;

    }

    public Scene buildCourseMenuScene() {
        //System.out.println("menu alustus");
        //System.out.println(this.selectedCourse.formatTime());
        Double timetracked = this.selectedCourse.getTime();
        //Double timetracked = 1000.0;
        Double minutes = Math.floor(timetracked / 60);
        Double hours = Math.floor(minutes / 60);

        NumberAxis xAx = new NumberAxis();
        CategoryAxis yAx = new CategoryAxis();

        BarChart<Number, String> courseBarChart = new BarChart<>(xAx, yAx);
        courseBarChart.setTitle("Time tracked (hours) on course " + this.selectedCourse.getName());
        courseBarChart.setLegendVisible(false);
        XYChart.Series trackedTime = new XYChart.Series();
        //trackedTime.getData().add(new XYChart.Data(120, "JYM"));
        trackedTime.getData().add(new XYChart.Data(hours.intValue(), this.selectedCourse.getName()));
        courseBarChart.getData().add(trackedTime);

        courseBarChart.setMinSize(500, 120);
        courseBarChart.setMaxSize(500, 120);

        GridPane courseMenuPane = new GridPane();
        courseMenuPane.add(courseBarChart, 0, 0);
        //Button b1 = new Button("Test Button!");
        //this.mainMenuButton = new Button("Back to menu!");
        //this.trackTimeSceneButton = new Button("Track time");
        this.trackTimeSceneButton.setMaxWidth(180);
        //Button addTimeSceneButton = new Button("Add time manually");
        this.addTimeSceneButton.setMaxWidth(180);
        //Button changeTimeSceneButton = new Button("Change tracked time");
        this.changeTimeSceneButton.setMaxWidth(180);
        // changeTimeSceneButton.wrapTextProperty().setValue(true);
        this.mainMenuButton.setMaxWidth(180);
        courseMenuPane.add(this.trackTimeSceneButton, 0, 1);
        courseMenuPane.add(this.addTimeSceneButton, 0, 2);
        courseMenuPane.add(this.changeTimeSceneButton, 0, 3);
        courseMenuPane.add(this.mainMenuButton, 0, 4);

        courseMenuPane.setPrefSize(500, 320);
        courseMenuPane.setAlignment(Pos.CENTER);
        courseMenuPane.setVgap(10);
        courseMenuPane.setHgap(10);
        courseMenuPane.setPadding(new Insets(20, 20, 20, 20));

        //Scene courseScene = new Scene(courseBarChart, 500, 120);
        Scene courseScene = new Scene(courseMenuPane);
        return courseScene;

    }

    public void buildTimeTrackerScene() {
        this.timerStatus = "Stopped";
        GridPane timeTrackingPane = new GridPane();
        timeTrackingPane.setPrefSize(500, 320);
        timeTrackingPane.setAlignment(Pos.CENTER);
        timeTrackingPane.setVgap(10);
        timeTrackingPane.setHgap(10);
        timeTrackingPane.setPadding(new Insets(20, 20, 20, 20));
        //String timerString = "00:00:00";
        Label courseNameLabel = new Label(this.selectedCourse.getName());
        courseNameLabel.setFont(new Font(32.0));
        Label timerLabel = new Label("00:00:00");
        timerLabel.setFont(new Font(32.0));

        Button startButton = new Button("Start");
        //Button pauseButton = new Button("Pause");
        Button stopButton = new Button("Stop");

        timeTrackingPane.add(courseNameLabel, 0, 0, 2, 1);
        timeTrackingPane.add(timerLabel, 0, 1, 2, 1);
        timeTrackingPane.add(startButton, 0, 2);
        //timeTrackingPane.add(pauseButton, 1, 1);
        timeTrackingPane.add(stopButton, 1, 2);

        Scene timerScene = new Scene(timeTrackingPane);

        this.window.setScene(timerScene);

        startButton.setOnAction((event) -> {
            this.timerStatus = "On";
            new AnimationTimer() {
                long pasttime = 0;
                int t = 0;

                @Override
                public void handle(long now) {
                    if (CurrentTimerStatus().equals("Stopped")) {
                        try {
                            addTrackedTime(this.t);
                        } catch (Exception e) {
                            System.out.println("Error");
                        }

                        stop();
                    }
                    if (CurrentTimerStatus().equals("Paused")) {
                        try {
                            addTrackedTime(this.t);
                        } catch (Exception e) {
                            System.out.println("Error");
                        }
                        stop();
                    }

                    if (now - pasttime < 1000000000) {
                        return;
                    }

                    this.t++;
                    Double minutes = Math.floor(this.t / 60);
                    Double hours = Math.floor(minutes / 60);
                    minutes = minutes - (60 * hours);
                    Double seconds = this.t - (60 * minutes) - (60 * 60 * hours);

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

                    // System.out.println("sec");
                    timerLabel.setText(hoursFormat + ":" + minutesFormat + ":" + secondsFormat);

                    this.pasttime = now;
                }
            }.start();
        });

        stopButton.setOnAction((event) -> {
            this.timerStatus = "Stopped";
            this.window.setScene(buildCourseMenuScene());
        });

        //pauseButton.setOnAction((event) -> {
        //this.timerStatus = "Paused";
        //});
    }

    public void buildTimeAddingScene() {
        this.basePane.getChildren().clear();
        Label courseNameLabel = new Label("Course: " + this.selectedCourse.getName());
        Label courseTimeLabel = new Label("Time tracked: " + this.selectedCourse.formatTime());
        Label hourLabel = new Label("Hours to add");
        TextField hourField = new TextField();
        Label minuteLabel = new Label("Minutes to add");
        TextField minuteField = new TextField();
        Label secondLabel = new Label("Seconds to add");
        TextField secondField = new TextField();
        Label errorLabel = new Label("");

        Button addButton = new Button("Add time");
        addButton.setMaxWidth(180);
        Button backButton = new Button("Back to menu");
        backButton.setMaxWidth(180);

        this.basePane.add(courseNameLabel, 0, 0);
        this.basePane.add(courseTimeLabel, 0, 1);
        this.basePane.add(hourLabel, 0, 2);
        this.basePane.add(hourField, 0, 3);
        this.basePane.add(minuteLabel, 0, 4);
        this.basePane.add(minuteField, 0, 5);
        this.basePane.add(secondLabel, 0, 6);
        this.basePane.add(secondField, 0, 7);
        this.basePane.add(addButton, 0, 8);
        this.basePane.add(backButton, 0, 9);
        this.basePane.add(errorLabel, 0, 10);

        this.window.setScene(this.baseScene);

        addButton.setOnAction((event) -> {
            Double hours = 0.0;
            Double minutes = 0.0;
            Double seconds = 0.0;

            try {
                if (!hourField.getText().equals("")) {
                    hours = Double.parseDouble(hourField.getText());
                }

                if (!minuteField.getText().equals("")) {
                    minutes = Double.parseDouble(minuteField.getText());
                }

                if (!secondField.getText().equals("")) {
                    seconds = Double.parseDouble(secondField.getText());
                }

                if (hours < 0 || minutes < 0 || seconds < 0) {
                    errorLabel.setText("Error! Please try again!");
                } else {
                    Double timeToAdd = (hours * 60 * 60) + (minutes * 60) + seconds;
                    this.dbwriter.addTime(this.selectedCourse, timeToAdd);
                    //this.window.setScene(this.mainMenuScene);
                    this.window.setScene(buildCourseMenuScene());
                }

            } catch (Exception e) {
                errorLabel.setText("Error! Please try again!");
            }

        });

        backButton.setOnAction((event) -> {
            this.window.setScene(buildCourseMenuScene());
        });

    }

    public GridPane makeDefaultPane() {
        GridPane newpane = new GridPane();
        newpane.setPrefSize(500, 320);
        newpane.setAlignment(Pos.CENTER);
        newpane.setVgap(10);
        newpane.setHgap(10);
        newpane.setPadding(new Insets(20, 20, 20, 20));
        return newpane;
    }

    public void buildTimeChangingScene() {
        this.basePane.getChildren().clear();
        Label courseNameLabel = new Label("Course: " + this.selectedCourse.getName());
        Label courseTimeLabel = new Label("Time tracked: " + this.selectedCourse.formatTime());
        Label hourLabel = new Label("Hours");
        TextField hourField = new TextField();
        Label minuteLabel = new Label("Minutes");
        TextField minuteField = new TextField();
        Label secondLabel = new Label("Seconds");
        TextField secondField = new TextField();
        Label errorLabel = new Label("NOTE! THESE VALUES WILL OVERWRITE CURRENT TRACKED TIME!");

        Button addButton = new Button("Change time");
        addButton.setMaxWidth(180);
        Button backButton = new Button("Back to menu");
        backButton.setMaxWidth(180);

        this.basePane.add(courseNameLabel, 0, 0);
        this.basePane.add(courseTimeLabel, 0, 1);
        this.basePane.add(hourLabel, 0, 2);
        this.basePane.add(hourField, 0, 3);
        this.basePane.add(minuteLabel, 0, 4);
        this.basePane.add(minuteField, 0, 5);
        this.basePane.add(secondLabel, 0, 6);
        this.basePane.add(secondField, 0, 7);
        this.basePane.add(addButton, 0, 8);
        this.basePane.add(backButton, 0, 9);
        this.basePane.add(errorLabel, 0, 10);

        this.window.setScene(this.baseScene);

        addButton.setOnAction((event) -> {
            Double hours = 0.0;
            Double minutes = 0.0;
            Double seconds = 0.0;

            try {
                if (!hourField.getText().equals("")) {
                    hours = Double.parseDouble(hourField.getText());
                }

                if (!minuteField.getText().equals("")) {
                    minutes = Double.parseDouble(minuteField.getText());
                }

                if (!secondField.getText().equals("")) {
                    seconds = Double.parseDouble(secondField.getText());
                }

                if (hours < 0 || minutes < 0 || seconds < 0) {
                    errorLabel.setText("Error! Please try again!");
                } else {
                    Double timeToAdd = (hours * 60 * 60) + (minutes * 60) + seconds;
                    this.dbwriter.editTime(this.selectedCourse, timeToAdd);
                    //this.window.setScene(this.mainMenuScene);
                    this.window.setScene(buildCourseMenuScene());
                }

            } catch (Exception e) {
                errorLabel.setText("Error! Please try again!");
            }

        });

        backButton.setOnAction((event) -> {
            this.window.setScene(buildCourseMenuScene());
        });
    }

    public void buildSummaryScene() {
        //this.basePane.getChildren().clear();
        GridPane summaryPane = new GridPane();
        summaryPane.setPrefSize(800, 520);
        summaryPane.setAlignment(Pos.CENTER);
        summaryPane.setVgap(10);
        summaryPane.setHgap(10);
        summaryPane.setPadding(new Insets(20, 20, 20, 20));

        NumberAxis xAx = new NumberAxis();
        CategoryAxis yAx = new CategoryAxis();

        BarChart<Number, String> courseBarChart = new BarChart<>(xAx, yAx);
        courseBarChart.setTitle("Summary");
        courseBarChart.setLegendVisible(false);
        XYChart.Series trackedTime = new XYChart.Series();
        for (Course c : getUsersCourses(this.loggedUser)) {
            Double timetracked = c.getTime();
            Double minutes = Math.floor(timetracked / 60);
            Double hours = Math.floor(minutes / 60);
            trackedTime.getData().add(new XYChart.Data(hours.intValue(), c.getName()));
        }

        courseBarChart.getData().add(trackedTime);
        
        ScrollPane courseListPaneScroll = new ScrollPane();
        courseListPaneScroll.setPrefSize(900, 600);
        
        Label infoLabel = new Label("SUMMARY OF TRACKED TIMES");
        infoLabel.setFont(new Font("Arial", 30));
        summaryPane.add(infoLabel, 0, 0);
        summaryPane.add(courseBarChart, 0, 1);
        int index = 2;
        for (Course c : getUsersCourses(this.loggedUser)) {
            Label l = new Label(c.getName() + ": " + c.formatTime());
            summaryPane.add(l, 0, index);
            index++;
        }
        
        Button menuButton = new Button("Back to menu");
        summaryPane.add(menuButton, 0, index);
        
        courseListPaneScroll.setContent(summaryPane);
        Scene summaryListScene = new Scene(courseListPaneScroll);
        this.window.setScene(summaryListScene);
        
        menuButton.setOnAction((event) -> {
            this.window.setScene(buildMainMenuScene());
        });

    }

    public void buildNewCourseScene() {
        this.basePane.getChildren().clear();
        Label newCourseNameLabel = new Label("Define course name");
        TextField newCourseNameField = new TextField();
        Button createCourseButton = new Button("Create course");
        Button menuButton = new Button("Back to menu");
        Label newCourseErrorLabel = new Label("");

        this.basePane.add(newCourseNameLabel, 0, 0, 2, 1);
        this.basePane.add(newCourseNameField, 0, 1, 2, 1);
        this.basePane.add(createCourseButton, 0, 2);
        this.basePane.add(menuButton, 1, 2);
        this.basePane.add(newCourseErrorLabel, 0, 3, 2, 1);

        //Scene newCourseScene = new Scene(this.basePane);
        this.window.setScene(this.baseScene);
        
        menuButton.setOnAction((event) -> {
            this.window.setScene(buildMainMenuScene());
        });
        
        createCourseButton.setOnAction((event) -> {
            if (newCourseNameField.getText().equals("")) {
                newCourseErrorLabel.setText("Error! Please try again!");
            } else {
                try {
                    Course c = new Course(newCourseNameField.getText(), this.loggedUser);
                    this.dbwriter.addCourse(c);
                    this.window.setScene(buildMainMenuScene());
                } catch (Exception e) {
                    newCourseErrorLabel.setText("Error! Please try again!");
                }
            }
        });
        
        
    }

    public boolean logUserIn(String userName) {
        User user = new User(userName);
        if (this.users.contains(user)) {
            this.loggedUser = user;
            return true;
        } else {
            return false;
        }
    }

    public String createNewUser(String userName) {
        User user = new User(userName);
        if (this.users.contains(user)) {
            return "Username " + userName + " exists! Please try another name!";
        } else {
            try {
                this.dbwriter.addUser(user);
                this.loggedUser = user;
                return "";
            } catch (Exception e) {
                return "Unexpected error while writing to database!";
            }

        }
    }
    
    

    public List<Course> getUsersCourses(User user) {
        List<Course> courseList = new ArrayList<>();
        int index = 0;
        while (index < this.courses.size()) {
            if (this.courses.get(index).getUser().equals(user)) {
                courseList.add(this.courses.get(index));
            }
            index++;
        }

        return courseList;
    }

    public String CurrentTimerStatus() {
        return this.timerStatus;
    }

    public void addTrackedTime(int time) throws Exception {
        Double timeToAdd = 0.0 + time;
        this.dbwriter.addTime(this.selectedCourse, timeToAdd);
    }

    public static void main(String[] args) {
        launch(MainApp.class);
    }

}