package edu.uga.cs.athensrideshare;

import android.app.Application;

import java.util.ArrayList;

public class Global extends Application {
    private ArrayList<Request> acceptRequest = new ArrayList<Request>();
    private ArrayList<Offer> acceptOffer = new ArrayList<Offer>();



    public ArrayList<Request> getList() {
        return acceptRequest;
    }

    public void setList(Request r) {
        acceptRequest.add(r);
    }

    public ArrayList<Offer> getOList() {
        return acceptOffer;
    }

    public void setOList(Offer o) {
        acceptOffer.add(o);
    }


}
