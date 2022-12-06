package edu.uga.cs.athensrideshare;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class acceptedRequest extends AppCompatActivity {

    public static final String DEBUG_TAG = "ReviewRequest";

    ListView ar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept);

        ArrayList<String> nl = (ArrayList<String>) getIntent().getSerializableExtra("list");

        Request pls = new Request(nl.get(1), nl.get(2), nl.get(3), nl.get(4), nl.get(5), nl.get(6), nl.get(7), nl.get(8),  nl.get(9), nl.get(10) );

        ((Global) this.getApplication()).setList(pls);

        ArrayList<Request> workk =  ((Global) this.getApplication()).getList();

        ar = findViewById(R.id.listOWork);

        RequestListAdapter acc = new RequestListAdapter(this, R.layout.accept_listview, workk);

        ar.setAdapter(acc);

    }
}