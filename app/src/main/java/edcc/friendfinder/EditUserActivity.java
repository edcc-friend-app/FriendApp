package edcc.friendfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;

public class EditUserActivity extends AppCompatActivity {

    private UserManager um;
    private ImageButton ibtnUserPhoto;
    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtBio;
    private EditText txtAvailability;
    private User courseList;
    private Spinner spnMajor;
    private Spinner spnLanguage;
    private Spinner spn1Classes;
    private Spinner spn2Classes;
    private Spinner spn3Classes;
    private ImageButton getIbtnUserPhoto;
    private boolean photoChanged;
    private boolean class1Changed;
    private boolean class2Changed;
    private boolean class3Changed;
    private boolean availabilityChanged;
    private boolean majorChanged;
    private boolean languageChanged;
    private int class1Id;
    private int class2Id;
    private int class3Id;
    private int majorId;
    private int languageId;
    private Bitmap newPhoto;
    private User thisUser;
    private int studentId;
    private int userId;
//    private List<User> friendList = new ArrayList<>();
//    private ArrayAdapter<User> classAdapter;

//    private List<Language> languageList = new ArrayList<>();
    private Classes classes;
    private ArrayAdapter<String> classesAdapter;
    //    private List<Classes> classesList = new ArrayList<>();
    private Major majors;
    private ArrayAdapter<String> majorAdapter;
//    private List<Major> majorList = new ArrayList<>();
    private Language languages;
    private ArrayAdapter<String> languageAdapter;
    //    private List<Language> languageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ibtnUserPhoto = findViewById(R.id.ibtnUserPhoto);


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState != null) {
            photoChanged = savedInstanceState.getBoolean("photoChanged");
            newPhoto = savedInstanceState.getParcelable("newPhoto");

            class1Changed = savedInstanceState.getBoolean("class1Changed");
            class1Id = savedInstanceState.getInt("class1Id", -1);
            class2Changed = savedInstanceState.getBoolean("class2Changed");
            class2Id = savedInstanceState.getInt("class2Id", -1);
            class3Changed = savedInstanceState.getBoolean("class3Changed");
            class3Id = savedInstanceState.getInt("class3Id", -1);
            availabilityChanged = savedInstanceState.getBoolean("availabilityChanged");
            majorChanged = savedInstanceState.getBoolean("majorChanged");
            majorId = savedInstanceState.getInt("majorId", -1);
            languageChanged = savedInstanceState.getBoolean("languageChanged");
            languageId = savedInstanceState.getInt("languageId", -1);

            //studentId = savedInstanceState.getInt("studentId", -1);
        }
        txtLastName = findViewById(R.id.txtLastName);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtBio = findViewById(R.id.lblBio);
        txtAvailability = findViewById(R.id.txtAvailable);
