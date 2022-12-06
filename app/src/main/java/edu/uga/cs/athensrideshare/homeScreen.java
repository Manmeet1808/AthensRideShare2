package edu.uga.cs.athensrideshare;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
//import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class homeScreen extends AppCompatActivity {

    public static final String TAG = "SigningIn";

    //private FirebaseAuth mAuth;
    //private Button logoutButton;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navView;
    private ActionBarDrawerToggle drawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen_layout);

        // assigning ID of the toolbar to a variable
        toolbar = findViewById( R.id.toolbar );

        // using toolbar as ActionBar
        //setSupportActionBar( toolbar );

        // Find our drawer view
        drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
        drawerToggle = setupDrawerToggle();

        drawerToggle.setDrawerIndicatorEnabled( true );
        drawerToggle.syncState();

        // Connect DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener( drawerToggle );

        // Find the drawer view
        navView = findViewById( R.id.nvView );
        navView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem( menuItem );
                    return true;
                });

        //find button
        //logoutButton = findViewById(R.id.logoutButton);

        //make listeners
        //logoutButton.setOnClickListener(new LogOutClickListener());

    }

    public void selectDrawerItem( MenuItem menuItem ) {
        Fragment fragment = null;

        // Create a new fragment based on the used selection in the nav drawer
        switch( menuItem.getItemId() ) {
            case R.id.review_requests:
                //fragment = new AddJobLeadFragment();
//                Intent switchActivityIntent = new Intent(this, ActualClass.class);
//                startActivity(switchActivityIntent);
                break;
            case R.id.review_offers:
                //fragment = new ReviewHistory2();
//                FragmentManager fragManager = getSupportFragmentManager();
//                fragManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();
//                Intent move = new Intent(this, ReviewHistory2.class);
//                startActivity(move);
                break;
            case R.id.accepted_request:
//                fragment = new HelpScreen();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();
                break;
            case R.id.accepted_offer:
//                fragment = new HelpScreen();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();
                break;
            case R.id.check_points:
//                fragment = new HelpScreen();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();
                break;
            case R.id.logout:
                Log.d(TAG, "Logged out");
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                return;
        }

        // Set up the fragment by replacing any existing fragment in the main activity
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace( R.id.fragmentContainerView, fragment).addToBackStack("main screen" ).commit();


        /*
        // this is included here as a possible future modification
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked( true );
        // Set action bar title
        setTitle( menuItem.getTitle());
         */

        // Close the navigation drawer
        drawerLayout.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_d,  R.string.close_d);
    }

    /**
     * This class defines the onClickListener class for the logout button.
     * Signs the current user out and returns to MainActivity.
     */
    private class LogOutClickListener implements View.OnClickListener {

        /**
         * Signs out the current user and returns to the MainActivity login/register screen.
         *
         * @param v the button view that has called the method
         */
        @Override
        public void onClick(View v) {
            Log.d(TAG, "Logged out");
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(v.getContext(), MainActivity.class);
            v.getContext().startActivity(intent);
        }
    }





}