package edu.uga.cs.athensrideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "Sign in Activity";

    private FirebaseAuth mAuth;
    private Button register;

    private EditText firstNameText;
    private EditText lastNameText;
    private EditText emailText;
    private EditText passwordText;
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        firstNameText = findViewById(R.id.enterFirst);
        lastNameText = findViewById(R.id.enterLast);
        emailText = findViewById(R.id.regEmail);
        passwordText = findViewById(R.id.regPassword);
        register = findViewById(R.id.regB);

        register.setOnClickListener(new ButtonClickListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * Registers a user using the information they gave us in the app
     */
    private class ButtonClickListener implements View.OnClickListener {

        /**
         * When clicked, registers a user
         *
         * @param v the button view that has called the method
         */
        @Override
        public void onClick(View v) {
            //gets values from information entered
            String firstName = firstNameText.getText().toString();
            String lastName = lastNameText.getText().toString();
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            // Create new user object to be pushed into db
            //final User currUser = new User( firstName, lastName, email );
            //private User currUser = new User(null, email, null, firstName, null);
            createAccount(email, password, firstName);
        }
    }


    private void createAccount(String email, String password, String firstName) {
        Log.d(TAG, "account created - " + email);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Log.d(TAG, "user successfully created");
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = user.getUid();
                    database = FirebaseDatabase.getInstance();

                    DatabaseReference myRef = database.getReference("users");
                    myRef.child( userID ).setValue( user ).addOnSuccessListener( new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Show a quick confirmation
                                    Toast.makeText(getApplicationContext(), "Welcome " + firstName + "!", Toast.LENGTH_SHORT).show();

                                    // Clear the EditTexts for next use.
                                    firstNameText.setText("");
                                    lastNameText.setText("");
                                    emailText.setText("");
                                    passwordText.setText("");
                                }
                            })
                            .addOnFailureListener( new OnFailureListener() {
                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText( getApplicationContext(), "Registration failed.", Toast.LENGTH_SHORT).show();
                                }
                            });

                    updateUI(user);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "account not created", task.getException());
                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * This is called on a successful registration. When someone registers,
     * this method will update the UI to now go to the MainNavigationActivity and display a
     * successful registration message.
     *
     * @param account the current FirebaseUser's information
     */
    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"Registration was successful!",Toast.LENGTH_LONG).show();
            //startActivity(new Intent(this, MainNavigationActivity.class));

        }else {
            Toast.makeText(this,"Error: user was not found",Toast.LENGTH_LONG).show();
        }

    }


}