package edcc.friendfinder;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class PotentialFriendActivity extends BaseActivity {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String thisUserName;
    private UserManager um;
    private User thisUser;
    private TextView lblName;
    private TextView lblMajor;
    private TextView lblClasses;
    private TextView lblLanguage;
    private TextView lblAvailability;
    private TextView lblBio;
    private ImageView imgUser;
    private PreferencesManager pm;
    private EventListener<QuerySnapshot> profileDataListener;
    private ListenerRegistration profileReg;
    private EventListener<QuerySnapshot> friendDataListener;
    private ListenerRegistration friendReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pm = PreferencesManager.getInstance(getApplicationContext());
        setContentView(R.layout.activity_potential_friend);
        //create action bar and back arrow
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //get current user
        Intent intent = getIntent();
        thisUserName = intent.getStringExtra(Extras.USER_ID);

        //find UI components
        lblName = findViewById(R.id.lblName);
        lblMajor = findViewById(R.id.lblMajor);
        lblClasses = findViewById(R.id.lblClasses);
        lblLanguage = findViewById(R.id.lblLanguage);
        lblAvailability = findViewById(R.id.txtAvailability);
        lblBio = findViewById(R.id.txtBio);
        imgUser = findViewById(R.id.imgFriendPicture);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (profileReg != null && profileDataListener != null) {
            profileReg.remove();
        }
        if (friendReg != null && friendDataListener != null) {
            friendReg.remove();
        }
    }


    @Override
    public void setUpDataListeners() {
        um = UserManager.getUserManager(getApplicationContext(), userId);
        thisUser = um.getUser(thisUserName);
        lblName.setText(thisUser.toString() + thisUser.getMatchCount());
        lblMajor.setText(thisUser.getMajor());
        lblClasses.setText(thisUser.printClasses());
        lblLanguage.setText(thisUser.getLanguage());
        lblAvailability.setText(thisUser.getAvailability());
        lblBio.setText(thisUser.getBio());
        String photoStr = thisUser.getPhoto();
        imgUser = findViewById(R.id.imgFriendPicture);
        if (photoStr != null) {
            byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
            imgUser.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
            imgUser.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imgUser.setImageBitmap(null);
        }
        //friends listener
        final CollectionReference friendRef = db.collection("users").document(userId)
                .collection("friends");
        friendDataListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                    ArrayList<User> list = new ArrayList<>();
                    for (int i = 0; i < documentSnapshots.size(); i++) {
                        DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(i);
                        User friend = snapshot.toObject(User.class);
                        list.add(friend);
                    }
                    um.setFriendList(list);
                }
            }
        };
        friendReg = friendRef.addSnapshotListener(friendDataListener);
        //profile listener
        final CollectionReference ref = db.collection("users").document(userId)
                .collection("profile");
        profileDataListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
                    DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(0);
                    User profile = snapshot.toObject(User.class);
                    um.setThisUser(profile);
                } else {
                    //((ProfileFragment) fragment).updateData();
                }
            }
        };
        profileReg = ref.addSnapshotListener(profileDataListener);
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
                signOut();
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

