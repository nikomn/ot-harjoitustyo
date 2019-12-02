/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.dao;

import java.util.List;
import studytimetracker.domain.User;


public interface UserDao {

    User create(User user) throws Exception;

    User find(String username);

    List<User> getUsers();

}
