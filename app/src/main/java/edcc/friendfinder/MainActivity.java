package edcc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import java.util.ArrayList;
import java.util.List;

//import javax.annotation.Nullable;

/**
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FriendsFragment.FriendListener,
        ProfileFragment.ProfileListener, FindFriendsFragment.FriendListener {

    private PreferencesManager pm;
    private String currentFragment;
    private Fragment fragment;
    private UserManager um;
    private int type;
    public static final String ITEM_ID = "itemId";
    public static final String USER_ID = "userId";

    //anthony's attempt
//    private List<User> list;
//    private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private final int MAX_USERS = 1000;
//    private ArrayAdapter<User> listUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //anthony cont.
        //list = new ArrayList<>();
        //ListView lstUser = findViewById(R.id.User);
        //listUser = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

//        db.collection("users").addSnapshotListener(this, new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (!documentSnapshots.isEmpty()) {
//                    list.clear();
//
//                }
//            }
//        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                //actionBar.setTitle(R.string.titlePetList);
                navigationView.getMenu().getItem(0).setChecked(true);
                break;
            case "find":
                fragment = new FindFriendsFragment();
                //actionBar.setTitle(R.string.titleClientList);
                navigationView.getMenu().getItem(1).setChecked(true);
                break;
            case "friends":
                fragment = new FriendsFragment();
                //actionBar.setTitle(R.string.titleVetList);
                navigationView.getMenu().getItem(2).setChecked(true);
                break;
        }
        fragment = new ProfileFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frmFragment, fragment);
        ft.commit();
        navigationView.getMenu().getItem(0).setChecked(true);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
            currentFragment = "profile";
        } else if (id == R.id.nav_find) {
            fragment = new FindFriendsFragment();
            currentFragment = "find";
        } else if (id == R.id.nav_friends) {
            fragment = new FriendsFragment();
            currentFragment = "friends";
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frmFragment, fragment);
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
        intent.putExtra(ITEM_ID, friend.getId());
        startActivity(intent);
    }

    @Override
    public void viewPotFriendRequested(User friend) {
        Intent intent = new Intent(MainActivity.this, PotentialFriendActivity.class);
        intent.putExtra(ITEM_ID, friend.getId());
        startActivity(intent);
    }
}
