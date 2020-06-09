package edcc.friendfinder;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Activity class for potential friends details screen.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class PotentialFriendActivity extends BaseActivity {

    //fields
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
    private CircleImageView imgUser;
    private Button btnSendFriendRequest;
    private Button btnDeclineFriendRequest;

    private EventListener<QuerySnapshot> profileDataListener;
    private ListenerRegistration profileReg;
    private EventListener<QuerySnapshot> friendDataListener;
    private ListenerRegistration friendReg;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef, friendsRef, friendRequestsRef;
    private String currentUserID;
    private String userID;
    private String currentState;
    private String saveCurrentDate;


    /**
     * Builds the activity on startup.
     *
     * @param savedInstanceState the saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potential_friend);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        friendsRef = FirebaseDatabase.getInstance().getReference().child("Friends");
        friendRequestsRef = FirebaseDatabase.getInstance().getReference().child("FriendRequests");

        //create action bar and back arrow
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //get selected user's ID
        Intent intent = getIntent();
        userID = intent.getStringExtra(Extras.FRIEND_ID);

        //initial state
        currentState = "not_friends";

        //find UI components
        lblName = findViewById(R.id.lblUserName);
        lblMajor = findViewById(R.id.lblUserMajor);
        lblClasses = findViewById(R.id.lblUserClasses);
        lblLanguage = findViewById(R.id.lblUserLanguage);
        lblAvailability = findViewById(R.id.lblUserAvailability);
        lblBio = findViewById(R.id.lblUserBio);
        imgUser = findViewById(R.id.imgUserPicture);

        btnSendFriendRequest = findViewById(R.id.btnSendFriendRequest);
        btnDeclineFriendRequest = findViewById(R.id.btnDeclineFriendRequest);

        btnDeclineFriendRequest.setVisibility(View.INVISIBLE);
        btnDeclineFriendRequest.setEnabled(false);

        if (currentUserID.equals(userID)) {
            btnDeclineFriendRequest.setVisibility(View.INVISIBLE);
            btnSendFriendRequest.setVisibility(View.INVISIBLE);
        } else {
            btnSendFriendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnSendFriendRequest.setEnabled(false);

                    if (currentState.equals("not_friends")) {
                        sendFriendRequest();
                    }
                    if (currentState.equals("request_sent")) {
                        cancelFriendRequest();
                    }
                    if (currentState.equals("request_received")) {
                        acceptFriendRequest();
                    }

                    if (currentState.equals("friends")) {
                        unfriend();
                    }
                }
            });
        }

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
        if (friendReg != null && friendDataListener != null) {
            friendReg.remove();
        }
    }


    /**
     * Connects up the data listeners once authentication is completed in the BaseActivity.
     * The user listener is listening for changes in this particular user entry.
     */
    @Override
    public void setUpDataListeners() {
        um = UserManager.getUserManager(getApplicationContext(), userId);
//        thisUser = um.getUser(thisUserName);
//        lblName.setText(thisUser.printName());
//        lblMajor.setText(thisUser.getMajor());
//        lblClasses.setText(printClasses(thisUser));
//        lblLanguage.setText(thisUser.getLanguage());
//        lblAvailability.setText(thisUser.getAvailability());
//        lblBio.setText(thisUser.getBio());
//        String photoStr = thisUser.getPhoto();
//        imgUser = findViewById(R.id.imgFriendPicture);
//        if (photoStr != null) {
//            byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
//            imgUser.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
//            imgUser.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        } else {
//            imgUser.setImageBitmap(null);
//        }
//        //friends listener
//        final CollectionReference friendRef = db.collection("users").document(userId)
//                .collection("friends");
//        friendDataListener = new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
//                    ArrayList<User> list = new ArrayList<>();
//                    for (int i = 0; i < documentSnapshots.size(); i++) {
//                        DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(i);
//                        User friend = snapshot.toObject(User.class);
//                        list.add(friend);
//                    }
//                    um.setFriendList(list);
//                }
//            }
//        };
//        friendReg = friendRef.addSnapshotListener(friendDataListener);
        //profile listener
//        final CollectionReference ref = db.collection("users").document(userId)
//                .collection("profile");
//        profileDataListener = new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
//                    DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(0);
//                    User profile = snapshot.toObject(User.class);
//                    um.setThisUser(profile);
//                }
//            }
//        };
//        profileReg = ref.addSnapshotListener(profileDataListener);

        usersRef.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("first_name").getValue() + " " + dataSnapshot.child("last_name").getValue();
                    String major = dataSnapshot.child("major").getValue().toString();
                    String language = dataSnapshot.child("language").getValue().toString();
                    String bio = dataSnapshot.child("bio").getValue().toString();
                    String availability = dataSnapshot.child("availability").getValue().toString();
                    String imgProfile = dataSnapshot.child("profile_image").getValue().toString();

                    lblName.setText(name);
                    lblMajor.setText(major);
                    lblLanguage.setText(language);
                    lblBio.setText(bio);
                    lblAvailability.setText(availability);
                    Picasso.get().load(imgProfile).into(imgUser);

                    buttonMaintenance();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void buttonMaintenance() {
        friendRequestsRef.child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(userID)) {
                    String request_type = dataSnapshot.child(userID).child("request_type").getValue().toString();

                    if (request_type.equals("sent")) {
                        currentState = "request_sent";
                        btnSendFriendRequest.setText("Cancel Friend Request");

                        btnDeclineFriendRequest.setVisibility(View.INVISIBLE);
                        btnDeclineFriendRequest.setEnabled(false);
                    } else if (request_type.equals("received")) {
                        currentState = "request_received";
                        btnSendFriendRequest.setText("Accept Request");

                        btnDeclineFriendRequest.setVisibility(View.VISIBLE);
                        btnDeclineFriendRequest.setEnabled(true);

                        btnDeclineFriendRequest.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancelFriendRequest();
                            }
                        });
                    }
                } else {
                    friendsRef.child(currentUserID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(userID)) {
                                currentState = "friends";
                                btnSendFriendRequest.setText("Unfriend");

                                btnDeclineFriendRequest.setVisibility(View.INVISIBLE);
                                btnDeclineFriendRequest.setEnabled(false);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cancelFriendRequest() {
        friendRequestsRef.child(currentUserID).child(userID).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            friendRequestsRef.child(userID).child(currentUserID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        btnSendFriendRequest.setEnabled(true);
                                        currentState = "not_friends";
                                        btnSendFriendRequest.setText("Add Friend");

                                        btnDeclineFriendRequest.setVisibility(View.INVISIBLE);
                                        btnDeclineFriendRequest.setEnabled(false);
                                    }
                                }
                            });
                        }
                    }
                });
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
            case R.id.action_add:
                //Add this friend
                addFriend();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendFriendRequest() {
        friendRequestsRef.child(currentUserID).child(userID).child("request_type").setValue("sent")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            friendRequestsRef.child(userID).child(currentUserID).child("request_type").setValue("received")
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                btnSendFriendRequest.setEnabled(true);
                                                currentState = "request_sent";
                                                btnSendFriendRequest.setText("Cancel Friend Request");

                                                btnDeclineFriendRequest.setVisibility(View.INVISIBLE);
                                                btnDeclineFriendRequest.setEnabled(false);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void acceptFriendRequest() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        saveCurrentDate = date.format(calendar.getTime());

        friendsRef.child(currentUserID).child(userID).child("date").setValue(saveCurrentDate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            friendsRef.child(userID).child(currentUserID).child("date").setValue(saveCurrentDate)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                friendRequestsRef.child(currentUserID).child(userID).removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    friendRequestsRef.child(userID).child(currentUserID).
                                                                            removeValue()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        btnSendFriendRequest.setEnabled(true);
                                                                                        currentState = "friends";
                                                                                        btnSendFriendRequest.setText("Unfriend");

                                                                                        btnDeclineFriendRequest.setVisibility(View.INVISIBLE);
                                                                                        btnDeclineFriendRequest.setEnabled(false);
                                                                                    }
                                                                                }
                                                                            });
                                                                }
                                                            }
                                                        });

                                            }
                                        }
                                    });
                        }
                    }
                });


    }

    private void unfriend() {

        friendsRef.child(currentUserID).child(userID)
                .removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            friendsRef.child(userID).child(currentUserID)
                                    .removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                btnSendFriendRequest.setEnabled(true);
                                                currentState = "not_friends";
                                                btnSendFriendRequest.setText("Add Friend");

                                                btnDeclineFriendRequest.setVisibility(View.INVISIBLE);
                                                btnDeclineFriendRequest.setEnabled(true);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    /**
     * Add the current user.
     */
    private void addFriend() {
        um.addFriend(thisUser);
    }

    /**
     * Method to display classes
     *
     * @param u user being sent to check for matching parameters
     * @return matching class list
     */
    private String printClasses(User u) {
        List<String> classes = um.getCourses();
        return classes.get(u.getArrMatch().get(0)) + '\n' + classes.get(u.getArrMatch().get(1)) +
                '\n' + classes.get(u.getArrMatch().get(2));
    }


}

