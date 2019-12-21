package studytimetracker.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import studytimetracker.dao.DBWriter;
import studytimetracker.domain.Course;
import studytimetracker.domain.User;


public class StudyTimeTrackerGui extends Application {

    private List<User> users;
    private List<Course> courses;
    private DBWriter dbwriter;
    private User loggedUser;
    private Course selectedCourse;
    private String timerStatus;

    private Stage window;
    private Scene baseScene;
    private GridPane basePane;
    private Scene scrollScene;
    private GridPane listPane;
    private ScrollPane scrollPaneBase;
    
    public boolean startCli;

    @Override
    public void start(Stage window) throws Exception {
        this.window = window;
        this.window.setTitle("StudyTimeTracker!");
        this.timerStatus = "Off";
        this.dbwriter = new DBWriter("userdb.txt", "coursedb.txt");
        this.courses = dbwriter.getCourses();
        this.users = dbwriter.getUsers();

        // Base Scene setup
        this.basePane = new GridPane();
        this.basePane.setPrefSize(800, 540);
        this.basePane.setAlignment(Pos.CENTER);
        this.basePane.setVgap(10);
        this.basePane.setHgap(10);
        this.basePane.setPadding(new Insets(20, 20, 20, 20));
        this.baseScene = new Scene(this.basePane);

        // Scroll Scene setup
        this.scrollPaneBase = new ScrollPane();
        this.listPane = new GridPane();
        this.scrollPaneBase.setPadding(new Insets(40, 40, 40, 40));
        this.listPane.setAlignment(Pos.CENTER);
        this.listPane.setVgap(10);
        this.listPane.setHgap(10);
        this.basePane.setPadding(new Insets(20, 20, 20, 20));
        this.scrollPaneBase.setContent(this.listPane);
        this.scrollScene = new Scene(this.scrollPaneBase);

        buildLoginScene();
        this.window.show();

    }

    public void buildLoginScene() {
        this.basePane.getChildren().clear();
        Label userNameLabel = new Label("Username");
        TextField usernameField = new TextField();
        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(180);
        Button newUserSceneButton = new Button("Create new user");
        newUserSceneButton.setMaxWidth(180);
        Button exitButton = new Button("Close program");
        exitButton.setMaxWidth(180);
        Label loginErrorLabel = new Label("");

        this.basePane.add(userNameLabel, 0, 0);
        this.basePane.add(usernameField, 0, 1, 3, 1);
        this.basePane.add(loginButton, 0, 2);
        this.basePane.add(newUserSceneButton, 1, 2);
        this.basePane.add(exitButton, 2, 2);
        this.basePane.add(loginErrorLabel, 0, 3, 3, 1);

        this.window.setScene(this.baseScene);

        loginButton.setOnAction((event) -> {
            if (logUserIn(usernameField.getText())) {
                buildMainMenuScene();
            } else {
                loginErrorLabel.setText("Username " + usernameField.getText() + " not found!");
            }

        });

        newUserSceneButton.setOnAction((event) -> {
            buildNewUserScene();
        });
        
        exitButton.setOnAction((event) -> {
            this.window.close();
        });
        
    }

    public void buildNewUserScene() {
        this.basePane.getChildren().clear();
        Label newUserNameLabel = new Label("Select a username");
        TextField newUserNameField = new TextField();
        Button createUserButton = new Button("Create user");
        Button backToLoginButton = new Button("Back to login");
        Label newUserErrorLabel = new Label("");

        this.basePane.add(newUserNameLabel, 0, 0, 2, 1);
        this.basePane.add(newUserNameField, 0, 1, 2, 1);
        this.basePane.add(createUserButton, 0, 2);
        this.basePane.add(backToLoginButton, 1, 2);
        this.basePane.add(newUserErrorLabel, 0, 3, 2, 1);

        this.window.setScene(this.baseScene);

        createUserButton.setOnAction((event) -> {
            String errorCheck = createNewUser(newUserNameField.getText());
            if (errorCheck.equals("")) {
                buildMainMenuScene();
            } else {
                newUserErrorLabel.setText(errorCheck);
            }

        });

        backToLoginButton.setOnAction((event) -> {
            buildLoginScene();
        });

    }