//        spnLanguage = findViewById(R.id.spnLanguage);
//        spnMajor = findViewById(R.id.spnMajor);

        //get user info
        Intent intent = getIntent();
        userId = intent.getIntExtra(Extras.USER_ID, -1);
        if (userId >= 0) {
            getSupportActionBar().setTitle("Edit Profile");
        } else {
            getSupportActionBar().setTitle("Create Profile");
        }
        ibtnUserPhoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(EditUserActivity.this)
                        .setTitle("Confirm")
                        .setMessage("Are you sure you want to delete this photo?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ibtnUserPhoto.setImageResource(R.drawable.ic_menu_camera);
                                ibtnUserPhoto.setScaleType(ImageView.ScaleType.CENTER);
                                photoChanged = true;
                                newPhoto = null;
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                return true;
            }
        });
        // course 1
        spn1Classes = findViewById(R.id.spnClass1);
        classesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes.getCourseList());
        spn1Classes.setAdapter(classesAdapter);
        spn1Classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                class1Changed = true;
                //Id = ((Classes) spn1Classes.getItemAtPosition(position)).getVetId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // course 2
        spn2Classes = findViewById(R.id.spnClass2);
        classesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes.getCourseList());
        spn2Classes.setAdapter(classesAdapter);
        spn2Classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                class2Changed = true;
                //Id = ((Classes) spn1Classes.getItemAtPosition(position)).getVetId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // course 3
        spn3Classes = findViewById(R.id.spnClass3);
        classesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, classes.getCourseList());
        spn3Classes.setAdapter(classesAdapter);
        spn3Classes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                class3Changed = true;
                //Id = ((Classes) spn1Classes.getItemAtPosition(position)).getVetId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // language
        spnLanguage = findViewById(R.id.spnLanguage);
        languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, languages.getLanguageList());
        spnLanguage.setAdapter(languageAdapter);
        spnLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                languageChanged = true;
                //Id = ((Classes) spn1Classes.getItemAtPosition(position)).getVetId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // major
        spnMajor = findViewById(R.id.spnMajor);
        majorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, majors.getMajorList());
        spnMajor.setAdapter(majorAdapter);
        spnMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorChanged = true;
                //Id = ((Classes) spn1Classes.getItemAtPosition(position)).getVetId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
        getMenuInflater().inflate(R.menu.menu_done, menu);
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
                //signOut();
                return true;
            case R.id.action_done:
                saveUser();
                return true;
            case android.R.id.home:
                //back arrow
                finish();
                return true;
            case R.id.action_settings:
                //settings menu option
//                Intent intent = new Intent(this, PreferencesActivity.class);
//                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Save the current user.
     */
    private void saveUser() {
        //make sure important fields are filled
        String entry = txtFirstName.getText().toString();
        if (TextUtils.isEmpty(entry)) {
            txtFirstName.setError("Name is required.");
            return;
        }
        String entryLast = txtLastName.getText().toString();
        if (TextUtils.isEmpty(entryLast)) {
            txtLastName.setError("Last name is required.");
        }
//        String language = spnLanguage.getSelectedItem().toString();
//        if (spnLanguage != null && spnLanguage.getSelectedItem() !=null) {
//            spnLanguage.setTooltipText("Please selected a prefered language.");
//            return;
//        }
//        String major = spnMajor.getSelectedItem().toString();
//        if (spnMajor != null && spnMajor.getSelectedItem() !=null) {
//            spnMajor.setTooltipText("Please select a desired major.");
//            return;
//        }
        //set up user object
        User p = new User();
        //set user fields
        p.setFirstName(txtFirstName.getText().toString());
        p.setLastName(txtLastName.getText().toString());
        p.setLanguage(spnLanguage.getSelectedItem().toString());
        p.setMajor(spnMajor.getSelectedItem().toString());


        if (photoChanged && newPhoto != null) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            newPhoto.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] photo = bos.toByteArray();
            p.setPhoto(Base64.encodeToString(photo, Base64.DEFAULT));
        } else if (!photoChanged) {  //!photoChanged && petId >= 0
            p.setPhoto(um.getUser(studentId).getPhoto());
        } else {
            p.setPhoto(null);
        }
        p.setBio(txtBio.getText().toString());
//        if (spnClient.getAdapter().getCount() > 0) {
//            p.setClientId(((Client) spnClient.getSelectedItem()).getClientId());
//        } else {
//            p.setClientId(-1);
//        }
//        if (spnVet.getAdapter().getCount() > 0) {
//            p.setVetId(((Vet) spnVet.getSelectedItem()).getVetId());
//        } else {
//            p.setVetId(-1);
//        }
        //if new, add to list
        if (studentId < 0) {
            um.addFriend(p);
        } else {
            p.setId(studentId);
            //um.replaceUser(p);
        }
        //close
        finish();
    }

    /**
     * Event handler for the photo button click event.
     *
     * @param view the photo button
     */
    public void ibtnUserPhotoOnClick(View view) {
        dispatchTakePictureIntent();
    }

    /**
     * Sends control to a camera app to get a thumbnail.
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, Extras.REQUEST_IMAGE_CAPTURE);
        }
    }

}