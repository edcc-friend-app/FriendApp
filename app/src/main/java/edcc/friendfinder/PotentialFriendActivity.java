package edcc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class PotentialFriendActivity extends AppCompatActivity {

    private int id;
    private UserManager um;
    private User thisUser;
    private TextView lblName;
    private TextView lblMajor;
    private TextView lblClasses;
    private TextView lblBio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //um = PreferencesManager.getInstance(getApplicationContext());
        setContentView(R.layout.activity_potential_friend);
        //create action bar and back arrow
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        um = UserManager.getUserManager();
        //get current friend
        Intent intent = getIntent();
        id = intent.getIntExtra("itemId", -1);
        thisUser = um.getPotFriend(id);
        if (id < 0) {
            finish();
        }
        //find UI components
        lblName = findViewById(R.id.lblName);
        lblMajor = findViewById(R.id.lblMajor);
        lblClasses = findViewById(R.id.lblClasses);
        lblBio = findViewById(R.id.lblBio);
        lblName.setText(thisUser.toString());
        lblMajor.setText(thisUser.getMajor());
        lblClasses.setText(thisUser.printClasses());
        lblBio.setText(thisUser.getBio());

    }

    /**
     * Creates the top menu.
     *
     * @param menu the top menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    /**
     * Handles the top menu item selection.
     *
     * @param item the item selected
     * @return true or false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sign_out:
                //signOut();
                return true;
            case android.R.id.home:
                //back arrow
                finish();
                return true;
            case R.id.action_settings:
                //settings menu option
//                Intent intent = new Intent(this, PreferencesActivity.class);
//                startActivity(intent);
                return true;
            case R.id.action_add:
                //Add this friend
                addFriend();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addFriend() {
        um.addFriend(thisUser);
    }


}

