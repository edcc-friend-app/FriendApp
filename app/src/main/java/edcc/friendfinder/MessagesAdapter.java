package edcc.friendfinder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>{

    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersDatabaseRef;

    public MessagesAdapter(List<Messages> userMessagesList) {
        this.userMessagesList = userMessagesList;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMessageSender, txtMessageReceiver;
        private CircleImageView imgMessageProfile;

        public MessageViewHolder(View itemView) {
            super(itemView);

            txtMessageSender = itemView.findViewById(R.id.txtMessageSender);
            txtMessageReceiver = itemView.findViewById(R.id.txtMessageReceiver);
            imgMessageProfile = itemView.findViewById(R.id.imgMessageProfile);
        }


    }

    @NonNull
    @Override
    public MessagesAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.message_layout, viewGroup, false);
        mAuth = FirebaseAuth.getInstance();
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessagesAdapter.MessageViewHolder messageViewHolder, int i) {
        String messageSenderID = mAuth.getCurrentUser().getUid();
        Messages messages  = userMessagesList.get(i);

        String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();

        usersDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);
        usersDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String image = dataSnapshot.child("profile_image").getValue().toString();

                    Picasso.get().load(image).placeholder(R.mipmap.ic_launcher_round)
                            .into(messageViewHolder.imgMessageProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (fromMessageType.equals("text")) {
            messageViewHolder.txtMessageReceiver.setVisibility(View.INVISIBLE);
            messageViewHolder.imgMessageProfile.setVisibility(View.INVISIBLE);

            if (fromUserID.equals(messageSenderID)) {
                messageViewHolder.txtMessageSender.setBackgroundResource(R.drawable.message_sender);
                messageViewHolder.txtMessageSender.setTextColor(Color.WHITE);
                messageViewHolder.txtMessageSender.setGravity(Gravity.LEFT);
                messageViewHolder.txtMessageSender.setText(messages.getMessage());
            } else {
                messageViewHolder.txtMessageSender.setVisibility(View.INVISIBLE);

                messageViewHolder.txtMessageReceiver.setVisibility(View.VISIBLE);
                messageViewHolder.imgMessageProfile.setVisibility(View.VISIBLE);

                messageViewHolder.txtMessageReceiver.setBackgroundResource(R.drawable.message_receiver);
                messageViewHolder.txtMessageReceiver.setTextColor(Color.BLACK);
                messageViewHolder.txtMessageReceiver.setGravity(Gravity.LEFT);
                messageViewHolder.txtMessageReceiver.setText(messages.getMessage());
            }

        }

    }

    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }
}
