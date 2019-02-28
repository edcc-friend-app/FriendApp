package edcc.friendfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class FriendDetailsActivity extends AppCompatActivity {

    private int id;
    private UserManager um;
    private User thisUser;
    private TextView lblName;
    private TextView lblMajor;
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
        um = UserManager.getUserManager();
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
        lblBio = findViewById(R.id.lblBio);
        lblName.setText(thisUser.toString());
        lblMajor.setText(thisUser.getMajor());
        lblBio.setText(thisUser.getBio());

    }



}
