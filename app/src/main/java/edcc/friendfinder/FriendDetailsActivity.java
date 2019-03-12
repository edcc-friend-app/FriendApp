package edcc.friendfinder;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

import java.util.List;

/**
 * Activity class for friend details screen.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class FriendDetailsActivity extends BaseActivity {

    //fields
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int id;
    private UserManager um;
    private TextView lblName;
    private TextView lblMajor;
    private TextView lblClasses;
    private TextView lblLanguage;
    private TextView lblAvailability;
    private TextView lblBio;
    private ImageView imgFriend;
    private EventListener<QuerySnapshot> profileDataListener;
    private ListenerRegistration profileReg;

    /**
     * Builds the activity on startup.
     *
     * @param savedInstanceState the saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_details);
        //create action bar and back arrow
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        um = UserManager.getUserManager(getApplicationContext(), userId);
        //get current friend
        Intent intent = getIntent();
        id = intent.getIntExtra(Extras.FRIEND_ID, -1);
        if (id < 0) {
            finish();
        }
        //find UI components
        lblName = findViewById(R.id.lblName);
        lblMajor = findViewById(R.id.lblMajor);
        lblClasses = findViewById(R.id.lblClasses);
        lblLanguage = findViewById(R.id.lblLanguage);
        lblBio = findViewById(R.id.txtBio);
        lblAvailability = findViewById(R.id.txtAvailability);
        imgFriend = findViewById(R.id.imgFriendPicture);
    }

    /**
     * On pause, all data listeners are stopped.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (profileReg != null && profileDataListener != null) {
            profileReg.remove();
        }
    }

    /**
     * Connects up the data listeners once authentication is completed in the BaseActivity.
     * The friend listener is listening for changes in this particular friend entry.
     */
    @Override
    public void setUpDataListeners() {
        um = UserManager.getUserManager(getApplicationContext(), userId);
        User thisFriend = um.getFriend(id);
        um.setFriend(thisFriend);
        lblName.setText(thisFriend.printName());
        lblMajor.setText(thisFriend.getMajor());
        lblClasses.setText(printClasses(thisFriend));
        lblLanguage.setText(thisFriend.getLanguage());
        lblBio.setText(thisFriend.getBio());
        lblAvailability.setText(thisFriend.getAvailability());
        String photoStr = thisFriend.getPhoto();
        imgFriend = findViewById(R.id.imgFriendPicture);
        if (photoStr != null) {
            byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
            imgFriend.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
            imgFriend.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imgFriend.setImageBitmap(null);
        }

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
        getMenuInflater().inflate(R.menu.menu_delete, menu);
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
            case R.id.action_delete:
                deleteFriend();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Delete the current friend.
     */
    private void deleteFriend() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Are you sure you want to delete this friend?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        um.deleteFriend(id);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    /**
     * Prints out the user's classes
     *
     * @param u the user you want to print the classes of
     * @return the user's classes
     */
    private String printClasses(User u) {
        List<String> classes = um.getCourses();
        return classes.get(u.getArrMatch().get(0)) + '\n' + classes.get(u.getArrMatch().get(1)) +
                '\n' + classes.get(u.getArrMatch().get(2));
    }
}
