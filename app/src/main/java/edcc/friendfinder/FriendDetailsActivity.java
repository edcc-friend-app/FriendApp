package edcc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import static java.security.AccessController.getContext;

public class FriendDetailsActivity extends BaseActivity {

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
        setContentView(R.layout.activity_friend_details);
        //create action bar and back arrow
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        um = UserManager.getUserManager(getApplicationContext(), userId);
        //get current pet
        Intent intent = getIntent();
        id = intent.getIntExtra("itemId", -1);
        thisUser = um.getUser(id);
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

    @Override
    protected void setUpDataListeners() {

    }

    /**
     * Creates the top menu.
     *
     * @param menu the top menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.menu_done, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }


}
