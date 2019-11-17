/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.ui;

import studytimetracker.domain.Tag;
import studytimetracker.domain.User;

/**
 *
 * @author nikoniem
 */
public class StudyTimeTrackerUi {
    
    public static void main(String[] args) {
        User user = new User("Testaaja");
        Tag tagi = new Tag("Ohjelmointitekniikka", user);
        tagi.addTime(120.0);
        System.out.println("Tulostus");
        System.out.println(tagi.formatTime());
        
    }
    
}
