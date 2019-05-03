package edcc.friendfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class FindFriendsTestFragment extends Fragment {

    private View rootView;

    private RecyclerView lstUsers;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    private String currentUserID;

    private FirebaseRecyclerAdapter adapter;
    private FirebaseRecyclerOptions<Users> options;

    private FindFriendsTestFragment.FriendListener listener;

    public FindFriendsTestFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_find_friends_test, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        lstUsers = rootView.findViewById(R.id.lstUsers);
        lstUsers.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        lstUsers.setLayoutManager(manager);

        displayAllUsers();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    /**
     * Registers the controlling activity as a listener when the fragment is attached to it.
     *
     * @param context the controlling activity
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FindFriendsFragment.FriendListener) {
            this.listener = (FindFriendsTestFragment.FriendListener) context;
        }
    }

    private void displayAllUsers() {
        options = new FirebaseRecyclerOptions.Builder<Users>().setQuery(usersRef, Users.class).build();
        adapter = new FirebaseRecyclerAdapter<Users, UsersViewHolder>(options) {
            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_users_layout,
                        viewGroup, false);
                return new UsersViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, final int position, @NonNull Users model) {
                holder.setName(model.getFirst_name() + " " + model.getLast_name());
                holder.setMajor(model.getMajor());
                holder.setPhoto(model.getPhoto());
                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String friendID = getRef(position).getKey();
                        listener.viewUser(friendID);
                    }
                });
            }
        };
        lstUsers.setAdapter(adapter);

    }

    public static class UsersViewHolder extends RecyclerView.ViewHolder {

        View mView;


        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name) {
            TextView lblName = mView.findViewById(R.id.all_users_fullname);
            lblName.setText(name);
        }

        public void setMajor(String major) {
            TextView lblMajor = mView.findViewById(R.id.all_users_major);
            lblMajor.setText(major);
        }

        public void setPhoto(String photo) {
            CircleImageView imgProfile = mView.findViewById(R.id.all_users_profile_image);
            Picasso.get().load(photo).placeholder(R.drawable.user_icon).into(imgProfile);
        }
    }

    /**
     * Interface for an activity to register as a FindFriendsFragment listener.
     */
    interface FriendListener {
        void viewUser(String id);
    }
}
