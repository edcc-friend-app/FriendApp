package edcc.friendfinder;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private View rootView;
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
    private int userId;
    static final int REQUEST_IMAGE_CAPTURE = 1;


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
        //imgUser = rootView.findViewById(R.id.imgUserPhoto);
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
}
