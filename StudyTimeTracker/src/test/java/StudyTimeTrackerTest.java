/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import studytimetracker.domain.Course;
import studytimetracker.domain.User;

/**
 *
 * @author nikoniem
 */
public class StudyTimeTrackerTest {
    
    User testuser;
    Course testtag;

    @Before
    public void setUp() {
        testuser = new User("test");
        testtag = new Course("test", testuser);
    }
    
    
    @Test
    public void timeFormatIsCorrectAfterAtBeginning() {

        assertEquals("00:00:00", testtag.formatTime());

    }
    @Test
    public void timeFormatIsCorrectAfterAdd() {
        testtag.addTime(120);

        assertEquals("00:02:00", testtag.formatTime());

    }
}
