package edcc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

//import com.google.firebase.firestore.EventListener;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.FirebaseFirestoreException;
//import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//import javax.annotation.Nullable;

/**
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0
 */

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, FriendsFragment.FriendListener,
        ProfileFragment.ProfileListener, FindFriendsFragment.FriendListener {
    //fields
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EventListener<QuerySnapshot> profileDataListener;
    private ListenerRegistration profileReg;
    private EventListener<QuerySnapshot> friendDataListener;
    private ListenerRegistration friendReg;
    private EventListener<QuerySnapshot> userDataListener;
    private ListenerRegistration userReg;
    private Fragment fragment;
    private UserManager um;
    private ActionBar actionBar;
    private String currentFragment;
    private PreferencesManager pm;

    private int type;
    public static final String USER_ID = "userId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        //set up navigation drawer
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //set up fragment
        pm = PreferencesManager.getInstance(this);
        currentFragment = pm.getCurrentFragment();
        switch (currentFragment) {
            case "profile":
                fragment = new ProfileFragment();
                actionBar.setTitle("Profile");
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case "find":
                fragment = new FindFriendsFragment();
                actionBar.setTitle("Find Friends");
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case "friends":
                fragment = new FriendsFragment();
                actionBar.setTitle("Friends");
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frmFragment, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    @Override
    protected void setUpDataListeners() {
        stopDataListeners();
        if (fragment instanceof ProfileFragment) {
            //set up pet list
            um = UserManager.getUserManager(this, userId);
            final CollectionReference ref = db.collection("users").document(userId)
                    .collection("frie");
            profileDataListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                        ArrayList<User> userList = new ArrayList<>();
                        for (int i = 0; i < documentSnapshots.size(); i++) {
                            DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(i);
                            User user = snapshot.toObject(User.class);
                            userList.add(user);
                        }
                        um.setThisUser(userList.get(0));
                        //((FriendsFragment)fragment).updateData();
                    }
                }
            };
            profileReg = ref.addSnapshotListener(profileDataListener);
        } else if (fragment instanceof FindFriendsFragment) {
            //set up client list
            um = UserManager.getUserManager(this, userId);
            final CollectionReference ref = db.collection("users").document(userId)
                    .collection("clients");
            userDataListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                        ArrayList<User> userList = new ArrayList<>();
                        for (int i = 0; i < documentSnapshots.size(); i++) {
                            DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(i);
                            User user = snapshot.toObject(User.class);
                            userList.add(user);
                        }
                        //um.setUserList(userList);
                        ((FindFriendsFragment)fragment).updateData();
                    }
                }
            };
            //userReg = ref.addSnapshotListener(userDataListener);
        } else if (fragment instanceof FriendsFragment) {
            //
            um = UserManager.getUserManager(this, userId);
            final CollectionReference ref = db.collection("users").document(userId)
                    .collection("friends");
            friendDataListener = new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                        ArrayList<User> friendList = new ArrayList<>();
                        for (int i = 0; i < documentSnapshots.size(); i++) {
                            DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(i);
                            User friend = snapshot.toObject(User.class);
                            friendList.add(friend);
                        }
                        um.setFriendList(friendList);
                        ((FriendsFragment)fragment).updateData();
                    }
                }
            };
            friendReg = ref.addSnapshotListener(friendDataListener);
        }
    }

    /**
     * Stops all data listeners.
     */
    private void stopDataListeners() {
        if (profileReg != null && profileDataListener != null) {
            profileReg.remove();
        }
        if (userReg != null && userDataListener != null) {
            userReg.remove();
        }
        if (friendReg != null && friendDataListener != null) {
            friendReg.remove();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * On pause, data listeners are stopped.
     */
    @Override
    protected void onPause() {
        super.onPause();
        stopDataListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


            if (id == R.id.action_sign_out) {
                signOut();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            actionBar.setTitle("Profile");
            fragment = new ProfileFragment();
            currentFragment = "profile";
        } else if (id == R.id.nav_find) {
            actionBar.setTitle("Find Friends");
            fragment = new FindFriendsFragment();
            currentFragment = "find";
        } else if (id == R.id.nav_friends) {
            actionBar.setTitle("Friends");
            fragment = new FriendsFragment();
            currentFragment = "friends";
        }
        pm.setCurrentFragment(currentFragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frmFragment, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void editUser(User current) {
        Intent intent = new Intent(MainActivity.this, EditUserActivity.class);
        intent.putExtra(USER_ID, current.getId());
        startActivity(intent);
    }

    @Override
    public void viewFriendRequested(User friend) {
        Intent intent = new Intent(MainActivity.this, FriendDetailsActivity.class);
        intent.putExtra(Extras.FRIEND_ID, friend.getId());
        startActivity(intent);
    }

    @Override
    public void viewPotFriendRequested(User friend) {
        Intent intent = new Intent(MainActivity.this, PotentialFriendActivity.class);
        intent.putExtra(Extras.USER_ID, friend.toString());
        startActivity(intent);
    }

}
