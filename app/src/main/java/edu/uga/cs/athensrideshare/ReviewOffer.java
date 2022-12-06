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

/**
 * This is an activity class that lists all of the available ride offers.
 */
public class ReviewOffer extends AppCompatActivity implements AddOfferDialogFragment.AddOfferDialogListener, EditOfferFragment.EditOfferListener {
    public static final String DEBUG_TAG = "ReviewOffer";

    private RecyclerView recyclerView;
    private offerRecyclerAdapter recyclerAdapter;

    private List<Offer> offerList;

    private FirebaseDatabase database;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        Log.d( DEBUG_TAG, "onCreate()" );

        super.onCreate( savedInstanceState );
        setContentView( R.layout.review_offer );

        recyclerView = findViewById( R.id.recyclerView );

        FloatingActionButton floatingButton = findViewById(R.id.floatingActionButton);
        floatingButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new AddOfferDialogFragment();
                newFragment.show( getSupportFragmentManager(), null);
            }
        });

        // initialize the offer list
        offerList = new ArrayList<Offer>();

        // use a linear layout manager for the recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new offerRecyclerAdapter( offerList, ReviewOffer.this );
        recyclerView.setAdapter( recyclerAdapter );

        // get a Firebase DB instance reference
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("offers");

        // Sets up a listener to receive a value for the database reference.
        // The listener is called by Firebase by executing its onDataChange method
        myRef.addValueEventListener( new ValueEventListener() {

            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ) {
                // Once we have a DataSnapshot object, we need to iterate over the elements and place them on our offer list
                offerList.clear();
                for( DataSnapshot postSnapshot: snapshot.getChildren() ) {
                    Offer offer = postSnapshot.getValue(Offer.class);
                    offer.setKey( postSnapshot.getKey() );
                    offerList.add( offer );
                    Log.d( DEBUG_TAG, "ValueEventListener: added: " + offer );
                    Log.d( DEBUG_TAG, "ValueEventListener: key: " + postSnapshot.getKey() );
                }

                Log.d( DEBUG_TAG, "ValueEventListener: notifying recyclerAdapter" );
                //will update only item changed
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled( @NonNull DatabaseError databaseError ) {
                System.out.println( "ValueEventListener: reading failed: " + databaseError.getMessage() );
            }
        } );
    }

    // Adds a new offer to the list of offers in Firebase
    public void addOffer(Offer offer) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("offers");


        myRef.push().setValue( offer )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                        recyclerView.post( new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.smoothScrollToPosition( offerList.size() -1 );
                            }
                        } );

                        Log.d( DEBUG_TAG, "Offer saved: " + offer );
                        // Show a quick confirmation
                        Toast.makeText(getApplicationContext(), "Ride Offer to " + offer.getDestination() + " has been created.",
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener( new OnFailureListener() {
                    @Override
                    public void onFailure( @NonNull Exception e ) {
                        Toast.makeText( getApplicationContext(), "Failed to create a ride offer to " + offer.getDestination(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * This updates an already existing ride offer. The update could either be
     * an edit to the offer or a deletion of the offer.
     * @param position
     * @param offer
     * @param action
     */
    public void updateOffer( int position, Offer offer, int action ) {
        if( action == EditOfferFragment.SAVE ) {
            Log.d( DEBUG_TAG, "Updating offer for: " + position + "(" + offer.getName() + ")" );

            // Update the recycler view to show the changes in the updated offer in that view
            recyclerAdapter.notifyItemChanged( position );

            // Update this offer in Firebase
            DatabaseReference ref = database.getReference().child( "offers" ).child( offer.getKey() );

            ref.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    dataSnapshot.getRef().setValue( offer ).addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( DEBUG_TAG, "updated offer for: " + offer.getName());
                            Toast.makeText(getApplicationContext(), offer.getName() + "'s Offer has been Updated",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {
                    Log.d( DEBUG_TAG, "failed to update offer");
                    Toast.makeText(getApplicationContext(), "Offer Failed to Update",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if( action == EditOfferFragment.DELETE ) {
            Log.d( DEBUG_TAG, "Deleting" +  offer.getName() + "'s Offer" );

            // removes a deleted ride offer from the list
            offerList.remove( position );

            // removes the ride offer from the view
            recyclerAdapter.notifyItemRemoved( position );

            // deletes the ride offer in Firebase
            DatabaseReference ref = database
                    .getReference()
                    .child( "offers" )
                    .child( offer.getKey() );

            ref.addListenerForSingleValueEvent( new ValueEventListener() {
                @Override
                public void onDataChange( @NonNull DataSnapshot dataSnapshot ) {
                    dataSnapshot.getRef().removeValue().addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d( DEBUG_TAG, "Offer Deleted");
                            Toast.makeText(getApplicationContext(), "Offer has been Deleted",
                                    Toast.LENGTH_SHORT).show();                        }
                    });
                }

                @Override
                public void onCancelled( @NonNull DatabaseError databaseError ) {
                    Log.d( DEBUG_TAG, "failed to delete offer");
                    Toast.makeText(getApplicationContext(), "Offer Failed to Delete",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
