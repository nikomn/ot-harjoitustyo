/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import studytimetracker.dao.DBWriter;
import studytimetracker.domain.Course;
import studytimetracker.domain.User;

/**
 *
 * @author nikoniem
 */
public class StudyTimeTrackerTest {
    
    User testuser;
    Course testcourse;
    DBWriter dbwriter;

    @Before
    public void setUp() {
        testuser = new User("test");
        testcourse = new Course("test", testuser);
        try {
            dbwriter = new DBWriter("testusers.txt", "testcourses.txt");
            dbwriter.addUser(testuser);
            dbwriter.addCourse(testcourse);
        } catch (Exception e) {
            System.out.println("ERRROR!");
        }
        
        
    }
    
    
    @Test
    public void timeFormatIsCorrectAfterAtBeginning() {

        assertEquals("00:00:00", testcourse.formatTime());

    }
    @Test
    public void timeFormatIsCorrectAfterAdd() {
        testcourse.addTime(120);

        assertEquals("00:02:00", testcourse.formatTime());

    }
    
    @Test
    public void userGetNameReturnsCorrectValue() {

        assertEquals("test", testuser.getName());

    }
    
    @Test
    public void addingUsersToDbWorks() {
        List<User> users = dbwriter.getUsers();
        
        assertEquals(testuser, users.get(0));
    }
    
    @Test
    public void addingCoursesToDbWorks() {
        List<Course> courses = dbwriter.getCourses();
        
        assertEquals(testcourse, courses.get(0));
    }
}
