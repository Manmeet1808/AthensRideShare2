package edu.uga.cs.athensrideshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "SigningIn";

    private FirebaseAuth mAuth;
    private Button loginB;
    private EditText enterEmail;
    private EditText enterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        loginB = findViewById(R.id.loginb);
        enterEmail = findViewById(R.id.emailField);
        enterPassword = findViewById(R.id.passwordField);

        loginB.setOnClickListener(new LoginActivity.ButtonClickListener());

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currUser = mAuth.getCurrentUser();
    }

    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String userEmail = enterEmail.getText().toString();
            String userPassword = enterPassword.getText().toString();
            signIn(userEmail, userPassword);

        }
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn" + email);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, signinAttempt -> {
            if (signinAttempt.isSuccessful()) {
                Log.d(TAG, "signin is successful");
                FirebaseUser user = mAuth.getCurrentUser();
                updateUserInfo(user);
            } else {
                Log.w(TAG, "signin failed", signinAttempt.getException());
                Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                updateUserInfo(null);
            }
        });

    }

    public void updateUserInfo(FirebaseUser userAccount) {
        if (userAccount != null) {
            Toast.makeText(this, "Welcome Back!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, homeScreen2.class);
            startActivity(intent);
        } else {
            Toast.makeText(this,"User Email or Password is Incorrect",Toast.LENGTH_LONG).show();
        }
    }


}