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

/**
 * This class implements the register function.
 */
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
     * This class registers a new user in the app with information input by the user.
     */
    private class ButtonClickListener implements View.OnClickListener {

        /**
         * Information input by the user is converted to a string format and then is sent to a method
         * in order to create the account.
         * @param v
         */
        @Override
        public void onClick(View v) {

            String firstName = firstNameText.getText().toString();
            String lastName = lastNameText.getText().toString();
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            // Creates a new user object in the database
            createAccount(email, password, firstName);
        }
    }

    /**
     * Creates the account with the user input.
     * @param email
     * @param password
     * @param firstName
     */
    private void createAccount(String email, String password, String firstName) {
        Log.d(TAG, "account created - " + email);

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
                                    //Welcome back message displays if the user signs in successfully
                                    Toast.makeText(getApplicationContext(), "Welcome " + firstName + "!", Toast.LENGTH_SHORT).show();

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
                    Log.w(TAG, "account not created", task.getException());
                    // Displays an Authentication Failed message to the user when the account fails to create
                    Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * When someone successfully registers, this updates the information in the Firebase database
     * @param account - the user information for this account
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