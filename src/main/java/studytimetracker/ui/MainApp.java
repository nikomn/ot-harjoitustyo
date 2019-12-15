/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.ui;

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
    private boolean timerOn;
    private User loggedUser;

    // Gui Components
    private Scene courseListScene;
    private Label userNameLabel, stopTimerLabel, loginErrorLabel, newUserNameLabel, newUserErrorLabel;
    private TextField usernameField, newUserNameField;
    private Button loginSceneButton, loginButton, startTimerButton, stopTimerButton, pauseTimerButton, newUserSceneButton, createUserButton, backToLoginButton, selectCourseButton, selectedCourseButton;

    @Override
    public void start(Stage window) throws Exception {
        this.dbwriter = new DBWriter("userdb.txt", "coursedb.txt");
        this.courses = dbwriter.getCourses();
        this.users = dbwriter.getUsers();

        Scene loginScene = buildLoginScene();
        Scene newUserScene = buildNewUserScene();
        Scene mainMenuScene = buildMainMenuScene();
        Scene testScene = buildCourseMenuScene();

        window.setTitle("StudyTimeTracker!");
        window.setScene(loginScene);
        //window.setScene(testScene);
        window.show();

        this.loginButton.setOnAction((event) -> {
            if (logUserIn(this.usernameField.getText())) {
                window.setScene(mainMenuScene);
            } else {
                this.loginErrorLabel.setText("Username " + this.usernameField.getText() + " not found!");
            }

        });

        this.newUserSceneButton.setOnAction((event) -> {
            window.setScene(newUserScene);
        });

        this.backToLoginButton.setOnAction((event) -> {
            window.setScene(loginScene);
        });

        this.createUserButton.setOnAction((event) -> {
            String errorCheck = createNewUser(this.newUserNameField.getText());
            //System.out.println(errorCheck);
            if (errorCheck == "") {
                window.setScene(mainMenuScene);
            } else {
                this.newUserErrorLabel.setText(errorCheck);
            }
        });

        this.selectCourseButton.setOnAction((event) -> {
            Scene courseListScene = buildCourseListScene();
            window.setScene(courseListScene);
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
        this.selectCourseButton = new Button("Select course");
        this.selectCourseButton.setMaxWidth(140);
        Button addCourseButton = new Button("Add new course");
        addCourseButton.setMaxWidth(140);
        Button showOverviewButton = new Button("Show overwiev");
        showOverviewButton.setMaxWidth(140);
        Button logOutButton = new Button("Log out");
        logOutButton.setMaxWidth(140);

        mainMenuPane.add(this.selectCourseButton, 0, 1);
        mainMenuPane.add(addCourseButton, 0, 2);
        mainMenuPane.add(showOverviewButton, 0, 3);
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
                System.out.println("Nappia " + b.getText() + " painettu!"); 
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
        NumberAxis xAx = new NumberAxis();
        CategoryAxis yAx = new CategoryAxis();
        BarChart<Number, String> courseBarChart = new BarChart<>(xAx, yAx);
        courseBarChart.setTitle("Time tracked (hours)");
        courseBarChart.setLegendVisible(false);
        XYChart.Series trackedTime = new XYChart.Series();
        trackedTime.getData().add(new XYChart.Data(120, "JYM"));
        courseBarChart.getData().add(trackedTime);
        
        courseBarChart.setMinSize(500, 120);
        courseBarChart.setMaxSize(500, 120);
        
        GridPane courseMenuPane = new GridPane();
        Button b1 = new Button("Test Button!");
        courseMenuPane.add(b1, 0, 0);
        courseMenuPane.add(courseBarChart, 0, 1);
        
        courseMenuPane.setPrefSize(500, 320);
        courseMenuPane.setAlignment(Pos.CENTER);
        courseMenuPane.setVgap(10);
        courseMenuPane.setHgap(10);
        courseMenuPane.setPadding(new Insets(20, 20, 20, 20));

        //Scene courseScene = new Scene(courseBarChart, 500, 120);
        Scene courseScene = new Scene(courseMenuPane);
        return courseScene;

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

    public static void main(String[] args) {
        launch(MainApp.class);
    }

}
