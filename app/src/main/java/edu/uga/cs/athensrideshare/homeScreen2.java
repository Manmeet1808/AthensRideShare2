package edu.uga.cs.athensrideshare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeScreen2 extends AppCompatActivity {

    private static final String DEBUG_TAG = "homeScreen";

    private TextView signedInTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen2);

        Log.d( DEBUG_TAG, "homescreen.onCreate()" );

        Button newLeadButton = findViewById(R.id.button1);
        Button reviewLeadsButton = findViewById(R.id.button2);
        Button newRequestButton = findViewById(R.id.button4);
        Button reviewRequestButton = findViewById(R.id.button5);
        Button logoutButton = findViewById(R.id.button6);

        // Upon each button being clicked, the corresponding listener shall be called
        newLeadButton.setOnClickListener( new NewLeadButtonClickListener() );
        reviewLeadsButton.setOnClickListener( new ReviewLeadsButtonClickListener() );
        newRequestButton.setOnClickListener( new NewRequestButtonClickListener() );
        reviewRequestButton.setOnClickListener( new ReviewRequestButtonClickListener() );
        logoutButton.setOnClickListener( new LogOutClickListener() );

        // Setup a listener for a change in the sign in status (authentication status change)
        // when it is invoked, check if a user is signed in and update the UI text view string,
        // as needed.
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if( currentUser != null ) {
                    // User is signed in
                    Log.d(DEBUG_TAG, "onAuthStateChanged:signed_in:" + currentUser.getUid());
                    String userEmail = currentUser.getEmail();
                } else {
                    // User is signed out
                    Log.d( DEBUG_TAG, "onAuthStateChanged:signed_out" );
                }
            }
        });
    }

    private class NewLeadButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NewOffer.class);
            view.getContext().startActivity( intent );
        }
    }

    private class ReviewLeadsButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewOffer.class);
            view.getContext().startActivity(intent);
        }
    }

    private class NewRequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), NewRequest.class);
            view.getContext().startActivity( intent );
        }
    }

    private class ReviewRequestButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), ReviewRequest.class);
            view.getContext().startActivity(intent);
        }
    }

    private class LogOutClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);
        }
    }

}

