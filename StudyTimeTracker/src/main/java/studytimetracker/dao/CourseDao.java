/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.dao;

import java.util.List;
import studytimetracker.domain.Course;

public interface CourseDao {
    Course create(Course course) throws Exception;

    Course find(String name);

    List<Course> getCourses();

}