    public void buildMainMenuScene() {
        this.basePane.getChildren().clear();
        Button selectCourseButton = new Button("Select course");
        selectCourseButton.setMaxWidth(140);
        Button addCourseButton = new Button("Add new course");
        addCourseButton.setMaxWidth(140);
        Button showOverviewButton = new Button("Show overwiev");
        showOverviewButton.setMaxWidth(140);
        Button logOutButton = new Button("Log out");
        logOutButton.setMaxWidth(140);

        this.basePane.add(selectCourseButton, 0, 1);
        this.basePane.add(addCourseButton, 0, 2);
        this.basePane.add(showOverviewButton, 0, 3);
        this.basePane.add(logOutButton, 0, 4);

        this.window.setScene(this.baseScene);

        selectCourseButton.setOnAction((event) -> {
            buildCourseListScene();
        });

        addCourseButton.setOnAction((event) -> {
            buildNewCourseScene();
        });

        showOverviewButton.setOnAction((event) -> {
            buildSummaryScene();
        });

        logOutButton.setOnAction((event) -> {
            buildLoginScene();
        });

    }

    public void buildCourseListScene() {
        this.listPane.getChildren().clear();

        Label courseLabel = new Label("SELECT COURSE");
        courseLabel.setFont(new Font("Arial", 30));
        this.listPane.add(courseLabel, 0, 0);
        Button backToMenuButton = new Button("Back to menu");
        this.listPane.add(backToMenuButton, 1, 0);
        int index = 1;
        for (Course c : getUsersCourses(this.loggedUser)) {
            Button b = new Button(c.getName());
            b.wrapTextProperty().setValue(true);
            b.setMaxWidth(300);
            b.setMinWidth(300);
            b.setMinHeight(90);
            b.setMaxHeight(90);
            b.setOnAction((event) -> {
                int i = this.courses.indexOf(new Course(b.getText(), this.loggedUser));
                this.selectedCourse = this.courses.get(i);
                buildCourseMenuScene();
            });
            this.listPane.add(b, 0, index);
            index++;
        }

        this.window.setScene(this.scrollScene);

        backToMenuButton.setOnAction((event) -> {
            buildMainMenuScene();
        });

    }

    public void buildCourseMenuScene() {
        this.basePane.getChildren().clear();
        Double timetracked = this.selectedCourse.getTime();
        Double minutes = Math.floor(timetracked / 60);
        Double hours = Math.floor(minutes / 60);

        NumberAxis xAx = new NumberAxis();
        CategoryAxis yAx = new CategoryAxis();

        BarChart<Number, String> courseBarChart = new BarChart<>(xAx, yAx);
        courseBarChart.setTitle(this.selectedCourse.getName().toUpperCase() 
                + " (" + this.selectedCourse.formatTime() + ")");
        courseBarChart.setLegendVisible(false);
        XYChart.Series trackedTime = new XYChart.Series();
        trackedTime.getData().add(new XYChart.Data(hours.intValue(), this.selectedCourse.getName()));
        courseBarChart.getData().add(trackedTime);

        courseBarChart.setMinSize(500, 120);
        courseBarChart.setMaxSize(500, 120);

        this.basePane.add(courseBarChart, 0, 0);
        Button trackTimeSceneButton = new Button("Track time");
        trackTimeSceneButton.setMaxWidth(180);
        Button addTimeSceneButton = new Button("Add time manually");
        addTimeSceneButton.setMaxWidth(180);
        Button changeTimeSceneButton = new Button("Change tracked time");
        changeTimeSceneButton.setMaxWidth(180);
        Button mainMenuButton = new Button("Back to menu");
        mainMenuButton.setMaxWidth(180);

        this.basePane.add(trackTimeSceneButton, 0, 1);
        this.basePane.add(addTimeSceneButton, 0, 2);
        this.basePane.add(changeTimeSceneButton, 0, 3);
        this.basePane.add(mainMenuButton, 0, 4);

        this.window.setScene(this.baseScene);

        mainMenuButton.setOnAction((event) -> {
            buildMainMenuScene();
        });

        trackTimeSceneButton.setOnAction((event) -> {
            buildTimeTrackerScene();
        });

        addTimeSceneButton.setOnAction((event) -> {
            buildTimeAddingScene();
        });

        changeTimeSceneButton.setOnAction((event) -> {
            buildTimeChangingScene();
        });
    }

