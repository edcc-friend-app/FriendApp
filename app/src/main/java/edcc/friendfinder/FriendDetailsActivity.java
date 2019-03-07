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

public class FriendDetailsActivity extends BaseActivity {

    private int id;
    private UserManager um;
    private User thisFriend;
    private TextView lblName;
    private TextView lblMajor;
    private TextView lblClasses;
    private TextView lblLanguage;
    private TextView lblBio;
    private ImageView imgFriend;
    private PreferencesManager pm;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private EventListener<QuerySnapshot> profileDataListener;
    private ListenerRegistration profileReg;
    private EventListener<QuerySnapshot> friendDataListener;
    private ListenerRegistration friendReg;
    private EventListener<DocumentSnapshot> userDataListener;
    private ListenerRegistration userReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pm = PreferencesManager.getInstance(getApplicationContext());
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
        imgFriend = findViewById(R.id.imgFriendPicture);
    }

    @Override
    protected void onPause() {
        super.onPause();
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
    public void setUpDataListeners() {
        um = UserManager.getUserManager(getApplicationContext(), userId);
        thisFriend = um.getFriend(id);
        um.setFriend(thisFriend);
        lblName.setText(thisFriend.toString());
        lblMajor.setText(thisFriend.getMajor());
        //String[] genderArray = getResources().getStringArray(R.array.arrGenders);
        //lblGender.setText(genderArray[thisUser.getGender()]);
        lblClasses.setText(thisFriend.printClasses());
        lblLanguage.setText(thisFriend.getLanguage());
        lblBio.setText(thisFriend.getBio());
//                    if (thisUser.getFriendId() > -1 && um.getFriendList().size() > 0 &&
//                            um.getFriend(thisUser.getFriendId()) != null) {
//                        lblClient.setText(dm.getClient(thisPet.getClientId()).toString());
//                    }
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
            case R.id.action_settings:
                //settings menu option
//                Intent intent = new Intent(this, PreferencesActivity.class);
//                startActivity(intent);
            case R.id.action_delete:
                deleteFriend();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteFriend() {
        if (pm.isWarnBeforeDeletingFriend()) {
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
        } else {
            um.deleteFriend(id);
        }
    }


}
