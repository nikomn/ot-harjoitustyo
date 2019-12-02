/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.dao;

import java.util.List;
import studytimetracker.domain.Course;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import studytimetracker.domain.User;

public class CourseFileDao implements CourseDao {

    private List<Course> courses;
    private String coursefile;

    public CourseFileDao(String coursefile) throws Exception {
        this.coursefile = coursefile;
        this.courses = new ArrayList<>();
        try {
            Scanner freader = new Scanner(new File("coursedb.txt"));
            while (freader.hasNextLine()) {
                String line = freader.nextLine();
                String[] splitline = line.split("£");
                User u = new User(splitline[2]);
                Course c = new Course(splitline[0], u);
                Double t = Double.parseDouble(splitline[1]);
                c.changeTime(t);
                this.courses.add(c);

            }
            freader.close();
        } catch (Exception e) {
            FileWriter fwriter = new FileWriter(new File(coursefile));
            fwriter.close();
        }
    }

    private void writeToFile() throws Exception {
        try {
            //FileWriter fwriter = new FileWriter(new File(this.userfile));
            PrintWriter fwriter = new PrintWriter(this.coursefile);
            //PrintWriter fwriter = new PrintWriter(new FileOutputStream(new File(this.userfile), true)); //huom. true

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

    @Override
    public Course create(Course course) throws Exception {
        this.courses.add(course);
        writeToFile();
        return course;
    }

    @Override
    public Course find(String name) {
        return this.courses.stream()
                .filter(c -> c.getName()
                .equals(name))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Course> getCourses() {
        return this.courses;
    }

}
