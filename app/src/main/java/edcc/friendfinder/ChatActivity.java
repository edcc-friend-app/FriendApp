package edcc.friendfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private Toolbar chatToolbar;
    private ImageButton btnSendMessage, btnSendImageFile;
    private EditText txtUserMessage;
    private RecyclerView lstMessages;

    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager manager;
    private MessagesAdapter adapter;

    private String messageReceiverID, messageReceiverName, messageSenderID, saveCurrentDate, saveCurrentTime;

    private TextView receiverName, userLastSeen;
    private CircleImageView receiverProfileImage;

    private DatabaseReference rootRef;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mAuth = FirebaseAuth.getInstance();

        messageSenderID = mAuth.getCurrentUser().getUid();


        rootRef = FirebaseDatabase.getInstance().getReference();

        messageReceiverID = getIntent().getStringExtra("friendID");
        messageReceiverName = getIntent().getStringExtra("userName");


        initializeFields();

        displayReceiverInfo();

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        fetchMessages();
    }

    private void initializeFields() {
//        chatToolbar = findViewById(R.id.chat_bar_layout);
//        setSupportActionBar(chatToolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = inflater.inflate(R.layout.chat_custom_bar, null);
        actionBar.setCustomView(action_bar_view);


        receiverName = findViewById(R.id.custom_profile_name);
        receiverProfileImage = findViewById(R.id.custom_profile_image);

        userLastSeen = findViewById(R.id.custom_user_last_seen);

        btnSendMessage = findViewById(R.id.btnSendMessage);
        btnSendImageFile = findViewById(R.id.btnSendImage);

        txtUserMessage = findViewById(R.id.txtMessage);

        adapter = new MessagesAdapter(messagesList);
        lstMessages = findViewById(R.id.lstMessages);
        manager = new LinearLayoutManager(this);
        lstMessages.setHasFixedSize(true);
        lstMessages.setLayoutManager(manager);
        lstMessages.setAdapter(adapter);

    }

    private void fetchMessages() {
        rootRef.child("Messages").child(messageSenderID).child(messageReceiverID)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        if (dataSnapshot.exists()) {
                            Messages message = dataSnapshot.getValue(Messages.class);
                            messagesList.add(message);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void sendMessage() {
        //updateUserStatus("online");

        String message = txtUserMessage.getText().toString();
        if (TextUtils.isEmpty(message)) {
            Toast.makeText(this, "Please type a message first", Toast.LENGTH_SHORT).show();
        } else {
            String message_sender_ref = "Messages/" + messageSenderID + "/" + messageReceiverID;
            String message_receiver_ref = "Messages/" + messageReceiverID + "/" + messageSenderID;

            DatabaseReference user_message_key = rootRef.child("Messages").child(messageSenderID).child(messageReceiverID)
                    .push();
            String message_push_id = user_message_key.getKey();

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat date = new SimpleDateFormat("MMMM-dd-yyyy");
            saveCurrentDate = date.format(calendar.getTime());

            SimpleDateFormat time = new SimpleDateFormat("HH:mm aa");
            saveCurrentTime = time.format(calendar.getTime());

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", message);
            messageTextBody.put("time", saveCurrentTime);
            messageTextBody.put("date", saveCurrentDate);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(message_sender_ref + "/" + message_push_id, messageTextBody);
            messageBodyDetails.put(message_receiver_ref + "/" + message_push_id, messageTextBody);

            rootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ChatActivity.this, "Message Sent Successfully", Toast.LENGTH_SHORT).show();
                        txtUserMessage.setText("");
                    } else {
                        Toast.makeText(ChatActivity.this, "Error Occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }


                }
            });


        }

    }

    private void updateUserStatus(String state) {
        String saveCurrentDate, saveCurrentTime;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM/dd/yy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        Map currentStateMap = new HashMap();

        currentStateMap.put("time", saveCurrentTime);
        currentStateMap.put("date", saveCurrentDate);
        currentStateMap.put("type", state);

        rootRef.child("Users").child(messageSenderID).child("userstate")
                .updateChildren(currentStateMap);

    }

    private void displayReceiverInfo() {
        receiverName.setText(messageReceiverName);

        rootRef.child("Users").child(messageReceiverID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String profileImage = dataSnapshot.child("profile_image").getValue().toString();
                   // String type = dataSnapshot.child("userstate").child("type").getValue().toString();
                    //String date = dataSnapshot.child("userstate").child("date").getValue().toString();
                    //String time = dataSnapshot.child("userstate").child("time").getValue().toString();

//                    if (type.equals("online")) {
//                        userLastSeen.setText("online");
//                    } else {
//                        userLastSeen.setText("last seen: " + time + "  " + date);
//                    }


                    Picasso.get().load(profileImage).placeholder(R.mipmap.ic_launcher_round).into(receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
