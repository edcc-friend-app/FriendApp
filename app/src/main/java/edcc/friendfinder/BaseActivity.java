package edcc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;


/**
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0
 */

public abstract class BaseActivity extends AppCompatActivity {


    //fields
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;
    String userId; //needed in child classes

    /**
     * Android onCreate method.
     *
     * @param savedInstanceState the class state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userId = user.getUid();
                    setUpDataListeners();
                } else {
                    userId = null;
                    startActivityForResult(AuthUI.getInstance()
                            .createSignInIntentBuilder().build(), Extras.REQUEST_AUTH);
                }
            }
        };

    }

    /**
     * On pause, stop listening for authentication changes.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    /**
     * On resume, start listening for authentication changes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (authListener != null) {
            auth.addAuthStateListener(authListener);
        }
    }

    /**
     * Handles the return data from the AuthUI activity.
     *
     * @param requestCode the code sent
     * @param resultCode  the code returned
     * @param data        the data returned
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Extras.REQUEST_AUTH) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in!",
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in canceled",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    /**
     * Signs out of Firebase.
     */
    void signOut() {
        auth.signOut();
    }

    /**
     * The abstract method to handle setting up data listeners after authentication has been
     * established. This method must be overridden by all child activities.
     */
    protected abstract void setUpDataListeners();
}
