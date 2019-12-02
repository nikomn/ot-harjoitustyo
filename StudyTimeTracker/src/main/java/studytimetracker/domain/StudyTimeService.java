/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.domain;

import studytimetracker.dao.CourseDao;
import studytimetracker.dao.UserDao;

/**
 *
 * @author nikoniem
 */
public class StudyTimeService {
    private UserDao userDao;
    private CourseDao courseDao;
    
    public StudyTimeService(UserDao userDao, CourseDao couresDao) {
        this.userDao = userDao;
        this.courseDao = courseDao;
    }
    
    public boolean createUser(String name) {
        User u = new User(name);
        try {
            this.userDao.create(u);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public boolean createCourse(String name, User user) {
        Course c = new Course(name, user);
        try {
            this.courseDao.create(c);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    public Course getCourse(String name) {
        return this.courseDao.find(name);
    }
    
    public void trackCourse(String name) {
        System.out.println("");
    }
    
    public void updateCourses() {
        System.out.println("1");
    }
    
    
    
}
