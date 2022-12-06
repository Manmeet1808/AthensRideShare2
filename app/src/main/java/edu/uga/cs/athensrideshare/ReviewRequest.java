package edu.uga.cs.athensrideshare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewRequest extends AppCompatActivity implements AddRequestDialogFragment.AddRequestDialogListener, EditRequestFragment.EditRequestListener {
    public static final String DEBUG_TAG = "ReviewRequest";

    private RecyclerView recyclerView;
    private requestRecyclerAdapter recyclerAdapter;

    private List<Request> requestList;

    private FirebaseDatabase database;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( DEBUG_TAG, "onCreate()" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.review_request );

        recyclerView = findViewById( R.id.recyclerView );

        FloatingActionButton floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddRequestDialogFragment();
                newFragment.show( getSupportFragmentManager(), null);
            }
        });



        requestList = new ArrayList<Request>();

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new requestRecyclerAdapter( requestList, ReviewRequest.this );
        recyclerView.setAdapter( recyclerAdapter );

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("requests");


        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, we need to iterate over the elements and place them on our job lead list.
                requestList.clear(); // clear the current content; this is inefficient!
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Request request = postSnapshot.getValue(Request.class);
                    request.setKey( postSnapshot.getKey() );
                    requestList.add( request );
                    Log.d( DEBUG_TAG, "ValueEventListener: added: " + request );
                    Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                }

                Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );
                //will update only item changed (it is smart enough to tell)
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );
    }

    public void addRequest(Request request) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("requests");


        myRef.push().setValue( request )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        recyclerView.post( new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition( requestList.size()-1 );
                            }
                        } );

                        Log.d( DEBUG_TAG, "Request saved: " + request );
                        // Show a quick confirmation
                        Toast.makeText(getApplicationContext(), "Ride Request to " + request.getDestination() + " has been created.",
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {
                        Toast.makeText( getApplicationContext(), "Failed to create a ride request to " + request.getDestination(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void updateRequest( int position, Request request, int action ) {
        if( action == EditRequestFragment.SAVE ) {
            Log.d( DEBUG_TAG, "Updating request for: " + position + "(" + request.getName() + ")" );

            // Update the recycler view to show the changes in the updated job lead in that view
            recyclerAdapter.notifyItemChanged( position );

            // Update this job lead in Firebase
            // Note that we are using a specific key (one child in the list)
            DatabaseReference ref = database.getReference().child( "requests" ).child( request.getKey() );

            // This listener will be invoked asynchronously, hence no need for an AsyncTask class, as in the previous apps
            // to maintain job leads.
            ref.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    dataSnapshot.getRef().setValue( request ).addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( DEBUG_TAG, "updated request for: " + request.getName());
                            Toast.makeText(getApplicationContext(), request.getName() + "'s Request has been Updated",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {
                    Log.d( DEBUG_TAG, "failed to update request");
                    Toast.makeText(getApplicationContext(), "Request Failed to Update",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if( action == EditRequestFragment.DELETE ) {
            Log.d( DEBUG_TAG, "Deleting" +  request.getName() + "'s Request" );

            requestList.remove( position );

            recyclerAdapter.notifyItemRemoved( position );

            DatabaseReference ref = database
                    .getReference()
                    .child( "requests" )
                    .child( request.getKey() );

            ref.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    dataSnapshot.getRef().removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( DEBUG_TAG, "Request Deleted");
                            Toast.makeText(getApplicationContext(), "Request has been Deleted",
                                    Toast.LENGTH_SHORT).show();                        }
                    });
                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {
                    Log.d( DEBUG_TAG, "failed to delete Request");
                    Toast.makeText(getApplicationContext(), "Request Failed to Delete",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
