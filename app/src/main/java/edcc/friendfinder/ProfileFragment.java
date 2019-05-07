package edcc.friendfinder;


import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Fragment class for user profile screen
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
public class ProfileFragment extends Fragment {

    private ProfileListener listener;
    private UserManager um;
    private TextView lblName;
    private TextView lblMajor;
    private TextView lblClasses;
    private TextView lblBio;
    private TextView lblAvailability;
    private TextView lblLanguage;
    private CircleImageView imgUser;

    private DatabaseReference usersRef, friendsRef, postsRef;
    private FirebaseAuth mAuth;

    private Button btnMyPosts, btnMyFriends;

    private String currentUserID;

    private int countFriends = 0, countPosts = 0;

    /**
     * Required default constructor.
     */
    public ProfileFragment() {
    }

    /**
     * Creaes the fragment
     *
     * @param inflater           the layout inflater
     * @param container          the container holding the fragment
     * @param savedInstanceState the saved state
     * @return the root vie of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        friendsRef = FirebaseDatabase.getInstance().getReference().child("Friends");
        postsRef = FirebaseDatabase.getInstance().getReference().child("Posts");

        // Inflate the layout for this fragment
        //fields
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        um = UserManager.getUserManager(getContext(), FirebaseAuth.getInstance().getUid());
        //find UI components
        lblName = rootView.findViewById(R.id.lblProfileName);
        lblMajor = rootView.findViewById(R.id.lblProfileMajor);
        lblLanguage = rootView.findViewById(R.id.lblProfileLanguage);
        lblClasses = rootView.findViewById(R.id.lblProfileClasses);
        lblBio = rootView.findViewById(R.id.lblProfileBio);
        lblAvailability = rootView.findViewById(R.id.lblProfileAvailability);
        imgUser = rootView.findViewById(R.id.imgProfilePicture);

        btnMyFriends = rootView.findViewById(R.id.btnProfileFriends);
        //updateData();

        friendsRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    countFriends = (int) dataSnapshot.getChildrenCount();
                    btnMyFriends.setText(countFriends + " Friends");
                } else {
                    btnMyFriends.setText("No Friends, Loser");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        usersRef.child(currentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String myProfileImage = dataSnapshot.child("profile_image").getValue().toString();
                    String myName = dataSnapshot.child("first_name").getValue() + " " + dataSnapshot.child("last_name").getValue();
                    String myMajor = dataSnapshot.child("major").getValue().toString();
                    String myLanguage = dataSnapshot.child("language").getValue().toString();
                    String myBio = dataSnapshot.child("bio").getValue().toString();
                    String myAvailability = dataSnapshot.child("availability").getValue().toString();

                    Picasso.get().load(myProfileImage).placeholder(R.drawable.user_icon).into(imgUser);
                    lblName.setText(myName);
                    lblMajor.setText(myMajor);
                    lblLanguage.setText(myLanguage);
                    lblBio.setText(myBio);
                    lblAvailability.setText(myAvailability);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
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
            case R.id.action_edit:
                listener.editUser(um.getThisUser());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Registers the controlling activity as a listener when the fragment is attached to it.
     *
     * @param context the controlling activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ProfileFragment.ProfileListener) {
            this.listener = (ProfileFragment.ProfileListener) context;
        }
    }

    /**
     * Called when the activity is first created. This method also provides you with a Bundle
     * containing the activity's previously frozen state, if there was one.
     *
     * @param savedInstanceState the activity of the fragment
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu     the options menu as last shown or first initialized by onCreateOptionsMenu()
     * @param inflater the menu's layout
     */
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
    }

    /**
     * Updates the user list. This method must be called by the controlling activity
     * whenever this fragment is visible and user data is altered outside of this fragment.
     */
    public void updateData() {
        //set UI components
        User thisUser = um.getThisUser();
//        lblName.setText(thisUser.printName());
//        lblMajor.setText(thisUser.getMajor());
//        lblLanguage.setText(thisUser.getLanguage());
        lblClasses.setText(printClasses(thisUser));
//        lblBio.setText(thisUser.getBio());
//        lblAvailability.setText(thisUser.getAvailability());
//        String photoStr = thisUser.getPhoto();
//        if (photoStr != null) {
//            byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
//            imgUser.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
//            imgUser.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        } else {
//            imgUser.setImageBitmap(null);
//            Picasso.get().load(R.drawable.user_icon).into(imgUser);
//        }
//        lblName.setText(thisUser.printName());
//        if (lblName.getText().toString().equals(" ")) {
//            listener.editUser(um.getThisUser());
//        } else {
//            lblMajor.setText(thisUser.getMajor());
//            lblLanguage.setText(thisUser.getLanguage());
//            lblClasses.setText(printClasses(thisUser));
//            lblBio.setText(thisUser.getBio());
//            lblAvailability.setText(thisUser.getAvailability());
//
//            String photoStr = thisUser.getPhoto();
//            if (photoStr != null) {
//                byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
//                imgUser.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
//                imgUser.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            } else {
//                imgUser.setImageBitmap(null);
//            }
//        }
    }


    /**
     * Interface for an activity to register as a ProfileFragment listener.
     */
    interface ProfileListener {
        void editUser(User current);
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
