package edcc.friendfinder;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Fragment class for friends list screen.
 *
 * @author Anthony Luong
 * @author Estefano Felipa
 * @author Jonathan Young
 * @version 1.0 3/10/19
 */
@SuppressWarnings("NullableProblems")
public class FriendsFragment extends Fragment {

    //fields
    private RecyclerView lstFriends;
    private EditText txtFilter;
    private ArrayAdapter<User> lstAdapter;
    private List<User> friendList = new ArrayList<>();
    private boolean isFiltered;
    private FriendsFragment.FriendListener listener;
    private UserManager um;
    private View rootView;

    private DatabaseReference friendsRef, usersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;


    /**
     * Required empty public constructor
     */
    public FriendsFragment() {

    }

    /**
     * Creates the fragment.
     *
     * @param inflater           the layout inflater
     * @param container          the container holding the fragment
     * @param savedInstanceState the saved state
     * @return the root view of the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        um = UserManager.getUserManager(getContext(), FirebaseAuth.getInstance().getUid());
        //Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        lstFriends = rootView.findViewById(R.id.lstFriends);
        txtFilter = rootView.findViewById(R.id.txtFilter);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        friendsRef = FirebaseDatabase.getInstance().getReference().child("Friends").child(currentUserID);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        lstFriends.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        lstFriends.setLayoutManager(manager);

        //get saved state
        if (savedInstanceState != null) {
            isFiltered = savedInstanceState.getBoolean("isFiltered");
        }
        //set up filter button
        ImageButton btnFilter = rootView.findViewById(R.id.ibtnFilter);
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFilterClick();
            }
        });
        //updateData();
        displayAllFriends();
        return rootView;
    }

    /**
     * Registers the controlling activity as a listener when the fragment is attached to it.
     *
     * @param context the controlling activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FriendListener) {
            this.listener = (FriendListener) context;
        }
    }

    /**
     * Saves the state of the activity.
     *
     * @param outState the class state
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isFiltered", isFiltered);
    }

    /**
     * On resume, set up the list adapter and filter if was filtered.
     */
    @Override
    public void onResume() {
        super.onResume();
        if (lstAdapter != null) {
            lstAdapter.notifyDataSetChanged();
        }
        if (isFiltered) {
            filterList();
        }
    }

    /**
     * Updates the friends list. This method must be called by the controlling activity
     * whenever this fragment is visible and user data is altered outside of this fragment.
     */
    public void updateData() {
//        friendList = um.getFriendList();
//        if (isFiltered) {
//            filterList();
//        } else {
//            //set up list view adapter
//            lstAdapter = new ArrayAdapter<>(rootView.getContext(),
//                    android.R.layout.simple_list_item_1, friendList);
//            lstFriends.setAdapter(lstAdapter);
//            //create list view click event
//            AdapterView.OnItemClickListener itemClickListener =
//                    new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            //User thisFriend = (User) lstFriends.getItemAtPosition(position);
//                            listener.viewFriendRequested(thisFriend);
//                            clearFilter();
//                            isFiltered = false;
//                        }
//                    };
//            //lstFriends.setOnItemClickListener(itemClickListener);
//        }
//        //hide keyboard
//        if (getActivity() != null) {
//            InputMethodManager inputManager = (InputMethodManager)
//                    rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            if (inputManager != null) {
//                inputManager.hideSoftInputFromWindow(
//                        (null == getActivity().getCurrentFocus()) ? null :
//                                getActivity().getCurrentFocus().getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//        }
        //lstFriends = rootView.findViewById(R.id.lstFriends);


    }

