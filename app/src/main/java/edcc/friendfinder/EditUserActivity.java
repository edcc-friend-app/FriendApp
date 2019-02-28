package edcc.friendfinder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EditUserActivity extends AppCompatActivity {

    private UserManager um;
    private ImageButton ibtnUserPhoto;
    private EditText txtFirstName;
    private EditText txtLastName;
    private Spinner spnMajor;
    private Spinner spnLanguage;
    private Spinner spnClasses;
    private EditText txtBio;
    private boolean photoChanged;
    private Bitmap newPhoto;
    private int petId;
    private User thisUser;
    private int studentId;
    private int clientId;
    private List<User> friendList = new ArrayList<>();
    private ArrayAdapter<User> vetAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ibtnUserPhoto = findViewById(R.id.ibtnUserPhoto);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        } else if (!photoChanged && petId >= 0) {
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
        //if new, add to pet list
        if (studentId < 0) {
            um.addUser(p);
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

//package zuvichl.petsitter;
//
///**
// * Activity class for edit pet screen.
// *
// * @author Linda Zuvich
// * @version 12/21/2018
// */
//public class EditPetActivity extends BaseActivity {
//
//    //fields
//    private boolean vetChanged;
//    private boolean clientChanged;
//    private Spinner spnVet;
//    private Spinner spnClient;
//    private RadioButton rbtnIntactMale;
//    private RadioButton rbtnNeuteredMale;
//    private RadioButton rbtnIntactFemale;
//    private RadioButton rbtnSpayedFemale;
//    private EditText txtCareInstructions;
//    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private EventListener<DocumentSnapshot> petDataListener;
//    private EventListener<QuerySnapshot> vetDataListener;
//    private EventListener<QuerySnapshot> clientDataListener;
//    private ListenerRegistration petReg;
//    private ListenerRegistration vetReg;
//    private ListenerRegistration clientReg;
//
//    /**
//     * Android onCreate method.
//     *
//     * @param savedInstanceState the class state
//     */
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_pet);
//        //create action bar and back arrow
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        ibtnPet = findViewById(R.id.ibtnPet);
//        //get saved state
//        if (savedInstanceState != null) {
//            photoChanged = savedInstanceState.getBoolean("photoChanged");
//            newPhoto = savedInstanceState.getParcelable("newPhoto");
//            vetChanged = savedInstanceState.getBoolean("vetChanged");
//            vetId = savedInstanceState.getInt("vetId", -1);
//            clientChanged = savedInstanceState.getBoolean("clientChanged");
//            clientId = savedInstanceState.getInt("clientId", -1);
//        }
//        //get UI components
//        rbtnIntactMale = findViewById(R.id.rbtnIntactMale);
//        rbtnNeuteredMale = findViewById(R.id.rbtnNeuteredMale);
//        rbtnIntactFemale = findViewById(R.id.rbtnIntactFemale);
//        rbtnSpayedFemale = findViewById(R.id.rbtnSpayedFemale);
//        txtName = findViewById(R.id.txtName);
//        txtBirthYear = findViewById(R.id.txtBirthYear);
//        txtSpeciesBreed = findViewById(R.id.txtSpeciesBreed);
//        txtCareInstructions = findViewById(R.id.txtCareInstructions);
//        //get pet info
//        Intent intent = getIntent();
//        petId = intent.getIntExtra(Extras.PET_ID, -1);
//        if (petId >= 0) {
//            getSupportActionBar().setTitle(getResources().getString(R.string.titleEditPet));
//        } else {
//            getSupportActionBar().setTitle(getResources().getString(R.string.titleAddPet));
//        }
//        ibtnPet.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                new AlertDialog.Builder(EditPetActivity.this)
//                        .setTitle("Confirm")
//                        .setMessage("Are you sure you want to delete this photo?")
//                        .setIcon(android.R.drawable.ic_dialog_alert)
//                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ibtnPet.setImageResource(R.drawable.camera);
//                                ibtnPet.setScaleType(ImageView.ScaleType.CENTER);
//                                photoChanged = true;
//                                newPhoto = null;
//                            }
//                        })
//                        .setNegativeButton(android.R.string.no, null)
//                        .show();
//                return true;
//            }
//        });
//        //vet
//        spnVet = findViewById(R.id.spnVet);
//        vetAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, vetList);
//        spnVet.setAdapter(vetAdapter);
//        spnVet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                vetChanged = true;
//                vetId = ((Vet) spnVet.getItemAtPosition(position)).getVetId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//        //client
//        spnClient = findViewById(R.id.spnClient);
//        clientAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, clientList);
//        spnClient.setAdapter(clientAdapter);
//        spnClient.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                clientChanged = true;
//                clientId = ((Client) spnClient.getItemAtPosition(position)).getClientId();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
//    }
//
//    /**
//     * Removes the database listeners on pause.
//     */
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (petReg != null && petDataListener != null) {
//            petReg.remove();
//        }
//        if (vetReg != null && vetDataListener != null) {
//            vetReg.remove();
//        }
//        if (clientReg != null && clientDataListener != null) {
//            clientReg.remove();
//        }
//    }
//
//    /**
//     * On resume, reset the vet and client spinners.
//     */
//    @Override
//    protected void onResume() {
//        super.onResume();
//        vetAdapter.clear();
//        vetAdapter.addAll(vetList);
//        clientAdapter.clear();
//        clientAdapter.addAll(clientList);
//        if (dm != null) {
//            if (petId >= 0) {
//                int index = vetList.indexOf(dm.getVet(thisPet.getVetId()));
//                spnVet.setSelection(index);
//                index = clientList.indexOf(dm.getClient(thisPet.getClientId()));
//                spnClient.setSelection(index);
//            }
//            if (vetChanged) {
//                Vet v = dm.getVet(vetId);
//                int index = vetList.indexOf(v);
//                spnVet.setSelection(index);
//            }
//            if (clientChanged) {
//                Client c = dm.getClient(clientId);
//                int index = clientList.indexOf(c);
//                spnClient.setSelection(index);
//            }
//        }
//    }
//
//    /**
//     * Saves the class state.
//     *
//     * @param outState the class state
//     */
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putBoolean("photoChanged", photoChanged);
//        if (photoChanged) {
//            outState.putParcelable("newPhoto", newPhoto);
//        }
//        outState.putBoolean("vetChanged", vetChanged);
//        if (vetChanged) {
//            outState.putInt("vetId", vetId);
//        }
//        outState.putBoolean("clientChanged", clientChanged);
//        if (clientChanged) {
//            outState.putInt("clientId", clientId);
//        }
//    }
//
//

//
//    /**
//     * Event handler for the photo button click event.
//     *
//     * @param view the photo button
//     */
//    public void ibtnPetOnClick(View view) {
//        dispatchTakePictureIntent();
//    }
//
//    /**
//     * Sends control to a camera app to get a thumbnail.
//     */
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, Extras.REQUEST_IMAGE_CAPTURE);
//        }
//    }
//
//    /**
//     * Callback method that listens for results from intents with results.
//     *
//     * @param requestCode the code sent to identify the reply
//     * @param resultCode  the code that indicates if the result is ok
//     * @param data        the result intent that holds the data
//     */
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Extras.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            if (extras != null) {
//                newPhoto = (Bitmap) extras.get("data");
//                photoChanged = true;
//                ibtnPet.setImageBitmap(newPhoto);
//                ibtnPet.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            }
//        } else if (requestCode == Extras.REQUEST_VET && resultCode == RESULT_OK) {
//            vetId = data.getIntExtra(Extras.VET_ID, -1);
//            vetChanged = true;
//        } else if (requestCode == Extras.REQUEST_CLIENT && resultCode == RESULT_OK) {
//            clientId = data.getIntExtra(Extras.CLIENT_ID, -1);
//            clientChanged = true;
//        }
//    }
//
//    /**
//     * Sets up data listeners for the pet object and the client and vet lists after
//     * authentication is completed.
//     */
//    @Override
//    public void setUpDataListeners() {
//        dm = DataManager.getDataManager(getApplicationContext(), userId);
//        vetList = dm.getVetList();
//        clientList = dm.getClientList();
//        final DocumentReference petRef = db.collection("users").document(userId).
//                collection("pets").document(String.valueOf(petId));
//        if (petId >= 0) {
//            petDataListener = new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
//                    if (e != null) {
//                        Log.w("MYLOG", "Pet listener failed.", e);
//                        return;
//                    }
//                    if (snapshot != null && snapshot.exists()) {
//                        thisPet = snapshot.toObject(Pet.class);
//                        dm.setPet(thisPet);
//                        populateFields();
//                    }
//                }
//            };
//            petReg = petRef.addSnapshotListener(petDataListener);
//        }
//        //vet listener
//        final CollectionReference vetRef = db.collection("users").document(userId)
//                .collection("vets");
//        vetDataListener = new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
//                    vetList.clear();
//                    for (int i = 0; i < documentSnapshots.size(); i++) {
//                        DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(i);
//                        Vet vet = snapshot.toObject(Vet.class);
//                        vetList.add(vet);
//                    }
//                    dm.setVetList(vetList);
//                    vetAdapter.clear();
//                    vetAdapter.addAll(vetList);
//                    if (vetChanged) {
//                        Vet v = dm.getVet(vetId);
//                        int index = vetList.indexOf(v);
//                        spnVet.setSelection(index);
//                    } else {
//                        if (thisPet != null && thisPet.getVetId() > -1) {
//                            int index = vetList.indexOf(dm.getVet(thisPet.getVetId()));
//                            spnVet.setSelection(index);
//                        }
//                    }
//                }
//            }
//        };
//        vetReg = vetRef.addSnapshotListener(vetDataListener);
//        //client listener
//        final CollectionReference clientRef = db.collection("users").document(userId)
//                .collection("clients");
//        clientDataListener = new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
//                    ArrayList<Client> list = new ArrayList<>();
//                    for (int i = 0; i < documentSnapshots.size(); i++) {
//                        DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(i);
//                        Client client = snapshot.toObject(Client.class);
//                        list.add(client);
//                    }
//                    dm.setClientList(list);
//                    clientAdapter.clear();
//                    clientAdapter.addAll(clientList);
//                    if (clientChanged) {
//                        Client c = dm.getClient(clientId);
//                        int index = clientList.indexOf(c);
//                        spnClient.setSelection(index);
//                    } else {
//                        if (thisPet != null && thisPet.getClientId() > -1) {
//                            int index = clientList.indexOf(dm.getClient(thisPet.getClientId()));
//                            spnClient.setSelection(index);
//                        }
//                    }
//                }
//            }
//        };
//        clientReg = clientRef.addSnapshotListener(clientDataListener);
//    }
//
//    /**
//     * Event handler to add a new vet.
//     *
//     * @param view the add vet button
//     */
//    public void btnAddVetOnClick(View view) {
//        Intent intent = new Intent(this, EditVetActivity.class);
//        startActivityForResult(intent, Extras.REQUEST_VET);
//    }
//
//    /**
//     * Event handler to add a new client.
//     *
//     * @param view the add client button
//     */
//    public void btnAddClientOnClick(View view) {
//        Intent intent = new Intent(this, EditClientActivity.class);
//        startActivityForResult(intent, Extras.REQUEST_CLIENT);
//    }
//
//    /**
//     * Fills the UI fields with the proper pet data.
//     */
//    private void populateFields() {
//        //name
//        EditText txtName = findViewById(R.id.txtName);
//        txtName.setText(thisPet.getName());
//        //year of birth
//        EditText txtYearOfBirth = findViewById(R.id.txtBirthYear);
//        txtYearOfBirth.setText(String.valueOf(thisPet.getBirthYear()));
//        //gender
//        int gender = thisPet.getGender();
//        switch (gender) {
//            case Pet.INTACT_MALE:
//                rbtnIntactMale.setChecked(true);
//                break;
//            case Pet.NEUTERED_MALE:
//                rbtnNeuteredMale.setChecked(true);
//                break;
//            case Pet.INTACT_FEMALE:
//                rbtnIntactFemale.setChecked(true);
//                break;
//            case Pet.SPAYED_FEMALE:
//                rbtnSpayedFemale.setChecked(true);
//                break;
//            default:
//                break;
//        }
//        //species/breed
//        EditText txtSpeciesBreed = findViewById(R.id.txtSpeciesBreed);
//        txtSpeciesBreed.setText(thisPet.getType());
//        //care
//        EditText txtCareInstructions = findViewById(R.id.txtCareInstructions);
//        txtCareInstructions.setText(thisPet.getCare());
//        //photo
//        if (photoChanged && newPhoto != null) {
//            ibtnPet.setImageBitmap(newPhoto);
//        } else if (!photoChanged && petId >= 0) {
//            String photoStr = thisPet.getPhoto();
//            if (photoStr != null) {
//                byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
//                ibtnPet.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
//            }
//        } else {
//            ibtnPet.setImageResource(R.drawable.camera);
//        }
//        ibtnPet.setScaleType(ImageView.ScaleType.FIT_CENTER);
//    }
//
//}