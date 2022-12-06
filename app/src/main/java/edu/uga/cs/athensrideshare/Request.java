package edu.uga.cs.athensrideshare;

/**
 * This class represents a single request, including:
 * name, age, number, location, destination, # of seats needed, date, time, social, fuel
 */
public class Request {
    private String key;
    private String name;
    private String age;
    private String number;
    private String startingLoc;
    private String destination;
    private String date;
    private String time;
    private String seatsNeed;
    private String social;
    private String fuel;


    public Request() {
        this.key = null;
        this.name  = null;
        this.age = null;
        this.number = null;
        this.startingLoc = null;
        this.destination = null;
        this.date = null;
        this.time = null;
        this.seatsNeed = null;
        this.social = null;
        this.fuel = null;
    }

    public Request(String name, String age, String number, String startingLoc, String destination, String date, String time, String seatsAvail, String social, String fuel) {
        this.key = null;
        this.name = name;
        this.age = age;
        this.number = number;
        this.startingLoc = startingLoc;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.seatsNeed = seatsAvail;
        this.social =  social;
        this.fuel = fuel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {return name;}

    public void setName(String name ) {this.name = name;}

    public String getAge() {return age;}

    public void setAge(String age ) {this.age = age;}

    public String getNumber() {return number;}

    public void setNumber(String number ) {this.number = number;}

    public String getStart() {return startingLoc;}

    public void setStart(String start ) {this.startingLoc = start;}

    public String getDestination() {return destination;}

    public void setDestination(String destination ) {this.destination = destination;}

    public String getDate() {return date;}

    public void setDate(String date ) {this.date = date;}

    public String getTime() {return time;}

    public void setTime(String time ) {this.time = time;}

    public String getSeats() {return seatsNeed;}

    public void setSeats(String seats ) {this.seatsNeed = seats;}

    public String getSocial() {return social;}

    public void setSocial(String social ) {this.social = social;}

    public String getFuel() {return fuel;}

    public void setFuel(String fuel ) {this.fuel = fuel;}

    public String toString() {return name + " " + age + " " + number + " " + startingLoc + " "
    + destination + " " + date + " " + time + " " + seatsNeed + " " + social + " " + fuel;}

}
