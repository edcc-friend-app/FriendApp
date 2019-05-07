package edcc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Base activity class used for authentication in all child activity classes.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */

public abstract class BaseActivity extends AppCompatActivity {
    String userId; //needed in child classes
    //fields
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authListener;

    private DatabaseReference usersRef;

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
                    usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
                    setUpDataListeners();
                } else {
                    userId = null;
                    startActivity(new Intent(BaseActivity.this, LoginActivity.class));
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
//        if (requestCode == Extras.REQUEST_AUTH) {
//            if (resultCode == RESULT_OK) {
//                Toast.makeText(this, "Signed in!",
//                        Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "Sign in canceled",
//                        Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        }
    }

    /**
     * Signs out of Firebase.
     */
    void signOut() {
        updateUserStatus("offline");
        auth.signOut();
        startActivity(new Intent(BaseActivity.this, LoginActivity.class));
        finish();
    }

    /**
     * The abstract method to handle setting up data listeners after authentication has been
     * established. This method must be overridden by all child activities.
     */
    protected abstract void setUpDataListeners();

    private void updateUserStatus(String state) {
        String saveCurrentDate, saveCurrentTime;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        Map currentStateMap = new HashMap();

        currentStateMap.put("time", saveCurrentTime);
        currentStateMap.put("date", saveCurrentDate);
        currentStateMap.put("type", state);

        usersRef.child(userId).child("user_state")
                .updateChildren(currentStateMap);

    }

}