    /**
     * Handles the filter click
     */
    private void handleFilterClick() {
        //hide keyboard
        if (getActivity() != null) {
            InputMethodManager inputManager = (InputMethodManager)
                    rootView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(
                        (null == getActivity().getCurrentFocus()) ? null :
                                getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        //handle filter request
        if (txtFilter.getText().toString().isEmpty()) {
            clearFilter();
            isFiltered = false;
        } else {
            isFiltered = true;
            filterList();
        }
    }

    /**
     * Filters the list based on user selection.
     */
    private void filterList() {
        if (um == null) {
            return;
        }
        String filter = txtFilter.getText().toString().toLowerCase();
        //filter list
        List<User> filteredList = new ArrayList<>();
        for (User f : friendList) {
            if ((f.getFirstName().toLowerCase().contains(filter))
                    || (f.getMajor().toLowerCase().contains(filter))
                    || (f.getLanguage().toLowerCase().contains(filter))) {
                filteredList.add(f);
            }
        }
        lstAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_list_item_1, filteredList);
        //lstFriends.setAdapter(lstAdapter);
    }

    /**
     * Clears the list filter.
     */
    private void clearFilter() {
        lstAdapter = new ArrayAdapter<>(rootView.getContext(),
                android.R.layout.simple_list_item_1, um.getFriendList());
        //lstFriends.setAdapter(lstAdapter);
        txtFilter.setText("");
    }

    /**
     * Interface for an activity to register as a FriendsFragment listener.
     */
    interface FriendListener {
        void viewFriendRequested(User friend);
        void messageFriend(String userID, String fullname);

    }



    private void displayAllFriends() {
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        friendsRef = FirebaseDatabase.getInstance().getReference().child("Friends").child(currentUserID);
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Query query = friendsRef.limitToLast(50);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Friends>().setQuery(query, Friends.class).build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Friends, FriendsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final FriendsViewHolder holder, int position, @NonNull Friends model) {
                holder.setDate(model.getDate());
                final String userID = getRef(position).getKey();

                usersRef.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            final String fullname = dataSnapshot.child("first_name").getValue() + " " + dataSnapshot.child("last_name").getValue();
                            final String profileImage = dataSnapshot.child("profile_image").getValue().toString();
                            final String type;

                            if (dataSnapshot.hasChild("userstate")) {
                                type = dataSnapshot.child("userstate").child("type").getValue().toString();

                                if (type.equals("online")) {
                                    holder.onlineStatusView.setVisibility(View.VISIBLE);
                                } else {
                                    holder.onlineStatusView.setVisibility(View.INVISIBLE);
                                }
                            }

                            holder.setFullname(fullname);
                            holder.setProfileimage(profileImage);

                            holder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    CharSequence[] options = new CharSequence[] {
                                            fullname + "'s Profile", "Send Message"
                                    };

                                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                    builder.setTitle("Select Option");

                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(which == 0) {
//                                                Intent profileIntent = new Intent(FriendsActivity.this, PersonProfileActivity.class);
//                                                profileIntent.putExtra("friendID", userID);
//                                                startActivity(profileIntent);

                                            } else {
                                                listener.messageFriend(userID, fullname);
                                            }
                                        }
                                    });
                                    builder.show();

                                }
                            });



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.all_users_layout, viewGroup, false);
                return new FriendsViewHolder(view);
            }


        };
        lstFriends.setAdapter(adapter);
        adapter.startListening();
    }


    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;
        ImageView onlineStatusView;


        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            //onlineStatusView = mView.findViewById(R.id.all_user_online_icon);
        }

        public void setFullname(String fullname) {
            TextView username = mView.findViewById(R.id.all_users_fullname);
            username.setText(fullname);
        }

        public void setProfileimage(String profileimage) {
            CircleImageView image = mView.findViewById(R.id.all_users_profile_image);
            Picasso.get().load(profileimage).into(image);
        }

        public void setMajor(String status) {
            TextView postDescription = mView.findViewById(R.id.all_users_major);
            postDescription.setText(status);
        }

        public void setDate(String date) {
            TextView myDate = mView.findViewById(R.id.all_users_major);
            myDate.setText("Friend since: " + date);
        }
    }

}
