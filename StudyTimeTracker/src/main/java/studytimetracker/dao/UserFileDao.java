/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import studytimetracker.domain.User;

/**
 *
 * @author nikoniem
 */
public class UserFileDao implements UserDao {

    private List<User> users;
    private String userfile;

    public UserFileDao(String userfile) throws Exception {
        this.userfile = userfile;
        this.users = new ArrayList<>();
        try {
            Scanner freader = new Scanner(new File("userdb.txt"));
            while (freader.hasNextLine()) {
                String line = freader.nextLine();
                User u = new User(line);
                this.users.add(u);
                
            }
            freader.close();
        } catch (Exception e) {
            FileWriter fwriter = new FileWriter(new File(userfile));
            fwriter.close();
        }
    }

    private void writeToFile() throws Exception {
        try {
            //FileWriter fwriter = new FileWriter(new File(this.userfile));
            PrintWriter fwriter = new PrintWriter(this.userfile);
            //PrintWriter fwriter = new PrintWriter(new FileOutputStream(new File(this.userfile), true)); //huom. true

            for (User user : this.users) {
                System.out.println(user.getName());
                //fwriter.write(user.getName() + "\n");
                fwriter.println(user.getName());
            }
            fwriter.close();
        } catch (Exception e) {
            System.out.println("Unexpected error!");
        }

    }

    @Override
    public User create(User user) throws Exception {
        this.users.add(user);
        writeToFile();
        return user;
    }

    @Override
    public User find(String username) {
        return this.users.stream()
                .filter(u -> u.getName()
                .equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsers() {
        return this.users;
    }

}
