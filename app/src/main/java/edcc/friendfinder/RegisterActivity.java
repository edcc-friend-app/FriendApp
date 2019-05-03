package edcc.friendfinder;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.opencensus.trace.unsafe.ContextUtils;

public class RegisterActivity extends AppCompatActivity {

    private CircleImageView imgProfile;
    private EditText txtEmail, txtFirstName, txtLastName, txtMajor, txtBio, txtLanguage, txtAvailability
            , txtPassword, txtConfirmPassword;
    private Button btnSignUp;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;
    private StorageReference userProfileImageRef;

    private String currentUserID;

    private UserManager um;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        initializeComponents();

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    private void signUp() {

        String email = txtEmail.getText().toString();
        final String firstName = txtFirstName.getText().toString();
        final String lastName = txtLastName.getText().toString();
        final String major = txtMajor.getText().toString();
        final String bio = txtBio.getText().toString();
        final String language = txtLanguage.getText().toString();
        final String availability = txtAvailability.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Please enter your email");
        } else if(TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Please enter your Last Name", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(major)) {
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(bio)) {
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(language)) {
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(availability)) {
            Toast.makeText(this, "Please enter your First Name", Toast.LENGTH_SHORT).show();
        } else if(TextUtils.isEmpty(password)) {
            txtPassword.setError("Please enter your password");
        } else if(TextUtils.isEmpty(confirmPassword)) {
            txtConfirmPassword.setError("Please enter your password");
        } else if (!password.equals(confirmPassword)) {
            txtConfirmPassword.setError("Passwords do not match!");
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Uncomment to add email verification
                        //sendEmailVerificationMessage();
                        //currentUserID = mAuth.getCurrentUser().getUid();
                        Toast.makeText(RegisterActivity.this, "SUCCESS!!!!!!", Toast.LENGTH_SHORT).show();
                        createUser(firstName, lastName, language, major, bio, availability);
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(RegisterActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            currentUserID = mAuth.getCurrentUser().getUid();
//
//            HashMap userMap = new HashMap();
//            userMap.put("first_name", firstName);
//            userMap.put("last_name", lastName);
//            userMap.put("language", language);
//            userMap.put("major", major);
//            userMap.put("bio", bio);
//            userMap.put("availability", availability);
//            userMap.put("photo", "photo");
//            userMap.put("uid", currentUserID);
//            usersRef.child(currentUserID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
//                @Override
//                public void onComplete(@NonNull Task task) {
//                    if(task.isSuccessful()) {
//                        sendUserToMainActivity();
//                        Toast.makeText(RegisterActivity.this, "your account was created!", Toast.LENGTH_SHORT).show();
//                    } else {
//                        String message = task.getException().getMessage();
//                        Toast.makeText(RegisterActivity.this, "Error occurred: " + message, Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });

            //Will test later
            //User thisUser = new User(firstName, lastName, major, bio, language, availability);
            //um.setThisUser(thisUser);
            //sendUserToMainActivity();
        }


    }

    private void createUser(String firstName, String lastName, String language, String major, String bio, String availability) {
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();

        HashMap userMap = new HashMap();
        userMap.put("first_name", firstName);
        userMap.put("last_name", lastName);
        userMap.put("language", language);
        userMap.put("major", major);
        userMap.put("bio", bio);
        userMap.put("availability", availability);
        userMap.put("photo", "photo");
        userMap.put("uid", currentUserID);
        usersRef.child(currentUserID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()) {
                    sendUserToMainActivity();
                    Toast.makeText(RegisterActivity.this, "your account was created!", Toast.LENGTH_SHORT).show();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(RegisterActivity.this, "Error occurred: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void initializeComponents() {
        um = UserManager.getUserManager(this, currentUserID);

        imgProfile = findViewById(R.id.imgRegisterPhoto);
        txtEmail = findViewById(R.id.txtRegisterEmail);
        txtFirstName = findViewById(R.id.txtRegisterFirstName);
        txtLastName = findViewById(R.id.txtRegisterLastName);
        txtMajor = findViewById(R.id.txtRegisterMajor);
        txtBio = findViewById(R.id.txtRegisterBio);
        txtLanguage = findViewById(R.id.txtRegisterLanguage);
        txtAvailability = findViewById(R.id.txtRegisterAvailability);

        txtPassword = findViewById(R.id.txtRegisterPassword);
        txtConfirmPassword = findViewById(R.id.txtRegisterConfirmPassword);

        btnSignUp = findViewById(R.id.btnSignUp);


    }
}
