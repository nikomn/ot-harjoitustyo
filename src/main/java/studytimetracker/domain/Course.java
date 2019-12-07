/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studytimetracker.domain;
;

import java.util.Objects;

/**
 *
 * @author nikoniem
 */
public class Course {
    
    private String name;
    private double totaltime;  // Time is stored in seconds, double used in order to be able to store longer timeperiods
    private User user;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        hash = 79 * hash + Objects.hashCode(this.user);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Course other = (Course) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        return true;
    }
    private double startTime;
    
    public Course(String name, User user) {
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
    
    public void startTimeTracking() {
        System.out.println("Total time so far... " + this.totaltime);
        this.startTime = System.currentTimeMillis();
    }
    
    public void stopTimeTracking() {
        double endtime = System.currentTimeMillis();
        System.out.println("Tracked time " + ((endtime - this.startTime) / 1000));
        this.totaltime = this.totaltime + ((endtime - this.startTime) / 1000);
        this.startTime = 0.0;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getTime() {
        return this.totaltime;
    }
    
    public User getUser() {
        return this.user;
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
    
    public String formatVisual() {
        Double minutes = Math.floor(this.totaltime / 60);
        Double hours = Math.floor(minutes / 60);
        
        String visualTime = "";
        
        if (hours < 1) {
            visualTime = "|";
        } else {
            for (int i = 0; i < hours.intValue(); i++) {
                visualTime = visualTime + "#"; 
            }
        }
        
        return visualTime + " (" + formatTime() + ")";
    }
    
    
}
