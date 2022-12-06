package edu.uga.cs.athensrideshare;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewRequest extends AppCompatActivity {

    public static final String DEBUG_TAG = "NewRequest";

    private EditText namesText;
    private EditText agesText;
    private EditText numbersText;
    private EditText startsText;
    private EditText destinationsText;
    private EditText timesText;
    private EditText Dates;
    private EditText seatssText;
    private EditText socialsText;
    private EditText fuelsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_requests);

        namesText = findViewById( R.id.namesText);
        agesText = findViewById( R.id.agesText);
        numbersText = findViewById( R.id.phonesText);
        startsText = findViewById( R.id.meetingsText);
        destinationsText = findViewById( R.id.destinationsText);
        Dates = findViewById( R.id.datesText);
        timesText = findViewById(R.id.timesText);
        seatssText = findViewById(R.id.seatssText);
        socialsText = findViewById(R.id.socialsText);
        fuelsText = findViewById(R.id.fuelsText);

        Button saveButton = findViewById(R.id.button);

        saveButton.setOnClickListener( new ButtonClickListener()) ;
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = namesText.getText().toString();
            String age = agesText.getText().toString();
            String num = numbersText.getText().toString();
            String start = startsText.getText().toString();
            String dest = destinationsText.getText().toString();
            String date = Dates.getText().toString();
            String time = timesText.getText().toString();
            String seat = seatssText.getText().toString();
            String social = socialsText.getText().toString();
            String fuel = fuelsText.getText().toString();

            final Request request = new Request(name, age, num, start, dest, date, time, seat, social, fuel );

            // Add a new element (JobLead) to the list of job leads in Firebase.
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("requests");

            // First, a call to push() appends a new node to the existing list (one is created
            // if this is done for the first time).  Then, we set the value in the newly created
            // list node to store the new job lead.
            // This listener will be invoked asynchronously, as no need for an AsyncTask, as in
            // the previous apps to maintain job leads.
            myRef.push().setValue( request )
                    .addOnSuccessListener( new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Show a quick confirmation
                            Toast.makeText(getApplicationContext(), "Request created for " + request.getName(),
                                    Toast.LENGTH_SHORT).show();

                            // Clear the EditTexts for next use.
                            namesText.setText("");
                            agesText.setText("");
                            numbersText.setText("");
                            startsText.setText("");
                            destinationsText.setText("");
                            Dates.setText("");
                            timesText.setText("");
                            seatssText.setText("");
                            socialsText.setText("");
                            fuelsText.setText("");
                        }
                    })
                    .addOnFailureListener( new OnFailureListener() {
                        @Override
                        public void onFailure( @NonNull Exception e ) {
                            Toast.makeText( getApplicationContext(), "Failed to create an Request for " + request.getName(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        Log.d( DEBUG_TAG, "NewRequest.onResume()" );
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d( DEBUG_TAG, "NewRequest.onPause()" );
        super.onPause();
    }


}

