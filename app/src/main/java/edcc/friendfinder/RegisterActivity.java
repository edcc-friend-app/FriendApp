package edcc.friendfinder;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

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
    private StorageReference filePath;

    private String currentUserID;

    private Uri resultUri;

    final static int GALLERY_PIC = 1;

    private int confirm = 0;



    private UserManager um;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference().child("Users");
        userProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");

        initializeComponents();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_PIC);
            }
        });
//        usersRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    if (dataSnapshot.hasChild("profile_image")) {
//                        //String image = dataSnapshot.child("profile_image").getValue().toString();
//                        //Picasso.get().load(image).placeholder(R.drawable.user_icon).into(imgProfile);
//
//                    } else {
//                        Toast.makeText(RegisterActivity.this, "Please select profile image first", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

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
        } else if(resultUri == null) {
            Toast.makeText(this, "Please Select an Image first", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        //Uncomment to add email verification
                        //sendEmailVerificationMessage();
                        //currentUserID = mAuth.getCurrentUser().getUid();
                        createUser(firstName, lastName, language, major, bio, availability);
                    } else {
                        String message = task.getException().getMessage();
                        Toast.makeText(RegisterActivity.this, "Error Occurred: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            if (confirm == 1) {
                sendUserToMainActivity();
            }

            //Will test later
            //User thisUser = new User(firstName, lastName, major, bio, language, availability);
            //um.setThisUser(thisUser);
            //sendUserToMainActivity();
        }


    }

    private void createUser(String firstName, String lastName, String language, String major, String bio, String availability) {
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        HashMap userMap = new HashMap<>();
        userMap.put("first_name", firstName);
        userMap.put("last_name", lastName);
        userMap.put("language", language);
        userMap.put("major", major);
        userMap.put("bio", bio);
        userMap.put("availability", availability);
        userMap.put("uid", currentUserID);
        usersRef.child(currentUserID).updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    // sendUserToMainActivity();
                    confirm = 1;
                    Toast.makeText(RegisterActivity.this, "Your account was created!", Toast.LENGTH_SHORT).show();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText(RegisterActivity.this, "Error occurred: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploadProfilePicture();
    }

    private void uploadProfilePicture() {
        filePath = userProfileImageRef.child(currentUserID + ".jpg");
        filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    String downloadUrl;
                    Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    downloadUrl = uriTask.getResult().toString();
                    usersRef.child(currentUserID).child("profile_image").setValue(downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                sendUserToMainActivity();
                                Toast.makeText(RegisterActivity.this, "IT IS WORKING!!", Toast.LENGTH_SHORT).show();
                            } else {
                                String message = task.getException().getMessage();
                                Toast.makeText(RegisterActivity.this, "Error occurred: " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "THIS DOES NOT WORK", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PIC && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1, 1).start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imgProfile.setImageURI(resultUri);
            } else {
                Toast.makeText(RegisterActivity.this, "Error occurred: image can't be cropped, dumbass!", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
