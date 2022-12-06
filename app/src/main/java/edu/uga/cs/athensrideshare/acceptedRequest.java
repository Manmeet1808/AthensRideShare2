package edu.uga.cs.athensrideshare;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class acceptedRequest extends AppCompatActivity {

    public static final String DEBUG_TAG = "ReviewRequest";

    private RecyclerView recyclerView;
    private requestRecyclerAdapter recyclerAdapter;

    private List<Request> requestList;

    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_request);

        recyclerView = findViewById( R.id.recyclerView );
    }
}
