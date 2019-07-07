package edcc.friendfinder;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.CircularPropagation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestsFragment extends Fragment {

    private View rootView;

    private RecyclerView lstRequests;

    private DatabaseReference usersRef, friendRequestsRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    public FriendRequestsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_friend_requests, container, false);

        lstRequests = rootView.findViewById(R.id.lstRequests);

        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        friendRequestsRef = FirebaseDatabase.getInstance().getReference().child("FriendRequests");

        lstRequests.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        lstRequests.setLayoutManager(manager);

        displayAllRequests();
        return rootView;
    }

    private void displayAllRequests() {
        Query requestsQuery = friendRequestsRef.child(currentUserID);
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<FriendRequests>().setQuery(requestsQuery, FriendRequests.class).build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<FriendRequests, RequestsViewHolder>(options) {
            @Override
            public RequestsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.all_requests_layout, viewGroup, false);
                return new RequestsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final RequestsViewHolder holder, int position, @NonNull FriendRequests model) {
                holder.setMajor(model.getRequest_type());
                final String userID = getRef(position).getKey();
                usersRef.child(userID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            final String fullname = dataSnapshot.child("first_name").getValue() + " " + dataSnapshot.child("last_name").getValue();
                            final String profileImage = dataSnapshot.child("profile_image").getValue().toString();
                            final String major = dataSnapshot.child("major").getValue().toString();
                            holder.setFullName(fullname);
                            holder.setProfileImage(profileImage);
                            holder.setMajor(major);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        
                    }
                });

            }
        };

        lstRequests.setAdapter(adapter);
        adapter.startListening();

    }

    public static class RequestsViewHolder extends RecyclerView.ViewHolder {

        public View mView;

        public RequestsViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setFullName(String name) {
            TextView lblName = mView.findViewById(R.id.lblRequestName);
            lblName.setText(name);
        }

        public void setMajor(String major) {
            TextView lblMajor = mView.findViewById(R.id.lblRequestMajor);
            lblMajor.setText(major);
        }


        public void setProfileImage(String profileImage) {
            CircleImageView imgProfile = mView.findViewById(R.id.imgRequestPhoto);
            Picasso.get().load(profileImage).into(imgProfile);
        }
    }


}
