/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.domain;
;

/**
 *
 * @author nikoniem
 */
public class Tag {
    
    private String name;
    private double totaltime;  // Time is stored in seconds, double used in order to be able to store longer timeperiods
    private User user;
    
    public Tag(String name, User user) {
        this.name = name;
        this.user = user;
        this.totaltime = 0.0;
    }
    
    public void addTime(double elapsedtime) {
        this.totaltime = this.totaltime + elapsedtime;
    }
    
    public void changeTime(double time) {
        this.totaltime = time;
    }
    
    public String formatTime() {
        Double minutes = Math.floor(this.totaltime / 60);
        Double hours = Math.floor(minutes / 60);
        minutes = minutes - (60 * hours);
        Double seconds = this.totaltime - (60 * minutes) - (60 * 60 * hours);
        
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
        
        
        return hoursFormat + ":" + minutesFormat + ":" + secondsFormat;
    }
    
    
}
