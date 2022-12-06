package edu.uga.cs.athensrideshare;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class acceptedOffer extends AppCompatActivity {

    public static final String DEBUG_TAG = "ReviewRequest";

    ListView ao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept);

        ArrayList<String> nl = (ArrayList<String>) getIntent().getSerializableExtra("myList");
//
        Offer val = new Offer(nl.get(1), nl.get(2), nl.get(3), nl.get(4), nl.get(5), nl.get(6), nl.get(7), nl.get(8),  nl.get(9) );
//
//
//
//
        ((Global) this.getApplication()).setOList(val);
//
        ArrayList<Offer> check =  ((Global) this.getApplication()).getOList();
//
//
//
//
//
        ao = findViewById(R.id.listOWork);
//
//
       OfferListAdapter adapt = new OfferListAdapter(this, R.layout.accept_listview, check);
//
        ao.setAdapter(adapt);


    }
}