    public void buildTimeTrackerScene() {
        this.basePane.getChildren().clear();
        this.timerStatus = "Off";
        Label courseNameLabel = new Label(this.selectedCourse.getName());
        courseNameLabel.setFont(new Font(32.0));
        Label timerLabel = new Label("00:00:00");
        timerLabel.setFont(new Font(32.0));

        Button startButton = new Button("Start");
        Button stopButton = new Button("Stop");

        this.basePane.add(courseNameLabel, 0, 0, 2, 1);
        this.basePane.add(timerLabel, 0, 1, 2, 1);
        this.basePane.add(startButton, 0, 2);
        this.basePane.add(stopButton, 1, 2);

        this.window.setScene(this.baseScene);

        startButton.setOnAction((event) -> {
            this.timerStatus = "On";
            new AnimationTimer() {
                long pasttime = 0;
                int t = 0;

                @Override
                public void handle(long now) {
                    if (CurrentTimerStatus().equals("Off")) {
                        try {
                            addTrackedTime(this.t);
                            buildCourseMenuScene();
                        } catch (Exception e) {
                            System.out.println("Error");
                            buildCourseMenuScene();
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

                    timerLabel.setText(hoursFormat + ":" + minutesFormat + ":" + secondsFormat);

                    this.pasttime = now;
                }
            }.start();
        });

        stopButton.setOnAction((event) -> {
            this.timerStatus = "Off";
        });

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
                    buildCourseMenuScene();
                }

            } catch (Exception e) {
                errorLabel.setText("Error! Please try again!");
            }

        });

        backButton.setOnAction((event) -> {
            buildCourseMenuScene();
        });

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
                    buildCourseMenuScene();
                }

            } catch (Exception e) {
                errorLabel.setText("Error! Please try again!");
            }

        });

        backButton.setOnAction((event) -> {
            buildCourseMenuScene();
        });

    }

    public void buildSummaryScene() {
        this.listPane.getChildren().clear();

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

        Label infoLabel = new Label("SUMMARY OF TRACKED TIME");
        infoLabel.setFont(new Font("Arial", 30));
        this.listPane.add(infoLabel, 0, 0);
        this.listPane.add(courseBarChart, 0, 1);
        int index = 2;
        for (Course c : getUsersCourses(this.loggedUser)) {
            Label l = new Label(c.getName() + ": " + c.formatTime());
            this.listPane.add(l, 0, index);
            index++;
        }

        Button menuButton = new Button("Back to menu");
        this.listPane.add(menuButton, 0, index);

        this.window.setScene(this.scrollScene);

        menuButton.setOnAction((event) -> {
            buildMainMenuScene();
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

        this.window.setScene(this.baseScene);

        menuButton.setOnAction((event) -> {
            buildMainMenuScene();
        });

        createCourseButton.setOnAction((event) -> {
            String errorCheck = createNewCourse(newCourseNameField.getText(), this.loggedUser);
            if (errorCheck.equals("")) {
                buildMainMenuScene();
            } else {
                newCourseErrorLabel.setText(errorCheck);
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
        if (userName.length() < 2) {
            return "Username must contain atleast 2 charactes!";
        } else {
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

    }

    public String createNewCourse(String name, User user) {
        if (name.length() < 2) {
            return "Coursename must contain atleast 2 charactes!";
        } else {
            Course course = new Course(name, user);
            if (this.courses.contains(course)) {
                return "Course " + name + " exists! Please try another name!";
            } else {
                try {
                    this.dbwriter.addCourse(course);
                    return "";
                } catch (Exception e) {
                    return "Unexpected error while writing to database!";
                }
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
        launch(StudyTimeTrackerGui.class);
    }

}
