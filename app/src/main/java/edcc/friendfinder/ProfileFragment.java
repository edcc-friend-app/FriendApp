package edcc.friendfinder;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View rootView;
    private User thisUser;
    private ProfileListener listener;
    private UserManager um;
    private TextView lblName;
    private TextView lblMajor;
    private TextView lblClasses;
    private TextView lblBio;
    private TextView studentId;
    private ImageView imgUser;
    private PreferencesManager pm;
    //private final FirebaseFirestore db = FirebaseFirestore.getInstance();
//    private EventListener<DocumentSnapshot> petDataListener;
//    private EventListener<QuerySnapshot> vetDataListener;
//    private EventListener<QuerySnapshot> clientDataListener;
//    private ListenerRegistration petReg;
//    private ListenerRegistration vetReg;
//    private ListenerRegistration clientReg;


    public ProfileFragment() {
        // Required empty public constructor
    }

    interface ProfileListener {
        void editUser(User current);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        um = UserManager.getUserManager();
        pm = PreferencesManager.getInstance(getActivity().getApplicationContext());
        //find UI components
        lblName = rootView.findViewById(R.id.lblName);
        lblMajor = rootView.findViewById(R.id.lblMajor);
        lblClasses = rootView.findViewById(R.id.lblClasses);
        lblBio = rootView.findViewById(R.id.lblBio);
        imgUser = rootView.findViewById(R.id.imgUserPhoto);
        //set UI components
        thisUser = um.getThisUser();
        lblName.setText(thisUser.toString());
        lblMajor.setText(thisUser.getMajor());
        lblClasses.setText(thisUser.printClasses());
        lblBio.setText(thisUser.getBio());
        String photoStr = thisUser.getPhoto();
        if (photoStr != null) {
            byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
            imgUser.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
            imgUser.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            imgUser.setImageBitmap(null);
        }
        //added for mock-up user
        //imgUser.setImageDrawable(rootView.getResources().getDrawable(R.drawable.user_icon));
        return rootView;
    }

//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        pm = PreferencesManager.getInstance(getApplicationContext());
//        setContentView(R.layout.activity_pet_details);
//        //create action bar and back arrow
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        //get current pet
//        Intent intent = getIntent();
//        petId = intent.getIntExtra(Extras.PET_ID, -1);
//        if (petId < 0) {
//            finish();
//        }
//    }

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
                System.out.println("hey");
                //signOut();
                return true;
            case R.id.action_edit:
                listener.editUser(um.getUsers().get(0));
                return true;
            case R.id.action_settings:
                //Intent intent = new Intent(this, PreferencesActivity.class);
                //startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
        @Override
        public void onAttach (Context context){
            super.onAttach(context);
            if (context instanceof ProfileFragment.ProfileListener) {
                this.listener = (ProfileFragment.ProfileListener) context;
            }
        }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit, menu);
    }


//    /**
//     * Connects up the data listeners once authentication is completed in the BaseActivity.
//     * The pet listener is listening for changes in this particular pet entry.
//     */
//    @Override
//    public void setUpDataListeners() {
//        dm = DataManager.getDataManager(getApplicationContext(), userId);
//        //pet listener
//        final DocumentReference petRef = db.collection("users").document(userId).
//                collection("pets").document(String.valueOf(petId));
//        petDataListener = new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.w("MYLOG", "Pet listener failed.", e);
//                    return;
//                }
//                if (snapshot != null && snapshot.exists()) {
//                    thisPet = snapshot.toObject(Pet.class);
//                    dm.setPet(thisPet);
//                    lblName.setText(thisPet.getName());
//                    lblBirthYear.setText(String.valueOf(thisPet.getBirthYear()));
//                    String[] genderArray = getResources().getStringArray(R.array.arrGenders);
//                    lblGender.setText(genderArray[thisPet.getGender()]);
//                    lblSpeciesBreed.setText(thisPet.getType());
//                    lblCare.setText(thisPet.getCare());
//                    if (thisPet.getClientId() > -1 && dm.getClientList().size() > 0 &&
//                            dm.getClient(thisPet.getClientId()) != null) {
//                        lblClient.setText(dm.getClient(thisPet.getClientId()).toString());
//                    }
//                    if (thisPet.getVetId() > -1 && dm.getVetList().size() > 0 &&
//                            dm.getVet(thisPet.getVetId()) != null) {
//                        lblVet.setText(dm.getVet(thisPet.getVetId()).toString());
//                    }
//                    String photoStr = thisPet.getPhoto();
//                    imgPet = findViewById(R.id.imgPet);
//                    if (photoStr != null) {
//                        byte[] photo = Base64.decode(photoStr, Base64.DEFAULT);
//                        imgPet.setImageBitmap(BitmapFactory.decodeByteArray(photo, 0, photo.length));
//                        imgPet.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                    } else {
//                        imgPet.setImageBitmap(null);
//                    }
//                }
//            }
//        };
//        petReg = petRef.addSnapshotListener(petDataListener);
//        //vet listener
//        final CollectionReference vetRef = db.collection("users").document(userId)
//                .collection("vets");
//        vetDataListener = new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
//                if (documentSnapshots != null && !documentSnapshots.isEmpty()) {
//                    ArrayList<Vet> list = new ArrayList<>();
//                    for (int i = 0; i < documentSnapshots.size(); i++) {
//                        DocumentSnapshot snapshot = documentSnapshots.getDocuments().get(i);
//                        Vet vet = snapshot.toObject(Vet.class);
//                        list.add(vet);
//                    }
//                    dm.setVetList(list);
//                    if (thisPet != null && thisPet.getVetId() >= 0) {
//                        lblVet.setText(dm.getVet(thisPet.getVetId()).toString());
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
//                    if (thisPet != null && thisPet.getClientId() >= 0) {
//                        Client client;
//                        client = dm.getClient(thisPet.getClientId());
//                        if (client != null) {
//                            lblClient.setText(client.toString());
//                        }
//                    }
//                }
//            }
//        };
//        clientReg = clientRef.addSnapshotListener(clientDataListener);
//    }

}
